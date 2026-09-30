<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../includes/taglibs.jsp"%>
<ul class="sf-menu">
	<li>
		<a href="javascript: void(0);" accesskey="A"><label style="text-decoration: underline; ">A</label>rchivi</a>
		<ul>
			<li>
				<a href="javascript: void(0);" accesskey="B">Archivi di <label style="text-decoration: underline; ">b</label>ase</a>
				<ul>
					<li><a href='javascript:historyClear("/welcome/calendario.htm?software=TT")'>Calendario</a></li>
					<li><a href="javascript: void(0);" onclick='window.open("${pageContext.request.contextPath}/file/ajaxSearch.htm","","menubar=0,status=1,width=600,height=250");return false;'>Upload files</a></li>									
					<li><a href='javascript:historyClear("/anagrafe/list.htm")'>Richiedenti e Tecnici</a></li>
					<li><a href='javascript:historyClear("/foarconfigurazione/view.htm?software=TT")'>Fo Configurazione</a></li>
                    <li><a href='javascript:historyClear("/amministrazioni/list.htm?software=CO")'>Amministrazioni</a></li>
                    <li><a href='javascript:historyClear("/inventarioprocedimenti/list.htm?software=CO")'>Endoprocedimenti</a></li>
                    <li><a href='javascript:historyClear("/anagrafeinterdetti/list.htm")'>Gestione interdizioni</a></li>
                    <li><a href='javascript:historyClear("/domandestc/list.htm")'>Domande STC non importate</a></li>
				</ul>
			</li>
			<li>
				<a href="javascript: void(0);">Archivi commercio</a>
				<ul>
					<li><a href='javascript:historyClear("/messaggicfg/view.htm?software=CO")'>Messaggi cfg</a></li>
					<li><a href='javascript:historyClear("/mercaticonfigurazione/view.htm?software=CO")'>Configurazione Mercati</a></li>					
					<li><a href='javascript:historyClear("/tipibando/list.htm?software=CO")'>Tipi bando</a></li>	
					<li><a href='javascript:historyClear("/vwconcessionilista/create.htm?software=CO")'>Lista Concessioni</a></li>
					<li><a href='javascript:historyClear("/istanze/search.htm?software=CO")'>Inserimento massivo movimenti</a></li>
					<li><a href='javascript:historyClear("/sorteggicategorie/list.htm?software=CO")'>Categorie sorteggi</a></li>	
					<li><a href='javascript:historyClear("/alberoprocdocumenticat/list.htm?software=CO")'>Albero Proc Documenti Cat</a></li>
					<li>
						<a href="javascript: void(0);">Contabilit&#224;</a>
						<ul>						
							<li>
								<a href='#'>TEST</a>
								<ul>
									<li>
										<a href='#'>SUB TEST</a>
									</li>
								</ul>
							</li>
							<li><a href='javascript:historyClear("/schedacontabile/search.htm?software=CO")'>Schede contabili</a></li>
							<li><a href='javascript:historyClear("/conti/list.htm?software=CO")'>Conti</a></li>
							<li><a href='javascript:historyClear("/registrazionicausali/list.htm?software=CO")'>Registrazioni Causali</a></li>
							<li><a href='javascript:historyClear("/mercaticonti/list.htm?mercati.id.codice=23&software=CO")'>Configurazione Conti mercati</a></li>							
							<li><a href='javascript:historyClear("/alberocausali/list.htm?alberoproc.id.codice=137&software=CO")'>Configurazione Causali oneri</a></li>
							<li><a href='javascript:historyClear("/endocausali/list.htm?software=CO&inventarioprocedimenti.id.codice=1")'>Endo Oneri Causali</a></li>
							<li><a href='javascript:historyClear("/calendariomercato/listusianni.htm?mercati.id.codice=1&software=CO")'>Calendario mercato</a></li>							
							<li><a href='javascript:historyClear("/registrazioni/createSearch.htm?software=CO")'>Registrazioni</a></li>
							<li><a href='javascript:historyClear("/registrazioniinout/list.htm?software=CO")'>Incassi</a></li>
							<li><a href='javascript:historyClear("/accertamenti/create.htm?software=CO")'>Riepiloghi</a></li>
							<li><a href='javascript:historyClear("/registrazioniinout/searchScadenze.htm?software=CO")'>Scadenze</a></li>
							<li><a href='javascript:historyClear("/mercaticonti/list.htm?software=CO&mercati.id.codice=1")'>Mercati Conti</a></li>
							<li><a href='javascript:historyClear("/mercatidconti/list.htm?software=CO&posteggio.id.codice=570")'>Posteggi Conti</a></li>
							<li><a href='javascript:historyClear("/registrazionimercato/statisticheMercato.htm?software=CO&mercati.id.codice=1")'>Statistiche Mercati</a></li>
							<li><a href='javascript:historyClear("/registrazionimercato/registrazioniByMercato.htm?software=CO&mercati.id.codice=1&mercatoUso=14&anno=2009")'>Registrazione da Mercati</a></li>
							<li><a href='javascript:historyClear("/gestionepresenze/list.htm?software=CO&codiceMercato=15&usoMercato=30&giornoMercato=06/01/2010")'>Presenze</a></li>
							<li><a href='javascript:historyClear("/calendariomercato/createSearch.htm?software=CO")'>Presenze vigili</a></li>
							<li><a href='javascript:historyClear("/mercatipresenzestorico/create.htm?software=CO")'>Presenze Storico</a></li>
						</ul>
					</li>
					<li>
						<a href="javascript: void(0);">Manifestazioni</a>
						<ul>
							<li><a href='javascript:historyClear("/gestionepresenze/createControlloAssenze.htm?software=CO")'>Controllo assenze</a></li>
						</ul>
					</li>
				</ul>
			</li>	
		</ul>
	</li>
	<li>
		<a href="javascript: void(0);" accesskey="P"><label style="text-decoration: underline; ">P</label>ratiche</a>
		<ul>
			<li><a href="javascript: void(0);">Pratiche commercio</a>
				<ul>
				    <li><a href='javascript:historyClear("/istanze/searchIstanze.htm?software=CO")'>Archivi Istanze</a></li>						
					<li><a href='javascript:historyClear("/bandi/list.htm?software=CO")'>Gestione bandi</a></li>
					<li><a href='javascript:historyClear("/contabilitamercati/createSearch.htm?software=CO")'>Contabilit&agrave; mercati</a></li>
					<li><a href='javascript:historyClear("/registrazioni/createSearchRateNonPagate.htm?software=CO")'>Ricerca rate non pagate</a></li>
					<li><a href='javascript:historyClear("/stc/createNotifica.htm?software=CO&codiceMovimento=849")'>Notifica attivita</a></li>
					<li><a href='javascript:historyClear("/parametristc/list.htm?tipimovimento.idtipomovimento=SU2")'>Parametri stc</a></li>
				</ul>
			</li>
		</ul>
	</li>
	<li>
		<a href="javascript: void(0);" accesskey="R"><label style="text-decoration: underline; ">R</label>icerche</a>
		<ul>
			<li><a href='javascript:historyClear("/batchscadenzario/listPerOperatore.htm?software=TT")'>Mio Scadenzario</a></li>
			<li><a href='javascript:historyClear("/batchscadenzario/createSearch.htm?software=TT")'>Scadenzario Tutti</a></li>
			<li><a href='javascript:historyClear("/batchscadenzario/createSearch.htm?software=CO")'>Scadenzario Commercio</a></li>
			<li><a href='javascript:historyClear("/batchscadenzario/createSearch.htm?software=CE")'>Scadenzario Edilizia</a></li>
		</ul>
	</li>
	<li>
		<a href="javascript: void(0);" accesskey="S"><label style="text-decoration: underline; ">S</label>tampe</a>
		<ul>
			<li><a href='javascript:historyClear("/registrazioniinout/createStampaIVA.htm?software=CO")'>Stampa ufficio IVA</a></li>
			<li><a href='javascript:historyClear("/report/createReporBase.htm?software=TT")'>Archivi Base</a></li>
			<li>
				<a href='javascript: void(0);'>Commercio</a>
			    <ul>						
					<li><a href='javascript:historyClear("/report/createReporPerModulo.htm?software=CO")'>Stampe istanze</a></li>	
				</ul>
			</li>
		</ul>
	</li>
	<li>
		<a href="javascript: void(0);" accesskey="S"><label style="text-decoration: underline; ">S</label>tatistiche</a>
		<ul>
			<li>
				<a href='javascript: void(0);'>Commercio</a>
			    <ul>						
					<li><a href='javascript:historyClear("/report/createStatisticaPerModulo.htm?software=CO")'>Statistica istanze</a></li>	
				</ul>
			</li>
		</ul>
	</li>
    <li>
		<a href="javascript: void(0);" accesskey="N"><label style="text-decoration: underline; ">N</label>otifiche</a>
		<ul>
			<li><a href='javascript:historyClear("/notificheausl/list.htm?filterIndirizzo=&filterRagSoc=")'>Notifiche AUSL</a></li>
			<li><a href='javascript:historyClear("/notificheausl/list.htm?filterIndirizzo=via&filterRagSoc=")'>Notifiche AUSL filtro per Indirizzo</a></li>
			<li><a href='javascript:historyClear("/notificheausl/list.htm?filterIndirizzo=&filterRagSoc=PATRIZIA")'>Notifiche AUSL filtro per Anagrafe</a></li>
		</ul>
	</li>
    <li>
		<a href="javascript: void(0);" accesskey="N"><label style="text-decoration: underline; ">A</label>lbo Pretorio</a>
		<ul>
			<li><a href='javascript:historyClear("/albopubblicazioni/list.htm?software=CO")'>Lista pubblicazioni</a></li>
			<li><a href='javascript:historyClear("/albocategorie/list.htm?software=CO")'>Albo Categorie</a></li>
		</ul>
	</li>
	
	<li>
		<a href="javascript: void(0);" accesskey="C"><label style="text-decoration: underline; ">U</label>tilità</a>
		<ul>
			<li>
				<a href='javascript:historyClear("/iattivita/searchIstanze.htm?software=TT")'>Gestione attivita</a>
			</li>
		</ul>
	</li>
	
	<li>
		<a href="javascript: void(0);" accesskey="C"><label style="text-decoration: underline; ">C</label>onfigurazione</a>
		<ul>
			<li>
				<a href="javascript: void(0);" accesskey="T">Tutti i front office</a>
				<ul>
					<li><a href='javascript:historyClear("/configurazione/createAndViewLoghi.htm?software=TT")'>Gestione Loghi</a></li>
					<li><a href='javascript:historyClear("/menuinfo/list.htm?software=TT")'>Menù Info</a></li>
					<li><a href='javascript:historyClear("/menufo/list.htm?software=TT")'>Menù Servizi</a></li>
					
				</ul>
			</li>
		</ul>
	</li>
	
	

	<li>
		<spring-security:authorize ifNotGranted="ROLE_PREVIOUS_ADMINISTRATOR"><a href="<%=request.getContextPath()%>/j_spring_security_logout" accesskey="U"><label style="text-decoration: underline; ">U</label>scita</a></spring-security:authorize>
		<spring-security:authorize ifAllGranted="ROLE_PREVIOUS_ADMINISTRATOR"><a href="<%=request.getContextPath()%>/j_spring_security_exit_user">Torna al tuo utente</a></spring-security:authorize>
	</li>
</ul>