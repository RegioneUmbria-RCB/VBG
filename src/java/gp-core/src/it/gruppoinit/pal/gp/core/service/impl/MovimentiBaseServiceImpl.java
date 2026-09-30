package it.gruppoinit.pal.gp.core.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;
import java.util.zip.ZipOutputStream;

import org.apache.commons.beanutils.BasicDynaClass;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaClass;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.InvalidValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.gruppoinit.fileconverter.MergeAndConvertRequest;
import it.gruppoinit.fileconverter.MergeAndConvertResponse;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.MessaggiRabbitMovimentiDAO;
import it.gruppoinit.pal.gp.core.dao.MovimentiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.Amministrazionireferenti;
import it.gruppoinit.pal.gp.core.domain.Amministrazioniresponsabili;
import it.gruppoinit.pal.gp.core.domain.Amministrazioniruoli;
import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;
import it.gruppoinit.pal.gp.core.domain.AutorizzazioniSubentri;
import it.gruppoinit.pal.gp.core.domain.CommedilizieTipologie;
import it.gruppoinit.pal.gp.core.domain.CommedilizieTipologiedett;
import it.gruppoinit.pal.gp.core.domain.CommissioniedilizieT;
import it.gruppoinit.pal.gp.core.domain.Configurazione;
import it.gruppoinit.pal.gp.core.domain.ConfigurazioneId;
import it.gruppoinit.pal.gp.core.domain.Dyn2Modellit;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimentisoftware;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzedyn2modellit;
import it.gruppoinit.pal.gp.core.domain.Istanzedyn2modellitId;
import it.gruppoinit.pal.gp.core.domain.Istanzeeventi;
import it.gruppoinit.pal.gp.core.domain.Istanzeprocedimenti;
import it.gruppoinit.pal.gp.core.domain.IstanzeprocedimentiId;
import it.gruppoinit.pal.gp.core.domain.Letteretipo;
import it.gruppoinit.pal.gp.core.domain.Mailtipo;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.MovimentiContromovimenti;
import it.gruppoinit.pal.gp.core.domain.MovimentiMetadati;
import it.gruppoinit.pal.gp.core.domain.MovimentiMetadatiId;
import it.gruppoinit.pal.gp.core.domain.MovimentiTempistica;
import it.gruppoinit.pal.gp.core.domain.Movimentiallegati;
import it.gruppoinit.pal.gp.core.domain.Movimentidyn2modellit;
import it.gruppoinit.pal.gp.core.domain.Movimentidyn2modellitId;
import it.gruppoinit.pal.gp.core.domain.Movimentihummingbird;
import it.gruppoinit.pal.gp.core.domain.Movimentimail;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PecInbox;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.ProtocolloConfigurazione;
import it.gruppoinit.pal.gp.core.domain.ProtocolloConfigurazioneId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Responsabiliruoli;
import it.gruppoinit.pal.gp.core.domain.Ruoli;
import it.gruppoinit.pal.gp.core.domain.Statiistanza;
import it.gruppoinit.pal.gp.core.domain.StatiistanzaId;
import it.gruppoinit.pal.gp.core.domain.Tempirisposta;
import it.gruppoinit.pal.gp.core.domain.Tipicontromovimento;
import it.gruppoinit.pal.gp.core.domain.TipimovStcMapping;
import it.gruppoinit.pal.gp.core.domain.Tipimovimentidyn2modellit;
import it.gruppoinit.pal.gp.core.domain.Tipimovimento;
import it.gruppoinit.pal.gp.core.domain.TipimovimentoId;
import it.gruppoinit.pal.gp.core.domain.Tipimovimentodoctipo;
import it.gruppoinit.pal.gp.core.domain.Tipiprocedure;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.domain.helper.AlberoprocHelper;
import it.gruppoinit.pal.gp.core.domain.helper.AnagrafedocumentiDTO;
import it.gruppoinit.pal.gp.core.domain.helper.CdsattiDTO;
import it.gruppoinit.pal.gp.core.domain.helper.CodiceDescrizioneBean;
import it.gruppoinit.pal.gp.core.domain.helper.DocumentiHelper;
import it.gruppoinit.pal.gp.core.domain.helper.DocumentiistanzaDTO;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeallegatiDTO;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeprocureDTO;
import it.gruppoinit.pal.gp.core.domain.helper.MovimentiDTO;
import it.gruppoinit.pal.gp.core.domain.helper.MovimentiHelper;
import it.gruppoinit.pal.gp.core.domain.helper.MovimentiallegatiDTO;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.domain.web.ChiaveValoreBean;
import it.gruppoinit.pal.gp.core.domain.web.IstanzeeventiFilter;
import it.gruppoinit.pal.gp.core.domain.web.ProtocollazioneCommand;
import it.gruppoinit.pal.gp.core.domain.web.ValidaEliminazioneAutConcCommand;
import it.gruppoinit.pal.gp.core.exception.FunzioneBusinessRemotaException;
import it.gruppoinit.pal.gp.core.exception.InvalidConfigurationException;
import it.gruppoinit.pal.gp.core.features.alberoproc.metadati.AlberoprocMetadatiEnum;
import it.gruppoinit.pal.gp.core.features.alberoproc.metadati.AlberoprocMetadatiService;
import it.gruppoinit.pal.gp.core.features.attivita.eventi.EventoIstanzaModificaOperante;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.AutorizzazioniService;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.eventi.EsitoCancellazioneAutOConc;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.exceptions.OperazioneCancellazioneAutConcException;
import it.gruppoinit.pal.gp.core.features.buslightyear.exceptions.EventAbortedException;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventPublisher;
import it.gruppoinit.pal.gp.core.features.documenticondivisi.IDocumentiCondivisiService;
import it.gruppoinit.pal.gp.core.features.mailtipo.MailtipoService;
import it.gruppoinit.pal.gp.core.features.movimenti.FaseMovimentoEnum;
import it.gruppoinit.pal.gp.core.features.movimenti.RicercaMovimentiEnum;
import it.gruppoinit.pal.gp.core.features.movimenti.configurazione.doctipo.TipimovimentodoctipoService;
import it.gruppoinit.pal.gp.core.features.movimenti.configurazione.rabbit.ITipimovimentoRabbitDAO;
import it.gruppoinit.pal.gp.core.features.movimenti.eventi.EventoMovimentoAbilitato;
import it.gruppoinit.pal.gp.core.features.movimenti.eventi.EventoMovimentoAggiornato;
import it.gruppoinit.pal.gp.core.features.movimenti.eventi.EventoMovimentoCancellato;
import it.gruppoinit.pal.gp.core.features.movimenti.eventi.EventoMovimentoDisabilitato;
import it.gruppoinit.pal.gp.core.features.movimenti.eventi.EventoMovimentoInserito;
import it.gruppoinit.pal.gp.core.features.movimenti.metadati.IMovimentiMetadatiDAO;
import it.gruppoinit.pal.gp.core.features.movimenti.metadati.MovimentoMetadatoScadAggTrasmessa;
import it.gruppoinit.pal.gp.core.features.movimenti.metadati.MovimentoMetadatoScadenzaAggiornata;
import it.gruppoinit.pal.gp.core.features.movimenti.metadati.MovimentoMetadatoUUID;
import it.gruppoinit.pal.gp.core.features.movimenti.rest.AggiornaRiferimentiProtocolloMovimentoRequest;
import it.gruppoinit.pal.gp.core.features.movimenti.ziplogico.MovimentiZipLogicoService;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.features.oneri.IstanzeoneriService;
import it.gruppoinit.pal.gp.core.features.protocollazione.eventi.EventoMovimentoProtocollato;
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.PopolaProtocollazioneCommandNotAutomatica;
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.ProtocollazioneService;
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.ProtocolloSourceEnum;
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.exceptions.AggiornamentoProtocolloException;
import it.gruppoinit.pal.gp.core.features.protocollazione.verticalizzazione.VerticalizzazioneProtocolloAttivoServiceImpl;
import it.gruppoinit.pal.gp.core.features.rabbitmq.model.MovimentiRabbitTestoBean;
import it.gruppoinit.pal.gp.core.features.scadenzario.BatchScadenzarioFilter;
import it.gruppoinit.pal.gp.core.features.stc.richiestapratica.RichiestaPraticaCollegataFactory;
import it.gruppoinit.pal.gp.core.features.stc.richiestapratica.RichiestaPraticaFromNotificaPraticaStoricaParams;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.filters.AndOrRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.AlberoprocService;
import it.gruppoinit.pal.gp.core.service.AmministrazioniService;
import it.gruppoinit.pal.gp.core.service.AmministrazionireferentiService;
import it.gruppoinit.pal.gp.core.service.AutorizzazioniSubentriService;
import it.gruppoinit.pal.gp.core.service.CommedilizieTipologieService;
import it.gruppoinit.pal.gp.core.service.ComuniassociatiService;
import it.gruppoinit.pal.gp.core.service.ConfigurazioneService;
import it.gruppoinit.pal.gp.core.service.DocumentiistanzaService;
import it.gruppoinit.pal.gp.core.service.Dyn2ModellitService;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentiService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.IstanzeallegatiService;
import it.gruppoinit.pal.gp.core.service.Istanzedyn2modellitService;
import it.gruppoinit.pal.gp.core.service.IstanzeeventiService;
import it.gruppoinit.pal.gp.core.service.IstanzeprocedimentiService;
import it.gruppoinit.pal.gp.core.service.LetteretipoService;
import it.gruppoinit.pal.gp.core.service.MovimentiBaseService;
import it.gruppoinit.pal.gp.core.service.MovimentiContromovimentiService;
import it.gruppoinit.pal.gp.core.service.MovimentiService;
import it.gruppoinit.pal.gp.core.service.MovimentiTempisticaService;
import it.gruppoinit.pal.gp.core.service.MovimentiallegatiService;
import it.gruppoinit.pal.gp.core.service.Movimentidyn2modellitService;
import it.gruppoinit.pal.gp.core.service.MovimentihummingbirdService;
import it.gruppoinit.pal.gp.core.service.MovimentimailService;
import it.gruppoinit.pal.gp.core.service.PecInboxService;
import it.gruppoinit.pal.gp.core.service.ProtocolloConfigurazioneService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliruoliService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.pal.gp.core.service.StatiistanzaService;
import it.gruppoinit.pal.gp.core.service.StcService;
import it.gruppoinit.pal.gp.core.service.TempirispostaService;
import it.gruppoinit.pal.gp.core.service.TipiMovimentoService;
import it.gruppoinit.pal.gp.core.service.TipicontromovimentoService;
import it.gruppoinit.pal.gp.core.service.TipimovStcMappingService;
import it.gruppoinit.pal.gp.core.service.TipimovStcMappingService.TipoNotificaAutomatica;
import it.gruppoinit.pal.gp.core.service.Tipimovimentidyn2modellitService;
import it.gruppoinit.pal.gp.core.service.TipimovimentoComunicazioniService;
import it.gruppoinit.pal.gp.core.service.TipiprocedureService;
import it.gruppoinit.pal.gp.core.service.TipisoggettopeopleService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;
import it.gruppoinit.pal.gp.core.service.exception.BusinessValidationException;
import it.gruppoinit.pal.gp.core.service.exception.EntityValidationException;
import it.gruppoinit.pal.gp.core.service.exception.OperazioniAutomaticheException;
import it.gruppoinit.pal.gp.core.service.exception.STCNotificaAttivitaException;
import it.gruppoinit.pal.gp.core.service.helper.BatchScadenzarioFilterHelper;
import it.gruppoinit.pal.gp.core.service.helper.BatchScadenzarioFilterHelper.QUERY_PER;
import it.gruppoinit.pal.gp.core.service.helper.ComunicazioniTelematicheHelper;
import it.gruppoinit.pal.gp.core.service.helper.EsitoCreaZipLogico;
import it.gruppoinit.pal.gp.core.service.helper.FlashMessages;
import it.gruppoinit.pal.gp.core.service.helper.IstanzeeventiConstants;
import it.gruppoinit.pal.gp.core.service.helper.NotificheAutomaticheAsincroneHelper;
import it.gruppoinit.pal.gp.core.service.helper.TipoAccessoEnum;
import it.gruppoinit.pal.gp.core.service.helper.TipoComunicazionemovimentoEnum;
import it.gruppoinit.pal.gp.core.service.helper.TipoRicercaDocumentoEnum;
import it.gruppoinit.pal.gp.core.service.rules.IstanzeBusinessRules;
import it.gruppoinit.pal.gp.core.service.rules.OperazioniAutomaticheBusinessRules;
import it.gruppoinit.pal.gp.core.service.rules.ServiceValidationRules;
import it.gruppoinit.pal.gp.core.service.rules.SigeproBusinessRules;
import it.gruppoinit.pal.gp.core.utils.LoggerModificheIstanze;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.pal.gp.core.ws.client.FileConverterWsClient;
import it.init.sigepro.rte.RichiestaPraticaCollegataResponse;
import it.init.sigepro.rte.types.ErroreType;

public class MovimentiBaseServiceImpl extends BaseServiceImpl<Movimenti, PkId> implements MovimentiBaseService {

    private static final Logger log = LoggerFactory.getLogger(MovimentiBaseServiceImpl.class);
    private AlberoprocService alberoprocService;
    private PecInboxService pecInboxService;
    private AmministrazioniService amministrazioniService;
    private AmministrazionireferentiService amministrazionireferentiService;
    private AutorizzazioniService autorizzazioniService;
    private AutorizzazioniSubentriService autorizzazioniSubentriService;
    private ComuniassociatiService comuniassociatiService;
    private ConfigurazioneService configurazioneService;
    private InventarioprocedimentiService inventarioprocedimentiService;
    private Istanzedyn2modellitService istanzedyn2modellitService;
    private IstanzeService istanzeService;
    private IstanzeoneriService istanzeoneriService;
    private IstanzeprocedimentiService istanzeprocedimentiService;
    private LetteretipoService letteretipoService;
    private MailtipoService mailtipoService;
    private MovimentiallegatiService movimentiallegatiService;
    private MovimentiContromovimentiService movimentiContromovimentiService;
    private MovimentiDAO movimentiDAO;
    private Movimentidyn2modellitService movimentidyn2modellitService;
    private MovimentimailService movimentimailService;
    private MovimentihummingbirdService movimentihummingbirdService;
    private MovimentiTempisticaService movimentiTempisticaService;
    private OggettiService oggettiService;
    private ProtocollazioneService protocollazioneService;
    private ProtocolloConfigurazioneService protocolloConfigurazioneService;
    private ResponsabiliruoliService responsabiliruoliService;
    private ResponsabiliService responsabiliService;
    private SoftwareService softwareService;
    private StcService stcService;
    private TempirispostaService tempirispostaService;
    private TipicontromovimentoService tipicontromovimentoService;
    private TipimovimentodoctipoService tipimovimentodoctipoService;
    private Tipimovimentidyn2modellitService tipimovimentidyn2modellitService;
    private TipiMovimentoService tipiMovimentoService;
    private TipimovStcMappingService tipimovStcMappingService;
    private TipiprocedureService tipiprocedureService;
    @Autowired
    private TipisoggettopeopleService tipisoggettopeopleService;
    private UserSecurityService userSecurityService;
    private VerticalizzazioniService verticalizzazioniService;
    private IstanzeeventiService istanzeeventiService;
    private StatiistanzaService statiistanzaService;
    private CommedilizieTipologieService commedilizieTipologieService;
    private NotificheAutomaticheAsincroneHelper notificheAutomaticheAsincroneHelper;
    private TipimovimentoComunicazioniService tipimovimentoComunicazioniService;
    private MovimentiZipLogicoService movimentiZipLogicoService;
    private Dyn2ModellitService dyn2ModellitService;
    private IstanzeallegatiService istanzeallegatiService;
    private DocumentiistanzaService documentiistanzaService;
    private IDocumentiCondivisiService documentiCondivisiService;
    @Autowired
    private MessaggiRabbitMovimentiDAO messaggiRabbitMovimentiDAO;
    @Autowired
    private IMovimentiMetadatiDAO movimentiMetadatiDAO;
    @Autowired
    private AlberoprocMetadatiService alberoprocMetadatiService;
    @Autowired
    private ITipimovimentoRabbitDAO iTipimovimentoRabbitDAO;

    @Autowired
    public void setDocumentiistanzaService(DocumentiistanzaService documentiistanzaService) {

	this.documentiistanzaService = documentiistanzaService;
    }

    @Autowired
    public void setIstanzeallegatiService(IstanzeallegatiService istanzeallegatiService) {

	this.istanzeallegatiService = istanzeallegatiService;
    }

    @Autowired
    public void setDyn2ModellitService(Dyn2ModellitService dyn2ModellitService) {

	this.dyn2ModellitService = dyn2ModellitService;
    }

    @Autowired
    private IEventPublisher eventPublisher;

    @Autowired
    public void setAlberoprocService(AlberoprocService alberoprocService) {

	this.alberoprocService = alberoprocService;
    }

    @Autowired
    public void setAmministrazioniService(AmministrazioniService amministrazioniService) {

	this.amministrazioniService = amministrazioniService;
    }

    @Autowired
    public void setAmministrazionireferentiService(AmministrazionireferentiService amministrazionireferentiService) {

	this.amministrazionireferentiService = amministrazionireferentiService;
    }

    @Autowired
    public void setAutorizzazioniService(AutorizzazioniService autorizzazioniService) {

	this.autorizzazioniService = autorizzazioniService;
    }

    @Autowired
    public void setAutorizzazioniSubentriService(AutorizzazioniSubentriService autorizzazioniSubentriService) {

	this.autorizzazioniSubentriService = autorizzazioniSubentriService;
    }

    @Autowired
    public void setComuniassociatiService(ComuniassociatiService comuniassociatiService) {

	this.comuniassociatiService = comuniassociatiService;
    }

    @Autowired
    public void setConfigurazioneService(ConfigurazioneService configurazioneService) {

	this.configurazioneService = configurazioneService;
    }

    @Autowired
    public void setInventarioprocedimentiService(InventarioprocedimentiService inventarioprocedimentiService) {

	this.inventarioprocedimentiService = inventarioprocedimentiService;
    }

    @Autowired
    public void setIstanzedyn2modellitService(Istanzedyn2modellitService istanzedyn2modellitService) {

	this.istanzedyn2modellitService = istanzedyn2modellitService;
    }

    @Autowired
    public void setIstanzeService(IstanzeService istanzeService) {

	this.istanzeService = istanzeService;
    }

    @Autowired
    public void setIstanzeoneriService(IstanzeoneriService istanzeoneriService) {

	this.istanzeoneriService = istanzeoneriService;
    }

    @Autowired
    public void setIstanzeprocedimentiService(IstanzeprocedimentiService istanzeprocedimentiService) {

	this.istanzeprocedimentiService = istanzeprocedimentiService;
    }

    @Autowired
    public void setLetteretipoService(LetteretipoService letteretipoService) {

	this.letteretipoService = letteretipoService;
    }

    @Autowired
    public void setMailtipoService(MailtipoService mailtipoService) {

	this.mailtipoService = mailtipoService;
    }

    @Autowired
    public void setMovimentiallegatiService(MovimentiallegatiService movimentiallegatiService) {

	this.movimentiallegatiService = movimentiallegatiService;
    }

    @Autowired
    public void setMovimentiContromovimentiService(MovimentiContromovimentiService movimentiContromovimentiService) {

	this.movimentiContromovimentiService = movimentiContromovimentiService;
    }

    @Autowired
    public void setMovimentiDAO(MovimentiDAO movimentiDAO) {

	this.movimentiDAO = movimentiDAO;
    }

    @Autowired
    public void setMovimentidyn2modellitService(Movimentidyn2modellitService movimentidyn2modellitService) {

	this.movimentidyn2modellitService = movimentidyn2modellitService;
    }

    @Autowired
    public void setMovimentimailService(MovimentimailService movimentimailService) {

	this.movimentimailService = movimentimailService;
    }

    @Autowired
    public void setMovimentihummingbirdService(MovimentihummingbirdService movimentihummingbirdService) {

	this.movimentihummingbirdService = movimentihummingbirdService;
    }

    @Autowired
    public void setMovimentiTempisticaService(MovimentiTempisticaService movimentiTempisticaService) {

	this.movimentiTempisticaService = movimentiTempisticaService;
    }

    @Autowired
    public void setOggettiService(OggettiService oggettiService) {

	this.oggettiService = oggettiService;
    }

    @Autowired
    public void setProtocollazioneService(ProtocollazioneService protocollazioneService) {

	this.protocollazioneService = protocollazioneService;
    }

    @Autowired
    public void setProtocolloConfigurazioneService(ProtocolloConfigurazioneService protocolloConfigurazioneService) {

	this.protocolloConfigurazioneService = protocolloConfigurazioneService;
    }

    @Autowired
    public void setResponsabiliruoliService(ResponsabiliruoliService responsabiliruoliService) {

	this.responsabiliruoliService = responsabiliruoliService;
    }

    @Autowired
    public void setResponsabiliService(ResponsabiliService responsabiliService) {

	this.responsabiliService = responsabiliService;
    }

    @Autowired
    public void setSoftwareService(SoftwareService softwareService) {

	this.softwareService = softwareService;
    }

    @Autowired
    public void setStcService(StcService stcService) {

	this.stcService = stcService;
    }

    @Autowired
    public void setStatiistanzaService(StatiistanzaService statiistanzaService) {

	this.statiistanzaService = statiistanzaService;
    }

    @Autowired
    public void setTempirispostaService(TempirispostaService tempirispostaService) {

	this.tempirispostaService = tempirispostaService;
    }

    @Autowired
    public void setTipicontromovimentoService(TipicontromovimentoService tipicontromovimentoService) {

	this.tipicontromovimentoService = tipicontromovimentoService;
    }

    @Autowired
    public void setTipiMovimentoService(TipiMovimentoService tipiMovimentoService) {

	this.tipiMovimentoService = tipiMovimentoService;
    }

    @Autowired
    public void setTipimovStcMappingService(TipimovStcMappingService tipimovStcMappingService) {

	this.tipimovStcMappingService = tipimovStcMappingService;
    }

    @Autowired
    public void setTipimovimentidyn2modellitService(Tipimovimentidyn2modellitService tipimovimentidyn2modellitService) {

	this.tipimovimentidyn2modellitService = tipimovimentidyn2modellitService;
    }

    @Autowired
    public void setTipimovimentodoctipoService(TipimovimentodoctipoService tipimovimentodoctipoService) {

	this.tipimovimentodoctipoService = tipimovimentodoctipoService;
    }

    @Autowired
    public void setTipiprocedureService(TipiprocedureService tipiprocedureService) {

	this.tipiprocedureService = tipiprocedureService;
    }

    @Autowired
    public void setUserSecurityService(UserSecurityService userSecurityService) {

	this.userSecurityService = userSecurityService;
    }

    @Autowired
    public void setVerticalizzazioniService(VerticalizzazioniService verticalizzazioniService) {

	this.verticalizzazioniService = verticalizzazioniService;
    }

    @Autowired
    public void setIstanzeeventiService(IstanzeeventiService istanzeeventiService) {

	this.istanzeeventiService = istanzeeventiService;
    }

    @Autowired
    public void setCommedilizieTipologieService(CommedilizieTipologieService commedilizieTipologieService) {

	this.commedilizieTipologieService = commedilizieTipologieService;
    }

    @Autowired
    public void setNotificheAutomaticheAsincroneHelper(NotificheAutomaticheAsincroneHelper notificheAutomaticheAsincroneHelper) {

	this.notificheAutomaticheAsincroneHelper = notificheAutomaticheAsincroneHelper;
    }

    @Autowired
    public void setTipimovimentoComunicazioniService(TipimovimentoComunicazioniService tipimovimentoComunicazioniService) {

	this.tipimovimentoComunicazioniService = tipimovimentoComunicazioniService;
    }

    @Autowired
    public void setMovimentiZipLogicoService(MovimentiZipLogicoService movimentiZipLogicoService) {

	this.movimentiZipLogicoService = movimentiZipLogicoService;
    }

    @Autowired
    public void setDocumentiCondivisiService(IDocumentiCondivisiService documentiCondivisiService) {

	this.documentiCondivisiService = documentiCondivisiService;
    }

    @Override
    protected Class<Movimenti> getEntityClass() {

	return Movimenti.class;
    }

    @Override
    public void delete(Movimenti entity) {

	EventoMovimentoCancellato evt = EventoMovimentoCancellato.fromEntity(entity.getId().getCodice(), movimentiDAO);
	if (isDeleteAllowed(entity)) {
	    childDelete(entity);
	    movimentiDAO.delete(entity);
	    try {
		eventPublisher.publishThrowOnFailure(evt);
	    } catch (EventAbortedException e) {
		log.error("Errore nella pubblicazione dell'evento " + evt, e);
		throw new BusinessValidationException(e);
	    }
	}
    }

    @Override
    public List<Movimenti> findAll(Integer firstResult, Integer maxResult) {

	return movimentiDAO.findAll(firstResult, maxResult);
    }

    @Override
    public Movimenti findById(PkId id) {

	return movimentiDAO.findById(id);
    }

    @Override
    public void insert(Movimenti movimenti) {

	this.daoInsert(movimenti, false);
    }

    @Override
    public void insertScadenza(Movimenti entity) {

	this.daoInsert(entity, true);
    }

    public void operazioniAutomatiche(Movimenti entity) throws OperazioniAutomaticheException {

	OperazioniAutomaticheBusinessRules opautRules = (OperazioniAutomaticheBusinessRules) SigeproBusinessRules
		.getClassRules(OperazioniAutomaticheBusinessRules.class);
	boolean isNotificaStcAutomatica = opautRules.isNotificaStcAutomatica();
	if (isNotificaStcAutomatica) {
	    // §§§BEGIN§§§
	    try {
		this.notificaStc(entity);
	    } catch (Exception e) {
		throw new OperazioniAutomaticheException("Non è stato possibile notificare il movimento a causa di: " + e.getMessage(), e);
	    }
	    log.debug("operazioniAutomatiche: notificaStc eseguita");
	    // §§§END§§§ 
	}
    }

    /**
     * @param movimenti
     */
    private Set<Movimentiallegati> copiaAllegati(Movimenti movimenti, boolean isInserimentoSTC, boolean isImport) {

	// Codice aggiunto nel caso sia a valore true la bussiness rule:
	// IstanzeBusinessRules.CustomRuleEnum.inserimentoDaStc.name(). Evita un eccezione 
	// "Failed to lazily initialize a collection " quando viene recuperata la lista.
	if (!(isInserimentoSTC || isImport) && EntityUtils.getNestedProperty(movimenti, "id.codice") != null) {
	    movimenti = this.findById(new PkId(movimenti.getId().getCodice()));
	}
	if (!movimenti.getMovimentiallegatis().isEmpty()) {
	    Set<Movimentiallegati> allegatis = movimenti.getMovimentiallegatis();
	    movimenti.setMovimentiallegatis(null);
	    return allegatis;
	}
	return new HashSet<Movimentiallegati>(0);
    }

    @Override
    public void update(Movimenti entity) {

	dataIntegration(entity, false);
	if (validateEntity(entity)) {
	    movimentiDAO.update(entity);
	    if (entity.getData() != null) { // Dovrebbe essere sempre vero
		childDataIntegration(entity, false, null);
	    }
	}
    }

    private void dataIntegration(Movimenti entity, boolean isInsert) {

	if (entity == null) {
	    throw new IllegalArgumentException("Il movimento passato è nullo");
	}
	fixMergeEntityProperties(entity);
	if (entity.getInviatoACamcom() == null) {
	    entity.setInviatoACamcom(Boolean.FALSE);
	}
	if (entity.getInviatoConStc() == null) {
	    entity.setInviatoConStc(MovimentiService.STC_NON_INVIATO);
	}
	if (entity.getCreatoDaStc() == null) {
	    entity.setCreatoDaStc(Boolean.FALSE);
	}
	if (entity.getFlagDaLeggere() == null) {
	    entity.setFlagDaLeggere(Boolean.FALSE);
	}
	if (entity.getFlagDisabilitato() == null) {
	    entity.setFlagDisabilitato(Boolean.FALSE);
	}
	if (entity.getFlagCmovObblig() == null) {
	    entity.setFlagCmovObblig(Boolean.FALSE);
	}
	if (entity.getFlgModManualedata() == null) {
	    entity.setFlgModManualedata(Boolean.FALSE);
	}
	if (EntityUtils.getNestedProperty(entity.getTipomovimento(), "id.tipomovimento") != null) {
	    if (StringUtils.isBlank(entity.getMovimento())) {
		entity.setMovimento(entity.getTipomovimento().getMovimento());
	    }
	    if (entity.getTipomovimento().getTipologiaesito() == null || entity.getTipomovimento().getTipologiaesito().intValue() == 0) {
		// BOCCI 2012-09-20 : SE TIPIMOVIMENTO.TIPOLOGIAESITO=0 ESITO DEVONO VENIRE CONSIDERATI COME ESITO POSITIVO
		// BOCCI 2018-06-13 SETTO L'ESITO SOLO SE NESSUNO LO HA SETTATO PRIMA
		if (entity.getEsito() == null) {
		    entity.setEsito(Boolean.TRUE);
		}
	    }
	    if (entity.getPubblica() == null) {
		boolean pubblica = entity.getTipomovimento().getFlagPubblicamovimento() == null ? Boolean.FALSE
			: entity.getTipomovimento().getFlagPubblicamovimento();
		entity.setPubblica(pubblica);
	    }
	    if (entity.getPubblicaparere() == null) {
		boolean pubblicaParere = entity.getTipomovimento().getFlagPubblicaparere() == null ? Boolean.FALSE
			: entity.getTipomovimento().getFlagPubblicaparere();
		entity.setPubblicaparere(pubblicaParere);
	    }
	    if (isInsert) {
		IstanzeBusinessRules rules = (IstanzeBusinessRules) SigeproBusinessRules.getClassRules(IstanzeBusinessRules.class);
		if (rules.getCustomRule(IstanzeBusinessRules.CustomRuleEnum.inserimentoDaStc.name())
			|| rules.getCustomRule(IstanzeBusinessRules.CustomRuleEnum.inserimentoDaStcDiretto.name())) {
		    entity.setFlagDaLeggere(Boolean.TRUE);
		    if (entity.getTipomovimento().getFlagDisdavisionare() != null) {
			if (entity.getTipomovimento().getFlagDisdavisionare().booleanValue()) {
			    entity.setFlagDaLeggere(Boolean.FALSE);
			}
		    }
		}
	    }
	} else {
	    if (entity.getPubblica() == null) {
		entity.setPubblica(Boolean.FALSE);
	    }
	    if (entity.getPubblicaparere() == null) {
		entity.setPubblicaparere(Boolean.FALSE);
	    }
	}
	// BOCCI 2012-09-20 : I VALORI NULLI DI ESITO DEVONO VENIRE CONSIDERATI COME ESITO POSITIVO
	// BOCCI 2018-06-13 SETTO L'ESITO SOLO SE NESSUNO LO HA SETTATO PRIMA. NESSUNA DELLE OPERAZIONI HA SETTATO L'ESITO ALLORA LO SETTO DI DEFAULT A TRUE SE NULLO
	if (entity.getEsito() == null) {
	    entity.setEsito(Boolean.TRUE);
	}
	if (EntityUtils.getNestedProperty(entity.getResponsabile(), "id.codice") == null) {
	    OperazioniAutomaticheBusinessRules rules = (OperazioniAutomaticheBusinessRules) SigeproBusinessRules
		    .getClassRules(OperazioniAutomaticheBusinessRules.class);
	    if (rules.isOperazioneAutomatica()) {
		// RECUPERO L'OPERATORE STC DALL'ALBERO DEI PROCEDIMENTI
		Alberoproc alberoproc = alberoprocService.bindDomainObject(entity.getIstanza().getAlberoproc(), PkId.class, "id.codice");
		if (alberoproc != null) {
		    AlberoprocHelper helper = alberoprocService.findAlberoprocHelper(alberoproc);
		    if (helper != null) {
			if (helper.getOperatoreStc() != null) {
			    if (helper.getOperatoreStc().getId() != null) {
				if (helper.getOperatoreStc().getId().getCodice() != null) {
				    entity.setResponsabile(helper.getOperatoreStc());
				}
			    }
			}
		    }
		}
	    }
	    if (EntityUtils.getNestedProperty(entity.getResponsabile(), "id.codice") == null) {
		if (EntityUtils.getNestedProperty(entity.getIstanza(), "id.codice") != null) {
		    entity.setResponsabile(entity.getIstanza().getResponsabile());
		}
	    }
	}
	if (EntityUtils.getNestedProperty(entity.getAmministrazioni(), "id.codice") == null) {
	    Configurazione conf = configurazioneService.findById(new ConfigurazioneId(WebConstants.SOFTWARE_TT));
	    if (conf == null) {
		throw new BusinessValidationException("Configurazione non trovata per il software " + WebConstants.SOFTWARE_TT);
	    }
	    Integer codiceAmministrazioneSportelloUnico = conf.getCodammsportellounico();
	    if (codiceAmministrazioneSportelloUnico != null) {
		Amministrazioni ammdefault = amministrazioniService.findById(new PkId(codiceAmministrazioneSportelloUnico));
		if (ammdefault == null) {
		    throw new BusinessValidationException(
			    "Non è stata trovata l'amministrazione di default per lo sportello [configurata in configurazione, software " +
							  WebConstants.SOFTWARE_TT + " con codice " + codiceAmministrazioneSportelloUnico + "]");
		}
		entity.setAmministrazioni(ammdefault);
	    }
	}
	// BUGFIX: 713 2012-02-01 BOCCI Sui movimenti sarebbe auspicabile che, quando vengono aggiornati a null i campi numero protocollo e data protocollo, 
	// sia aggiornato a null anche il campo fkidprotocollo come avviene ad esempio sulla maschera delle istanze.
	if (StringUtils.isBlank(entity.getNumeroprotocollo()) && entity.getDataprotocollo() == null) {
	    if (StringUtils.isNotBlank(entity.getFkidprotocollo())) {
		entity.setFkidprotocollo(null);
	    }
	}
	if (StringUtils.isNotBlank(entity.getOraInserimento())) {
	    Date date = entity.getData();
	    date = Utilities.addTime(date, entity.getOraInserimento());
	    entity.setData(date);
	}
	// END BUGFIX 713
	//Gestione del flag tipimovimenti.flagRiportaProtIstanza : Nel caso il movimento non ha popolato i campi del protocollo
	// se il flag == true allora riporta i dati del prot dell'istanza, se presenti (solo in fase di inserimento)
	if (isInsert && entity.getTipomovimento() != null && BooleanUtils.toBoolean(entity.getTipomovimento().getFlagRiportaProtIstanza())) {
	    // Controllo se il movimento ha popolati i due campi del prot
	    if (StringUtils.isBlank(entity.getNumeroprotocollo()) && entity.getDataprotocollo() == null) {
		String numProtIstanza = StringUtils.defaultIfEmpty(entity.getIstanza().getNumeroprotocollo(), "");
		Date dataProtIstanza = null;
		if (entity.getIstanza().getDataprotocollo() != null) {
		    dataProtIstanza = entity.getIstanza().getDataprotocollo();
		}
		entity.setNumeroprotocollo(numProtIstanza);
		entity.setDataprotocollo(dataProtIstanza);
		String idprotocollo = StringUtils.defaultIfEmpty(entity.getFkidprotocollo(), "");
		entity.setFkidprotocollo(idprotocollo);
	    }
	}
    }

    protected void fixMergeEntityProperties(Movimenti entity) {

	Tipimovimento tipimovimento = tipiMovimentoService.bindDomainObject(entity.getTipomovimento(), TipimovimentoId.class, "id.tipomovimento");
	entity.setTipomovimento(tipimovimento);
	Istanze istanza = istanzeService.bindDomainObject(entity.getIstanza(), PkId.class, "id.codice");
	entity.setIstanza(istanza);
	Inventarioprocedimenti endoprocedimento = inventarioprocedimentiService.bindDomainObject(entity.getEndoprocedimento(), PkId.class,
		"id.codice");
	entity.setEndoprocedimento(endoprocedimento);
	Amministrazioni amministrazioni = amministrazioniService.bindDomainObject(entity.getAmministrazioni(), PkId.class, "id.codice");
	entity.setAmministrazioni(amministrazioni);
	Amministrazionireferenti amministrazionireferenti = amministrazionireferentiService.bindDomainObject(entity.getAmministrazionireferenti(),
		PkId.class, "id.codice");
	entity.setAmministrazionireferenti(amministrazionireferenti);
	Amministrazioni amministrazioniStc = amministrazioniService.bindDomainObject(entity.getAmministrazioniStc(), PkId.class, "id.codice");
	entity.setAmministrazioniStc(amministrazioniStc);
	Responsabili responsabile = responsabiliService.bindDomainObject(entity.getResponsabile(), PkId.class, "id.codice");
	entity.setResponsabile(responsabile);
	Oggetti oggettoNotifica = oggettiService.bindDomainObject(entity.getOggettoNotifica(), PkId.class, "id.codice");
	entity.setOggettoNotifica(oggettoNotifica);
    }

    @Override
    protected void childDelete(Movimenti entity) {

	this.movimentiMetadatiDAO.deleteByCodiceMovimento(entity.getId().getCodice());
	MovimentiMetadati metadatoUuId = this.movimentiMetadatiDAO
		.findById(new MovimentiMetadatiId(entity.getId().getCodice(), MovimentoMetadatoUUID.NOME_METADATO));
	if (metadatoUuId != null && !StringUtils.isBlank(metadatoUuId.getValore())) {
	    messaggiRabbitMovimentiDAO.deleteByUuIdMovimento(metadatoUuId.getValore());
	}
	Set<Movimentiallegati> movimentiallegatis = entity.getMovimentiallegatis();
	for (Movimentiallegati movimentiallegati : movimentiallegatis) {
	    movimentiallegatiService.delete(movimentiallegati);
	}
	if (this.movimentiZipLogicoService.isZipLogicoExistInMovimento(entity.getId().getCodice())) {
	    this.movimentiZipLogicoService.eliminaZipLogicoByCodiceMovimento(entity.getId().getCodice());
	}
	Set<Autorizzazioni> autorizzazionis = entity.getAutorizzazioni();
	for (Autorizzazioni autorizzazioni : autorizzazionis) {
	    ValidaEliminazioneAutConcCommand cmd = new ValidaEliminazioneAutConcCommand("",
		    autorizzazioniService.findById(new PkId(autorizzazioni.getId().getCodice())));
	    try {
		EsitoCancellazioneAutOConc esito = autorizzazioniService.validaCancellazioneAutConc(cmd);
		cmd.setEsito(esito);
		autorizzazioniService.deleteAutorizzazione(cmd);
	    } catch (OperazioneCancellazioneAutConcException e) {
		throw new BusinessValidationException(e);
	    }
	}
	Set<AutorizzazioniSubentri> autorizzazioniSubentris = entity.getAutorizzazioniSubentri();
	for (AutorizzazioniSubentri autorizzazioniSubentri : autorizzazioniSubentris) {
	    autorizzazioniSubentriService.delete(autorizzazioniSubentri);
	}
	Set<Movimentidyn2modellit> movimentidyn2modellits = entity.getMovimentidyn2modellits();
	for (Movimentidyn2modellit movimentidyn2modellit : movimentidyn2modellits) {
	    movimentidyn2modellitService.delete(movimentidyn2modellit);
	}
	Set<Movimentimail> movimentimails = entity.getMovimentimails();
	for (Movimentimail movimentimail : movimentimails) {
	    movimentimailService.delete(movimentimail);
	}
	Movimentihummingbird movimentihummingbird = entity.getMovimentihummingbird();
	if (movimentihummingbird != null) {
	    movimentihummingbirdService.delete(movimentihummingbird);
	}
	Set<MovimentiTempistica> movimentiTempisticasForFkApertura = entity.getMovimentiTempisticasForFkApertura();
	for (MovimentiTempistica movimentiTempistica : movimentiTempisticasForFkApertura) {
	    movimentiTempisticaService.delete(movimentiTempistica);
	}
	Set<MovimentiTempistica> movimentiTempisticasForFkChiusura = entity.getMovimentiTempisticasForFkChiusura();
	for (MovimentiTempistica movimentiTempistica : movimentiTempisticasForFkChiusura) {
	    movimentiTempisticaService.delete(movimentiTempistica);
	}
	this.documentiCondivisiService.deleteByCodiceMovimento(entity.getId().getCodice());
	// Alla cancellazione di un movimento devono essere eliminati i contro movimenti non eseguiti.
	// Questa operazione va fatta controllando che nella tabella movimenticontromovimenti
	// non siano già associati ad altri movimenti.
	// In questo caso va tolta solamente l’associazione del movimento nella tabella movimenticontromovimenti
	// cerco i contromovimenti di cui il movimento è padre
	Integer codiceMovimentoDaCancellare = entity.getId().getCodice();
	List<MovimentiContromovimenti> movimentiContromovimentisForFkPadre = movimentiContromovimentiService.findByMovimentoByFkPadre(entity);//entity.getMovimentiContromovimentisForFkPadre();
	for (MovimentiContromovimenti mc : movimentiContromovimentisForFkPadre) {
	    Movimenti contromovimento = mc.getMovimentoByFkFiglio();
	    // se il contromovimento non è stato eseguito data == null
	    if (!isEffettuato(contromovimento)) {
		// se il contromovimento non è associato ad altri movimenti allora elimino il contromovimento
		boolean almenoUno = false;
		Set<MovimentiContromovimenti> movimentiContromovimentisForFkFiglio = contromovimento.getMovimentiContromovimentisForFkFiglio();
		for (MovimentiContromovimenti movimentiContromovimenti : movimentiContromovimentisForFkFiglio) {
		    if (movimentiContromovimenti.getMovimentoByFkPadre().getId().getCodice().intValue() != codiceMovimentoDaCancellare.intValue()) {
			almenoUno = true;
			break;
		    }
		}
		if (!almenoUno) {
		    this.delete(contromovimento);
		}
	    }
	    // elimino l'associazione nella tabella movimenticontromovimenti
	    movimentiContromovimentiService.delete(mc);
	}
	// elimino le associazioni per le quali il movimento è stato creato come figlio 
	List<MovimentiContromovimenti> movimentiContromovimentisForFkFiglio = movimentiContromovimentiService.findByMovimentoByFkFiglio(entity);
	for (MovimentiContromovimenti movimentiContromovimenti : movimentiContromovimentisForFkFiglio) {
	    movimentiContromovimentiService.delete(movimentiContromovimenti);
	}
	// elimino tutti gli eventi associati
	Set<Istanzeeventi> istanzeeventis = entity.getIstanzeeventis();
	for (Istanzeeventi istanzeeventi : istanzeeventis) {
	    istanzeeventiService.delete(istanzeeventi);
	}
	if (verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_AUTORIZ_ACCESSI) && //
		entity.getTipomovimento() != null && //
		entity.getData() != null // non devo eseguire le operazioni sulle scadenze
	) {
	    boolean isProroga = BooleanUtils.toBoolean(entity.getTipomovimento().getFlagAutorizProroga());
	    boolean isPreavviso = BooleanUtils.toBoolean(entity.getTipomovimento().getFlagAutorizPreavv());
	    boolean isModifica = BooleanUtils.toBoolean(entity.getTipomovimento().getFlagAutorizModifica());
	    boolean isRinnovo = BooleanUtils.toBoolean(entity.getTipomovimento().getFlagAutorizRinnovo());
	    Integer codiceMovimentoPraticaOrigine = entity.getId().getCodice();
	    Integer codicePraticaOrigine = entity.getIstanza().getId().getCodice();
	    if (isProroga) {
		CodiceDescrizioneBean result = autorizzazioniService.rollbackAutorizzazioneCollegataProroga(codicePraticaOrigine,
			codiceMovimentoPraticaOrigine);
		if (result.getCodice().equals("KO")) {
		    throw new BusinessValidationException(result.getDescrizione());
		} else {
		    FlashMessages.getInfos().add(result.getDescrizione());
		}
	    }
	    if (isPreavviso) {
		CodiceDescrizioneBean result = autorizzazioniService.rollbackAutorizzazioneCollegataPreavviso(codicePraticaOrigine,
			codiceMovimentoPraticaOrigine);
		if (result.getCodice().equals("KO")) {
		    throw new BusinessValidationException(result.getDescrizione());
		} else {
		    FlashMessages.getInfos().add(result.getDescrizione());
		}
	    }
	    if (isModifica) {
		// FIXME
		//		    CodiceDescrizioneBean result = autorizzazioniService.rollbackAutorizzazioneCollegataModifica(codicePraticaOrigine,
		//			    codiceMovimentoPraticaOrigine);
		//		    if (result.getCodice().equals("KO")) {
		//			throw new BusinessValidationException(result.getDescrizione());
		//		    } else {
		//			FlashMessages.getInfos().add(result.getDescrizione());
		//		    }
	    }
	    if (isRinnovo) {
		// FIXME
		//		    CodiceDescrizioneBean result = autorizzazioniService.rollbackAutorizzazioneCollegataRinnovo(codicePraticaOrigine,
		//			    codiceMovimentoPraticaOrigine);
		//		    if (result.getCodice().equals("KO")) {
		//			throw new BusinessValidationException(result.getDescrizione());
		//		    } else {
		//			FlashMessages.getInfos().add(result.getDescrizione());
		//		    }
	    }
	}
    }

    @Override
    protected boolean isDeleteAllowed(Movimenti entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	// 1. Verifica che il movimento non sia legato ad una CDS
	//	if (entity.getCdss() != null && !entity.getCdss().isEmpty()) {
	//	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, "", "CDS", null));
	//	    delete = false;
	//	}
	// 2. Verifica che il movimento non sia stato generato da una commissione edilizia
	if (entity.getCommedilizieForFKMovimentoRientro() != null && !entity.getCommedilizieForFKMovimentoRientro().isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, "", "COMMISSIONIEDILIZIE_R", null));
	    delete = false;
	}
	// 3. Verifica che il movimento non sia stato generato da una commissione edilizia
	if (entity.getCommedilizieMovimento() != null && !entity.getCommedilizieMovimento().isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, "", "COMMISSIONIEDILIZIE_R", null));
	    delete = false;
	}
	// 4. Verifica che al movimento non sia collegata un'autorizzazione
	if (entity.getAutorizzazioni() != null && !entity.getAutorizzazioni().isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, "", "AUTORIZZAZIONI", null));
	    delete = false;
	}
	if (entity.getAutorizzazioniSubentri() != null && !entity.getAutorizzazioniSubentri().isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, "", "AUTORIZZAZIONI_SUBENTRI", null));
	    delete = false;
	}
	// 5. Verifica che il movimento non sia stato generato da un sorteggio
	if (entity.getSorteggidettagliomovimentis() != null && !entity.getSorteggidettagliomovimentis().isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, "", "SORTEGGIDETTAGLIOMOVIMENTI", null));
	    delete = false;
	}
	List<PecInbox> pecs = pecInboxService.findByMovimento(entity.getId().getCodice(), 0, 1);
	if (pecs != null && !pecs.isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, "", "PEC_INBOX", null));
	    delete = false;
	}
	boolean canDelete = true;
	if (verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_COMPORTAMENTI_ISTANZE)) {
	    Verticalizzazioniparametri vp = verticalizzazioniService.getVerticalizzazioniparametri(
		    WebConstants.VERTICALIZZAZIONE_COMPORTAMENTI_ISTANZE,
		    WebConstants.VERTICALIZZAZIONE_COMPORTAMENTI_ISTANZE_DIS_CANC_MOV_PROTOCOLLATI);
	    if (vp != null && StringUtils.defaultIfEmpty(vp.getValore(), "0").trim().equalsIgnoreCase("1")) {
		canDelete = false;
	    }
	}
	if (!canDelete) {
	    if (StringUtils.isNotBlank(entity.getNumeroprotocollo())) {
		_ivs.add(new InvalidValue("alert.delete.movimenti.protocollati", null, "", "", null));
		delete = false;
	    }
	}
	if (!delete) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }

    private void childDataIntegration(Movimenti entity, boolean isInsert, Set<Movimentiallegati> allegati) {

	String tipomovimento = (String) EntityUtils.getNestedProperty(entity, "tipomovimento.id.tipomovimento");
	log.debug("childDataIntegration: tipomovimento {}", tipomovimento);
	Istanze istanza = istanzeService.bindDomainObject(entity.getIstanza(), PkId.class, "id.codice");
	TipoAccessoEnum tipoAccesso = this.getTipoAccessoAllaPratica(istanza);
	// a. Aggiornamento della data di validità dell'istanza
	// BOCCI 02-02-2012 SE L'OPERATORE NON PUO' AGGIORNARE LA PRATICA (ESEMPIO OPERATORE CON RUOLO READONLY E WRITE SU MOVIMENTO) ALLORA DA' ERRORE
	// IN QUESTO CASO NON DOBBIAMO FAR ESEGUIRE IL CALCOLO DELLA TEMPISTICA DELL'ISTANZA E calcolaDataValidita
	if (tipoAccesso.equals(TipoAccessoEnum.CONSENTITO)) {
	    istanzeService.calcolaDataValidita(istanza.getId().getCodice());
	    log.debug("childDataIntegration: dopo istanzeService.calcolaDataValidita(istanza)");
	}
	// b. Se l’istanza è collegata ad una attività ricalcolo l’ultima istanza e setto i campi I_ATTIVITA.ATTIVA e I_ATTIVITA.OPERANTE legata a gestione attività
	// BOCCI 02-02-2012 SE L'OPERATORE NON PUO' AGGIORNARE LA PRATICA (ESEMPIO OPERATORE CON RUOLO READONLY E WRITE SU MOVIMENTO) ALLORA DA' ERRORE
	// IN QUESTO CASO NON DOBBIAMO FAR ESEGUIRE IL CALCOLO DELLA TEMPISTICA DELL'ISTANZA E calcolaDataValidita
	if (BooleanUtils.isTrue(entity.getTipomovimento().getFlagOperante()) || BooleanUtils.isTrue(entity.getTipomovimento().getFlagNonoperante())) {
	    this.eventPublisher.publish(new EventoIstanzaModificaOperante(istanza.getId().getCodice()));
	}
	// c. Verifica delle operazioni da svolgere sugli oneri
	this.gestisciOneri(entity, isInsert);
	log.debug("childDataIntegration: dopo gestisciOneri");
	// d. Elabora lo scadenzario per l'istanza corrente
	// ATTENZIONE  !!!! non posso lanciare l'elaborazione dell'istanza causa loop. infatti l'elaborazione riesegue il salvataggio di ogni movimento che a sua volta chiamerebbe istanzeService.elabora
	// istanzeService.elabora(istanza); // 	
	this.insertContromovimenti(entity);
	log.debug("childDataIntegration: dopo this.insertContromovimenti(entity);");
	// e. Aggiornamento degli eventuali permessi per le amministrazioni dei contro movimenti
	// importante è necessario aggiornare i permessi solamente dopo avere settato i contromovimenti
	this.aggiornaPermessi(entity);
	log.debug("childDataIntegration: dopo this.aggiornaPermessi(entity)");
	// f. collega eventuali schede dinamiche all'istanza
	this.gestisciSchedeDinamiche(entity, tipomovimento);
	log.debug("childDataIntegration: dopo his.gestisciSchedeDinamiche(entity, tipomovimento)");
	// g. gestione evento del movimento
	this.gestisciEventoMovimento(entity);
	log.debug("childDataIntegration: dopo this.gestisciEventoMovimento(entity);");
	// h. calcolo della tempistica
	// BOCCI 02-02-2012 SE L'OPERATORE NON PUO' AGGIORNARE LA PRATICA (ESEMPIO OPERATORE CON RUOLO READONLY E WRITE SU MOVIMENTO) ALLORA DA' ERRORE
	// IN QUESTO CASO NON DOBBIAMO FAR ESEGUIRE IL CALCOLO DELLA TEMPISTICA DELL'ISTANZA
	if (tipoAccesso.equals(TipoAccessoEnum.CONSENTITO)) {
	    istanzeService.calcolaTempisticaIstanza(istanza);
	    log.debug("childDataIntegration: dopo istanzeService.calcolaTempisticaIstanza(istanza);");
	}
	// i. ??????
	if (allegati != null && !allegati.isEmpty()) {
	    log.debug("childDataIntegration: inizio inserimento movimentiallegati");
	    for (Movimentiallegati movimentiallegati : allegati) {
		movimentiallegati.setMovimento(entity);
		movimentiallegatiService.insert(movimentiallegati);
	    }
	    log.debug("childDataIntegration: dopo inserimento movimentiallegati");
	}
	// j. ???
	if (verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_AUTORIZ_ACCESSI) && //
		isInsert && // 
		entity.getTipomovimento() != null && //
		entity.getData() != null // non deve essere una scadenza
	) {
	    boolean isProroga = BooleanUtils.toBoolean(entity.getTipomovimento().getFlagAutorizProroga());
	    boolean isPreavviso = BooleanUtils.toBoolean(entity.getTipomovimento().getFlagAutorizPreavv());
	    boolean isModifica = BooleanUtils.toBoolean(entity.getTipomovimento().getFlagAutorizModifica());
	    boolean isRinnovo = BooleanUtils.toBoolean(entity.getTipomovimento().getFlagAutorizRinnovo());
	    Integer codiceMovimentoPraticaOrigine = entity.getId().getCodice();
	    Integer codicePraticaOrigine = entity.getIstanza().getId().getCodice();
	    if (isProroga) {
		CodiceDescrizioneBean result = autorizzazioniService.aggiornaAutorizzazioneCollegataProroga(codicePraticaOrigine,
			codiceMovimentoPraticaOrigine);
		if (result.getCodice().equals("KO")) {
		    throw new BusinessValidationException(result.getDescrizione());
		} else {
		    FlashMessages.getInfos().add(result.getDescrizione());
		}
	    }
	    if (isPreavviso) {
		CodiceDescrizioneBean result = autorizzazioniService.aggiornaAutorizzazioneCollegataPreavviso(codicePraticaOrigine,
			codiceMovimentoPraticaOrigine);
		if (result.getCodice().equals("KO")) {
		    throw new BusinessValidationException(result.getDescrizione());
		} else {
		    FlashMessages.getInfos().add(result.getDescrizione());
		}
	    }
	    if (isModifica) {
		CodiceDescrizioneBean result = autorizzazioniService.aggiornaAutorizzazioneCollegataModifica(codicePraticaOrigine,
			codiceMovimentoPraticaOrigine);
		if (result.getCodice().equals("KO")) {
		    throw new BusinessValidationException(result.getDescrizione());
		} else {
		    FlashMessages.getInfos().add(result.getDescrizione());
		}
	    }
	    if (isRinnovo) {
		CodiceDescrizioneBean result = autorizzazioniService.aggiornaAutorizzazioneCollegataRinnovo(codicePraticaOrigine,
			codiceMovimentoPraticaOrigine);
		if (result.getCodice().equals("KO")) {
		    throw new BusinessValidationException(result.getDescrizione());
		} else {
		    FlashMessages.getInfos().add(result.getDescrizione());
		}
	    }
	}
	// k. "Se impostato tipimovimento.fkstatoistanza allora in fase di inserimento di un movimento (non in update e non in elaborazione) verrà modificato lo 
	// stato dell'istanza con quello selezionato. Se il movimento viene cancellato lo stato dell'istanza non cambierà e 
	// non tornerà allo stato precedente."
	// BOCCI 2012-03-08 BUGZILLA ID 466 3.2
	if (isInsert) {
	    Tipimovimento tm = entity.getTipomovimento();
	    if (tm != null) {
		//Statiistanza simov = tm.getStatoistanza();
		Statiistanza simov = new Statiistanza();
		if (tm.getStatoistanza() != null && StringUtils.isNotBlank(tm.getStatoistanza().getId().getCodicestato())) {
		    StatiistanzaId id = new StatiistanzaId();
		    id.setCodicestato(tm.getStatoistanza().getId().getCodicestato());
		    simov = statiistanzaService.findById(id);
		}
		if (EntityUtils.getNestedProperty(simov, "id") != null) {
		    if (StringUtils.isNotBlank(simov.getId().getCodicestato())) {
			// BOCCI-MENDICHI 11/11/2014 - SE IL MOVIMENTO IMPOSTA UNO STATO SISTEMATICAMENTE 
			// VIENE MODIFICATO NELL'ISTANZA
			istanzeService.updateStatoIstanza(istanza, simov.getId().getCodicestato());
		    }
		}
	    }
	}
    }

    private TipoAccessoEnum getTipoAccessoAllaPratica(Istanze istanza) {

	Responsabili utenteLoggato = (Responsabili) userSecurityService.getCurrentlyAuthenticatedUserDetails();
	if (utenteLoggato != null) {
	    TipoAccessoEnum tipoAccesso = istanzeService.checkAccessoIstanza(istanza, utenteLoggato);
	    log.debug("getTipoAccessoAllaPratica: tipoAccesso per l'istanza dell'operatore {} è {}", utenteLoggato.getId(), tipoAccesso);
	    return tipoAccesso;
	} else {
	    log.debug("getTipoAccessoAllaPratica: operatore è nullo acceso alla modifica dell'istanza CONSENTITO");
	}
	return TipoAccessoEnum.CONSENTITO;
    }

    /**
     * @param entity
     * @return
     */
    //    private void deleteMovimentiDisabilitati(Movimenti entity) {
    //
    //	Integer codiceistanza = (Integer) EntityUtils.getNestedProperty(entity, "istanza.id.codice");
    //	String tipomovimento = (String) EntityUtils.getNestedProperty(entity, "tipomovimento.id.tipomovimento");
    //	Integer codiceinventario = (Integer) EntityUtils.getNestedProperty(entity, "endoprocedimento.id.codice");
    //	Integer codiceamministrazione = (Integer) EntityUtils.getNestedProperty(entity, "amministrazioni.id.codice");
    //	List<TipimovimentoDis> tipimovimentoDisList = tipimovimentoDisService.findTipimovimentoDisabilitati(codiceistanza, tipomovimento,
    //		codiceinventario, codiceamministrazione, entity.getData());
    //	for (TipimovimentoDis tipimovimentoDis : tipimovimentoDisList) {
    //	    tipimovimentoDisService.delete(tipimovimentoDis);
    //	}
    //    }
    /**
     * Controlla se da un movimento deve inserire nuove schede dinamiche
     * 
     * @param entity
     * @param tipomovimento
     */
    private void gestisciSchedeDinamiche(Movimenti entity, String tipomovimento) {

	Tipimovimento tipimovimento = tipiMovimentoService.findById(new TipimovimentoId(tipomovimento));
	List<Tipimovimentidyn2modellit> tipimovimentidyn2modellits = tipimovimentidyn2modellitService.findByTipimovimento(tipimovimento);
	for (Tipimovimentidyn2modellit tipimovimentidyn2modellit : tipimovimentidyn2modellits) {
	    Movimentidyn2modellitId id = new Movimentidyn2modellitId(entity.getId().getCodice(),
		    tipimovimentidyn2modellit.getDyn2Modellit().getId().getCodice());
	    Movimentidyn2modellit modello = movimentidyn2modellitService.findById(id);
	    if (modello == null) {
		Dyn2Modellit dyn2Modellit = tipimovimentidyn2modellit.getDyn2Modellit();
		Istanzedyn2modellitId idMt = new Istanzedyn2modellitId(entity.getIstanza().getId().getCodice(), id.getFkD2mtId());
		// devo inserire anche in istanzedyn2modellit se non presente
		Istanzedyn2modellit id2mt = istanzedyn2modellitService.findById(idMt);
		if (id2mt == null) {
		    id2mt = new Istanzedyn2modellit();
		    idMt = new Istanzedyn2modellitId(entity.getIstanza().getId().getCodice(), id.getFkD2mtId());
		    id2mt.setId(idMt);
		    id2mt.setIstanza(entity.getIstanza());
		    id2mt.setDyn2Modellit(dyn2Modellit);
		    istanzedyn2modellitService.insert(id2mt);
		}
		Movimentidyn2modellit movimentidyn2modellit = new Movimentidyn2modellit();
		Movimentidyn2modellitId movimentidyn2modellitId = new Movimentidyn2modellitId(entity.getId().getCodice(),
			dyn2Modellit.getId().getCodice());
		movimentidyn2modellit.setId(movimentidyn2modellitId);
		movimentidyn2modellit.setDyn2Modellit(dyn2Modellit);
		movimentidyn2modellit.setIstanza(entity.getIstanza());
		movimentidyn2modellit.setMovimento(entity);
		movimentidyn2modellitService.insert(movimentidyn2modellit);
	    }
	}
    }

    /**
     * Si occupa di effettuare le operazioni sugli oneri
     * 
     * @param entity
     * @param isInsert
     */
    private void gestisciOneri(Movimenti entity, boolean isInsert) {

	if (isInsert) {
	    istanzeoneriService.inserisciOnereDaMovimento(entity);
	}
	istanzeoneriService.settaScadenzeOneri(entity);
	istanzeoneriService.richiedePagamentoOnereDaMovimento(entity);
	istanzeoneriService.spostaImportoOneriDaMovimento(entity);
    }

    /**
     * <ol>
     * <li>Se il movimento ha dei contromovimenti non eseguiti ma legati nella tabella movimenticontromovimenti li
     * cancella perché li deve ricalcolare</li>
     * <li>Controlla se il movimento genera dei contromovimenti
     * <ol>
     * <li>Se ci sono e questi sono stati creati e già associati nella tabella movimenticontromovimenti allora non li
     * considera come da eseguire</li>
     * <li>Verifica se sono stati disabilitati</li>
     * <li>Per i movimenti da eseguire verifica se sono stati eseguiti dei contromovimenti con quelle caratteristiche e
     * li associa nella tabella movimenticontromovimenti</li>
     * <li>Per i movimenti da eseguire verifica se sono stati già creati dei contromovimenti con quelle caratteristiche
     * e li associa nella tabella movimenticontromovimenti al movimento corrente</li>
     * <li>Se non sono stati eseguiti li genera con data nulla e operatore nullo, data scadenza calcolata</li>
     * </ol>
     * </li>
     * </ol>
     * 
     * 
     * @param entity
     */
    private void insertContromovimenti(Movimenti entity) {

	// questo metodo generava deadlock su db. è stato corretto aggiungendo i seguenti indici (by fabrizioc)
	// CREATE INDEX IDX_MOV_CONTROMOV_001 ON MOVIMENTI_CONTROMOVIMENTI (IDCOMUNE,CODICEMOVIMENTO);
	// CREATE INDEX IDX_MOV_CONTROMOV_002 ON MOVIMENTI_CONTROMOVIMENTI (IDCOMUNE,CODICECONTROMOVIMENTO);
	entity = bindDomainObject(entity, PkId.class, "id.codice");
	// 1. se il movimento ha dei contromovimenti non eseguiti ma legati nella tabella movimenticontromovimenti li
	// cancella perché li deve ricalcolare
	List<Movimenti> contromovimentiNonEffettuati = this.findContromovimentidaEffettuare(entity);
	log.debug("insertContromovimenti: da cancellare {} movimenti non effettutati", contromovimentiNonEffettuati.size());
	Stack<Integer> codiciContromovimentoEliminati = new Stack<Integer>();
	if (!contromovimentiNonEffettuati.isEmpty()) {
	    for (Movimenti movimenti : contromovimentiNonEffettuati) {
		// cancello tutti i contromovimenti tranne quelli disabilitati
		if (BooleanUtils.isFalse(movimenti.getFlagDisabilitato()) && // 
			BooleanUtils.isFalse(movimenti.getFlgModManualedata())) { // se la scadenza è stata modificata manualmente
										  // non deve ricalcolare i contromovimenti
		    List<MovimentiContromovimenti> mcss = movimentiContromovimentiService.findByMovimentoByFkFiglio(movimenti);
		    if (!mcss.isEmpty()) {
			for (MovimentiContromovimenti movimentiContromovimenti : mcss) {
			    movimentiContromovimentiService.delete(movimentiContromovimenti);
			}
		    }
		    IstanzeeventiFilter filter = new IstanzeeventiFilter();
		    filter.setMovimenti(movimenti);
		    List<Istanzeeventi> evenIstanzeeventis = istanzeeventiService.findByFilter(filter, null, null);
		    for (Istanzeeventi istanzeeventi : evenIstanzeeventis) {
			istanzeeventiService.delete(istanzeeventi);
		    }
		    codiciContromovimentoEliminati.add(movimenti.getId().getCodice());
		    this.delete(movimenti);
		}
	    }
	}
	// 1. controlla se il movimento genera dei contromovimenti
	List<Movimenti> contromovimenti = this.elaboraContromovimenti(entity);
	log.debug("insertContromovimenti: contromovimenti da eseguire {}", contromovimenti.size());
	if (contromovimenti.isEmpty()) {
	    return;
	}
	Verticalizzazioniparametri elaboraMovimentiantecedentiLaChiusura = verticalizzazioniService.getVerticalizzazioniparametri(
		WebConstants.VERTICALIZZAZIONE_COMPORTAMENTI_ISTANZE, WebConstants.VERTICALIZZAZIONE_COMPORTAMENTI_ISTANZE_ELAB_MOVIMENTI_PREC_CHIUS);
	String elaboraMovimentiantecedentiLaChiusuraStr = "N";
	if (elaboraMovimentiantecedentiLaChiusura != null && StringUtils.isNotBlank(elaboraMovimentiantecedentiLaChiusura.getValore())) {
	    elaboraMovimentiantecedentiLaChiusuraStr = elaboraMovimentiantecedentiLaChiusura.getValore();
	}
	if (StringUtils.defaultString(elaboraMovimentiantecedentiLaChiusuraStr).equalsIgnoreCase("S")) {
	    // verifico che l'istanza non sia chiusa
	    Movimenti movchiu = findMovimentoChiusuraIstanza(entity.getIstanza().getId().getCodice());
	    if (movchiu != null) {
		// se l'istanza è chiusa non devo inserire i contromovimenti dei movimenti con data anteriore a quella della chiusura
		if (!entity.getTipomovimento().getId().getTipomovimento().equals(movchiu.getTipomovimento().getId().getTipomovimento())) {
		    // se non è il movimento di chiusura stesso verifico se la sua data sia antecedente o uguale a quella di chiusura istanza
		    Date datamovchiusura = movchiu.getData();
		    Date datamovimento = entity.getData();
		    int diff = Utilities.compareDates(datamovimento, datamovchiusura);
		    if (diff < 0) {
			// la data del movimento è antecedente a quello di chiusura
			return;
		    }
		    //		if (diff == 0) {
		    //		    // .. nel caso di movimenti inseriti lo stesso giorno valuto anche l'ordine di inserimento
		    //		    Integer ordineInserimento = entity.getOrdineInserimento() == null ? 0 : entity.getOrdineInserimento();
		    //		    Integer ordineInserimentoChiu = movchiu.getOrdineInserimento() == null ? 0 : movchiu.getOrdineInserimento();
		    //		    if (ordineInserimento.intValue() < ordineInserimentoChiu.intValue()) {
		    //			return;
		    //		    }
		    //		}
		}
	    }
	}
	List<Movimenti> daFare = new ArrayList<Movimenti>();
	daFare.addAll(contromovimenti);
	log.debug("insertContromovimenti: daFare {}", daFare.size());
	List<Movimenti> daTogliere = new ArrayList<Movimenti>();
	// 2. se ci sono contromovimenti e questi sono stati creati e già associati nella tabella
	// movimenticontromovimenti allora non li
	// considera come da eseguire
	List<MovimentiContromovimenti> mcs = movimentiContromovimentiService.findByMovimentoByFkPadre(entity);
	Set<Integer> codiciContormovimentiDaSlegare = new TreeSet<Integer>();
	if (!mcs.isEmpty()) {
	    for (Movimenti movimenti : contromovimenti) {
		boolean trovato = false;
		for (MovimentiContromovimenti movimentiContromovimenti : mcs) {
		    Movimenti controMovimento = movimentiContromovimenti.getMovimentoByFkFiglio();
		    if (checkMovimentoContromovimento(movimenti, controMovimento)) {
			// tra quelli eseguiti verifico se sono della stessa data e con ordine successivo
			// se si allora li sgancio come contromovimenti e l'inserisco più avanti
			if (Utilities.compareDates(entity.getData(), controMovimento.getData()) == 0
				&& (movimenti.getTransientSePrecedente() == null || !movimenti.getTransientSePrecedente().booleanValue())) {
			    Integer ordineInserimentoPrincipale = entity.getOrdineInserimento() == null ? 0 : entity.getOrdineInserimento();
			    Integer ordineContro = controMovimento.getOrdineInserimento() == null ? 0 : controMovimento.getOrdineInserimento();
			    if (ordineInserimentoPrincipale.compareTo(ordineContro) > 0) {
				// se si allora li sgancio come contromovimenti e l'inserisco più avanti
				codiciContormovimentiDaSlegare.add(movimentiContromovimenti.getId().getCodice());
				continue;
			    }
			}
			trovato = true;
			break;
		    }
		}
		if (trovato) {
		    daTogliere.add(movimenti);
		}
	    }
	}
	if (!codiciContormovimentiDaSlegare.isEmpty()) {
	    for (Integer codiceMCMid : codiciContormovimentiDaSlegare) {
		// elimino il collegamento come contromovimento di uno eseguito con la stessa data ma ordine successivo
		MovimentiContromovimenti mc = movimentiContromovimentiService.findById(new PkId(codiceMCMid));
		movimentiContromovimentiService.delete(mc);
	    }
	    movimentiDAO.flush();
	}
	daFare.removeAll(daTogliere);
	log.debug("insertContromovimenti: daFare-cmov creati {}", daFare.size());
	daTogliere = new ArrayList<Movimenti>();
	//
	// 3. Per i movimenti da eseguire verifica se sono stati eseguiti dei contromovimenti con quelle caratteristiche
	// e li associa nella tabella movimenticontromovimenti
	// Restriction comune per i movimenti dell'istanza
	FilterRestriction istanza = new FilterRestriction();
	istanza.addFilterField(FilterUtils.equals("istanzaId", entity.getIstanza().getId().getCodice(), Integer.class));
	// Restriction comune per eliminare i movimenti che non siano già stati associati alla tabella
	// movimenticontromovimenti
	// FilterRestriction movimentiNonAssociati = new FilterRestriction();
	// movimentiNonAssociati.addFilterField(FilterUtils.isEmpty("movimentiContromovimentisForFkFiglio"));
	FilterTable ft = null;
	for (Movimenti movimento : daFare) {
	    ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	    ft.addRestriction(istanza);
	    FilterRestriction movimentoFr = new FilterRestriction();
	    movimentoFr.addFilterField(FilterUtils.isNotNull("data"));
	    if (movimento.getTransientSePrecedente() == null || movimento.getTransientSePrecedente().booleanValue() == false) {
		// SE IL TIPOCONTROMOVIMENTO NON PREVEDE DI ESSERE ESEGUITO CON DATA ANTECEDENTE
		// ALLORA DEVO FILTRARE ANCHE PER LA DATA >= A QUELLA DEL MOVIMENTO
		movimentoFr.addFilterField(FilterUtils.greaterEqual("data", entity.getData(), Date.class));
		if (entity.getOrdineInserimento() != null) {
		    movimentoFr.addFilterField(FilterUtils.greater("ordineInserimento", entity.getOrdineInserimento(), Integer.class));
		}
	    }
	    String tipoMovimento = movimento.getTipomovimento().getId().getTipomovimento();
	    movimentoFr.addFilterField(FilterUtils.equals("tipomovimentoId", tipoMovimento, String.class));
	    if (EntityUtils.getNestedProperty(movimento.getAmministrazioni(), "id.codice") != null) {
		movimentoFr
			.addFilterField(FilterUtils.equals("amministrazioniId", movimento.getAmministrazioni().getId().getCodice(), Integer.class));
	    } else {
		movimentoFr.addFilterField(FilterUtils.isNull("amministrazioniId"));
	    }
	    if (EntityUtils.getNestedProperty(movimento.getEndoprocedimento(), "id.codice") != null) {
		movimentoFr
			.addFilterField(FilterUtils.equals("endoprocedimentoId", movimento.getEndoprocedimento().getId().getCodice(), Integer.class));
	    } else {
		movimentoFr.addFilterField(FilterUtils.isNull("endoprocedimentoId"));
	    }
	    //	    if (movimento.getEsito() != null) {
	    //		movimentoFr.addFilterField(FilterUtils.equals("esito", movimento.getEsito(), Boolean.class));
	    //	    }
	    ft.addRestriction(movimentoFr);
	    // ft.addRestriction(movimentiNonAssociati);
	    List<Movimenti> movimentiEffettuati = movimentiDAO.findByFilterTable(ft);
	    for (Movimenti movimenti : movimentiEffettuati) {
		log.debug("insertContromovimenti: daFare-eseguiti è stato trovato il contromovimento {}",
			EntityUtils.getNestedProperty(movimenti, "tipomovimento.id.tipomovimento"));
		// quelli trovati li associo nella tabella movimenti_contromovimenti
		List<MovimentiContromovimenti> cms = movimentiContromovimentiService.findByMovimentoByFkPadreAndFkFiglio(entity.getId().getCodice(),
			movimenti.getId().getCodice());
		if (cms.isEmpty()) {
		    MovimentiContromovimenti mc = new MovimentiContromovimenti();
		    mc.setMovimentoByFkPadre(entity);
		    mc.setMovimentoByFkFiglio(movimenti);
		    movimentiContromovimentiService.insert(mc);
		    log.debug("insertContromovimenti: daFare-eseguiti è stato associato il contromovimento {}",
			    EntityUtils.getNestedProperty(movimenti, "tipomovimento.id.tipomovimento"));
		}
		// e li segno come fatti
		daTogliere.add(movimento);
	    }
	}
	daFare.removeAll(daTogliere);
	log.debug("insertContromovimenti: daFare-eseguiti e non associati creati {}", daFare.size());
	daTogliere = new ArrayList<Movimenti>();
	// 4. Per i movimenti da eseguire verifica se sono stati già creati dei contromovimenti con quelle
	// caratteristiche e li associa nella tabella movimenticontromovimenti al movimento corrente
	ft = null;
	for (Movimenti movimento : daFare) {
	    ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	    ft.addRestriction(istanza);
	    FilterRestriction movimentoFr = new FilterRestriction();
	    movimentoFr.addFilterField(FilterUtils.isNull("data"));
	    String tipoMovimento = movimento.getTipomovimento().getId().getTipomovimento();
	    movimentoFr.addFilterField(FilterUtils.equals("tipomovimentoId", tipoMovimento, String.class));
	    if (EntityUtils.getNestedProperty(movimento.getAmministrazioni(), "id.codice") != null) {
		movimentoFr
			.addFilterField(FilterUtils.equals("amministrazioniId", movimento.getAmministrazioni().getId().getCodice(), Integer.class));
	    } else {
		movimentoFr.addFilterField(FilterUtils.isNull("amministrazioniId"));
	    }
	    if (EntityUtils.getNestedProperty(movimento.getEndoprocedimento(), "id.codice") != null) {
		movimentoFr
			.addFilterField(FilterUtils.equals("endoprocedimentoId", movimento.getEndoprocedimento().getId().getCodice(), Integer.class));
	    } else {
		movimentoFr.addFilterField(FilterUtils.isNull("endoprocedimentoId"));
	    }
	    // if (movimento.getEsito() != null) {
	    // movimentoFr.addFilterField(FilterUtils.equals("esito", movimento.getEsito(), Boolean.class));
	    // } else {
	    // movimentoFr.addFilterField(FilterUtils.isNull("esito"));
	    // }
	    ft.addRestriction(movimentoFr);
	    FilterRestriction movimentiNonAssociati = new FilterRestriction();
	    movimentiNonAssociati.addFilterField(FilterUtils.isNotEmpty("movimentiContromovimentisForFkFiglio"));
	    ft.addRestriction(movimentiNonAssociati);
	    List<Movimenti> movimentiEffettuati = movimentiDAO.findByFilterTable(ft);
	    for (Movimenti movimenti : movimentiEffettuati) {
		// quelli trovati li associo nella tabella movimenti_contromovimenti
		List<MovimentiContromovimenti> cms = movimentiContromovimentiService.findByMovimentoByFkPadreAndFkFiglio(entity.getId().getCodice(),
			movimenti.getId().getCodice());
		if (cms.isEmpty()) {
		    MovimentiContromovimenti mc = new MovimentiContromovimenti();
		    mc.setMovimentoByFkPadre(entity);
		    mc.setMovimentoByFkFiglio(movimenti);
		    movimentiContromovimentiService.insert(mc);
		}
		// e li segno come fatti
		daTogliere.add(movimento);
	    }
	}
	daFare.removeAll(daTogliere);
	log.debug("insertContromovimenti: daFare-comv creati non eseguiti {}", daFare.size());
	// 5. se non sono stati eseguiti li genera con data nulla e operatore nullo, data scadenza calcolata dalla
	// funzione elabora contromovimenti
	for (Movimenti movimenti : daFare) {
	    //1. Setto l'istanza
	    movimenti.setIstanza(entity.getIstanza());
	    //2. Setto eventualmente il codicemovimento da riutilizzare
	    Integer codiceMovimento = null;
	    if (!codiciContromovimentoEliminati.isEmpty()) {
		codiceMovimento = codiciContromovimentoEliminati.pop();
	    }
	    if (codiceMovimento != null) {
		movimenti.getId().setCodice(codiceMovimento);
	    }
	    //3. Inserisco la scadenza
	    this.insertScadenza(movimenti);
	    //4. Verifico se inserire il legame tra movimento padre e figlio
	    int cms = movimentiContromovimentiService.countByMovimentoByFkPadreAndFkFiglio(entity.getId().getCodice(), movimenti.getId().getCodice());
	    if (cms == 0) {
		MovimentiContromovimenti mc = new MovimentiContromovimenti();
		mc.setMovimentoByFkPadre(entity);
		mc.setMovimentoByFkFiglio(movimenti);
		movimentiContromovimentiService.insert(mc);
	    }
	}
    }

    @Override
    public void notificaStc(Movimenti entity) {

	// REDMINE #38 Notifica automatica, non aggancia la pratica destinataria se questa era la mittente della prima notifica
	// la notifica automatica viene eseguita solamente se non siamo in caso di inserimento da STC. In questo caso infatti viene eseguita in un thread a parte (vedi segnalazione)
	IstanzeBusinessRules rules = (IstanzeBusinessRules) SigeproBusinessRules.getClassRules(IstanzeBusinessRules.class);
	boolean isInserimentoDaStc = false;
	if (rules != null) {
	    isInserimentoDaStc = rules.getCustomRule(IstanzeBusinessRules.CustomRuleEnum.inserimentoDaStcDiretto.name());
	    if (!isInserimentoDaStc) {
		isInserimentoDaStc = rules.getCustomRule(IstanzeBusinessRules.CustomRuleEnum.inserimentoDaStc.name());
	    }
	}
	movimentiDAO.refreshEntity(entity);
	Integer codiceIstanza = entity.getIstanza().getId().getCodice();
	Integer codiceMovimento = entity.getId().getCodice();
	String codiceComune = entity.getIstanza().getComune().getCodicecomune();
	String riferimenti = getMessaggioRiferimenti(codiceIstanza, codiceMovimento);
	MovimentoDaNotificare isNotificaSTC = this.isMovimentoDaNotificareSTC(codiceMovimento);
	log.debug("operazioniAutomatiche:prima di eseguire la notifica STC {}, isNotificaSTC {}", riferimenti, isNotificaSTC);
	// 1. verifica parametri NOTIFICA STC
	if (isNotificaSTC.equals(MovimentoDaNotificare.SI)) {
	    log.debug("operazioniAutomatiche:prima di eseguire la notifica STC {}, isInserimentoDaStc {}", riferimenti, isInserimentoDaStc);
	    if (!isInserimentoDaStc) {
		String tipoMovimento = entity.getTipomovimento().getId().getTipomovimento();
		Integer codiceAmministrazione = null;
		if (entity.getAmministrazioni() != null && entity.getAmministrazioni().getId() != null
			&& entity.getAmministrazioni().getId().getCodice() != null) {
		    codiceAmministrazione = entity.getAmministrazioni().getId().getCodice();
		}
		log.debug("operazioniAutomatiche:prima di eseguire la notifica STC {}, codice amministrazione {}", riferimenti,
			codiceAmministrazione);
		TipimovStcMapping mapping = tipimovStcMappingService.findNotificheAutomaticheByTipimovimentoAndAmministrazione(tipoMovimento,
			codiceAmministrazione);
		// Se mapping per codiceAmministrazione allora prendo quello
		// Se non matcha e c'è una riga sola allora vecchia logica
		// se più righe solleva eccezione
		log.debug("operazioniAutomatiche:prima di eseguire la notifica STC {}, mapping {}", riferimenti, mapping);
		if (mapping != null) {
		    boolean eseguiNotifica = true;
		    log.debug("notificaStc: il movimento è configurato per effettuare la notifica automatica {}", riferimenti);
		    codiceAmministrazione = mapping.getAmministrazioni().getId().getCodice();
		    entity.setAmministrazioniStc(amministrazioniService.findById(new PkId(codiceAmministrazione)));
		    log.debug("notificaStc: cerco i mapping {}, tm={}, amm={}", new Object[] { riferimenti, tipoMovimento, codiceAmministrazione });
		    /////////////////////////////////////////////////////////////////////////////////////////////////
		    if (BooleanUtils.isTrue(mapping.getFlagRifpratStorica())) {
			log.debug("notificaStc# verifico se la pratica è collegata ad un altra {}", riferimenti);
			try {
			    RichiestaPraticaFromNotificaPraticaStoricaParams params = new RichiestaPraticaFromNotificaPraticaStoricaParams(
				    this.stcService, this.verticalizzazioniService, entity);
			    RichiestaPraticaCollegataResponse rpc = RichiestaPraticaCollegataFactory.fromNotificaPraticaStoricaParams(params)
				    .getPraticaCollegata();
			    if (rpc != null) {
				if (rpc.getDettaglioErrore() != null && !rpc.getDettaglioErrore().isEmpty()) {
				    String dettaglioErrore = "";
				    for (ErroreType errore : rpc.getDettaglioErrore()) {
					dettaglioErrore += StringUtils.defaultIfEmpty(errore.getNumeroErrore(), "ND") + "-" +
							   errore.getDescrizione() + "\n";
				    }
				    log.error("notificaStc: {}, errore {}, inserisco l'evento", new Object[] { riferimenti, dettaglioErrore });
				    try {
					movimentiDAO.commit();
					istanzeeventiService.insert(StringUtils
						.left("Attenzione! Non è stata trovata la pratica collegata per questa istanza. Dettaglio errori: " +
						      dettaglioErrore, 4000),
						IstanzeeventiConstants.CATEGORIA_STC_NAUT, entity, null);
					movimentiDAO.flush();
					movimentiDAO.commit();
				    } catch (Exception ex) {
					log.error("notificaStc(): {}, non è stato possibile inserire l'evento a causa di: {}", riferimenti,
						ex.getMessage());
				    }
				    eseguiNotifica = false;
				}
			    } else {
				try {
				    log.error("notificaStc: {}, pratica ncolelgata nulla, inserisco l'evento", new Object[] { riferimenti });
				    movimentiDAO.commit();
				    istanzeeventiService.insert(StringUtils.left(
					    "Attenzione non è stata trovata la pratica collegata per questa istanza. pratica collegata nulla", 4000),
					    IstanzeeventiConstants.CATEGORIA_STC_NAUT, entity, null);
				    movimentiDAO.flush();
				    movimentiDAO.commit();
				} catch (Exception ex) {
				    log.error("notificaStc(): non è stato possibile inserire l'evento a causa di: {}", ex.getMessage());
				}
				eseguiNotifica = false;
			    }
			} catch (Exception e) {
			    log.error("notificaStc# errore durante la verifica della pratica collegata: {},{}",
				    new Object[] { riferimenti, e.getMessage() }, e);
			    try {
				movimentiDAO.commit();
				istanzeeventiService.insert(StringUtils.left(
					"Attenzione non è stata trovata la pratica collegata per questa istanza. errore: " + e.getMessage(), 4000),
					IstanzeeventiConstants.CATEGORIA_STC_NAUT, entity, null);
				movimentiDAO.flush();
				movimentiDAO.commit();
			    } catch (Exception ex) {
				log.error("notificaStc(): non è stato possibile inserire l'evento a causa di: {}", ex.getMessage());
			    }
			    eseguiNotifica = false;
			}
		    }
		    /////////////////////////////////////////////////////////////////////////////////////////////////
		    movimentiDAO.update(entity);
		    movimentiDAO.commit();
		    EsitoCreaZipLogico esito = this.creaZipLogico(mapping, codiceIstanza, entity);
		    log.debug("notificaStc: {} creaZipLogico prosegui {}, ziplogico {}",
			    new Object[] { riferimenti, esito.isProsegui(), esito.isZipLogico() });
		    eseguiNotifica = esito.isProsegui();
		    movimentiDAO.refreshEntity(entity);
		    // 3. CREAZIONE DEGLI ALLEGATI CONFIGURATI PER IL MOVIMENTO
		    if (eseguiNotifica && BooleanUtils.toBoolean(mapping.getFlagCreainviaAllegati())) {
			log.debug("notificaStc(): creazione dell'allegato {}", riferimenti);
			eseguiNotifica = this.creaAllegato(codiceMovimento, tipoMovimento, codiceIstanza, esito, entity);
		    }
		    // Se flag convertipdf è attivo i documenti .odt e .rtf del movimento di notifica vengono convertiti in pdf e aggiornati sul DB
		    if (eseguiNotifica && BooleanUtils.toBoolean(mapping.getFlagConvertipdf())) {
			movimentiDAO.flush();
			movimentiDAO.commit();
			movimentiDAO.flush();
			movimentiDAO.clear();
			log.debug("notificaStc(): conversione in PDF dell'allegato {}", riferimenti);
			List<Movimentiallegati> allTemp = movimentiallegatiService.findByMovimento(codiceMovimento);
			for (Movimentiallegati movimentiallegati : allTemp) {
			    Oggetti oggetto = oggettiService.findById(new PkId(movimentiallegati.getOggetto().getId().getCodice()));
			    String nomeFile = oggetto.getNomefile();
			    log.debug("notificaStc(): {} conversione dell'allegato dell'allegato {}", riferimenti, nomeFile);
			    if (!nomeFile.toLowerCase().endsWith(".pdf")) {
				byte[] oggettoPDFByte = oggettiService.trasformInPdf(oggetto, null);
				oggetto.setOggetto(oggettoPDFByte);
				nomeFile = oggetto.getNomefile().substring(0, oggetto.getNomefile().lastIndexOf(".")) + ".pdf";
				oggetto.setNomefile(nomeFile);
				oggettiService.update(oggetto);
				movimentiDAO.flush();
				movimentiDAO.commit();
				movimentiDAO.flush();
			    }
			}
		    }
		    movimentiDAO.commit();
		    log.debug("notificaStc(): {} notifico {} e protocollo {}",
			    new Object[] { riferimenti, eseguiNotifica, mapping.getFlagProtocolla() });
		    if (eseguiNotifica && BooleanUtils.toBoolean(mapping.getFlagProtocolla())) {
			eseguiNotifica = this.protocollaMovimentoDaNotificaAutomatica(entity, mapping, eseguiNotifica, codiceComune);
		    }
		    // 4. NOTIFICA TRAMITE STC
		    log.debug("notificaStc(): {} notifica dell'attività {}", riferimenti, eseguiNotifica);
		    if (eseguiNotifica) {
			this.notificaAutomaticaAttivita(entity);
		    }
		    // ne posso notificare solamente uno		    
		}
	    } else {
		// end redmine#38
		String key = notificheAutomaticheAsincroneHelper.getStackKey(ORMHelper.getIdcomuneAlias(), ORMHelper.getIdcomune(), codiceIstanza);
		List<Integer> codiciMovimento = notificheAutomaticheAsincroneHelper.getStack().get(key);
		log.debug("operazioniAutomatiche: notifica asincrona la notifica STC {}, key {}, codici movimento {}",
			new Object[] { riferimenti, key, codiciMovimento });
		if (codiciMovimento == null) {
		    codiciMovimento = new ArrayList<Integer>();
		    codiciMovimento.add(entity.getId().getCodice());
		    log.debug(
			    "operazioniAutomatiche: notifica asincrona la notifica STC {}, key {}, codici movimento nulli aggiungo il codicemovimento {}",
			    new Object[] { riferimenti, key, codiciMovimento });
		    notificheAutomaticheAsincroneHelper.getStack().put(key, codiciMovimento);
		} else {
		    if (!codiciMovimento.contains(codiceMovimento)) {
			codiciMovimento.add(codiceMovimento);
			notificheAutomaticheAsincroneHelper.getStack().put(key, codiciMovimento);
			log.debug(
				"operazioniAutomatiche: notifica asincrona la notifica STC {}, key {}, codici movimento non nulli aggiungo il codicemovimento {}",
				new Object[] { riferimenti, key, codiciMovimento });
		    }
		}
	    }
	    // end redmine#38
	}
    }

    private boolean creaAllegato(Integer codiceMovimento, String tipoMovimento, Integer codiceIstanza, EsitoCreaZipLogico esito, Movimenti entity) {

	boolean eseguiNotifica = true;
	int allegati = movimentiallegatiService.countByMovimento(codiceMovimento);
	String riferimenti = getMessaggioRiferimenti(codiceIstanza, codiceMovimento);
	// TODO VERIFICARE PERCHE' PUR CON ERRORE DELLA GENERAZIONE ALLEGATI LA NOTIFICA AVVIENE LO STESSO
	if (allegati == 0 && StringUtils.isNotBlank(tipoMovimento)) {
	    log.debug("Crea allegato {}", riferimenti);
	    List<Tipimovimentodoctipo> tipimovimentodoctipos = tipimovimentodoctipoService.findByTipoMovimento(tipoMovimento);
	    for (Tipimovimentodoctipo tipimovimentodoctipo : tipimovimentodoctipos) {
		Integer codiceLettera = (Integer) EntityUtils.getNestedProperty(tipimovimentodoctipo, "id.codicelettera");
		log.debug("Crea allegato {}, codicelettera {}", riferimenti, codiceLettera);
		if (codiceLettera != null) {
		    Letteretipo lettera = letteretipoService.findById(new PkId(codiceLettera));
		    if (lettera != null) {
			Integer codiceOggetto = (Integer) EntityUtils.getNestedProperty(lettera.getFile(), "id.codice");
			if (codiceOggetto != null) {
			    if (lettera.getFile().getNomefile().endsWith(".rtf") || lettera.getFile().getNomefile().toLowerCase().endsWith(".odt")) {// Creazione allegato in modalità STANDARD o lettera tipo formato ODT
				try {
				    log.debug(
					    "notificaStc: prima di eseguire la chiamata al servizio di creazione allegato documentMergeService.insertAllegatoDaDocumentoTipo(cl={},{}), esito.isZipLogico() {}",
					    new Object[] { codiceLettera, riferimenti, esito.isZipLogico() });
				    if (esito.isZipLogico()) {
					movimentiallegatiService.createDocumentoConLink(entity, codiceLettera, new DocumentiHelper(),
						esito.isZipLogico());
				    } else {
					movimentiallegatiService.createAndInsertMovimentoAllegato(lettera, codiceIstanza, codiceMovimento);
				    }
				} catch (Exception e) {
				    log.error("notificaStc: " + riferimenti +
					      " La chiamata al servizio di generazione allegato ha tornato un errore di tipo: " + e.getMessage(),
					    e);
				    try {
					movimentiDAO.commit();
					istanzeeventiService.insert(
						StringUtils.left("Attenzione non è stato possibile generare l'allegato [" + codiceLettera + "," +
								 lettera.getDescrizione() + "] del movimento [" + tipoMovimento + "] a causa di: " +
								 e.getMessage(),
							4000),
						IstanzeeventiConstants.CATEGORIA_STC_IA, entity, null);
					movimentiDAO.flush();
					movimentiDAO.commit();
				    } catch (Exception ex) {
					log.error("notificaStc(): non è stato possibile inserire l'evento a causa di: " + ex.getMessage(), ex);
				    }
				    eseguiNotifica = false;
				    break;
				}
			    } else {
				log.debug("Crea allegato {}, inserisco l'oggetto con codiceoggetto {} non rtf o odt", riferimenti, codiceOggetto);
				// nel caso che la lettere non sia RTF o ODT allego semplicemento il file così come è senza fare sostituzioni
				Oggetti template = oggettiService.findById(new PkId(codiceOggetto));
				Movimentiallegati movimentiallegati = new Movimentiallegati();
				Movimenti movimento = this.findById(new PkId(codiceMovimento));
				movimentiallegati.setMovimento(movimento);
				Oggetti oggetto = new Oggetti();
				oggetto.setOggetto(template.getOggetto());
				oggetto.setNomefile(template.getNomefile());
				oggettiService.insert(oggetto);
				movimentiallegati.setOggetto(oggetto);
				movimentiallegati.setDescrizione(lettera.getDescrizione());
				movimentiallegatiService.insert(movimentiallegati);
				movimentiDAO.flush();
				movimentiDAO.commit();
				movimentiDAO.flush();
				log.debug("Crea allegato {}, inserisco l'oggetto non rtf o odt con codice {}, nomefile {}",
					new String[] { riferimenti, String.valueOf(oggetto.getId().getCodice()), oggetto.getNomefile() });
			    }
			}
		    }
		}
	    }
	}
	return eseguiNotifica;
    }

    private EsitoCreaZipLogico creaZipLogico(TipimovStcMapping mapping, Integer codiceIstanza, Movimenti entity) {

	log.debug("#creaZipLogico..");
	boolean prosegui = true;
	Integer codiceMovimento = entity.getId().getCodice();
	String riferimenti = getMessaggioRiferimenti(codiceIstanza, codiceMovimento);
	EsitoCreaZipLogico ret = new EsitoCreaZipLogico();
	if (BooleanUtils.toBoolean(mapping.getFlgCreaZipLogico())) {
	    log.debug("#creaZipLogico..BEGIN {}", riferimenti);
	    DocumentiHelper documentiHelper = new DocumentiHelper();
	    //documenti endo
	    if (BooleanUtils.toBoolean(mapping.getFlagAllegaDocumentiEndo())) {
		List<Istanzeprocedimenti> ips = istanzeprocedimentiService.findByIstanze(codiceIstanza);
		for (Istanzeprocedimenti procedimento : ips) {
		    List<IstanzeallegatiDTO> ialls = istanzeallegatiService.findIstanzeallegatiDTOByIstanzaAndEndo(codiceIstanza,
			    procedimento.getId().getCodiceinventario(), TipoRicercaDocumentoEnum.RICERCA_TUTTI);
		    if (!ialls.isEmpty()) {
			List<IstanzeallegatiDTO> iallegatis = new ArrayList<IstanzeallegatiDTO>();
			List<ChiaveValoreBean<String, List<IstanzeallegatiDTO>>> listDocumentoEndo = new ArrayList<ChiaveValoreBean<String, List<IstanzeallegatiDTO>>>();
			for (IstanzeallegatiDTO istanzeallegati : ialls) {
			    if (istanzeallegati.getCodiceOggetto() != null) {
				istanzeallegati.setTransientSegnaPerInvio(true);
				iallegatis.add(istanzeallegati);
				ChiaveValoreBean<String, List<IstanzeallegatiDTO>> bean = new ChiaveValoreBean<String, List<IstanzeallegatiDTO>>();
				bean.setChiave(procedimento.getDescrizioneAndAmministrazione());
				bean.setValore(iallegatis);
				listDocumentoEndo.add(bean);
			    }
			}
			if (!listDocumentoEndo.isEmpty()) {
			    documentiHelper.getDocumentiEndoprocedimentiList().addAll(listDocumentoEndo);
			}
		    }
		}
	    }
	    //documenti istanza
	    if (BooleanUtils.toBoolean(mapping.getFlagAllegaDocumentiIstanza())) {
		List<DocumentiistanzaDTO> list = new ArrayList<DocumentiistanzaDTO>();
		list = documentiistanzaService.findDocumentiistanzaDTOByIstanza(codiceIstanza, Boolean.FALSE);
		List<DocumentiistanzaDTO> list2 = new ArrayList<DocumentiistanzaDTO>();
		list2 = documentiistanzaService.findDocumentiistanzaDTOByIstanza(codiceIstanza, Boolean.TRUE);
		list.addAll(list2);
		List<DocumentiistanzaDTO> result = new ArrayList<DocumentiistanzaDTO>();
		List<ChiaveValoreBean<String, List<DocumentiistanzaDTO>>> documentiIstanzaList = new ArrayList<ChiaveValoreBean<String, List<DocumentiistanzaDTO>>>();
		for (DocumentiistanzaDTO documentiistanza : list) {
		    if (documentiistanza.getCodiceOggetto() != null) {
			documentiistanza.setTransientSegnaPerInvio(true);
			result.add(documentiistanza);
		    }
		}
		if (!result.isEmpty()) {
		    ChiaveValoreBean<String, List<DocumentiistanzaDTO>> bean = new ChiaveValoreBean<String, List<DocumentiistanzaDTO>>();
		    bean.setChiave(String.valueOf(codiceIstanza));
		    bean.setValore(result);
		    documentiIstanzaList.add(bean);
		    documentiHelper.setDocumentiIstanzaList(documentiIstanzaList);
		}
	    }
	    //documenti pratica
	    ////
	    try {
		log.debug("#creaZipLogico..inserisco zip logico {}", riferimenti);
		movimentiZipLogicoService.insertZipLogico(entity.getId().getCodice(), documentiHelper);
		movimentiDAO.flush();
		movimentiDAO.commit();
		ret.setZipLogico(true);
	    } catch (Exception e) {
		prosegui = false;
		log.error("Non è stato possibile creare lo zip logico {} - {}", new Object[] { riferimenti, e.getMessage() }, e);
		movimentiDAO.commit();
		istanzeeventiService.insert(StringUtils.left(
			"Attenzione non è stato possibile generare lo zip logico per il movimento [" + entity + "] a causa di: " + e.getMessage(),
			4000), IstanzeeventiConstants.CATEGORIA_STC_IA, entity, null);
		movimentiDAO.flush();
		movimentiDAO.commit();
		throw new RuntimeException("Non è stato possibile creare lo zip logico " + e.getMessage(), e);
	    }
	    ret.setDocumentHelper(documentiHelper);
	}
	ret.setProsegui(prosegui);
	return ret;
    }

    private boolean protocollaMovimentoDaNotificaAutomatica(Movimenti entity, TipimovStcMapping mapping, boolean eseguiNotifica,
	    String codiceComune) {

	log.debug("protocollaMovimentoDaNotificaAutomatica(): numero protocollo vuoto? {}", StringUtils.isBlank(entity.getNumeroprotocollo()));
	if (StringUtils.isBlank(entity.getNumeroprotocollo())) {
	    // 3. CONTROLLARE SE I PARAMETRI PREVEDONO LA PROTOCOLLAZIONE AUTOMATICA E NEL CASO ESEGUIRE LA PROTOCOLLAZIONE DEL MOVIMENTO
	    // controllare che siano stati settati gli altri parametri del protocollo
	    Integer codiceMovimento = entity.getId().getCodice();
	    Integer codiceIstanza = entity.getIstanza().getId().getCodice();
	    String riferimenti = getMessaggioRiferimenti(codiceIstanza, codiceMovimento);
	    log.debug("notificaStc(): PROTOCOLLAZIONE protocollazione del movimento {}", riferimenti);
	    try {
		List<String> erroriConf = new ArrayList<String>();
		if (EntityUtils.isNestedPropertyBlank(mapping.getProtocolloFlusso(), "codice")) {
		    erroriConf.add("Non è stato impostato il flusso della protocollazione");
		}
		if (EntityUtils.isNestedPropertyBlank(mapping.getAmministrazioneMittente(), "id.codice")) {
		    erroriConf.add("Non è stata impostata l'amministrazione mittente della protocollazione");
		}
		if (EntityUtils.isNestedPropertyBlank(mapping.getMailtipo(), "id.codice")) {
		    erroriConf.add("Non è stato impostato il testo tipo della protocollazione");
		}
		if (!erroriConf.isEmpty()) {
		    eseguiNotifica = false;
		    String errMesg = "(" + Utilities.getOrariosistema() +
				     ") Non è stato possibile effettuare la notifica a causa di errori nella configurazione del mapping STC: ";
		    for (String err : erroriConf) {
			errMesg = errMesg.concat("\n").concat(err).concat(",");
		    }
		    errMesg = errMesg.substring(0, errMesg.length() - 1);
		    log.error("notificaStc: {} PROTOCOLLAZIONE Errore nella protocollazione del movimento {}", riferimenti, errMesg);
		    try {
			istanzeeventiService.insert(StringUtils.left(errMesg, 4000), IstanzeeventiConstants.CATEGORIA_PROTOCOLLO, entity, null);
			movimentiDAO.flush();
			movimentiDAO.commit();
		    } catch (Exception ex) {
			log.error("notificaStc(): PROTOCOLLAZIONE non è stato possibile inserire l'evento a causa di: {}", ex.getMessage());
		    }
		} else {
		    movimentiDAO.flush();
		    movimentiDAO.commit();
		    movimentiDAO.refreshEntity(entity);
		    log.debug("notificaStc(): PROTOCOLLAZIONE protocollazione del movimento popolo il command {}", riferimenti);
		    ProtocollazioneCommand command = new PopolaProtocollazioneCommandNotAutomatica(istanzeprocedimentiService, //
			    oggettiService, //
			    documentiistanzaService, //
			    istanzeallegatiService, //
			    protocollazioneService, //
			    amministrazioniService, //
			    mailtipoService, //
			    movimentiallegatiService, //
			    entity, mapping, tipisoggettopeopleService, movimentiDAO,
			    new VerticalizzazioneProtocolloAttivoServiceImpl(verticalizzazioniService, codiceComune)).popolaCommand();
		    log.debug("notificaStc(): PROTOCOLLAZIONE protocollazione del movimento PROTOCOLLA {}", riferimenti);
		    protocollazioneService.protocolla(ProtocolloSourceEnum.ON_LINE, command, ORMHelper.getSoftware(), codiceComune);
		    movimentiDAO.refreshEntity(entity);
		    log.debug("notificaStc(): PROTOCOLLAZIONE {} protocollazione effettuata {}", riferimenti, entity.getNumeroprotocollo());
		    if (StringUtils.isBlank(entity.getNumeroprotocollo())) {
			eseguiNotifica = false;
			try {
			    String messaggioDiErrore = "(" + Utilities.getOrariosistema() +
						       ") Non è stato possibile effettuare la notifica a causa di un errore durante la protocollazione";
			    log.error("notificaStc: PROTOCOLLAZIONE {}, errore {}", riferimenti, messaggioDiErrore);
			    istanzeeventiService.insert(StringUtils.left(messaggioDiErrore, 4000), IstanzeeventiConstants.CATEGORIA_PROTOCOLLO,
				    entity, null);
			    movimentiDAO.commit();
			} catch (Exception ex) {
			    log.error("notificaStc(): PROTOCOLLAZIONE non è stato possibile inserire l'evento a causa di: {}", ex.getMessage());
			}
		    }
		}
	    } catch (Exception e) {
		try {
		    String messaggioDiErrore = "(" + Utilities.getOrariosistema() + ") Errore durante la protocollazione: " + e.getMessage();
		    log.error("notificaStc: PROTOCOLLAZIONE {} - {}", riferimenti, messaggioDiErrore);
		    movimentiDAO.commit();
		    istanzeeventiService.insert(StringUtils.left(messaggioDiErrore, 4000), IstanzeeventiConstants.CATEGORIA_PROTOCOLLO, entity, null);
		    movimentiDAO.flush();
		    movimentiDAO.commit();
		} catch (Exception ex) {
		    log.error("notificaStc(): non è stato possibile inserire l'evento a causa di: {}", ex.getMessage());
		}
		eseguiNotifica = false;
	    }
	}
	return eseguiNotifica;
    }

    private void notificaAutomaticaAttivita(Movimenti entity) {

	Integer codiceMovimento = entity.getId().getCodice();
	Integer codiceIstanza = entity.getIstanza().getId().getCodice();
	String riferimenti = getMessaggioRiferimenti(codiceIstanza, codiceMovimento);
	try {
	    Amministrazioni ammStc = null;
	    log.debug("notificaAutomaticaAttivita(): recupero amministrazione STC");
	    if (entity.getAmministrazioniStc() != null && entity.getAmministrazioniStc().getId() != null) {
		log.debug("notificaAutomaticaAttivita(): recupero amministrazione STC con codice {}",
			entity.getAmministrazioniStc().getId().getCodice());
		ammStc = amministrazioniService.findById(new PkId(entity.getAmministrazioniStc().getId().getCodice()));
		log.debug("notificaAutomaticaAttivita(): recuperata amministrazione STC {} eseguo il refresh dell'entity", ammStc);
		movimentiDAO.refreshEntity(ammStc);
		log.debug("notificaAutomaticaAttivita(): Refresh dell'entity eseguito {}", ammStc);
	    }
	    stcService.notificaAutomaticaAttivita(entity.getId().getCodice(), ammStc);
	} catch (STCNotificaAttivitaException e) {
	    String messaggioDiErrore = "(" + Utilities.getOrariosistema() + ") Errore nella notifica dell'attività: " + e.getMessage();
	    log.error("notificaAutomaticaAttivita(): {}, {}", new Object[] { riferimenti, messaggioDiErrore }, e);
	    try {
		movimentiDAO.commit();
		istanzeeventiService.insert(StringUtils.left(messaggioDiErrore, 4000), IstanzeeventiConstants.CATEGORIA_STC_IA, entity, null);
		movimentiDAO.commit();
	    } catch (Exception ex) {
		log.error("notificaAutomaticaAttivita(): non è stato possibile inserire l'evento a causa di: {}", ex.getMessage());
	    }
	}
    }

    /**
     * Verifica se le caratteristiche dei due movimenti sono uguali. Le caratteristiche controllate sono:
     * <ul>
     * <li>stessaAmministrazione</li>
     * <li>stessoEndo</li>
     * <li>stessoTipomovimento</li>
     * <li>stessoEsito</li>
     * </ul>
     * Se i due movimenti hanno tutte queste caratteristiche uguali allora torna true
     * 
     * @param movimento
     * @param contromovimento
     * @return
     */
    private boolean checkMovimentoContromovimento(Movimenti movimento, Movimenti contromovimento) {

	boolean stessaAmministrazione = false;
	boolean stessoEndo = false;
	boolean stessoTipoMovimento = false;
	boolean stessoEsito = false;
	// STESSO TIPOMOVIMENTO
	if (EntityUtils.getNestedProperty(movimento.getTipomovimento(), "id.tipomovimento") == null
		&& EntityUtils.getNestedProperty(contromovimento.getTipomovimento(), "id.tipomovimento") == null) {
	    stessoTipoMovimento = true;
	}
	if (!stessoTipoMovimento) {
	    if (EntityUtils.getNestedProperty(movimento.getTipomovimento(), "id.tipomovimento") == null
		    && EntityUtils.getNestedProperty(contromovimento.getTipomovimento(), "id.tipomovimento") != null) {
		return false;
	    }
	    if (EntityUtils.getNestedProperty(movimento.getTipomovimento(), "id.tipomovimento") != null
		    && EntityUtils.getNestedProperty(contromovimento.getTipomovimento(), "id.tipomovimento") == null) {
		return false;
	    }
	    String codiceTipomov = movimento.getTipomovimento().getId().getTipomovimento();
	    String codiceTipomovContro = contromovimento.getTipomovimento().getId().getTipomovimento();
	    if (!codiceTipomov.equalsIgnoreCase(codiceTipomovContro)) {
		return false;
	    }
	    stessoTipoMovimento = true;
	}
	// STESSA AMMINISTRAZIONE
	if (EntityUtils.getNestedProperty(movimento.getAmministrazioni(), "id.codice") == null
		&& EntityUtils.getNestedProperty(contromovimento.getAmministrazioni(), "id.codice") == null) {
	    stessaAmministrazione = true;
	}
	if (!stessaAmministrazione) {
	    if (EntityUtils.getNestedProperty(movimento.getAmministrazioni(), "id.codice") == null
		    && EntityUtils.getNestedProperty(contromovimento.getAmministrazioni(), "id.codice") != null) {
		return false;
	    }
	    if (EntityUtils.getNestedProperty(movimento.getAmministrazioni(), "id.codice") != null
		    && EntityUtils.getNestedProperty(contromovimento.getAmministrazioni(), "id.codice") == null) {
		return false;
	    }
	    Integer codiceAmministrazione = movimento.getAmministrazioni().getId().getCodice();
	    Integer codiceAmministrazioneContro = contromovimento.getAmministrazioni().getId().getCodice();
	    if (codiceAmministrazione.intValue() != codiceAmministrazioneContro.intValue()) {
		return false;
	    }
	    stessaAmministrazione = true;
	}
	// STESSO ENDOPROCEDIMENTO
	if (EntityUtils.getNestedProperty(movimento.getEndoprocedimento(), "id.codice") == null
		&& EntityUtils.getNestedProperty(contromovimento.getEndoprocedimento(), "id.codice") == null) {
	    stessoEndo = true;
	}
	if (!stessoEndo) {
	    if (EntityUtils.getNestedProperty(movimento.getEndoprocedimento(), "id.codice") == null
		    && EntityUtils.getNestedProperty(contromovimento.getEndoprocedimento(), "id.codice") != null) {
		return false;
	    }
	    if (EntityUtils.getNestedProperty(movimento.getEndoprocedimento(), "id.codice") != null
		    && EntityUtils.getNestedProperty(contromovimento.getEndoprocedimento(), "id.codice") == null) {
		return false;
	    }
	    Integer codiceEndo = movimento.getAmministrazioni().getId().getCodice();
	    Integer codiceEndoContro = contromovimento.getEndoprocedimento().getId().getCodice();
	    if (codiceEndo.intValue() != codiceEndoContro.intValue()) {
		return false;
	    }
	    stessoEndo = true;
	}
	// STESSO ESITO
	if (EntityUtils.getNestedProperty(movimento, "esito") == null && EntityUtils.getNestedProperty(contromovimento, "esito") == null) {
	    stessoEsito = true;
	}
	if (!stessoEsito) {
	    boolean esitoMovimento = BooleanUtils.toBoolean((Boolean) EntityUtils.getNestedProperty(movimento, "esito"));
	    boolean esitoControMovimento = BooleanUtils.toBoolean((Boolean) EntityUtils.getNestedProperty(contromovimento, "esito"));
	    stessoEsito = esitoMovimento == esitoControMovimento;
	}
	return stessaAmministrazione && stessoEndo && stessoTipoMovimento && stessoEsito;
    }

    /**
     * La funzione cerca tutti i possibili contromovimenti di un movimento effettuato.
     * 
     * @param entity
     * @return
     */
    private List<Movimenti> elaboraContromovimenti(Movimenti entity) {

	List<Movimenti> result = new ArrayList<Movimenti>();
	Tipimovimento tipomovimento = tipiMovimentoService.bindDomainObject(entity.getTipomovimento(), TipimovimentoId.class, "id.tipomovimento");
	List<Tipicontromovimento> contromovimenti = tipicontromovimentoService.findByTipimovimento(tipomovimento);
	if (!contromovimenti.isEmpty()) {
	    log.debug("elaboraContromovimenti: trovati {} contromovimenti per il movimento {}", contromovimenti.size(),
		    tipomovimento.getId().getTipomovimento());
	    // verifico la configurazione
	    ///////////  INIZIO INFORMAZIONI COMUNI
	    ConfigurazioneId idC = new ConfigurazioneId(WebConstants.SOFTWARE_TT);
	    Configurazione conf = configurazioneService.findById(idC);
	    if (conf == null) {
		throw new InvalidConfigurationException(
			"Non è stata configurata correttamente la Configurazione per il Software [" + WebConstants.SOFTWARE_TT + "]");
	    }
	    Integer codammsportellounico = conf.getCodammsportellounico();
	    if (codammsportellounico == null) {
		throw new InvalidConfigurationException(
			"Non è stata configurata correttamente il valore codammsportellounico nella Configurazione per il Software [" +
							WebConstants.SOFTWARE_TT + "]");
	    }
	    Integer codiceTutteAmministrazioni = conf.getCodicetutteamministrazioni();
	    if (codiceTutteAmministrazioni == null) {
		throw new InvalidConfigurationException(
			"Non è stata configurata correttamente il valore Codicetutteamministrazioni nella Configurazione per il Software [" +
							WebConstants.SOFTWARE_TT + "]");
	    }
	    Istanzeprocedimenti ip = null;
	    if (EntityUtils.getNestedProperty(entity.getEndoprocedimento(), "id.codice") != null) {
		IstanzeprocedimentiId id = new IstanzeprocedimentiId(entity.getIstanza().getId().getCodice(),
			entity.getEndoprocedimento().getId().getCodice());
		ip = istanzeprocedimentiService.findById(id);
	    }
	    Date dataInizioIstanza = null;
	    Integer codiceProc = entity.getIstanza().getProcedura().getId().getCodice();
	    ///////////  FINE INFORMAZIONI COMUNI
	    for (Tipicontromovimento tipicontromovimento : contromovimenti) {
		String tipoContromovimento = tipicontromovimento.getTipocontromovimento().getId().getTipomovimento();
		log.debug("elaboraContromovimenti: elaboro il tipo contromovimento {}", tipoContromovimento);
		boolean aggiungi = false;
		if (tipicontromovimento.getTipocontromovimento() != null && //
			BooleanUtils.isTrue(tipicontromovimento.getTipocontromovimento().getFlagDisabilitato())) {
		    // BOCCI 2012-08-31 BUGZILLA 500 SE IL CONTROMOVIMENTO e' disabilitato lo elimino dai contromovimenti da effettuare (SALTO AL PROSSIMO RECORD		    
		    continue;
		}
		// Per ogni contromovimento controllo il comportamento in relazione all'esito del movimento
		log.debug("elaboraContromovimenti: tipicontromovimento.getSoloseesitonegativo() = {}", tipicontromovimento.getSoloseesitonegativo());
		if (tipicontromovimento.getSoloseesitonegativo() == null) {
		    // Se nullo allora vale come se fosse SEMPRE
		    aggiungi = true;
		} else if (tipicontromovimento.getSoloseesitonegativo().intValue() == 0) {
		    // SEMPRE
		    aggiungi = true;
		} else if (tipicontromovimento.getSoloseesitonegativo().intValue() == 1) {
		    // Se esito NEGATIVO
		    if (BooleanUtils.isFalse(entity.getEsito())) {
			aggiungi = true;
		    }
		} else if (tipicontromovimento.getSoloseesitonegativo().intValue() == 2) {
		    // Se esito POSITIVO
		    if (BooleanUtils.isTrue(entity.getEsito())) {
			aggiungi = true;
		    }
		}
		log.debug("elaboraContromovimenti: tipicontromovimento.getSoloseesitonegativo() aggiungi {}", aggiungi);
		// Se la procedura per la quale è valido il contromovimento è nulla
		// o quella dell'istanza allora posso eseguire il contromovimento
		Tipiprocedure proceduraContro = tipicontromovimento.getTipiprocedure();
		if (EntityUtils.getNestedProperty(proceduraContro, "id.codice") != null && //
			!proceduraContro.getId().getCodice().equals(codiceProc)) {
		    aggiungi = false;
		}
		log.debug("elaboraContromovimenti: proceduraContro aggiungi {}", aggiungi);
		// Il contromovimento va fatto se l'amministrazione del movimento è nulla o
		if (EntityUtils.getNestedProperty(tipicontromovimento.getAmministrazioniTipiMovimento(), "id.codice") != null) {
		    // l'amministrazione configurata non è nulla
		    Integer codiceAmministrazioneMovimento = tipicontromovimento.getAmministrazioniTipiMovimento().getId().getCodice();
		    // L'AMMINISTRAZIONE configurata non è tutte le amministrazioni
		    if (!codiceAmministrazioneMovimento.equals(codiceTutteAmministrazioni)) {
			Amministrazioni movamm = entity.getAmministrazioni();
			if (EntityUtils.getNestedProperty(movamm, "id.codice") != null) {
			    if (!codiceAmministrazioneMovimento.equals(movamm.getId().getCodice())) {
				// l'amministrazione configurata non è uguale a quella del movimento
				aggiungi = false;
			    }
			} else {
			    // l'amministrazione configurata non è nulla mentre quella del movimento si
			    aggiungi = false;
			}
		    }
		}
		log.debug("elaboraContromovimenti: amministrazione aggiungi {}", aggiungi);
		if (tipicontromovimento.getDatacreazione() != null) {
		    log.debug("elaboraContromovimenti: datacreazione {}, data movimento {}", tipicontromovimento.getDatacreazione(),
			    entity.getData());
		    Date datamovimento = entity.getData();
		    if (datamovimento.before(tipicontromovimento.getDatacreazione())) {
			aggiungi = false;
		    }
		}
		log.debug("elaboraContromovimenti: datacreazione aggiungi {}", aggiungi);
		if (EntityUtils.getNestedProperty(entity.getEndoprocedimento(), "id.codice") != null) {
		    log.debug("elaboraContromovimenti: Il movimento appartiene all'endo {}. Controllo su istanzeprocedimenti se è acquisito o meno.",
			    EntityUtils.getNestedProperty(entity.getEndoprocedimento(), "id.codice"));
		    if (ip != null && BooleanUtils.isTrue(ip.getAcquisito())) {
			log.debug("elaboraContromovimenti: Istanzeprocedimenti è acquisito non aggiungo il contromovimento.");
			aggiungi = false;
		    }
		}
		Integer codiceAmmRitsu = null;
		if (tipicontromovimento.getAmministrazioniTipiContromovimento() != null
			&& tipicontromovimento.getAmministrazioniTipiContromovimento().getId() != null) {
		    codiceAmmRitsu = tipicontromovimento.getAmministrazioniTipiContromovimento().getId().getCodice();
		}
		log.debug("elaboraContromovimenti: codice Amministrazione Rientro {}", codiceAmmRitsu);
		if (codiceAmmRitsu == null) {
		    log.debug(
			    "elaboraContromovimenti: codice Amministrazione Rientro nulla verifico se escludere il movimento perché già elaborato ");
		    // Product Backlog Item 25774: BACKOFFICE - Gestione amministrazioni movimenti - (CR4 [9822-2022])
		    // La configurazione riguarderà le impostazioni dei contromovimenti senza amministrazione in modo che nell'elaborazione 
		    // sia possibile utilizzare qualsiasi amministrazione utilizzata per toglierla dai movimenti da effettuare.
		    Integer codiceMovimento = entity.getId().getCodice();
		    int cMovInseriti = countContromovimenti(codiceMovimento, tipoContromovimento, RicercaMovimentiEnum.EFFETTUATI);
		    if (cMovInseriti > 0) {
			log.warn(
				"CodiceAmministrazione di rientro è nulla. Esistono già {} contromovimenti eseguiti per tipocontromovimento {}. Non lo considero da elaborare" //
				, cMovInseriti, tipoContromovimento);
			continue;
		    }
		}
		if (aggiungi) {
		    log.debug("elaboraContromovimenti: Controllo il comportamento del contromovimento.propostostc");
		    // comportamento STC: 
		    //	1. se tipicontromovimenti.propostostc == null || tipicontromovimenti.propostostc==0 allora SEMPRE
		    //	2. se tipicontromovimenti.propostostc == 1 allora se istanza è stata creata tramite STC
		    //	3. se tipicontromovimenti.propostostc == 1 allora se istanza non è stata creata tramite STC
		    String propostostc = StringUtils.defaultIfEmpty(tipicontromovimento.getPropostostc(), "0");
		    log.debug("elaboraContromovimenti: propostostc={}", propostostc);
		    if (!propostostc.equalsIgnoreCase("0")) {
			Boolean creataDaStc = entity.getIstanza().getCreatoDaStc() == null ? Boolean.FALSE : entity.getIstanza().getCreatoDaStc();
			log.debug("elaboraContromovimenti: istanza creata da stc={}", creataDaStc);
			if (propostostc.equalsIgnoreCase("1")) {
			    if (!creataDaStc.booleanValue()) {
				aggiungi = false;
				log.debug("elaboraContromovimenti: propostostc=1 ma l'istanza non è stata creata da STC");
			    }
			} else {
			    if (creataDaStc.booleanValue()) {
				aggiungi = false;
				log.debug("elaboraContromovimenti: propostostc=2 ma l'istanza è stata creata da STC");
			    }
			}
		    }
		}
		if (aggiungi) {
		    Movimenti movimento = new Movimenti();
		    movimento.setTransientSePrecedente(tipicontromovimento.getSeprecedente());
		    movimento.setIstanza(entity.getIstanza());
		    movimento.setTipomovimento(tipicontromovimento.getTipocontromovimento());
		    movimento.setMovimento(tipicontromovimento.getTipocontromovimento().getMovimento());
		    movimento.setEndoprocedimento(entity.getEndoprocedimento());
		    movimento.setFlagCmovObblig(tipicontromovimento.getFlagbase());
		    if (EntityUtils.getNestedProperty(tipicontromovimento.getAmministrazioniTipiContromovimento(), "id.codice") != null) {
			Integer codLaStessaAmministrazione = conf.getCodicelastessaamministrazione();
			Integer codiceAmministrazione = tipicontromovimento.getAmministrazioniTipiContromovimento().getId().getCodice();
			if (codiceAmministrazione.equals(codLaStessaAmministrazione)) {
			    movimento.setAmministrazioni(entity.getAmministrazioni());
			} else {
			    movimento.setAmministrazioni(tipicontromovimento.getAmministrazioniTipiContromovimento());
			}
		    } else {
			// BOCCI 2012-03-07 Nel caso di amministrazione nulla setto codammsportello unico perchè così settata nel dataIntegration
			if (codammsportellounico != null) {
			    Amministrazioni ammsportellounico = amministrazioniService.findById(new PkId(codammsportellounico));
			    movimento.setAmministrazioni(ammsportellounico);
			} else {
			    movimento.setAmministrazioni(null);
			}
			movimento.setEndoprocedimento(null);
		    }
		    // BOCCI 2012-08-29: BUGZILLA 500
		    // NEL CASO DI AMMINISTRAZIONE DISABILITATA NON AGGIUNGO IL CONTROMOVIMENTO
		    if (entity.getAmministrazioni() != null && entity.getAmministrazioni().getId().getCodice() != null) {
			Amministrazioni amm = amministrazioniService.findById(new PkId(entity.getAmministrazioni().getId().getCodice()));
			if (amm != null && amm.getFlagDisabilitato() != null && amm.getFlagDisabilitato().booleanValue() == true) {
			    // NEL CASO DI AMMINISTRAZIONE DISABILITATA NON AGGIUNGO IL CONTROMOVIMENTO
			    continue;
			}
		    }
		    Integer tipologiaEsito = tipicontromovimento.getTipocontromovimento().getTipologiaesito() == null ? 0
			    : tipicontromovimento.getTipocontromovimento().getTipologiaesito();
		    // BOCCI 2012-09-20 : I VALORI NULLI DI ESITO DEVONO VENIRE CONSIDERATI COME ESITO POSITIVO
		    if (tipologiaEsito.equals(2) || tipologiaEsito.equals(0)) {
			movimento.setEsito(Boolean.TRUE);
		    } else if (tipologiaEsito.equals(1)) {
			movimento.setEsito(Boolean.FALSE);
		    }
		    boolean pubblica = tipicontromovimento.getTipocontromovimento().getFlagPubblicamovimento() == null ? Boolean.FALSE
			    : tipicontromovimento.getTipocontromovimento().getFlagPubblicamovimento();
		    movimento.setPubblica(pubblica);
		    boolean pubblicaParere = tipicontromovimento.getTipocontromovimento().getFlagPubblicaparere() == null ? Boolean.FALSE
			    : tipicontromovimento.getTipocontromovimento().getFlagPubblicaparere();
		    movimento.setPubblicaparere(pubblicaParere);
		    int gap = 0;
		    Verticalizzazioniparametri vertNonProporreData = verticalizzazioniService.getVerticalizzazioniparametri(
			    WebConstants.VERTICALIZZAZIONE_COMPORTAMENTI_ISTANZE,
			    WebConstants.VERTICALIZZAZIONE_COMPORTAMENTI_ISTANZE_MOV_NON_IMPOSTARE_SCADENZA);
		    String nonProporreData = "N";
		    if (vertNonProporreData != null && StringUtils.isNotBlank(vertNonProporreData.getValore())) {
			nonProporreData = vertNonProporreData.getValore();
		    }
		    if (StringUtils.defaultString(nonProporreData, "N").equalsIgnoreCase("N")) {
			Calendar c = Calendar.getInstance();
			c.setTime(entity.getData());
			// BOCCI 2012-02-23 PER CALCOLARE I TEMPI DI RISPOSTA VA PRESA L'AMMINISTRAZIONE DEL MOVIMENTO CHE GENERA IL CONTROMOVIMENTO
			// VEDI BUGZILLA 448
			Amministrazioni ammTempiRisposta = entity.getAmministrazioni();
			if (ammTempiRisposta == null) {
			    // se il movimento non ha specificato una amministrazione allora cerco i tempi risposta indicando l'amministrazione individuata da
			    // CONFIGURAZIONE.codammsportellounico
			    ammTempiRisposta = amministrazioniService.findById(new PkId(codammsportellounico));
			}
			if (ammTempiRisposta != null) {
			    Tempirisposta tempirisposta = new Tempirisposta();
			    tempirisposta.setTipimovimento(entity.getTipomovimento());
			    tempirisposta.setTipicontromovimento(movimento.getTipomovimento());
			    tempirisposta.setTipiprocedure(entity.getIstanza().getProcedura());
			    // VEDI BUGZILLA 448
			    tempirisposta.setAmministrazione(ammTempiRisposta);
			    // BUGZILLA 448
			    List<Tempirisposta> tr = tempirispostaService.findByFilter(tempirisposta);
			    boolean calcolaDaInizioIstanza = false;
			    for (Tempirisposta tempirisposta2 : tr) {
				if (tempirisposta2.getAttesa() != null) {
				    gap = tempirisposta2.getAttesa();
				    calcolaDaInizioIstanza = BooleanUtils.toBoolean(tempirisposta2.getCalcoladainizioistanza());
				}
			    }
			    if (calcolaDaInizioIstanza) {
				if (dataInizioIstanza == null) {
				    dataInizioIstanza = istanzeService.calcolaDataInizioIstanza(entity.getIstanza());
				}
				if (dataInizioIstanza != null) {
				    c.setTime(dataInizioIstanza);
				}
			    }
			}
			c.add(Calendar.DATE, gap);
			movimento.setDataScadenza(c.getTime());
		    }
		    result.add(movimento);
		    log.debug("elaboraContromovimenti: aggiunto contromovimento {}", tipicontromovimento.getTipocontromovimento());
		}
	    }
	}
	return result;
    }

    /**
     * 
     * per ogni contromovimento da effettuare aggiorna i permessi dei responsabili e dei ruoli legati
     * all'amministrazione
     */
    private void aggiornaPermessi(Movimenti entity) {

	IstanzeBusinessRules rules = (IstanzeBusinessRules) SigeproBusinessRules.getClassRules(IstanzeBusinessRules.class);
	boolean isPermessiRule = rules.getCustomRule(IstanzeBusinessRules.CustomRuleEnum.generaAutomaticamentePermessiIstanza.name());
	if (isPermessiRule) {
	    List<Movimenti> contromovimenti = this.findContromovimentidaEffettuare(entity);
	    for (Movimenti contromovimento : contromovimenti) {
		if (EntityUtils.getNestedProperty(contromovimento.getAmministrazioni(), "id.codice") != null) {
		    Amministrazioni amministrazione = amministrazioniService.bindDomainObject(contromovimento.getAmministrazioni(), PkId.class,
			    "id.codice");
		    if (BooleanUtils.isTrue(amministrazione.getFlagAmministrazioneinterna())) {
			// 1. amministrazioni referenti e inserisce i permessi responsabili
			Set<Amministrazioniresponsabili> ammresp = amministrazione.getAmministrazioniresponsabilis();
			for (Amministrazioniresponsabili amministrazioniresponsabili : ammresp) {
			    Responsabili responsabili = amministrazioniresponsabili.getResponsabili();
			    istanzeService.inserisciPermessoIstanza(entity.getIstanza(), responsabili);
			}
			// 2. ruoli dell'amministrazioni dei contromovimenti
			Set<Amministrazioniruoli> ammruoli = amministrazione.getAmministrazioniruolis();
			for (Amministrazioniruoli amministrazioniruoli : ammruoli) {
			    Ruoli ruolo = amministrazioniruoli.getRuoli();
			    istanzeService.inserisciRuoloIstanza(entity.getIstanza(), ruolo);
			}
		    }
		}
	    }
	}
    }

    @Override
    protected boolean validateEntity(Movimenti entity) {

	super.validateEntity(entity);
	boolean doBusinessValidation = true;
	ServiceValidationRules validationRule = (ServiceValidationRules) SigeproBusinessRules.getClassRules(ServiceValidationRules.class);
	if (validationRule != null) {
	    doBusinessValidation = validationRule.getCustomRule(ServiceValidationRules.CustomRuleEnum.doBusinessValidation.name());
	}
	if (doBusinessValidation) {
	    if (entity.getData() == null) {
		InvalidValue iv = new InvalidValue("field.required", getEntityClass(), "data", null, entity);
		throwValidationMessage(iv);
	    }
	    if (EntityUtils.getNestedProperty(entity.getResponsabile(), "id.codice") == null) {
		log.error("entity: {} - errori di validazione. Non è stato indicato alcun responsabile.", getEntityClass());
		throw new EntityValidationException(null, "Errori di validazione. Non è stato indicato alcun responsabile.", null);
	    }
	    boolean canInsertOrUpdate = this.isMovimentoModificabile(entity);
	    if (canInsertOrUpdate == false) {
		IstanzeBusinessRules rules = (IstanzeBusinessRules) SigeproBusinessRules.getClassRules(IstanzeBusinessRules.class);
		boolean isInserimentoDaStc = rules.getCustomRule(IstanzeBusinessRules.CustomRuleEnum.inserimentoDaStc.name());
		boolean isInserimentoDaImport = rules.getCustomRule(IstanzeBusinessRules.CustomRuleEnum.inserimentoDaProceduraImport.name());
		if (!(isInserimentoDaStc || isInserimentoDaImport)) {
		    log.error(
			    "entity: {} - errori di validazione. Impossibile inserire o modificare un movimento con data di presentazione antecedente la data di chiusura dell'istanza.",
			    getEntityClass());
		    throw new BusinessValidationException(null,
			    "Errori di validazione. Impossibile inserire o modificare un movimento con data di presentazione antecedente la data di chiusura dell'istanza",
			    null);
		}
	    }
	    // checkMovimentoAvvioRipetuto(entity);
	}
	return true;
    }

    @Override
    public boolean checkPermessiMovimento(Movimenti entity, Responsabili responsabile, boolean effettuaControllaPerModificaDati) {

	Amministrazioni amministrazione = this.recuperaAmministrazione(entity);
	boolean amministrazioneInterna = amministrazione.isAmministrazioneInterna();
	boolean escludiControlloAmministrazioneInterna = this.escludiControlloAmministrazioneInterna(entity);
	//
	// controllo i permessi sull'istanza
	//
	TipoAccessoEnum result = istanzeService.checkAccessoIstanza(entity.getIstanza(), responsabile);
	log.debug("L'operatore {} ha un accesso di tipo {} sull'istanza {}", new Object[] { responsabile, result, entity.getIstanza().getId() });
	if (result.equals(TipoAccessoEnum.NON_CONSENTITO)) {
	    return false;
	}
	if (effettuaControllaPerModificaDati) {
	    if (result.equals(TipoAccessoEnum.SOLA_LETTURA)) {
		return false;
	    }
	}
	//
	// verifico se non si tratta di un'amministrazione interna oppure se va escluso il controllo sulle amministrazioni interne
	//
	if (!amministrazioneInterna || escludiControlloAmministrazioneInterna) {
	    switch (result) {
	    case CONSENTITO:
	    case SOLA_LETTURA_TUTTI_MOVIMENTI:
		return true;
	    case NON_CONSENTITO:
	    case SOLA_LETTURA:
	    case SOLA_LETTURA_MOVIMENTI_AMM_INTERNA:
		return false;
	    default:
		return false;
	    }
	}
	// verifico la presenza di ruoli sull'amministrazione del movimento
	Set<Amministrazioniruoli> amministrazioniruolis = amministrazione.getAmministrazioniruolis();
	if (amministrazioniruolis.isEmpty()) {
	    log.debug("validateEntity: l'amministrazione [{}] non ha ruoli configurati l'operatore [{}] può eseguire il movimento [{}]",
		    new Object[] { amministrazione.getAmministrazione(), responsabile.getResponsabile(),
			    entity.getTipomovimento().getId().getTipomovimento() });
	    return true;
	}
	//verifico la presenza di ruoli sul responsabile
	List<Responsabiliruoli> responsabiliruolis = responsabiliruoliService.findByResponsabile(responsabile.getId().getCodice(),
		ORMHelper.getIdcomune());
	if (responsabiliruolis.isEmpty()) {
	    log.error("validateEntity: [{}] L'operatore [{}] non può eseguire il movimento [{}] per l'amministrazione [{}]",
		    new Object[] { ORMHelper.getSoftware(), responsabile.getResponsabile(), entity.getTipomovimento().getId().getTipomovimento(),
			    amministrazione.getAmministrazione() });
	    return false;
	}
	//verifico la compatibilità dei ruoli del responsabile con quelli dell'amministrazione
	Map<Integer, Integer> ruoli = new HashMap<Integer, Integer>();
	for (Responsabiliruoli ruoloOperatore : responsabiliruolis) {
	    boolean ruoloAbilitato = this.ruoloOperatoreNonReadonlySulMovimentoConAmmInterna(ruoloOperatore);
	    if (ruoloAbilitato) {
		ruoli.put(ruoloOperatore.getId().getIdruolo(), ruoloOperatore.getId().getIdruolo());
	    }
	}
	for (Amministrazioniruoli amministrazioniruoli : amministrazioniruolis) {
	    int ammRuolo = amministrazioniruoli.getId().getIdruolo();
	    if (ruoli.get(ammRuolo) != null) {
		if (log.isDebugEnabled()) {
		    log.debug(
			    "validateEntity: L'operatore [{}] appartiene al ruolo [{}] dell'amministrazione interna [{}] e può eseguire il movimento [{}]",
			    new Object[] { responsabile.getResponsabile(), amministrazioniruoli.getRuoli().getRuolo(),
				    amministrazione.getAmministrazione(), entity.getTipomovimento().getId().getTipomovimento() });
		}
		return true;
	    }
	}
	log.error("validateEntity: [{}] L'operatore [{}] non può eseguire il movimento [{}] per l'amministrazione [{}]",
		new Object[] { ORMHelper.getSoftware(), responsabile.getResponsabile(), entity.getTipomovimento().getId().getTipomovimento(),
			amministrazione.getAmministrazione() });
	return false;
    }

    private boolean ruoloOperatoreNonReadonlySulMovimentoConAmmInterna(Responsabiliruoli ruoloResponsabile) {

	Ruoli ruolo = ruoloResponsabile.getRuolo();
	if (!ruolo.isReadonly()) {
	    return true;
	}
	if (BooleanUtils.isTrue(ruolo.getFlagGestmovimenti())) {
	    return true;
	}
	return false;
    }

    private boolean escludiControlloAmministrazioneInterna(Movimenti movimento) {

	if (movimento.getTipomovimento() != null && movimento.getTipomovimento().getId() != null
		&& StringUtils.isNotBlank(movimento.getTipomovimento().getId().getTipomovimento())) {
	    return tipiMovimentoService.getFlagNoamminterna(movimento.getTipomovimento().getId().getTipomovimento());
	}
	return false;
    }

    private Amministrazioni recuperaAmministrazione(Movimenti movimento) {

	Integer codiceAmministrazione = null;
	if (movimento.getAmministrazioni() != null && movimento.getAmministrazioni().getId() != null
		&& movimento.getAmministrazioni().getId().getCodice() != null) {
	    codiceAmministrazione = movimento.getAmministrazioni().getId().getCodice();
	}
	if (codiceAmministrazione == null) {
	    Configurazione conf = configurazioneService.findById(new ConfigurazioneId(WebConstants.SOFTWARE_TT));
	    codiceAmministrazione = conf.getCodammsportellounico();
	}
	if (codiceAmministrazione == null) {
	    throw new InvalidConfigurationException(
		    "Non è stata configurata correttamente il valore codammsportellounico nella Configurazione per il Software [" +
						    WebConstants.SOFTWARE_TT + "]");
	}
	Amministrazioni amministrazione = amministrazioniService.findById(new PkId(codiceAmministrazione));
	if (amministrazione == null) {
	    throw new InvalidConfigurationException("Non è stata trovata l'amministrazione con ID [" + new PkId(codiceAmministrazione) + "]");
	}
	return amministrazione;
    }

    @Override
    public List<Movimenti> findMovimentiSimo(Calendar fromDate, Calendar toDate, Alberoproc alberoproc) {

	return movimentiDAO.findMovimentiSimo(fromDate, toDate, alberoproc);
    }

    @Override
    public Movimenti findMovimentiByTipoMovimento(Integer codiceistanza, String tipimovimento) {

	return movimentiDAO.findMovimentiByTipoMovimento(codiceistanza, tipimovimento);
    }

    @Override
    public Mailtipo findProtocolloOggetto(Movimenti movimento) {

	movimento = this.findById(movimento.getId());
	String oggettoDefault = mailtipoService.getOggettoProtocollazioneDefault() + movimento.getIstanza().getNumeroistanza();
	Mailtipo mailtipoDefault = new Mailtipo();
	mailtipoDefault.setOggetto(oggettoDefault);
	// Verifico se nel tipo movimento è stato popolato il campo MailtipoOggProt che castomizza l'oggetto 
	// mostrato sul protocollo di un movimento
	if (EntityUtils.getNestedProperty(movimento.getTipomovimento().getMailtipoOggProt(), "id.codice") != null) {
	    Tipimovimento tipimovimento = movimento.getTipomovimento();
	    // Verifico se c'è un oggetto collegato alla mail tipo
	    Mailtipo oggettoMovimento = movimento.getTipomovimento().getMailtipoOggProt();
	    if (oggettoMovimento != null) {
		log.debug("Oggetto del protocollo recuperato dal tipo movimento {}[{}]",
			new Object[] { tipimovimento.getDescrizioneEstesa(), tipimovimento.getId().getTipomovimento() });
		if (StringUtils.isNotBlank(oggettoMovimento.getOggetto())) {
		    oggettoDefault = checkExsitOggettoAndReplaceCorpo(oggettoMovimento, movimento);
		    Mailtipo result = new Mailtipo();
		    result.setOggetto(oggettoDefault);
		    Mailtipo mailProtocolloReplaced = mailtipoService.replaceOggettoCorpoProtocollo(oggettoMovimento, movimento.getIstanza(),
			    movimento);
		    result.setProtocolloOggettoMail(mailProtocolloReplaced.getProtocolloOggettoMail());
		    result.setProtocolloCorpoMail(mailProtocolloReplaced.getProtocolloCorpoMail());
		    return result;
		}
	    }
	}
	ProtocolloConfigurazione protocolloConfigurazione = protocolloConfigurazioneService
		.findById(new ProtocolloConfigurazioneId(ORMHelper.getSoftware()));
	if (protocolloConfigurazione != null) {
	    Mailtipo oggettoMovimento = protocolloConfigurazione.getMailtipoByFkMovimento();
	    if (oggettoMovimento != null) {
		if (StringUtils.isNotBlank(oggettoMovimento.getOggetto())) {
		    log.debug("Oggetto del protocollo recuperato dalla configurazione");
		    oggettoDefault = checkExsitOggettoAndReplaceCorpo(oggettoMovimento, movimento);
		    Mailtipo result = new Mailtipo();
		    result.setOggetto(oggettoDefault);
		    Mailtipo mailProtocolloReplaced = mailtipoService.replaceOggettoCorpoProtocollo(oggettoMovimento, movimento.getIstanza(),
			    movimento);
		    result.setProtocolloOggettoMail(mailProtocolloReplaced.getProtocolloOggettoMail());
		    result.setProtocolloCorpoMail(mailProtocolloReplaced.getProtocolloCorpoMail());
		    return result;
		}
	    }
	}
	log.debug("Oggetto del protocollo recuperato di default ");
	return mailtipoDefault;
    }

    private String checkExsitOggettoAndReplaceCorpo(Mailtipo mailtipo, Movimenti movimento) {

	String risultato = "";
	if (StringUtils.isNotBlank(mailtipo.getOggetto())) {
	    Mailtipo result = mailtipoService.replaceOggettoCorpo(mailtipo, movimento.getIstanza(), movimento);
	    risultato = result.getOggetto();
	}
	return risultato;
    }

    @Override
    public String findFascicoloOggetto(Movimenti movimento) {

	movimento = this.findById(movimento.getId());
	return mailtipoService.getOggettoFascicolazioneDefault() + movimento.getIstanza().getNumeroistanza();
    }

    @Override
    public List<Movimenti> findByFilterTable(FilterTable filterTable) {

	return movimentiDAO.findByFilterTable(filterTable);
    }

    @Override
    public List<Movimenti> findByFilterTable(FilterTable ft, Integer firstResult, Integer maxResult) {

	return movimentiDAO.findByFilterTable(ft, firstResult, maxResult);
    }

    @Override
    public List<Movimenti> findContromovimentidaEffettuare(Movimenti movimento) {

	return findContromovimenti(movimento.getId().getCodice(), null, RicercaMovimentiEnum.DA_EFFETTUARE);
    }

    @Override
    public List<Movimenti> findContromovimentiEffettuati(Movimenti movimento) {

	return findContromovimenti(movimento.getId().getCodice(), null, RicercaMovimentiEnum.EFFETTUATI);
    }

    private List<Movimenti> findContromovimenti(Integer codiceMovimento, String tipoControMovimento, RicercaMovimentiEnum tipoRicerca) {

	List<MovimentiContromovimenti> contromovs = movimentiContromovimentiService
		.findByFilterTable(ricercaContromovimenti(codiceMovimento, tipoControMovimento, tipoRicerca));
	List<Movimenti> result = new ArrayList<Movimenti>();
	for (MovimentiContromovimenti movimentiContromovimenti : contromovs) {
	    result.add(movimentiContromovimenti.getMovimentoByFkFiglio());
	}
	return result;
    }

    private int countContromovimenti(Integer codiceMovimento, String tipoControMovimento, RicercaMovimentiEnum tipoRicerca) {

	return movimentiContromovimentiService.countByFilterTable(ricercaContromovimenti(codiceMovimento, tipoControMovimento, tipoRicerca));
    }

    private FilterTable ricercaContromovimenti(Integer codiceMovimento, String tipoControMovimento, RicercaMovimentiEnum tipoRicerca) {

	if (tipoRicerca == null) {
	    tipoRicerca = RicercaMovimentiEnum.TUTTI;
	}
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("movimentoByFkPadreId", codiceMovimento, Integer.class));
	if (tipoRicerca.equals(RicercaMovimentiEnum.DA_EFFETTUARE)) {
	    fr.addFilterField(FilterUtils.isNull("data", "movimentoByFkFiglio"));
	} else if (tipoRicerca.equals(RicercaMovimentiEnum.EFFETTUATI)) {
	    fr.addFilterField(FilterUtils.isNotNull("data", "movimentoByFkFiglio"));
	}
	if (StringUtils.isNotBlank(tipoControMovimento)) {
	    fr.addFilterField(FilterUtils.equals("id.tipomovimento", tipoControMovimento, "movimentoByFkFiglio.tipomovimento", String.class));
	}
	ft.addRestriction(fr);
	return ft;
    }

    @Override
    public Movimenti findMovimentoSTCCheHaCreatoIstanza(Istanze istanza) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("istanzaId", istanza.getId().getCodice(), Integer.class));
	fr.addFilterField(FilterUtils.equals("creatoDaStc", Boolean.TRUE, Boolean.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("id.codice"));
	List<Movimenti> movimentis = movimentiDAO.findByFilterTable(ft);
	if (movimentis.size() > 0) {
	    for (Movimenti movimento : movimentis) {
		return movimento;
	    }
	}
	return null;
    }

    /**
     * La funzione verifica se il tipomovimento che si inserisce/aggiorna gestisca eventi di tipo:
     * <ol>
     * <li>
     * <ul>
     * <li><b>S</b>ospensione [tipomovimento.flagRichiestaintegrazione]</li>
     * <li><b>I</b>nterruzione [tipomovimento.flagInterruzione]</li>
     * <li><b>P</b>roroga [tipomovimento.flagProroga]</li>
     * </ul>
     * </li>
     * <li>Fine interruzione/sospensione.</li>
     * </ol>
     * Nel primo caso inserisce una riga nella tabella tempistica istanza con il riferimento al movimento che ha
     * generato l'evento, l'evento, e, nel caso di proroga anche i gg specificati nella configurazione del tipo
     * movimento.<br/>
     * Nel secondo caso, aggiorna le righe di tempistica istanza, ove fossero presenti, indicando come movimento che
     * chiude la tempistica l'entity passata come parametro.
     */
    private void gestisciEventoMovimento(Movimenti entity) {

	entity = bindDomainObject(entity, PkId.class, "id.codice");
	MovimentiTempisticaService.TIPO_EVENTO tipoEvento = null;
	// verifica se il movimento è di sospensione, interruzione, proroga
	Tipimovimento tm = tipiMovimentoService.bindDomainObject(entity.getTipomovimento(), TipimovimentoId.class, "id.tipomovimento");
	if (BooleanUtils.isTrue(tm.getFlagInterruzione())) {
	    tipoEvento = MovimentiTempisticaService.TIPO_EVENTO.I;
	} else if (BooleanUtils.isTrue(tm.getFlagRichiestaintegrazione())) {
	    tipoEvento = MovimentiTempisticaService.TIPO_EVENTO.S;
	} else if (BooleanUtils.isTrue(tm.getFlagProroga())) {
	    tipoEvento = MovimentiTempisticaService.TIPO_EVENTO.P;
	}
	if (tipoEvento != null) {
	    // Se è di tipo I,P,S allora inserisce la riga in movimenti_tempistica
	    // indicando in caso di proroga i giorni configurati e segnando il movimento come
	    // movimento di apertura tempistica
	    MovimentiTempistica tempistica = movimentiTempisticaService.findByMovimentoApertura(entity);
	    if (tempistica == null) {
		tempistica = new MovimentiTempistica();
		tempistica.setMovimentoByFkApertura(entity);
		if (tipoEvento.equals(MovimentiTempisticaService.TIPO_EVENTO.P)) {
		    Integer giorniProroga = tm.getGgproroga();
		    tempistica.setDurata(giorniProroga);
		}
		tempistica.setEvento(tipoEvento.toString());
		movimentiTempisticaService.insert(tempistica);
		log.debug("gestisciEventoMovimento: movimentiTempisticaService.insert(tempistica);");
	    } else {
		String tipoEventoDB = StringUtils.defaultString(tempistica.getEvento());
		if (!tipoEvento.name().equals(tipoEventoDB)) {
		    log.warn("gestisciEventoMovimento# L'evento del tipomovimento è cambiato da {} a {}, aggiorno la tempistica", tipoEventoDB,
			    tipoEvento.name());
		    movimentiTempisticaService.delete(tempistica);
		    tempistica = new MovimentiTempistica();
		    tempistica.setMovimentoByFkApertura(entity);
		    if (tipoEvento.equals(MovimentiTempisticaService.TIPO_EVENTO.P)) {
			Integer giorniProroga = tm.getGgproroga();
			tempistica.setDurata(giorniProroga);
		    }
		    tempistica.setEvento(tipoEvento.toString());
		    movimentiTempisticaService.insert(tempistica);
		    if (log.isDebugEnabled()) {
			log.debug("gestisciEventoMovimento# movimentiTempisticaService.insert(tempistica) con evento modificato;");
		    }
		}
	    }
	}
	if (BooleanUtils.isTrue(tm.getFlagFinesospinterr())) {
	    // 1 DEVO RICERCARE TRA I MOVIMENTI CHE LO HANNO GENERATO (deve essere per forza un contromovimento)
	    List<MovimentiContromovimenti> movimentiContromov = movimentiContromovimentiService.findByMovimentoByFkFiglio(entity);
	    for (MovimentiContromovimenti movimentiContromovimenti : movimentiContromov) {
		// 2 per ogni movimento trovato lo setto come movimento di chiusura della tempistica se non è stato
		// già settato un movimentodichiusura
		MovimentiTempistica tempistica = movimentiTempisticaService.findByMovimentoApertura(movimentiContromovimenti.getMovimentoByFkPadre());
		if (tempistica != null) {
		    if (tempistica.getMovimentoByFkChiusura() == null) {
			tempistica.setMovimentoByFkChiusura(entity);
			movimentiTempisticaService.update(tempistica);
			log.debug("gestisciEventoMovimento: movimentiTempisticaService.update(tempistica);");
		    }
		}
	    }
	}
    }

    @Override
    public List<Movimenti> findEseguitiByIstanza(Istanze istanza) {

	if (EntityUtils.getNestedProperty(istanza, "id.codice") == null) {
	    throw new RuntimeException("Il parametro istanza è nullo");
	}
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("istanzaId", istanza.getId().getCodice(), Integer.class));
	fr.addFilterField(FilterUtils.isNotNull("data"));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("data"));
	ft.addOrder(FilterUtils.orderAsc("ordineInserimento"));
	ft.addOrder(FilterUtils.orderAsc("id.codice"));
	List<Movimenti> movimentis = movimentiDAO.findByFilterTable(ft);
	// Per ogni movimento devo controllare se ci sono eventi non letti e .
	int numero = 0;
	for (Movimenti movimenti : movimentis) {
	    numero = istanzeeventiService.countEventiNonLettiByMovimento(movimenti.getId().getCodice());
	    movimenti.setNumeroEventiNonLetti(numero);
	}
	return movimentis;
    }

    @Override
    public List<Movimenti> findDaEseguireByIstanza(Istanze istanza) {

	if (EntityUtils.getNestedProperty(istanza, "id.codice") == null) {
	    throw new RuntimeException("Il parametro istanza è nullo");
	}
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("istanzaId", istanza.getId().getCodice(), Integer.class));
	fr.addFilterField(FilterUtils.isNull("data"));
	ft.addRestriction(fr);
	FilterRestriction disabilitato = new FilterRestriction();
	disabilitato.setAndOrRestriction(AndOrRestriction.OR);
	disabilitato.addFilterField(FilterUtils.equals("flagDisabilitato", Boolean.FALSE, Boolean.class));
	disabilitato.addFilterField(FilterUtils.isNull("flagDisabilitato"));
	ft.addRestriction(disabilitato);
	ft.addOrder(FilterUtils.orderAsc("dataScadenza"));
	ft.addOrder(FilterUtils.orderAsc("id.codice"));
	List<Movimenti> movimentis = movimentiDAO.findByFilterTable(ft);
	return movimentis;
    }

    /**
     * Verifica se un movimento è stato effettuato o meno. Un movimento è effettuato se la proprietà data non è nulla
     * 
     * @param movimento
     * @return
     */
    @Override
    public boolean isEffettuato(Movimenti movimento) {

	movimento = bindDomainObject(movimento, PkId.class, "id.codice");
	if (movimento == null) {
	    return false;
	}
	if (movimento.getData() == null) {
	    return false;
	}
	return true;
    }

    @Override
    public MovimentiHelper findCaratteristicheMovimento(Movimenti entity) {

	entity = bindDomainObject(entity, PkId.class, "id.codice");
	if (entity == null) {
	    throw new IllegalArgumentException("findCaratteristicheMovimento: Il parametro movimento passato non è corretto");
	}
	MovimentiHelper helper = new MovimentiHelper();
	helper.setMovimento(entity);
	helper.setEseguito(isEffettuato(entity));
	Tipiprocedure procedura = tipiprocedureService.bindDomainObject(entity.getIstanza().getProcedura(), PkId.class, "id.codice");
	String tipomovimento = entity.getTipomovimento().getId().getTipomovimento();
	// RILASCIO AUTORIZZAZIONE
	// 1. controllo se è stato impostato nel tipomovimento il rilascio dell'autorizzazione
	if (BooleanUtils.isTrue(entity.getTipomovimento().getFlagRegistro())) {
	    helper.setRilascioAutorizzazione(true);
	} else {
	    // 2. controllo se è stato impostato nella procedura il rilascio dell'autorizzazione
	    Tipimovimento idesitoprovvautorizzativo = procedura.getTipimovimentoEsitoAut();
	    if (idesitoprovvautorizzativo != null) {
		String idesitoprovvautorizzativoStr = idesitoprovvautorizzativo.getId().getTipomovimento();
		if (tipomovimento.equalsIgnoreCase(idesitoprovvautorizzativoStr)) {
		    helper.setRilascioAutorizzazione(true);
		}
	    }
	}
	// VERIFICA SE UNA CDS È STATA GIA' CREATA PER IL MOVIMENTO
	boolean isMovimentoCDS = isMovimentoCDS(entity);
	if (isMovimentoCDS) {
	    helper.setCdsCommissione(true);
	}
	// VERIFICA SE IL MOVIMENTO CHIUDE L'ISTANZA
	Tipimovimento tipomovChiusura = procedura.getTipimovimentoChiusura();
	if (tipomovChiusura != null) {
	    if (tipomovimento.equalsIgnoreCase(tipomovChiusura.getId().getTipomovimento())) {
		helper.setEffettuaChiusura(true);
	    }
	}
	Movimenti movimento = this.findMovimentoAvvioIstanza(entity.getIstanza());
	if (movimento != null) {
	    if (movimento.getId().getCodice().equals(entity.getId().getCodice())) {
		helper.setMovimentoAvvio(true);
	    }
	}
	return helper;
    }

    @Override
    public boolean isMovimentoCDS(Movimenti movimento) {

	Tipimovimento tipomovimento = movimento.getTipomovimento();
	boolean isMovimentoCDS = BooleanUtils.isTrue(tipomovimento.getFlagCds());
	if (!isMovimentoCDS) {
	    ConfigurazioneId id = new ConfigurazioneId(WebConstants.SOFTWARE_TT);
	    Configurazione configurazione = configurazioneService.findById(id);
	    if (configurazione == null) {
		throw new InvalidConfigurationException(
			"Non è stata effettuata correttamente la configurazione dell'applicativo. Manca la configurazione [" + id + "]");
	    }
	    Tipiprocedure procedura = movimento.getIstanza().getProcedura();
	    String idcdsvpr = StringUtils.defaultIfEmpty(configurazione.getIdcdsvpr(), "");
	    isMovimentoCDS = (procedura.getTipimovimentoCds() != null && procedura.getTipimovimentoCds().getId() != null
		    && procedura.getTipimovimentoCds().getId().getTipomovimento().equalsIgnoreCase(tipomovimento.getId().getTipomovimento())
		    || idcdsvpr.equalsIgnoreCase(tipomovimento.getId().getTipomovimento()));
	}
	return isMovimentoCDS;
    }

    @Override
    public void updateScadenza(Movimenti entity) {

	this.updateScadenza(entity, false);
    }

    private void updateScadenza(Movimenti entity, boolean isForAbilitazioneDisabilitazione) {

	dataIntegration(entity, false);
	if (super.validateEntity(entity)) {
	    // InvalidValue iv = new InvalidValue("field.required", getEntityClass(), "dataScadenza", null, entity);
	    // throwValidationMessage(iv);
	    // IN FASE DI AGGIORNAMENTO DELLA SCADENZA DEVO CONTROLLARE LA SITUAZIONE 
	    // SULLA BASE DATI DEL CAMPO DATA. SE NON E' PRESENTE UNA DATA SULLA BASE DATI 
	    // ALLORA ANCHE SE L'ENTITY ME LA PASSA NON LA CONSIDERO. LA FUNZIONALITA' IN POCHE PAROLE DEVE SOLAMENTE AGGIORNARE IL CAMPO DATASCADENZA
	    Movimenti copyOf = this.findById(entity.getId());
	    Date data = copyOf.getData();
	    entity.setData(data);
	    entity.setFlgModManualedata(Boolean.TRUE); // serve per l'elaborazione 
						       // non elimina i contromovimenti con questo flag impostato a true
	    movimentiDAO.update(entity);
	    salvaMetadatoScadenzaAggiornata(entity.getId().getCodice());
	    settaPermessiScadenza(entity);
	    if (!isForAbilitazioneDisabilitazione) {
		this.eventPublisher.publish(new EventoMovimentoAggiornato(entity.getId().getCodice(),
			entity.getData() == null ? FaseMovimentoEnum.SCADENZA : FaseMovimentoEnum.MOVIMENTO));
	    } else {
		try {
		    if (BooleanUtils.isTrue(entity.getFlagDisabilitato())) {
			this.eventPublisher.publishThrowOnFailure(new EventoMovimentoDisabilitato(entity.getId().getCodice()));
		    } else {
			this.eventPublisher.publishThrowOnFailure(new EventoMovimentoAbilitato(entity.getId().getCodice()));
		    }
		} catch (EventAbortedException e) {
		    log.error("errore nella abilitazione del movimento " + entity.getId().getCodice(), e);
		    throw new BusinessValidationException("Non è stato possibile abilitare il movimento a causa di " + e.getMessage(), e);
		}
	    }
	}
    }

    private void salvaMetadatoScadenzaAggiornata(Integer codiceMovimento) {

	MovimentiMetadati md = MovimentoMetadatoScadenzaAggiornata.fromMovimento(codiceMovimento,
		MovimentoMetadatoScadenzaAggiornata.VALORI_AMMESSI.AGGIORNATA);
	movimentiMetadatiDAO.insert(md);
	MovimentiMetadati trasmessoRabbit = MovimentoMetadatoScadAggTrasmessa.fromMovimento(codiceMovimento,
		MovimentoMetadatoScadAggTrasmessa.VALORI_AMMESSI.NON_TRASMESSO);
	movimentiMetadatiDAO.insert(trasmessoRabbit);
	movimentiMetadatiDAO.flush();
    }

    /**
     * Setta i permessi per la scadenza
     * 
     * @param entity
     */
    private void settaPermessiScadenza(Movimenti entity) {

	if (EntityUtils.getNestedProperty(entity.getAmministrazioni(), "id.codice") != null) {
	    Amministrazioni amministrazione = amministrazioniService.bindDomainObject(entity.getAmministrazioni(), PkId.class, "id.codice");
	    if (BooleanUtils.isTrue(amministrazione.getFlagAmministrazioneinterna())) {
		// 1. amministrazioni referenti e inserisce i permessi responsabili
		Set<Amministrazioniresponsabili> ammresp = amministrazione.getAmministrazioniresponsabilis();
		for (Amministrazioniresponsabili amministrazioniresponsabili : ammresp) {
		    Responsabili responsabili = amministrazioniresponsabili.getResponsabili();
		    istanzeService.inserisciPermessoIstanza(entity.getIstanza(), responsabili);
		}
		// 2. ruoli dell'amministrazioni dei contromovimenti
		Set<Amministrazioniruoli> ammruoli = amministrazione.getAmministrazioniruolis();
		for (Amministrazioniruoli amministrazioniruoli : ammruoli) {
		    Ruoli ruolo = amministrazioniruoli.getRuoli();
		    istanzeService.inserisciRuoloIstanza(entity.getIstanza(), ruolo);
		}
	    }
	}
    }

    @Override
    public Movimenti findMovimentoAvvioIstanza(Istanze istanza) {

	return movimentiDAO.findMovimentoAvvioIstanza(istanza.getId().getCodice());
    }

    @Override
    public Movimenti findMovimentoChiusuraIstanza(Integer codiceIstanza) {

	DynaProperty[] properties = new DynaProperty[] { new DynaProperty("procedura_id_codice", Integer.class) };
	DynaClass userDynaClass = new BasicDynaClass("IstanzeDC", null, properties);
	DynaBean istanza = movimentiDAO.findDynaBeanById(ORMHelper.getIdcomune(), codiceIstanza, userDynaClass, Istanze.class);
	Integer codiceProcedura = (Integer) istanza.get("procedura_id_codice");
	properties = new DynaProperty[] { new DynaProperty("tipimovimentoChiusura_id_tipomovimento", String.class) };
	userDynaClass = new BasicDynaClass("TipiprocedureDC", null, properties);
	DynaBean procedura = movimentiDAO.findDynaBeanById(ORMHelper.getIdcomune(), codiceProcedura, userDynaClass, Tipiprocedure.class);
	String tipomovChiusuraStr = (String) procedura.get("tipimovimentoChiusura_id_tipomovimento");
	Movimenti movChiusura = findMovimentiByTipoMovimento(codiceIstanza, tipomovChiusuraStr);
	return movChiusura;
    }

    @Override
    public Movimenti findMovimentoTrasmissioneByIstanzeprocedimenti(Istanzeprocedimenti istanzeprocedimenti) {

	if (istanzeprocedimenti == null) {
	    return null;
	}
	istanzeprocedimenti = istanzeprocedimentiService.findById(istanzeprocedimenti.getId());
	Inventarioprocedimenti endo = istanzeprocedimenti.getInventarioprocedimenti();
	endo = inventarioprocedimentiService.bindDomainObject(endo, PkId.class, "id.codice");
	Tipimovimento trasmissione = endo.getTipomovimento();
	if (endo.getSoftware().getCodice().equals(WebConstants.SOFTWARE_TT)) {
	    if (trasmissione == null) {
		// non è specificato il tipomovimento lo ricerco su tipiendo_software
		Set<Inventarioprocedimentisoftware> inventarioprocedimentisoftwares = endo.getInventarioprocedimentisoftwares();
		for (Inventarioprocedimentisoftware is : inventarioprocedimentisoftwares) {
		    if (is.getSoftware().getCodice().equalsIgnoreCase(ORMHelper.getSoftware())) {
			trasmissione = is.getTipimovimento();
			break;
		    }
		}
	    }
	}
	if (trasmissione != null) {
	    FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	    FilterRestriction fr = new FilterRestriction();
	    fr.addFilterField(FilterUtils.equals("istanzaId", istanzeprocedimenti.getIstanza().getId().getCodice(), Integer.class));
	    fr.addFilterField(FilterUtils.isNotNull("data"));
	    ft.addRestriction(fr);
	    FilterRestriction endoRestr = new FilterRestriction();
	    endoRestr.addFilterField(FilterUtils.equals("endoprocedimentoId", endo.getId().getCodice(), Integer.class));
	    endoRestr.addFilterField(FilterUtils.equals("amministrazioniId", endo.getAmministrazioni().getId().getCodice(), Integer.class));
	    endoRestr.addFilterField(FilterUtils.equals("tipomovimentoId", trasmissione.getId().getTipomovimento(), String.class));
	    ft.addRestriction(endoRestr);
	    ft.addOrder(FilterUtils.orderAsc("data"));
	    ft.addOrder(FilterUtils.orderAsc("ordineInserimento"));
	    List<Movimenti> movimentis = movimentiDAO.findByFilterTable(ft);
	    if (movimentis.size() > 0) {
		return movimentis.get(0);
	    }
	}
	return null;
    }

    @Override
    public Movimenti findMovimentoRitornoByIstanzeprocedimenti(Istanzeprocedimenti istanzeprocedimenti) {

	if (istanzeprocedimenti == null) {
	    return null;
	}
	Movimenti trasmissione = this.findMovimentoTrasmissioneByIstanzeprocedimenti(istanzeprocedimenti);
	if (trasmissione != null) {
	    Tipimovimento tipimovimento = tipiMovimentoService.bindDomainObject(trasmissione.getTipomovimento(), TipimovimentoId.class,
		    "id.tipomovimento");
	    // FIXME LOGICA DEI CONTROMOVIMENTI È NECESSARIA UNA FUNZIONE?
	    Set<Tipicontromovimento> tipicontromovimentos = tipimovimento.getTipicontromovimentos();
	    Tipimovimento contromovimentoObbligatorio = null;
	    for (Tipicontromovimento tipicontromovimento : tipicontromovimentos) {
		if (BooleanUtils.isTrue(tipicontromovimento.getFlagbase())) {
		    contromovimentoObbligatorio = tipicontromovimento.getTipocontromovimento();
		    break;
		}
	    }
	    if (contromovimentoObbligatorio != null) {
		List<Movimenti> controMovEffettuati = this.findContromovimentiEffettuati(trasmissione);
		for (Movimenti controMov : controMovEffettuati) {
		    String tipocontromovimento = controMov.getTipomovimento().getId().getTipomovimento();
		    if (tipocontromovimento.equals(contromovimentoObbligatorio.getId().getTipomovimento())) {
			return controMov;
		    }
		}
	    } else {
		// NON DOVREBBE MAI ESSERE
		log.warn("Contromovimento obbligatorio non trovato per il tipo movimento [{}]", tipimovimento.getId());
	    }
	}
	return null;
    }

    @Override
    public List<Movimenti> findMovimentiByIstanzeprocedimenti(Istanzeprocedimenti istanzeprocedimenti, SceltaMovimentiEnum sceltaMovimentiEnum) {

	return findMovimentiByIstanzeprocedimentiAndAmministrazione(istanzeprocedimenti, null, sceltaMovimentiEnum);
    }

    @Override
    public void disabilitaMovimento(Movimenti entity) {

	entity = bindDomainObject(entity, PkId.class, "id.codice");
	if (BooleanUtils.toBoolean(entity.getFlagCmovObblig())) {
	    throw new BusinessValidationException("Non è possibile disabilitare un contromovimento obbligatorio");
	}
	if (!isEffettuato(entity)) {
	    entity.setFlagDisabilitato(Boolean.TRUE);
	    this.updateScadenza(entity, true);
	} else {
	    throw new BusinessValidationException("Non è possibile disabilitare un movimento effettuato");
	}
    }

    @Override
    public void flush() {

	movimentiDAO.flush();
    }

    @Override
    public void abilitaMovimento(Movimenti entity) {

	entity = bindDomainObject(entity, PkId.class, "id.codice");
	if (!isEffettuato(entity)) {
	    entity.setFlagDisabilitato(Boolean.FALSE);
	    this.updateScadenza(entity, true);
	    List<MovimentiContromovimenti> movimentiContromov = movimentiContromovimentiService.findByMovimentoByFkFiglio(entity);
	    for (MovimentiContromovimenti movimentiContromovimenti : movimentiContromov) {
		Movimenti movimentoPadre = movimentiContromovimenti.getMovimentoByFkPadre();
		this.insertContromovimenti(movimentoPadre);
	    }
	} else {
	    throw new BusinessValidationException("Non è possibile disabilitare un movimento effettuato");
	}
    }

    @Override
    public List<Movimenti> findDisabilitatiByIstanza(Istanze istanza) {

	if (EntityUtils.getNestedProperty(istanza, "id.codice") == null) {
	    throw new RuntimeException("Il parametro istanza è nullo");
	}
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("istanzaId", istanza.getId().getCodice(), Integer.class));
	fr.addFilterField(FilterUtils.isNull("data"));
	fr.addFilterField(FilterUtils.equals("flagDisabilitato", Boolean.TRUE, Boolean.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("dataScadenza"));
	ft.addOrder(FilterUtils.orderAsc("id.codice"));
	List<Movimenti> movimentis = movimentiDAO.findByFilterTable(ft);
	return movimentis;
    }

    @Override
    public List<Movimenti> findMovimentiDaAssociareAllaCommissione(Date filtroData, CommissioniedilizieT commissioniedilizieT, Integer firstResult,
	    Integer maxResult) {

	return movimentiDAO.findMovimentiDaAssociareAllaCommissione(filtroData, commissioniedilizieT, firstResult, maxResult);
    }

    @Override
    public void gestioneAllegatiPerComunicazioniTelematiche(Movimenti entity) {

	log.debug("gestioneAllegatiPerComunicazioniTelematiche: BEGIN");
	if (StringUtils.isNotBlank(entity.getIstanza().getNumeroprotocollo()) && (entity.getIstanza().getDataprotocollo() != null)) {
	    entity = bindDomainObject(entity, PkId.class, "id.codice");
	    boolean isPresente = false;
	    String nomeFile = "";
	    String xmlFile = "";
	    byte[] xmlFileBytes = null;
	    byte[] pdfFileBytes = null;
	    Configurazione confSportello = configurazioneService.findById(new ConfigurazioneId());
	    Configurazione confEnte = configurazioneService.findById(new ConfigurazioneId(WebConstants.SOFTWARE_TT));
	    // verifico flag Ricevute telematiche
	    if (EntityUtils.getNestedProperty(entity.getTipomovimento().getMailtipoByFkTipimovricTelMailtipo(), "id.codice") != null) {
		nomeFile = "SUAP-ricevuta";
		// creo il file xml
		xmlFile = ComunicazioniTelematicheHelper.getXMLSUAPRicevuta(entity, confSportello, confEnte);
		log.debug("gestioneAllegatiPerComunicazioniTelematiche: ComunicazioniTelematicheHelper.getXMLSUAPRicevuta(entity);");
	    } else
	    // verifico flag Altre comunicazioni
	    if (EntityUtils.getNestedProperty(entity.getTipomovimento().getMailtipoByFkTipimovcomTelMailtipo(), "id.codice") != null) {
		nomeFile = "SUAP-comunicazione";
		// creo il file xml
		xmlFile = ComunicazioniTelematicheHelper.getXMLSUAPComunicazione(entity, confSportello);
		log.debug("gestioneAllegatiPerComunicazioniTelematiche: ComunicazioniTelematicheHelper.getXMLSUAPRicevuta(entity);");
	    } else {
		// nessun flag selezionato
		log.debug("gestioneAllegatiPerComunicazioniTelematiche: nessun Flag selezionato.");
		return;
	    }
	    try {
		xmlFileBytes = xmlFile.getBytes("UTF-8");
	    } catch (UnsupportedEncodingException e) {
		log.error(e.getMessage());
	    }
	    List<Movimentiallegati> allegati = movimentiallegatiService.findByMovimento(entity.getId().getCodice());
	    log.debug("gestioneAllegatiPerComunicazioniTelematiche: verifico presenza allegato SUAP-xxx.*");
	    // verifico presenza allegato SUAP-xxx.*
	    if (allegati != null) {
		for (Movimentiallegati allegato : allegati) {
		    if (allegato.getDescrizione().indexOf(nomeFile) != -1) {
			isPresente = true;
		    }
		}
	    }
	    if (!isPresente) {
		log.debug("gestioneAllegatiPerComunicazioniTelematiche: allegato SUAP-xxx.* non presente. Invoco il Fileconverter ");
		// creo il file pdf
		// recupero la lettera tipo che contiene la struttura xsl
		Letteretipo letteretipoXSL = entity.getTipomovimento().getLetteretipo();
		if (letteretipoXSL == null || letteretipoXSL.getFile() == null) {
		    throw new RuntimeException("Nella configurazione del movimento non è stata specificata la lettera tipo del DPR 160");
		}
		Oggetti oggetto = oggettiService.findById(letteretipoXSL.getFile().getId());
		byte[] xslFileBytes = oggetto.getOggetto();
		FileConverterWsClient fileConverterWsClient = new FileConverterWsClient();
		MergeAndConvertRequest req = new MergeAndConvertRequest(ORMHelper.getToken(), xmlFileBytes, "XML", xslFileBytes, "XSL",
			FileConverterWsClient.ConversionType.PDF.name());
		MergeAndConvertResponse resp = fileConverterWsClient.mergeAndConvert(req);
		log.debug("gestioneAllegatiPerComunicazioniTelematiche: Fileconverter invocato con successo");
		pdfFileBytes = resp.getBinaryData();
		// allego xml
		Movimentiallegati allegatoXML = new Movimentiallegati();
		allegatoXML.setMovimento(entity);
		allegatoXML.setDescrizione(nomeFile + ".xml");
		allegatoXML.setDataregistrazione(entity.getData());
		Oggetti oggettoXML = new Oggetti();
		oggettoXML.setNomefile(nomeFile + ".xml");
		oggettoXML.setOggetto(xmlFileBytes);
		oggettoXML.setDimensioneFile(xmlFileBytes.length);
		oggettiService.insert(oggettoXML);
		allegatoXML.setOggetto(oggettoXML);
		movimentiallegatiService.insert(allegatoXML);
		log.debug("gestioneAllegatiPerComunicazioniTelematiche: nuovo allegato del movimento inserito [{}]", oggettoXML.getNomefile());
		// allego pdf
		Movimentiallegati allegatoPDF = new Movimentiallegati();
		allegatoPDF.setMovimento(entity);
		allegatoPDF.setDescrizione(nomeFile + ".pdf");
		allegatoPDF.setDataregistrazione(entity.getData());
		Oggetti oggettoPDF = new Oggetti();
		oggettoPDF.setNomefile(nomeFile + ".pdf");
		oggettoPDF.setOggetto(pdfFileBytes);
		oggettoPDF.setDimensioneFile(pdfFileBytes.length);
		oggettiService.insert(oggettoPDF);
		allegatoPDF.setOggetto(oggettoPDF);
		movimentiallegatiService.insert(allegatoPDF);
		log.debug("gestioneAllegatiPerComunicazioniTelematiche: nuovo allegato del movimento inserito [{}]", oggettoPDF.getNomefile());
	    }
	}
    }

    @Override
    public boolean checkAllegatoFirmatoPerComunicazioniTelematiche(Movimenti entity) {

	log.debug("checkAllegatoFirmatoPerComunicazioniTelematiche: BEGIN");
	entity = bindDomainObject(entity, PkId.class, "id.codice");
	boolean isPresente = false;
	List<Movimentiallegati> allegati = movimentiallegatiService.findByMovimento(entity.getId().getCodice());
	log.debug("gestioneAllegatiPerComunicazioniTelematiche: verifico presenza allegato p7m");
	// verifico presenza allegato firmato
	if (allegati != null) {
	    for (Movimentiallegati allegato : allegati) {
		if (allegato.getOggetto() != null) {
		    if (allegato.getOggetto().getNomefile().toUpperCase().indexOf(".P7M") != -1) {
			isPresente = true;
		    }
		}
	    }
	}
	return isPresente;
    }

    @Override
    public boolean isDPR160(Movimenti entity) {

	boolean isDPR160 = false;
	// verifico flag Ricevute telematiche
	if (EntityUtils.getNestedProperty(entity, "tipomovimento.mailtipoByFkTipimovricTelMailtipo.id.codice") != null) {
	    isDPR160 = true;
	} else {
	    // verifico flag Altre comunicazioni
	    if (EntityUtils.getNestedProperty(entity, "tipomovimento.mailtipoByFkTipimovcomTelMailtipo.id.codice") != null) {
		isDPR160 = true;
	    }
	}
	return isDPR160;
    }

    @Override
    public List<Movimenti> findMovimentoPerFkIdProtocollo(String fkIdProtocollo) {

	List<Movimenti> movimentis = new ArrayList<Movimenti>();
	if (StringUtils.isNotBlank(fkIdProtocollo)) {
	    FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	    FilterRestriction fr = new FilterRestriction();
	    fr.addFilterField(FilterUtils.equals("fkidprotocollo", fkIdProtocollo, String.class));
	    ft.addRestriction(fr);
	    movimentis = movimentiDAO.findByFilterTable(ft);
	}
	return movimentis;
    }

    @Override
    public boolean isMovimentoModificabile(Movimenti entity) {

	if (entity == null) {
	    throw new IllegalArgumentException("Il parametro movimento non può essere vuoto");
	}
	boolean result = istanzeService.checkModificaIstanza(entity.getIstanza());
	if (!result) {
	    // se l'istanza non è modificabile devo controllare che la data del movimento sia successiva a quella di chiusura istanza ALTRIMENTI
	    // non posso salvare / cancellare il movimento
	    if (EntityUtils.isNestedPropertyBlank(entity.getIstanza(), "id.codice") == false) {
		////////////////////////////// 
		// Tipiprocedure procedura = tipiprocedureService.findById(entity.getIstanza().getProcedura().getId());
		DynaProperty[] properties = { new DynaProperty("id_codice", Integer.class), new DynaProperty("procedura", String.class),
			new DynaProperty("tipimovimentoChiusura_id_tipomovimento", String.class) };
		DynaClass userDynaClass = new BasicDynaClass("TipiprocedureDC", null, properties);
		DynaBean procedura = movimentiDAO.findDynaBeanById(ORMHelper.getIdcomune(), entity.getIstanza().getProcedura().getId().getCodice(),
			userDynaClass, Tipiprocedure.class);
		// /////////////////////////////// 
		String tipomovChiusura = null;
		if (procedura == null) {
		    log.error("isMovimentoModificabile: L'istanza [{}] non ha una procedura definita", entity.getIstanza().getId());
		    throw new RuntimeException("L'istanza [" + entity.getIstanza().getId() + "] non ha una procedura definita");
		} else {
		    tipomovChiusura = (String) procedura.get("tipimovimentoChiusura_id_tipomovimento");
		}
		if (tipomovChiusura == null) {
		    log.warn("La procedura [{}-{}] non ha definito un movimento di chiusura istanza.",
			    entity.getIstanza().getProcedura().getId().getCodice(), ORMHelper.getIdcomune());
		    // la procedura non ha definito un movimento di chiusura e quindi per assunzione faccio inserire movimenti.
		    return true;
		} else {
		    Movimenti movChiusura = this.findMovimentiByTipoMovimento(entity.getIstanza().getId().getCodice(), tipomovChiusura);
		    if (movChiusura != null) {
			if (movChiusura.getData() != null) {
			    Date datamovimento = entity.getData();
			    if (Utilities.compareDates(datamovimento, movChiusura.getData()) >= 0) {
				result = true;
			    }
			} else {
			    // NON dovrebbe mai accadere. se movimenti.data == null allora il  movimento non è stato eseguito
			    return true;
			}
		    } else {
			// il movimento di chiusura non è stato fatto per cui considero possibile la modifica / inserimento di nuovi movimenti
			// probabilmente l'operatore ha chiuso l'istanza tramite le INFO o la COMBOBOX sui movimenti
			return true;
		    }
		}
	    }
	}
	return result;
    }

    @Override
    public MovimentoDaNotificare isMovimentoDaNotificareSTC(Integer codiceMovimento) {

	Movimenti entity = this.findById(new PkId(codiceMovimento));
	log.debug("Verifico se da notificare codice movimento {}, flagt_stc {}, inviato_con_stc {} ",
		new Object[] { codiceMovimento, entity.getTipomovimento().getFlagStc(), entity.getInviatoConStc() });
	// se l'amministrazione non è stata specificata non posso notificare
	if (BooleanUtils.isTrue(entity.getTipomovimento().getFlagStc())) {
	    if (entity.getInviatoConStc() != null && entity.getInviatoConStc().equals(MovimentiBaseService.STC_INVIATO)) {
		// se è già stato notificato 
		return MovimentoDaNotificare.NO;
	    }
	    TipimovimentoId tipimovimentoId = new TipimovimentoId(entity.getTipomovimento().getId().getTipomovimento());
	    List<TipimovStcMapping> mappings = tipimovStcMappingService.findByTipimovimento(tipimovimentoId);
	    log.debug("Verifico se da notificare codice movimento {}, tipo movimento{}, mappings {} ",
		    new Object[] { codiceMovimento, tipimovimentoId, mappings.isEmpty() });
	    if (!mappings.isEmpty()) {
		return MovimentoDaNotificare.SI;
	    }
	}
	return MovimentoDaNotificare.NO;
    }

    @Override
    public void updateAmministrazioniStc(Integer codiceMovimento, Integer codiceAmministrazioneStc) {

	movimentiDAO.updateAmministrazioniStc(codiceMovimento, codiceAmministrazioneStc);
    }

    @Override
    public int countByInventarioprocedimento(Integer codiceProcedimento) {

	if (codiceProcedimento == null) {
	    throw new IllegalArgumentException("countByInventarioprocedimento: il parametro codiceProcedimento e' nullo");
	}
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceProcedimento, "endoprocedimento", Integer.class));
	filterTable.addRestriction(fr);
	int count = movimentiDAO.countRecord(filterTable);
	return count;
    }

    @Override
    public Set<Movimenti> findEseguitiByIstanze(List<Istanze> istanzes) {

	Set<Movimenti> movimentis = new LinkedHashSet<Movimenti>();
	for (Istanze istanze : istanzes) {
	    List<Movimenti> movTemp = this.findEseguitiByIstanza(istanze);
	    movimentis.addAll(movTemp);
	}
	return movimentis;
    }

    @Override
    public List<Movimenti> findByAmministrazioni(Integer codiceAmministrazione, Integer firstResult, Integer maxResult) {

	if (codiceAmministrazione == null) {
	    throw new IllegalArgumentException("findByAmministrazioni: il parametro codiceAmministrazione e' nullo");
	}
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceAmministrazione, "amministrazioni", Integer.class));
	filterTable.addRestriction(fr);
	return movimentiDAO.findByFilterTable(filterTable, firstResult, maxResult);
    }

    // ///////////////////////////////////////////////////////////////
    // ///////////////////////////////////////////////////////////////
    // ///////////////////////////////////////////////////////////////
    // ///////////////////////////////////////////////////////////////
    // ///////////////////////////////////////////////////////////////
    // ///////////////////////////////////////////////////////////////
    @Override
    public int countMovimentiDaLeggere(BatchScadenzarioFilter batchScadenzarioFilter) {

	BatchScadenzarioFilterHelper helper = new BatchScadenzarioFilterHelper(responsabiliService, softwareService, alberoprocService,
		statiistanzaService, comuniassociatiService);
	FilterTable filterTable = helper.getFilterTable(batchScadenzarioFilter, false, QUERY_PER.MOVIMENTI_DA_VISIONARE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("flagDaLeggere", true, Boolean.class));
	filterTable.addRestriction(fr);
	return movimentiDAO.countRecord(filterTable);
    }

    @Override
    public List<Movimenti> findMovimentiDaLeggere(BatchScadenzarioFilter batchScadenzarioFilter, int startRowPage, int endRowPage) {

	BatchScadenzarioFilterHelper helper = new BatchScadenzarioFilterHelper(responsabiliService, softwareService, alberoprocService,
		statiistanzaService, comuniassociatiService);
	FilterTable filterTable = helper.getFilterTable(batchScadenzarioFilter, false, QUERY_PER.MOVIMENTI_DA_VISIONARE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("flagDaLeggere", true, Boolean.class));
	filterTable.addRestriction(fr);
	return movimentiDAO.findByFilterTable(filterTable, startRowPage, endRowPage);
    }

    @Override
    public List<MovimentiDTO> findMovimentiDTODaLeggere(BatchScadenzarioFilter batchScadenzarioFilter, int startRowPage, int endRowPage) {

	return movimentiDAO.findMovimentiDTODaLeggere(batchScadenzarioFilter, startRowPage, endRowPage);
    }

    @Override
    public int countMovimentiSTCNonNotificati(BatchScadenzarioFilter batchScadenzarioFilter) {

	BatchScadenzarioFilterHelper helper = new BatchScadenzarioFilterHelper(responsabiliService, softwareService, alberoprocService,
		statiistanzaService, comuniassociatiService);
	FilterTable filterTable = helper.getFilterTable(batchScadenzarioFilter, false, QUERY_PER.MOVIMENTI_DA_NOTIFICARE);
	FilterRestriction filterRestriction = new FilterRestriction();
	filterRestriction.addFilterField(FilterUtils.equals("flagStc", true, "tipomovimento", Boolean.class));
	filterRestriction.addFilterField(FilterUtils.equals("inviatoConStc", MovimentiService.STC_NON_INVIATO, Integer.class));
	filterRestriction.addFilterField(FilterUtils.isNotNull("data"));
	filterTable.addRestriction(filterRestriction);
	return movimentiDAO.countRecord(filterTable);
    }

    @Override
    public List<Movimenti> findMovimentiSTCNonNotificati(BatchScadenzarioFilter batchScadenzarioFilter, int startRowPage, int endRowPage) {

	BatchScadenzarioFilterHelper helper = new BatchScadenzarioFilterHelper(responsabiliService, softwareService, alberoprocService,
		statiistanzaService, comuniassociatiService);
	FilterTable filterTable = helper.getFilterTable(batchScadenzarioFilter, false, QUERY_PER.MOVIMENTI_DA_NOTIFICARE);
	FilterRestriction filterRestriction = new FilterRestriction();
	filterRestriction.addFilterField(FilterUtils.equals("flagStc", true, "tipomovimento", Boolean.class));
	filterRestriction.addFilterField(FilterUtils.equals("inviatoConStc", MovimentiService.STC_NON_INVIATO, Integer.class));
	filterRestriction.addFilterField(FilterUtils.isNotNull("data"));
	filterTable.addRestriction(filterRestriction);
	return movimentiDAO.findByFilterTable(filterTable, startRowPage, endRowPage);
    }

    @Override
    public List<MovimentiDTO> findMovimentiDTOSTCNonNotificati(BatchScadenzarioFilter batchScadenzarioFilter, Integer firstResult,
	    Integer MaxResults) {

	return movimentiDAO.findMovimentiDTOSTCNonNotificati(batchScadenzarioFilter, firstResult, MaxResults);
    }

    @Override
    public int countMovimentiDaAssociareAllaCommissione(Date date, CommissioniedilizieT commissioniedilizieT) {

	// recupero la lista di tipi movimento che definiscono i movimenti che devono essere mandati in commissione
	List<String> list = new ArrayList<String>(0);
	CommedilizieTipologie commedilizieTipologie = commedilizieTipologieService.findById(commissioniedilizieT.getCommedilizieTipologie().getId());
	for (CommedilizieTipologiedett commedilizieTipologiedett : commedilizieTipologie.getCommedilizieTipologiedetts()) {
	    list.add(commedilizieTipologiedett.getTipimovimento().getId().getTipomovimento());
	}
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	if (date != null) {
	    fr.addFilterField(FilterUtils.greaterEqual("data", date, Date.class));
	}
	fr.addFilterField(FilterUtils.isNotNull("data"));
	fr.addFilterField(FilterUtils.in("id.tipomovimento", list.toArray(), "tipomovimento", String.class));
	fr.addFilterField(FilterUtils.isEmpty("commedilizieMovimento"));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderDesc("data"));
	return movimentiDAO.countRecord(ft);
    }

    @Override
    public void updateBooleanProperty(Integer codice, String propertyToUpdate, Boolean value) {

	if (codice == null) {
	    throw new RuntimeException("Il parametro codice non può essere nullo");
	}
	if (StringUtils.isBlank(propertyToUpdate)) {
	    throw new RuntimeException("Il parametro propertyToUpdate non può essere nullo");
	}
	Movimenti mov = this.findById(new PkId(codice));
	if (mov == null) {
	    throw new RuntimeException("Non è stato trovato nessun movimento con codice[" + codice + "]");
	}
	Class<?> c = mov.getClass();
	Field movField = null;
	try {
	    movField = c.getDeclaredField(propertyToUpdate);
	    Method set = c.getMethod("set" + StringUtils.capitalize(movField.getName()), movField.getType());
	    set.invoke(mov, value);
	} catch (NoSuchFieldException e) {
	    throw new RuntimeException("Non è stato trovata la proprietà [" + propertyToUpdate + "] nella classe Movimenti", e);
	} catch (IllegalAccessException e) {
	    throw new RuntimeException(e);
	} catch (SecurityException e) {
	    throw new RuntimeException(e);
	} catch (NoSuchMethodException e) {
	    throw new RuntimeException(e);
	} catch (IllegalArgumentException e) {
	    throw new RuntimeException(e);
	} catch (InvocationTargetException e) {
	    throw new RuntimeException(e);
	}
	movimentiDAO.update(mov);
    }

    @Override
    public void updateIntegerProperty(Integer codice, String propertyToUpdate, Integer value) {

	if (codice == null) {
	    throw new RuntimeException("Il parametro codice non può essere nullo");
	}
	if (StringUtils.isBlank(propertyToUpdate)) {
	    throw new RuntimeException("Il parametro propertyToUpdate non può essere nullo");
	}
	Movimenti mov = this.findById(new PkId(codice));
	if (mov == null) {
	    throw new RuntimeException("Non è stato trovato nessun movimento con codice[" + codice + "]");
	}
	Class<?> c = mov.getClass();
	Field movField = null;
	try {
	    movField = c.getDeclaredField(propertyToUpdate);
	    Method set = c.getMethod("set" + StringUtils.capitalize(movField.getName()), movField.getType());
	    set.invoke(mov, value);
	} catch (NoSuchFieldException e) {
	    throw new RuntimeException("Non è stato trovata la proprietà [" + propertyToUpdate + "] nella classe Movimenti", e);
	} catch (IllegalAccessException e) {
	    throw new RuntimeException(e);
	} catch (SecurityException e) {
	    throw new RuntimeException(e);
	} catch (NoSuchMethodException e) {
	    throw new RuntimeException(e);
	} catch (IllegalArgumentException e) {
	    throw new RuntimeException(e);
	} catch (InvocationTargetException e) {
	    throw new RuntimeException(e);
	}
	movimentiDAO.update(mov);
    }

    @Override
    public List<Movimenti> findMovimentiIstanzaFattiByTipoMovimento(String codTipomovimento, Integer codiceIstanza) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("istanzaId", codiceIstanza, Integer.class));
	fr.addFilterField(FilterUtils.equals("tipomovimentoId", codTipomovimento, String.class));
	fr.addFilterField(FilterUtils.isNotNull("data"));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderDesc("data"));
	return movimentiDAO.findByFilterTable(ft);
    }

    @Override
    public List<Movimenti> findMovimentiIstanzaDaFareByTipoMovimento(String codTipomovimento, Integer codiceIstanza) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("istanzaId", codiceIstanza, Integer.class));
	fr.addFilterField(FilterUtils.equals("tipomovimentoId", codTipomovimento, String.class));
	fr.addFilterField(FilterUtils.isNull("data"));
	ft.addOrder(FilterUtils.orderDesc("dataScadenza"));
	ft.addRestriction(fr);
	return movimentiDAO.findByFilterTable(ft);
    }

    @Override
    public List<Movimenti> findMovimentiByIstanzeprocedimentiAndAmministrazione(Istanzeprocedimenti istanzeprocedimenti,
	    Integer codiceAmministrazione, SceltaMovimentiEnum sceltaMovimentiEnum) {

	if (istanzeprocedimenti == null) {
	    return null;
	}
	if (sceltaMovimentiEnum == null) {
	    sceltaMovimentiEnum = SceltaMovimentiEnum.TUTTI;
	}
	istanzeprocedimenti = istanzeprocedimentiService.findById(istanzeprocedimenti.getId());
	Inventarioprocedimenti endo = istanzeprocedimenti.getInventarioprocedimenti();
	endo = inventarioprocedimentiService.bindDomainObject(endo, PkId.class, "id.codice");
	//	if (endo.getAmministrazioni() == null) {
	//	    throw new BusinessValidationException("L'endo [" + endo.getId() + "]-\"" + endo.getProcedimento()
	//		    + "\" non ha l'amministrazione configurata");
	//	}
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("istanzaId", istanzeprocedimenti.getIstanza().getId().getCodice(), Integer.class));
	switch (sceltaMovimentiEnum) {
	case ESEGUITI:
	    fr.addFilterField(FilterUtils.isNotNull("data"));
	    break;
	case NON_ESEGUITI:
	    fr.addFilterField(FilterUtils.isNull("data"));
	    break;
	default:
	    break;
	}
	ft.addRestriction(fr);
	FilterRestriction endoRestr = new FilterRestriction();
	endoRestr.addFilterField(FilterUtils.equals("endoprocedimentoId", endo.getId().getCodice(), Integer.class));
	if (codiceAmministrazione != null) {
	    endoRestr.addFilterField(FilterUtils.equals("amministrazioniId", codiceAmministrazione, Integer.class));
	}
	ft.addRestriction(endoRestr);
	ft.addOrder(FilterUtils.orderAsc("data"));
	switch (sceltaMovimentiEnum) {
	case ESEGUITI:
	    ft.addOrder(FilterUtils.orderAsc("ordineInserimento"));
	    break;
	case NON_ESEGUITI:
	    ft.addOrder(FilterUtils.orderAsc("id.codice"));
	    break;
	default:
	    break;
	}
	List<Movimenti> movimentis = movimentiDAO.findByFilterTable(ft);
	return movimentis;
    }

    @Override
    public List<Movimenti> findByIstanzaAndExcludeMovimento(Integer codiceIstanza, Integer codiceMovimento, SceltaMovimentiEnum sceltaMovimentiEnum) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("istanzaId", codiceIstanza, Integer.class));
	if (codiceMovimento != null) {
	    fr.addFilterField(FilterUtils.notEquals("id.codice", codiceMovimento, Integer.class));
	}
	switch (sceltaMovimentiEnum) {
	case ESEGUITI:
	    fr.addFilterField(FilterUtils.isNotNull("data"));
	    ft.addOrder(FilterUtils.orderAsc("data"));
	    ft.addOrder(FilterUtils.orderAsc("ordineInserimento"));
	    break;
	case NON_ESEGUITI:
	    fr.addFilterField(FilterUtils.isNull("data"));
	    break;
	default:
	    break;
	}
	ft.addRestriction(fr);
	List<Movimenti> list = this.findByFilterTable(ft);
	return list;
    }

    @Override
    public void evict(Movimenti movimento) {

	movimentiDAO.evict(movimento);
    }

    @Autowired
    public void setPecInboxService(PecInboxService pecInboxService) {

	this.pecInboxService = pecInboxService;
    }

    @Override
    public int countByTipimovimento(String tipomovimento) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("tipomovimentoId", tipomovimento, String.class));
	ft.addRestriction(fr);
	return movimentiDAO.countRecord(ft);
    }

    @Override
    public List<ChiaveValoreBean<String, Integer>> countMovimentiSTCConAnomalie() {

	return movimentiDAO.countMovimentiSTCConAnomalie();
    }

    @Override
    public void updateRimuoviNotificaConErrore(Integer codiceMovimento) {

	Movimenti mov = findById(new PkId(codiceMovimento));
	if (mov.getInviatoConStc() != null && mov.getInviatoConStc().equals(1) && mov.getStatoAttDest().equalsIgnoreCase("KO")
		&& StringUtils.isNotBlank(mov.getIdAttDest())) {
	    String idattDest = mov.getIdAttDest();
	    mov.setInviatoConStc(Integer.valueOf(0));
	    mov.setStatoAttDest(null);
	    mov.setIdAttDest(null);
	    movimentiDAO.update(mov);
	    Responsabili r = (Responsabili) userSecurityService.getCurrentlyAuthenticatedUserDetails();
	    String messaggio = "In data " + Utilities.getToday(true) + " l'operatore " + r + " ha modificato lo stato di invio STC della attività " +
			       mov.getId() + " dell'istanza " + mov.getIstanza() + ". rif(ID_ATT_DEST: " + idattDest + ")";
	    LoggerModificheIstanze.log("#VARIAZIONE_NOTIFICA_STC#" + messaggio);
	    istanzeeventiService.insert(messaggio, IstanzeeventiConstants.CATEGORIA_AVVERTIMENTI, mov, mov.getIstanza());
	}
    }

    @Override
    public boolean verificaSeNotificareSubEndo(String tipomovimento, Integer codiceAmministrazioneStc) {

	if (log.isDebugEnabled()) {
	    log.debug("verificaSeNotificareSubEndo#  {} - {}", new Object[] { tipomovimento, codiceAmministrazioneStc });
	}
	TipimovStcMapping confs = tipimovStcMappingService.findByTipimovimentoAndAmministrazione(tipomovimento, codiceAmministrazioneStc);
	if (confs != null) {
	    return BooleanUtils.isTrue(confs.getFlagNotificaSubEndo());
	}
	return false;
    }

    @Override
    public ByteArrayOutputStream downloadDocumentiZipLogico(Integer codicemovimento) {

	DocumentiHelper documentiHelper = movimentiZipLogicoService.findDocumentiZipLogicoToDisplay(codicemovimento);
	return downloadDocumentiZipLogico(documentiHelper);
    }

    private ByteArrayOutputStream downloadDocumentiZipLogico(DocumentiHelper documentiHelper) {

	List<String> nomeFileDuplicati = new ArrayList<String>();
	Set<Integer> oggettis = new HashSet<Integer>();
	// Bonifico la lista dei documenti dell'istanza che stanno sullo zip
	List<ChiaveValoreBean<String, List<DocumentiistanzaDTO>>> _docistChiaveValoreBeans = documentiHelper.getDocumentiIstanzaList();
	for (ChiaveValoreBean<String, List<DocumentiistanzaDTO>> bean : _docistChiaveValoreBeans) {
	    List<DocumentiistanzaDTO> documentiistanzas = bean.getValore();
	    for (DocumentiistanzaDTO documentiistanza : documentiistanzas) {
		oggettis.add(documentiistanza.getCodiceOggetto());
		nomeFileDuplicati.add(documentiistanza.getNomeFile());
	    }
	}
	// Bonifico la lista degli allegati dei movimenti
	List<ChiaveValoreBean<String, List<MovimentiallegatiDTO>>> _movChiaveValoreBeans = documentiHelper.getDocumentiAltriMovimentiList();
	for (ChiaveValoreBean<String, List<MovimentiallegatiDTO>> bean : _movChiaveValoreBeans) {
	    List<MovimentiallegatiDTO> movimentiallegatis = bean.getValore();
	    for (MovimentiallegatiDTO movimentiallegati : movimentiallegatis) {
		oggettis.add(movimentiallegati.getCodiceOggetto());
		nomeFileDuplicati.add(movimentiallegati.getNomeFile());
	    }
	}
	// Bonifico la lista degli Endoprocedimenti
	List<ChiaveValoreBean<String, List<IstanzeallegatiDTO>>> _endoChiaveValoreBeans = documentiHelper.getDocumentiEndoprocedimentiList();
	for (ChiaveValoreBean<String, List<IstanzeallegatiDTO>> bean : _endoChiaveValoreBeans) {
	    List<IstanzeallegatiDTO> istanzeallegatis = bean.getValore();
	    for (IstanzeallegatiDTO istanzeallegati : istanzeallegatis) {
		oggettis.add(istanzeallegati.getCodiceOggetto());
		nomeFileDuplicati.add(istanzeallegati.getNomeFile());
	    }
	}
	// Bonifico la lista delle istanzeprocure 
	List<ChiaveValoreBean<String, List<IstanzeprocureDTO>>> _istanzeprocChiaveValoreBeans = documentiHelper.getIstanzeprocureList();
	for (ChiaveValoreBean<String, List<IstanzeprocureDTO>> bean : _istanzeprocChiaveValoreBeans) {
	    List<IstanzeprocureDTO> istanzeprocures = bean.getValore();
	    for (IstanzeprocureDTO istanzeprocure : istanzeprocures) {
		oggettis.add(istanzeprocure.getCodiceOggetto());
		nomeFileDuplicati.add(istanzeprocure.getNomeFile());
	    }
	}
	// Bonifico la lista dei documenti anagrafe
	List<ChiaveValoreBean<String, List<AnagrafedocumentiDTO>>> _anagrafedocChiaveValoreBeans = documentiHelper.getDocumentiAnagrafeList();
	for (ChiaveValoreBean<String, List<AnagrafedocumentiDTO>> bean : _anagrafedocChiaveValoreBeans) {
	    List<AnagrafedocumentiDTO> anagrafedocumentis = bean.getValore();
	    for (AnagrafedocumentiDTO anagrafedocumenti : anagrafedocumentis) {
		oggettis.add(anagrafedocumenti.getCodiceOggetto());
		nomeFileDuplicati.add(anagrafedocumenti.getNomeFile());
	    }
	}
	// Bonifico la lista dei documenti cds 
	List<ChiaveValoreBean<String, List<CdsattiDTO>>> _cdsattiChiaveValoreBeans = documentiHelper.getCdsattiList();
	for (ChiaveValoreBean<String, List<CdsattiDTO>> bean : _cdsattiChiaveValoreBeans) {
	    List<CdsattiDTO> cdsattis = bean.getValore();
	    for (CdsattiDTO cdsatti : cdsattis) {
		oggettis.add(cdsatti.getCodiceoggetto());
		nomeFileDuplicati.add(cdsatti.getNomefile());
	    }
	}
	log.debug("downloadDocumentiZipLogico# Creo l'archivio Zip");
	ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
	ZipOutputStream zipOutStream = new ZipOutputStream(byteArrayOut);
	try {
	    int i = 1;
	    for (Integer codiceoggetto : oggettis) {
		Oggetti oggetti = oggettiService.findByIdLazy(new PkId(codiceoggetto));
		String nomeFile = oggetti.getNomefile();
		// nomeFileDuplicati.add(nomeFile);
		if (!nomeFileDuplicati.isEmpty()) {
		    if (Collections.frequency(nomeFileDuplicati, nomeFile) > 1) {
			nomeFile = i + "_" + nomeFile;
			i++;
		    }
		}
		InputStream oggettoAsInputStream = oggettiService.getOggettoAsInputStream(codiceoggetto);
		Utilities.writeZipEntries(oggettoAsInputStream, zipOutStream, nomeFile);
	    }
	    zipOutStream.closeEntry();
	    zipOutStream.close();
	} catch (IOException ioex) {
	    log.error("downloadDocumentiZipLogico# Errore durante la creazione dell'archivop zip: {}", ioex.getMessage());
	}
	return byteArrayOut;
    }

    @Override
    public boolean validateDownloadZipLogico(Integer codiceMovimento, String uuidIstanza) {

	Movimenti mov = this.findById(new PkId(codiceMovimento));
	String uuid = StringUtils.defaultString(mov.getIstanza().getUuid(), "-1111000111");
	return StringUtils.defaultString(uuidIstanza, "+2002222").equalsIgnoreCase(uuid);
    }

    @Override
    public Movimenti findMovimentiByTipoMovimentoAndDataAndAmministrazione(Integer codiceIstanzaDestinazione, String tipomovimento, Date data,
	    Integer codiceAmministrazione) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("istanzaId", codiceIstanzaDestinazione, Integer.class));
	fr.addFilterField(FilterUtils.equals("tipomovimentoId", tipomovimento, String.class));
	fr.addFilterField(FilterUtils.equals("amministrazioniId", codiceAmministrazione, Integer.class));
	fr.addFilterField(FilterUtils.isNotNull("data"));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderDesc("data"));
	List<Movimenti> movs = movimentiDAO.findByFilterTable(ft);
	if (!movs.isEmpty()) {
	    for (Movimenti m : movs) {
		if (Utilities.compareDates(data, m.getData()) == 0) {
		    return m;
		}
	    }
	}
	return null;
    }

    @Override
    public Movimenti findDataByTipoMovandcodIstanza(String tipomovimento, Integer codiceIstanza) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceIstanza, "istanza", Integer.class));
	fr.addFilterField(FilterUtils.equals("id.tipomovimento", tipomovimento, "tipomovimento", String.class));
	ft.addRestriction(fr);
	List<Movimenti> movimenti = movimentiDAO.findByFilterTable(ft);
	if (movimenti.size() > 0) {
	    return movimenti.get(0);
	}
	return new Movimenti();
    }

    @Override
    @Transactional(noRollbackFor = FunzioneBusinessRemotaException.class, propagation = Propagation.REQUIRED)
    public void eseguiFormuleDelleSchedeDinamiche(Integer codicemovimento) throws FunzioneBusinessRemotaException {

	List<Movimentidyn2modellit> list = movimentidyn2modellitService.findByCodiceMovimento(codicemovimento);
	List<String> errori = new ArrayList<String>();
	for (Movimentidyn2modellit m : list) {
	    Integer codiceScheda = m.getId().getFkD2mtId();
	    log.debug("eseguo formule della scheda {} del movimento {}", codiceScheda, codicemovimento);
	    try {
		String[] eseguiScriptAggiornamentoSchedaIstanza = dyn2ModellitService
			.eseguiScriptAggiornamentoSchedaIstanza(m.getMovimento().getIstanza().getId().getCodice(), codiceScheda);
		if (eseguiScriptAggiornamentoSchedaIstanza != null && eseguiScriptAggiornamentoSchedaIstanza.length > 0) {
		    errori.addAll(Arrays.asList(eseguiScriptAggiornamentoSchedaIstanza));
		    log.error("errore nell'esecuzione della scheda dinamica {} per il movimento [{}] {}",
			    new Object[] { codiceScheda, codicemovimento, Arrays.asList(eseguiScriptAggiornamentoSchedaIstanza) });
		}
	    } catch (FunzioneBusinessRemotaException e) {
		log.error("errore nell'esecuzione della scheda dinamica {} per il movimento [{}] {}",
			new Object[] { codiceScheda, codicemovimento, e.getMessage(), e });
		errori.add(e.getMessage());
	    }
	}
	if (!errori.isEmpty()) {
	    Movimenti m = findById(new PkId(codicemovimento));
	    log.debug("Scrivo gli errori riportati su istanze eventi per il movimento [{}]", codicemovimento);
	    Istanzeeventi istanzeeventi = new Istanzeeventi();
	    istanzeeventi.setMovimenti(m);
	    istanzeeventi.getCategorieeventibase().setId(IstanzeeventiConstants.CATEGORIA_AVVERTIMENTI);
	    istanzeeventi.setData(Calendar.getInstance().getTime());
	    StringBuilder stringError = new StringBuilder();
	    for (String err : errori) {
		stringError.append(err).append(",");
	    }
	    istanzeeventi.setDescrizione(stringError.toString());
	    log.debug("Salvo istanze eventi");
	    istanzeeventiService.insert(istanzeeventi);
	    log.debug("Salvato istanze eventi");
	    throw new FunzioneBusinessRemotaException(istanzeeventi.getDescrizione());
	}
    }

    private String getMessaggioRiferimenti(Integer codiceIstanza, Integer codiceMovimento) {

	return "codice istanza: " + codiceIstanza + ", codice movimento: " + codiceMovimento;
    }

    @Override
    public Date getDataMovimentoDaElaborare(Movimenti entity) {

	Verticalizzazioniparametri elaboraMovimentiDopoDataMov = verticalizzazioniService.getVerticalizzazioniparametri(
		WebConstants.VERTICALIZZAZIONE_COMPORTAMENTI_ISTANZE,
		WebConstants.VERTICALIZZAZIONE_COMPORTAMENTI_ISTANZE_ELAB_MOVIMENTI_DOPO_DATA_MOV);
	String elaboraMovimentiDopoDataMovStr = "N";
	if (elaboraMovimentiDopoDataMov != null) {
	    if (StringUtils.isNotBlank(elaboraMovimentiDopoDataMov.getValore())) {
		elaboraMovimentiDopoDataMovStr = elaboraMovimentiDopoDataMov.getValore();
	    }
	}
	if (StringUtils.defaultString(elaboraMovimentiDopoDataMovStr, "N").equalsIgnoreCase("S")) {
	    if (entity != null) {
		return entity.getData();
	    }
	}
	return null;
    }

    @Override
    public void updateStatoistanza(Movimenti movimento) {

	Tipimovimento tm = tipiMovimentoService.findById(movimento.getTipomovimento().getId());
	boolean eseguiUpdateStatoIstanza = (tm.getStatoistanza() != null && tm.getStatoistanza().getId() != null
		&& tm.getStatoistanza().getId().getCodicestato() != null);
	if (!eseguiUpdateStatoIstanza) {
	    return;
	}
	this.istanzeService.updateStatoIstanza(movimento.getIstanza(), movimento.getTipomovimento().getStatoistanza().getId().getCodicestato());
    }

    @Override
    public void updateRiferimentiProtocolloMovimento(AggiornaRiferimentiProtocolloMovimentoRequest request) throws AggiornamentoProtocolloException {

	updateRiferimentiProtocollo(request, true);
    }

    @Override
    public void updateRiferimentiProtocolloMovimentoAvvio(AggiornaRiferimentiProtocolloMovimentoRequest request)
	    throws AggiornamentoProtocolloException {

	updateRiferimentiProtocollo(request, false);
    }

    private void updateRiferimentiProtocollo(AggiornaRiferimentiProtocolloMovimentoRequest request, boolean lanciaEventoProtocollazioneMovimento)
	    throws AggiornamentoProtocolloException {

	Movimenti i = validaProtocolloRequest(request);
	Date dataProtocolllo = Utilities.parseDateString(request.getDataProtocollo(), "yyyy-MM-dd");
	if (dataProtocolllo == null) {
	    throw new AggiornamentoProtocolloException("Data non valida " + request);
	}
	String istanza = i.toString();
	i.setDataprotocollo(dataProtocolllo);
	i.setNumeroprotocollo(request.getNumeroProtocollo());
	i.setFkidprotocollo(request.getFkidProtocollo());
	movimentiDAO.update(i);
	LoggerModificheIstanze
		.log("#MODIFICAPROTOCOLLOMOVIMENTO#INIZIO updateRiferimentiProtocollo per il movimento " + istanza + " con request: " + request);
	if (lanciaEventoProtocollazioneMovimento) {
	    try {
		this.eventPublisher.publishThrowOnFailure(new EventoMovimentoProtocollato(request.getCodiceMovimento(), new HashSet<Integer>(0)));
	    } catch (EventAbortedException e) {
		log.error("Errore nella sottoscrizione dell'evento " + e.getMessage(), e);
		LoggerModificheIstanze.log("#MODIFICAPROTOCOLLOMOVIMENTO#ERRORE updateRiferimentiProtocollo per il movimento " + istanza +
					   " con request: " + request + ", dettaglio errore:" + e.getMessage());
		throw new AggiornamentoProtocolloException(
			"Errore nell'aggiornamento dei riferimenti protocollo per il movimento " + istanza + ", dettaglio: " + e.getMessage(), e);
	    }
	}
	LoggerModificheIstanze
		.log("#MODIFICAPROTOCOLLOMOVIMENTO#FINE updateRiferimentiProtocollo per il movimento " + istanza + " con request: " + request);
    }

    private Movimenti validaProtocolloRequest(AggiornaRiferimentiProtocolloMovimentoRequest request) throws AggiornamentoProtocolloException {

	if (request.getCodiceMovimento() == null || StringUtils.isBlank(request.getNumeroProtocollo())
		|| StringUtils.isBlank(request.getDataProtocollo())) {
	    throw new AggiornamentoProtocolloException("Dati non validi " + request);
	}
	Movimenti mov = movimentiDAO.findById(new PkId(request.getCodiceMovimento()));
	if (mov == null) {
	    throw new AggiornamentoProtocolloException("Movimento non trovato " + request.getCodiceMovimento());
	}
	if (!StringUtils
		.defaultIfEmpty(StringUtils.defaultString(mov.getNumeroprotocollo()).trim(), ProtocollazioneService.NUMERO_PROTOCOLLO_ASINCRONO_CHAR)
		.equals(ProtocollazioneService.NUMERO_PROTOCOLLO_ASINCRONO_CHAR)) {
	    throw new AggiornamentoProtocolloException("Il movimento ha già i riferimenti di protocollo: " + mov);
	}
	return mov;
    }

    @Override
    public MovimentiRabbitTestoBean replaceTestoPerMovimentoeTopic(Integer codiceMovimento, String topic) {

	Movimenti mov = movimentiDAO.findById(new PkId(codiceMovimento));
	if (mov == null) {
	    throw new IllegalArgumentException("Movimento con id " + codiceMovimento + " non trovato");
	}
	Integer alberoprocId = mov.getIstanza().getAlberoproc().getId().getCodice();
	// recupera la mail tipo da tipimovimentoRabbit
	Integer codiceMailTipo = this.iTipimovimentoRabbitDAO.recuperaTestoTipodaMovimentoETopic(mov.getTipomovimento().getId().getTipomovimento(),
		topic);
	if (codiceMailTipo == null) {
	    throw new IllegalArgumentException("Non e' stato trovato il testo tipo per il topic " + topic + " e tipo movimento " +
					       mov.getTipomovimento().getId().getTipomovimento());
	}
	Mailtipo m = mailtipoService.findById(new PkId(codiceMailTipo));
	m = mailtipoService.replaceOggettoCorpo(m, mov.getIstanza(), mov);
	String titolo = this.alberoprocMetadatiService.findValoreByInterventoRicorsivoEChiave(alberoprocId,
		AlberoprocMetadatiEnum.FRONTEND_TITOLO_SERVIZIO.value());
	String sottoTitolo = this.alberoprocMetadatiService.findValoreByInterventoRicorsivoEChiave(alberoprocId,
		AlberoprocMetadatiEnum.FRONTEND_SOTTOTITOLO_SERVIZIO.value());
	return MovimentiRabbitTestoBean.fromMailTipo(m, titolo, sottoTitolo);
    }

    @Override
    public Movimenti findMovimentoByUuId(String uuid) {

	return this.movimentiMetadatiDAO.findMovimentoByUuId(uuid);
    }

    private void daoInsert(Movimenti movimento, boolean isScadenza) {

	//1. Dataintegration
	this.dataIntegration(movimento, true);
	//2. Validazione
	boolean valido = false;
	if (isScadenza) {
	    if (movimento.getDataScadenza() == null) {
		Verticalizzazioniparametri vertNonProporreData = verticalizzazioniService.getVerticalizzazioniparametri(
			WebConstants.VERTICALIZZAZIONE_COMPORTAMENTI_ISTANZE,
			WebConstants.VERTICALIZZAZIONE_COMPORTAMENTI_ISTANZE_MOV_NON_IMPOSTARE_SCADENZA);
		String nonProporreData = "N";
		if (vertNonProporreData != null && StringUtils.isNotBlank(vertNonProporreData.getValore())) {
		    nonProporreData = vertNonProporreData.getValore();
		}
		if (StringUtils.defaultString(nonProporreData, "N").equalsIgnoreCase("N")) {
		    InvalidValue iv = new InvalidValue("field.required", getEntityClass(), "dataScadenza", null, movimento);
		    throwValidationMessage(iv);
		}
	    }
	    valido = super.validateEntity(movimento);
	} else {
	    valido = validateEntity(movimento);
	}
	if (!valido) {
	    return;
	}
	//3. Tolgo la data dalle scadenze
	if (isScadenza && movimento.getData() != null) {
	    movimento.setData(null);
	}
	Set<Movimentiallegati> allegati = new HashSet<Movimentiallegati>(0);
	//4. Settaggi proprietari dei movimenti fatti
	if (movimento.getData() != null) {
	    //4.1 Data inserimento
	    movimento.setDatainserimento(Calendar.getInstance().getTime());
	    //4.2 Ordine inserimento
	    if (movimento.getOrdineInserimento() == null) {
		// solo se nullo lo potrebbe impostare l'import e allora ci lascio quello
		int ordineInserimento = movimentiDAO.findProgressivoInserimento(movimento.getIstanza().getId().getCodice());
		movimento.setOrdineInserimento(ordineInserimento);
	    }
	}
	//4.3 Copia allegati in caso di inserimento da STC
	IstanzeBusinessRules rules = (IstanzeBusinessRules) SigeproBusinessRules.getClassRules(IstanzeBusinessRules.class);
	boolean isInserimentoDaStc = rules.getCustomRule(IstanzeBusinessRules.CustomRuleEnum.inserimentoDaStc.name());
	boolean isInserimentoDaImport = rules.getCustomRule(IstanzeBusinessRules.CustomRuleEnum.inserimentoDaProceduraImport.name());
	log.debug("insert: InserimentoDaStc {}, isInserimentoDaImport {}", isInserimentoDaStc, isInserimentoDaImport);
	//5. Viene richiamata la insert anche quando una scadenza diventa movimento fatto
	if (EntityUtils.getNestedProperty(movimento, "id.codice") != null) {
	    if (isInserimentoDaStc) {
		allegati = copiaAllegati(movimento, isInserimentoDaStc, isInserimentoDaImport);
	    }
	    movimentiDAO.update(movimento);
	} else {
	    allegati = copiaAllegati(movimento, isInserimentoDaStc, isInserimentoDaImport);
	    movimentiDAO.insert(movimento);
	}
	movimentiDAO.flush();
	movimentiDAO.clear();
	//6. Inserimento del metadato UUID
	if (!movimentiMetadatiDAO.isMetadatoPresente(movimento.getId().getCodice(), MovimentoMetadatoUUID.NOME_METADATO)) {
	    movimentiMetadatiDAO.insert(MovimentoMetadatoUUID.fromMovimento(movimento.getId().getCodice()));
	    movimentiDAO.flush();
	}
	if (!movimento.getMetadatiDaInserire().isEmpty()) {
	    for (CodiceDescrizioneBean mdcb : movimento.getMetadatiDaInserire()) {
		MovimentiMetadati md = new MovimentiMetadati();
		md.setId(new MovimentiMetadatiId(movimento.getId().getCodice(), mdcb.getCodice()));
		md.setValore(mdcb.getDescrizione());
		movimentiMetadatiDAO.insertOrUpdate(md, md.getId(), false);
		movimentiDAO.flush();
	    }
	}
	//7. Controlli proprietari 
	if (movimento.getData() != null) {
	    //7.1 Evento movimento inserito
	    this.eventPublisher.publish(new EventoMovimentoInserito(movimento.getId().getCodice(), false));
	    //7.2 child data integration
	    childDataIntegration(movimento, true, allegati);
	    //7.3 operazioni automatiche
	    try {
		operazioniAutomatiche(movimento);
	    } catch (OperazioniAutomaticheException e) {
		log.error("insert: {}", e.getMessage());
	    }
	} else {
	    //7.1 Permessi
	    settaPermessiScadenza(movimento);
	    //7.2 Notifiche automatiche
	    List<TipimovStcMapping> mappings = tipimovStcMappingService
		    .findByTipimovimento(new TipimovimentoId(movimento.getTipomovimento().getId().getTipomovimento()));
	    if (!mappings.isEmpty()) {
		for (TipimovStcMapping mapping : mappings) {
		    TipoNotificaAutomatica tipo = tipimovStcMappingService.decodeTipoNotifica(mapping);
		    if (tipo.equals(TipoNotificaAutomatica.NOTIFICA_AUTOMATICA)) {
			movimento.setData(Utilities.getTime(Calendar.getInstance()));
			this.insert(movimento);
			break;
		    }
		}
	    }
	    //7.3 Se la scadenza non ha data viene generato l'evento della scadenza inserita
	    if (movimento.getData() == null) {
		this.eventPublisher.publish(new EventoMovimentoInserito(movimento.getId().getCodice(), true));
	    }
	}
	//8. Verifico se creare zip logico
	if (!StringUtils.isEmpty(movimento.getGuidOrigine())) {
	    //1. Rilettura degli allegati dal db in quanto potrebbero essere stati inseriti anche a fronte dell'evento EventoMovimentoInserito
	    List<Movimentiallegati> a = this.movimentiallegatiService.findByMovimento(movimento.getId().getCodice());
	    if (!a.isEmpty()) {
		this.movimentiZipLogicoService.generaZipLogicoDaZipLogicoCollegato(movimento.getId().getCodice(), new HashSet<Movimentiallegati>(a),
			movimento.getGuidOrigine());
	    }
	}
	//9. Verifica ed esecuzione delle comunicazioni configurate
	tipimovimentoComunicazioniService.eseguiComunicazione(movimento, null, false, TipoComunicazionemovimentoEnum.INSER_MOV);
    }
}
