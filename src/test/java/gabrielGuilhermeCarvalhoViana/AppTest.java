package gabrielGuilhermeCarvalhoViana;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AppTest {
    static Discente discente;
    static Turma turma;

    Matricula matricula;

    @BeforeAll
    static void setupClass() {
        discente = new Discente();
        discente.setNome("Gabriel Viana");
        discente.setMatricula(123);

        Disciplina disciplina = new Disciplina();
        disciplina.setCodigo("DIM0507");
        disciplina.setNome("Testes de Software");

        Docente docente = new Docente();
        docente.setSiape(2025);
        docente.setNome("Eiji Adachi");

        turma = new Turma(docente, disciplina);
    }

    @BeforeEach
    void setupTest() {
        matricula = new Matricula(discente, turma);
    }

    @ParameterizedTest(name = "APR - Notas: {0},{1},{2} | Freq: {3}%")
    @CsvSource({
            "10.0, 10.0, 10.0, 100",
            "6.0, 6.0, 6.0, 75",
            "5.0, 6.0, 7.0, 80",
            "4.0, 7.0, 7.0, 90"
    })
    @DisplayName("Deve ser aprovado")
    void shouldBeApproved(double n1, double n2, double n3, int frequencia) {
        matricula.cadastrarNota1(new BigDecimal(n1));
        matricula.cadastrarNota2(new BigDecimal(n2));
        matricula.cadastrarNota3(new BigDecimal(n3));
        matricula.cadastrarFrequencia(frequencia);

        matricula.consolidarParcialmente();

        assertEquals(StatusAprovacao.APR, matricula.getStatus());
    }

    @ParameterizedTest(name = "REC - Notas: {0},{1},{2} | Freq: {3}%")
    @CsvSource({
            "3.0, 3.0, 3.0, 100",
            "5.0, 5.0, 5.0, 75",
            "5.9, 5.9, 5.9, 80",
            "10.0, 8.0, 3.0, 90"
    })
    @DisplayName("Deve ir para recuperação")
    void shouldGoToRecovery(double n1, double n2, double n3, int frequencia) {
        matricula.cadastrarNota1(new BigDecimal(n1));
        matricula.cadastrarNota2(new BigDecimal(n2));
        matricula.cadastrarNota3(new BigDecimal(n3));
        matricula.cadastrarFrequencia(frequencia);

        matricula.consolidarParcialmente();

        assertEquals(StatusAprovacao.REC, matricula.getStatus());
    }

    @ParameterizedTest(name = "REP - Notas: {0},{1},{2} | Freq: {3}%")
    @CsvSource({
            "2.9, 2.9, 2.9, 100",
            "0.0, 0.0, 0.0, 75",
            "1.0, 2.0, 3.0, 80"
    })
    @DisplayName("Deve ser reprovado por nota")
    void shouldFailByGrade(double n1, double n2, double n3, int frequencia) {
        matricula.cadastrarNota1(new BigDecimal(n1));
        matricula.cadastrarNota2(new BigDecimal(n2));
        matricula.cadastrarNota3(new BigDecimal(n3));
        matricula.cadastrarFrequencia(frequencia);

        matricula.consolidarParcialmente();

        assertEquals(StatusAprovacao.REP, matricula.getStatus());
    }

    @ParameterizedTest(name = "REPF - Notas: {0},{1},{2} | Freq: {3}%")
    @CsvSource({
            "10.0, 10.0, 10.0, 74",
            "5.0, 5.0, 5.0, 0",
            "3.0, 3.0, 3.0, 50"
    })
    @DisplayName("Deve ser reprovado por frequência")
    void shouldFailByAttendance(double n1, double n2, double n3, int frequencia) {
        matricula.cadastrarNota1(new BigDecimal(n1));
        matricula.cadastrarNota2(new BigDecimal(n2));
        matricula.cadastrarNota3(new BigDecimal(n3));
        matricula.cadastrarFrequencia(frequencia);

        matricula.consolidarParcialmente();

        assertEquals(StatusAprovacao.REPF, matricula.getStatus());
    }

    @ParameterizedTest(name = "REPMF - Notas: {0},{1},{2} | Freq: {3}%")
    @CsvSource({
            "2.9, 2.9, 2.9, 74",
            "0.0, 0.0, 0.0, 0",
            "1.0, 5.0, 1.0, 70"
    })
    @DisplayName("Deve ser reprovado por nota e frequência")
    void shouldFailByGradeAndAttendance(double n1, double n2, double n3, int frequencia) {
        matricula.cadastrarNota1(new BigDecimal(n1));
        matricula.cadastrarNota2(new BigDecimal(n2));
        matricula.cadastrarNota3(new BigDecimal(n3));
        matricula.cadastrarFrequencia(frequencia);

        matricula.consolidarParcialmente();

        assertEquals(StatusAprovacao.REPMF, matricula.getStatus());
    }
}