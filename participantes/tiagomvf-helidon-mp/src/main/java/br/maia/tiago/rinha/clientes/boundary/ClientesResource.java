package br.maia.tiago.rinha.clientes.boundary;

import br.maia.tiago.rinha.clientes.control.ClienteStore;
import br.maia.tiago.rinha.clientes.control.TransacoesStore;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Transactional
@Path("/clientes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClientesResource {

    @Inject
    TransacoesStore store;

    @Inject
    ClienteStore clientStore;

    public record Transacao(
        @Pattern(regexp = "[cd]")
        @NotNull
        @NotBlank
        String tipo,
        @PositiveOrZero
        @NotNull
        Integer valor,
        @Size(min = 1, max = 10)
        @NotBlank
        @NotEmpty
        @NotNull
        String descricao
    ) {}

    @POST
    @Path("/{id}/transacoes")
    public JsonObject transacoes(@PathParam("id") Integer clientId, @Valid Transacao transacao ) {
        var cliente =
            Optional.ofNullable(clientStore.findById(clientId)).orElseThrow(
                () ->  new NotFoundException("Cliente não encontrado")
            );
        var valor = transacao.valor();
        var tipo = transacao.tipo();
        var descricao = transacao.descricao();

        switch (tipo) {
            case "c":
                store.creditar(clientId, valor, descricao);
                break;
            case "d":
                store.debitar(clientId, valor, descricao);
                break;
            default:
                throw new WebApplicationException("Tipo de transação inválido");
        }
        return Json.createObjectBuilder()
            .add("limite", cliente.getLimite())
            .add("saldo", cliente.getSaldo())
            .build();
    }

    @GET
    @Path("/{id}/extrato")
    public JsonObject extrado(@PathParam("id") Integer clientId) {
        var cliente =
            Optional.ofNullable(clientStore.findById(clientId)).orElseThrow(
                () ->  new NotFoundException("Cliente não encontrado")
            );

        DateTimeFormatter dtf = DateTimeFormatter.ISO_DATE_TIME;

        List<JsonObject> list = store.extrato(clientId).stream().map(transacao -> {
            return Json.createObjectBuilder()
                .add("tipo", transacao.getTipo())
                .add("valor", transacao.getValor())
                .add("realizada_em", transacao.getRealizadaEm().format(dtf))
                .add("descricao", transacao.getDescricao())
                .build();
        }).toList();

        return Json.createObjectBuilder()
            .add("saldo",
                Json.createObjectBuilder()
                    .add("total", cliente.getSaldo())
                    .add("data_extrato",
                        dtf.format(LocalDateTime.now(ZoneId.systemDefault())))
                    .add("limite", cliente.getLimite())
                    .build())
            .add("transacoes", Json.createArrayBuilder(list).build())
            .build();
    }

}
