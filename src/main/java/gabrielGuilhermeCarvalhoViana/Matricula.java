package gabrielGuilhermeCarvalhoViana;

import java.math.BigDecimal;

public class Matricula {
	private final Discente discente;
	
	private final Turma turma;
	
	private BigDecimal nota1;

	private BigDecimal nota2;

	private BigDecimal nota3;

	private Integer frequencia;
	
	private StatusAprovacao status;

	Matricula(Discente discente, Turma turma) {
		this.discente = discente;
		this.turma = turma;
	}

	public BigDecimal getNota1() {
		return nota1;
	}

	private void cadastrarNota(BigDecimal nota, int numeroNota) {
		if (nota.compareTo(BigDecimal.ZERO) < 0 || nota.compareTo(new BigDecimal(10)) > 0) {
			throw new IllegalArgumentException();
		}

		switch (numeroNota) {
			case 1 -> this.nota1 = nota;
			case 2 -> this.nota2 = nota;
			case 3 -> this.nota3 = nota;
			default -> throw new IllegalArgumentException("Número da nota inválido");
		}
	}

	public void cadastrarNota1(BigDecimal nota1) {
		this.cadastrarNota(nota1, 1);
	}

	public BigDecimal getNota2() {
		return nota2;
	}

	public void cadastrarNota2(BigDecimal nota2) {
		this.cadastrarNota(nota2, 2);
	}

	public BigDecimal getNota3() {
		return nota3;
	}

	public void cadastrarNota3(BigDecimal nota3) {
		this.cadastrarNota(nota3, 3);
	}

	public Integer getFrequencia() {
		return frequencia;
	}

	public void cadastrarFrequencia(Integer frequencia) {
		if (frequencia < 0 || frequencia > 100) {
			throw new IllegalArgumentException();
		}

		this.frequencia = frequencia;
	}

	public Discente getDiscente() {
		return discente;
	}

	public Turma getTurma() {
		return turma;
	}

	public void consolidarParcialmente() {
		this.setStatus(StatusAprovacao.APR);
	}

	public StatusAprovacao getStatus() {
		return status;
	}

	private void setStatus(StatusAprovacao status) {
		this.status = status;
	}
}