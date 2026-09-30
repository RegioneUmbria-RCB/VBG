package it.gruppoinit.pal.gp.core.features.bollettazione.arrotondamento;

import org.junit.Assert;
import org.junit.Test;

public class ArrotondamentoFactoryTest {

    @Test
    public void ritornaArrotondamentoDefaultServiceDaEnum() {

	ArrotondamentoFactory arrotondamentoFactory = new ArrotondamentoFactory();
	ArrotondamentoService service = arrotondamentoFactory.get(ArrotondamentoEnum.STANDARD);
	Assert.assertEquals("Deve restituire ArrotondamentoDefaultServiceImpl", ArrotondamentoDefaultServiceImpl.class, service.getClass());
    }
}
