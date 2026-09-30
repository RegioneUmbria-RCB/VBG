package it.gruppoinit.pal.gp.core.features.sistema;

import java.util.Set;

public class FakeVerticalizzazioneTipoInstallazioneServiceImpl implements IVerticalizzazioneTipoInstallazioneService {

    private boolean attiva;
    private TipoInstallazioneEnum tipo;
    private TecnologiaPaginaEnum paginaOneri;
    private TecnologiaPaginaEnum paginaStampe;
    private TecnologiaPaginaEnum paginaSchedeDinamiche;
    private TecnologiaPaginaEnum paginaStatistiche;
    private TecnologiaPaginaEnum paginaStampeDocTipo;
    
    public static FakeVerticalizzazioneTipoInstallazioneServiceImpl AttivaStandardConPagineMicrosoft() {
	FakeVerticalizzazioneTipoInstallazioneServiceImpl v = new FakeVerticalizzazioneTipoInstallazioneServiceImpl();
	v.attiva = true;
	v.paginaOneri = TecnologiaPaginaEnum.MICROSOFT;
	v.paginaSchedeDinamiche = TecnologiaPaginaEnum.MICROSOFT;
	v.paginaStampe = TecnologiaPaginaEnum.MICROSOFT;
	v.paginaStampeDocTipo = TecnologiaPaginaEnum.MICROSOFT;
	v.paginaStatistiche = TecnologiaPaginaEnum.MICROSOFT;
	v.tipo = TipoInstallazioneEnum.STANDARD;
	return v;
    }
    
    public static FakeVerticalizzazioneTipoInstallazioneServiceImpl AttivaEnterpriseConPagineJava() {
	FakeVerticalizzazioneTipoInstallazioneServiceImpl v = new FakeVerticalizzazioneTipoInstallazioneServiceImpl();
	v.attiva = true;
	v.paginaOneri = TecnologiaPaginaEnum.JAVA;
	v.paginaSchedeDinamiche = TecnologiaPaginaEnum.JAVA;
	v.paginaStampe = TecnologiaPaginaEnum.JAVA;
	v.paginaStampeDocTipo = TecnologiaPaginaEnum.JAVA;
	v.paginaStatistiche = TecnologiaPaginaEnum.JAVA;
	v.tipo = TipoInstallazioneEnum.ENTERPRISE;
	return v;
    }
    
    public static FakeVerticalizzazioneTipoInstallazioneServiceImpl AttivaStandard() {
	FakeVerticalizzazioneTipoInstallazioneServiceImpl v = new FakeVerticalizzazioneTipoInstallazioneServiceImpl();
	v.attiva = true;
	v.paginaOneri = null;
	v.paginaSchedeDinamiche = null;
	v.paginaStampe = null;
	v.paginaStampeDocTipo = null;
	v.paginaStatistiche = null;
	v.tipo = TipoInstallazioneEnum.STANDARD;
	return v;
    }
    
    public static FakeVerticalizzazioneTipoInstallazioneServiceImpl AttivaEnterprise() {
	FakeVerticalizzazioneTipoInstallazioneServiceImpl v = new FakeVerticalizzazioneTipoInstallazioneServiceImpl();
	v.attiva = true;
	v.paginaOneri = null;
	v.paginaSchedeDinamiche = null;
	v.paginaStampe = null;
	v.paginaStampeDocTipo = null;
	v.paginaStatistiche = null;
	v.tipo = TipoInstallazioneEnum.ENTERPRISE;
	return v;
    }
    
    public static FakeVerticalizzazioneTipoInstallazioneServiceImpl NonAttiva() {
	FakeVerticalizzazioneTipoInstallazioneServiceImpl v = new FakeVerticalizzazioneTipoInstallazioneServiceImpl();
	v.attiva = false;
	return v;
    }
    
    @Override
    public boolean isAttiva() {

	return this.attiva;
    }

    @Override
    public boolean isInstallazioneEnterprise() {

	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public boolean isPaginaEnterprise(String parametro) {

	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public Set<String> overrideMenuStandard() {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public TecnologiaPaginaEnum paginaOneri() {

	return this.paginaOneri;
    }

    @Override
    public TecnologiaPaginaEnum paginaSchedeDinamiche() {

	return this.paginaSchedeDinamiche;
    }

    @Override
    public TecnologiaPaginaEnum paginaStampe() {

	return this.paginaStampe;
    }

    @Override
    public TecnologiaPaginaEnum paginaStatistiche() {
	return this.paginaStatistiche;
    }

    @Override
    public TecnologiaPaginaEnum paginaStampeDocTipo() {
	return this.paginaStampeDocTipo;
    }

    @Override
    public TipoInstallazioneEnum tipo() {
	return this.tipo;
    }
}
