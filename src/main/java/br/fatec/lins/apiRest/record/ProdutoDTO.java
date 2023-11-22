package br.fatec.lins.apiRest.record;

import java.math.BigDecimal;
import java.util.UUID;

public record ProdutoDTO(String nome, String descricao, BigDecimal valor, int qtdeEstoque, int estoqueMinimo, String imagem) {

}
