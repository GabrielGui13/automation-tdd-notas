package gabrielGuilhermeCarvalhoViana;

import java.math.BigDecimal;
import java.math.RoundingMode;

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
			throw new IllegalArgumentException("A nota deve estar entre 0 e 10.");
		}

		switch (numeroNota) {
			case 1:
				this.nota1 = nota;
				break;
			case 2:
				this.nota2 = nota;
				break;
			case 3:
				this.nota3 = nota;
				break;
			default:
				throw new IllegalArgumentException("Número da nota inválido");
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
			throw new IllegalArgumentException("Frequência inválida. Deve ser entre 0 e 100.");
		}
		this.frequencia = frequencia;
	}

	public Discente getDiscente() {
		return discente;
	}

	public Turma getTurma() {
		return turma;
	}

	public StatusAprovacao getStatus() {
		return status;
	}

	private void setStatus(StatusAprovacao status) {
		this.status = status;
	}

	public void consolidarParcialmente() {
		if (nota1 == null || nota2 == null || nota3 == null || frequencia == null) {
			throw new IllegalStateException("Notas ou frequência não cadastradas");
		}

		BigDecimal media = (this.nota1.add(this.nota2).add(this.nota3)).divide(new BigDecimal(3), 2, RoundingMode.HALF_UP);

		boolean temFrequenciaSuficiente = this.frequencia >= 75;

		if (!temFrequenciaSuficiente) {
			if (media.compareTo(new BigDecimal("3.0")) < 0) {
				this.setStatus(StatusAprovacao.REPMF);
			} else {
				this.setStatus(StatusAprovacao.REPF);
			}
			return;
		}

		if (media.compareTo(new BigDecimal("6.0")) >= 0) {
			boolean algumaNotaInferiorA4 = nota1.compareTo(new BigDecimal("4.0")) < 0 ||
					nota2.compareTo(new BigDecimal("4.0")) < 0 ||
					nota3.compareTo(new BigDecimal("4.0")) < 0;
			if (algumaNotaInferiorA4) {
				this.setStatus(StatusAprovacao.REC);
			} else {
				this.setStatus(StatusAprovacao.APR);
			}
		} else if (media.compareTo(new BigDecimal("3.0")) >= 0) {
			this.setStatus(StatusAprovacao.REC);
		} else {
			this.setStatus(StatusAprovacao.REP);
		}
	}
}