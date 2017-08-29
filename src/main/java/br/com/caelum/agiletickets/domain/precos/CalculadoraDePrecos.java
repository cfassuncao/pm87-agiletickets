package br.com.caelum.agiletickets.domain.precos;

import java.math.BigDecimal;

import br.com.caelum.agiletickets.models.Sessao;
import br.com.caelum.agiletickets.models.TipoDeEspetaculo;

public class CalculadoraDePrecos {
	
	private static BigDecimal cinquentaPorCento = BigDecimal.valueOf(0.50);
	private static BigDecimal cincoPorCento = BigDecimal.valueOf(0.05);
	private static BigDecimal vintePorCento = BigDecimal.valueOf(0.20);
	private static BigDecimal dezPorCento = BigDecimal.valueOf(0.10);
	
	
	public static BigDecimal calcula(Sessao sessao, Integer quantidade) {
		BigDecimal preco;
		
		if(sessao.getEspetaculo().getTipo().equals(TipoDeEspetaculo.CINEMA) || sessao.getEspetaculo().getTipo().equals(TipoDeEspetaculo.SHOW)) {
			preco = aplicarAcrescimoPercentualVendido(sessao,cincoPorCento,dezPorCento);
		} else if ((sessao.getEspetaculo().getTipo().equals(TipoDeEspetaculo.BALLET)) || sessao.getEspetaculo().getTipo().equals(TipoDeEspetaculo.ORQUESTRA )) {
			preco = aplicarAcrescimoPercentualVendido(sessao,cinquentaPorCento,vintePorCento);
			preco = aplicaAcrescimoMais60min(sessao, preco);			
		} else {
			preco = sessao.getPreco();
		} 

		return preco.multiply(BigDecimal.valueOf(quantidade));
	}

	public static BigDecimal aplicarAcrescimoPercentualVendido(Sessao sessao, BigDecimal percentualVendido, BigDecimal percentualAcrescimo) {
		BigDecimal preco;
		if((sessao.getTotalIngressos() - sessao.getIngressosReservados()) / sessao.getTotalIngressos().doubleValue() <= percentualVendido.doubleValue()) { 
			preco = sessao.getPreco().add(sessao.getPreco().multiply(percentualAcrescimo));
		} else {
			preco = sessao.getPreco();
		}				
		return preco;
	}

	public static BigDecimal aplicaAcrescimoMais60min(Sessao sessao, BigDecimal preco) {
		if(sessao.getDuracaoEmMinutos() > 60){
			preco = preco.add(sessao.getPreco().multiply(dezPorCento));
		}
		return preco;
	}
	


}