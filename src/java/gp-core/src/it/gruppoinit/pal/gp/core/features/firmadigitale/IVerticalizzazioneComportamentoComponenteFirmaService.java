package it.gruppoinit.pal.gp.core.features.firmadigitale;

public interface IVerticalizzazioneComportamentoComponenteFirmaService {

    public boolean isAttiva();

    public boolean convertibileInPdf(String nomeFileIn);

    public boolean accodaEstensioneFirmaCades();
}
