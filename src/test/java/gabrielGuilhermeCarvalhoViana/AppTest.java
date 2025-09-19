package gabrielGuilhermeCarvalhoViana;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class AppTest {
    static Matricula matricula;

    @BeforeAll
    static void beforeAll() {
        Discente discente = new Discente();
        discente.setNome("Gabriel Viana");
        discente.setMatricula(123);

        Disciplina disciplina = new Disciplina();
        disciplina.setCodigo("DIM0507");
        disciplina.setNome("Testes de Software");

        Docente docente = new Docente();
        docente.setSiape(2025);
        docente.setNome("Eiji Adachi");

        Turma turma = new Turma(docente, disciplina);

        matricula = new Matricula(discente, turma);
    }

    @Test
    void shouldBeApprovedWhenAboveAverage() {
        matricula.cadastrarNota1(new BigDecimal(10));
        matricula.cadastrarNota2(new BigDecimal(10));
        matricula.cadastrarNota3(new BigDecimal(10));

        matricula.cadastrarFrequencia(100);
    }
}
