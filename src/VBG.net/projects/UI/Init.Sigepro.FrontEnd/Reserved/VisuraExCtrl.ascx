<%@ Control Language="C#" AutoEventWireup="true" CodeBehind="VisuraExCtrl.ascx.cs"
	Inherits="Init.Sigepro.FrontEnd.Reserved.VisuraExCtrl" %>
<% if (!DaArchivio)
   { %>

<script type="text/javascript">

	var urlAllegatiMovimento = '<%= ResolveClientUrl("~/Reserved/ListaAllegatiMovimento.aspx?IdComune=" + IdComune + "&Software=" + DataSource.SOFTWARE + "&Token=" + Token + "&Istanza=" + DataSource.CODICEISTANZA) %>';
	var urlAllegatiEndo = '<%= ResolveClientUrl("~/Reserved/ListaAllegatiEndo.aspx?IdComune=" + IdComune + "&Software=" + DataSource.SOFTWARE + "&Token=" + Token + "&Istanza=" + DataSource.CODICEISTANZA) %>';
	
	var loadingIcon = '<%= ResolveClientUrl("~/Images/ajax-loader.gif")%>';
	var attachmentIcon = '<%= ResolveClientUrl("~/Images/attachment-icon.png")%>';

	require(['jquery', 'jquery.ui'], function ($) { 
		$(function() {

			$('#popupDettagli').dialog({ modal: true, autoOpen: false, width: 500 });
		
			$('.allegatiMovimento').css('cursor', 'pointer').click(function() {
				mostraAllegatiMovimento($(this));
			});
		
			$('.allegatiProcedimenti').css('cursor', 'pointer').click(function() {
				mostraAllegatiEndo($(this));
			});
		});
	
		function mostraAllegatiMovimento( imgTag )
		{
			mostraAllegati( imgTag , urlAllegatiMovimento + '&movimento=' + imgTag.attr('idMovimento') , 'Allegati del movimento');
		}
	
		function mostraAllegatiEndo( imgTag )
		{
			mostraAllegati( imgTag , urlAllegatiEndo + '&endo=' + imgTag.attr('idProcedimento') , 'Allegati dell\'endoprocedimento');
		}
	
		function mostraAllegati( imgTag , url , titolo)
		{
			var titolo = imgTag.parent() // td
							   .parent() // tr
							   .find(':nth-child(2)')
							   .html();

			imgTag.attr('src', loadingIcon);

			//var url = urlAllegatiMovimento + '&movimento=' + $(this).attr('idMovimento');

			$('#popupDettagli').load(url, function() {
				imgTag.attr('src', attachmentIcon);
				$('#popupDettagli').dialog('option', 'width', '700');
				$('#popupDettagli').dialog('option', 'title', titolo + ' \"' + titolo + '\"').dialog('open');
			});
		}
	});
	
</script>

<% } %>
<div class="inputForm">
	<fieldset>
		<div>
			<asp:Literal ID="ltrIntestazioneDettaglio" runat="server"></asp:Literal>
		</div>
	</fieldset>
	<fieldset>
		<legend>Dettagli dell'istanza</legend>
		<% if (!DaArchivio)
	 { %>
		<div>
			<asp:Label ID="Label16" runat="server" AssociatedControlID="lblProtocollo">Numero protocollo</asp:Label>
			<asp:Label runat="server" ID="lblProtocollo" />
		</div>
		<div>
			<asp:Label ID="Folabel9" runat="server" AssociatedControlID="lblDataProtocollo">Data protocollo</asp:Label>
			<asp:Label runat="server" ID="lblDataProtocollo" />
		</div>
		<%} %>
		<div>
			<asp:Label ID="Label17" runat="server" AssociatedControlID="lblNumeroPratica">N.Pratica</asp:Label>
			<asp:Label runat="server" ID="lblNumeroPratica" />
		</div>
		<div>
			<asp:Label ID="Label14" runat="server" AssociatedControlID="lblDataPresentazione">Data invio</asp:Label>
			<asp:Label runat="server" ID="lblDataPresentazione" />
		</div>
		<div>
			<asp:Label ID="Label12" runat="server" AssociatedControlID="lblOggetto">Oggetto</asp:Label>
			<asp:Label runat="server" ID="lblOggetto" />
		</div>
		<div>
			<asp:Label ID="Label13" runat="server" AssociatedControlID="lblIntervento">Descrizione Intervento</asp:Label>
			<asp:Label runat="server" ID="lblIntervento" />
		</div>
		<div>
			<asp:Label ID="Label15" runat="server" AssociatedControlID="lblStatoPratica">Stato</asp:Label>
			<asp:Label runat="server" ID="lblStatoPratica" />
		</div>
	</fieldset>
	
	<% if (!DaArchivio)
	{ %>	
	<fieldset class="blocco aperto">
		<legend>Riferimenti</legend>
		<div>
			<asp:Label ID="Label1" runat="server" AssociatedControlID="lblResponsabileProc">Responsabile procedimento</asp:Label>
			<asp:Label runat="server" ID="lblResponsabileProc" />
		</div>
		<div>
			<asp:Label ID="Label2" runat="server" AssociatedControlID="lblIstruttore">Istruttore</asp:Label>
			<asp:Label runat="server" ID="lblIstruttore" />
		</div>
		<div>
			<asp:Label ID="Label3" runat="server" AssociatedControlID="lblOperatore">Operatore</asp:Label>
			<asp:Label runat="server" ID="lblOperatore" />
		</div>
	</fieldset>

	<fieldset class="blocco aperto">
		<legend>Soggetti</legend>
		<div>
			<asp:GridView GridLines="None" runat="server" ID="dgSoggetti" AutoGenerateColumns="false"
				Width="100%">
				<Columns>
					<asp:BoundField DataField="Nominativo" HeaderText="Nominativo" />
					<asp:BoundField DataField="InQualitaDi" HeaderText="Tipo soggetto" />
					<asp:BoundField DataField="NominativoCollegato" HeaderText="Anagrafica collegata" />
					<asp:BoundField DataField="Procuratore" HeaderText="Procuratore" />
				</Columns>
			</asp:GridView>
		</div>
	</fieldset>
	<%} %>
	<fieldset class="blocco aperto">
		<legend>Localizzazioni</legend>
		<div>
			<asp:GridView GridLines="None" runat="server" ID="dgLocalizzazioni" AutoGenerateColumns="false"
				Width="100%">
				<Columns>
					<asp:TemplateField HeaderText="Indirizzo" ItemStyle-Width="45%">
						<ItemTemplate>
							<asp:Literal runat="server" ID="Literal1" Text='<%# DataBinder.Eval(Container.DataItem , "Stradario.PREFISSO") %>' />&nbsp;
							<asp:Literal runat="server" ID="Literal2" Text='<%# DataBinder.Eval(Container.DataItem , "Stradario.DESCRIZIONE") %>' />&nbsp;
						</ItemTemplate>
					</asp:TemplateField>
					
					<asp:BoundField DataField="Civico" HeaderText="Civ." ItemStyle-Width="5%" />
					<asp:BoundField DataField="Esponente" HeaderText="Esp." ItemStyle-Width="5%" />
					<asp:BoundField DataField="Colore" HeaderText="Col." ItemStyle-Width="5%"  />
					<asp:BoundField DataField="Scala" HeaderText="Sca." ItemStyle-Width="5%"  />
					<asp:BoundField DataField="Piano" HeaderText="Piano" ItemStyle-Width="5%" />
					<asp:BoundField DataField="Interno" HeaderText="Int." ItemStyle-Width="5%"  />					
					<asp:BoundField DataField="ESPONENTEINTERNO" HeaderText="Esp.Int." ItemStyle-Width="5%" />							
					<asp:BoundField DataField="FABBRICATO" HeaderText="Fab." ItemStyle-Width="5%"  />					
					<%--<asp:BoundField DataField="Km" HeaderText="Km" ItemStyle-Width="5%"  />	--%>

					
					<asp:TemplateField HeaderText="Località/Frazione"  ItemStyle-Width="15%">
						<ItemTemplate>
							<asp:Literal runat="server" ID="Literal3" Text='<%# DataBinder.Eval(Container.DataItem , "Stradario.LOCFRAZ") %>' />
						</ItemTemplate>
					</asp:TemplateField>


					
				</Columns>
				<EmptyDataTemplate>
					<asp:Label ID="lblErrNoLocalizzazioni" runat="server"><i>Non sono presenti localizzazioni</i></asp:Label>
				</EmptyDataTemplate>
			</asp:GridView>
		</div>
		<div>
			<asp:GridView GridLines="None" runat="server" ID="dgDatiCatastali" AutoGenerateColumns="false"
				Width="100%">
				<Columns>
					<asp:TemplateField HeaderText="Tipo catasto">
						<ItemTemplate>
							<asp:Literal runat="server" ID="ltrTipoCatasto" Text='<%# DataBinder.Eval(Container.DataItem , "Catasto.DESCRIZIONE")%>' />
						</ItemTemplate>
					</asp:TemplateField>
					<asp:BoundField DataField="Foglio" HeaderText="Foglio" />
					<asp:BoundField DataField="Particella" HeaderText="Particella" />
					<asp:BoundField DataField="Sub" HeaderText="Subalterno" />
				</Columns>
				<EmptyDataTemplate>
					<asp:Label ID="lblErrNoLocalizzazioni" runat="server"><i>Non sono presenti dati catastali</i></asp:Label>
				</EmptyDataTemplate>
			</asp:GridView>
		</div>
	</fieldset>
	
	<% if(!DaArchivio){%>
	<fieldset class="blocco aperto">
		<legend>Documenti istanza</legend>
		<div>
			<asp:GridView GridLines="None" runat="server" ID="gvAllegati" AutoGenerateColumns="false"
				Width="100%" OnRowDataBound="gvAllegati_RowDataBound">
				<Columns>
					<asp:TemplateField ItemStyle-Width="16px">
						<ItemTemplate>
							<asp:HyperLink runat="server" ID="hlDownloadAllegato" Target="_blank">
								<img src='<%= ResolveClientUrl("~/Images/attachment-icon.png") %>' />
							</asp:HyperLink>
							<%--<asp:HyperLink ID="hlDownloadAllegato1" runat="server" Text="Scarica" />--%>
						</ItemTemplate>
					</asp:TemplateField>
					<asp:TemplateField HeaderText="Documento">
						<ItemTemplate>
							<asp:Literal runat="server" ID="ltrDescrizione" Text='<%# ProcessaDescrizione( DataBinder.Eval( Container.DataItem , "DOCUMENTO" ) ) %>' />
							<div class="md5-allegato">
							[<asp:Literal runat="server" ID="ltrNomeFile" Text='<%# DataBinder.Eval( Container.DataItem , "Oggetto.NOMEFILE" )%>' />
							 <asp:Literal runat="server" ID="ltrMd5" Text='<%# DataBinder.Eval( Container.DataItem , "Oggetto.Md5", "- MD5: {0}" )%>' Visible='<%# Eval("Oggetto.HasMd5")%>' />]
							</div>
						</ItemTemplate>
					</asp:TemplateField>
					<asp:BoundField HeaderText="Data" DataField="DATA" DataFormatString="{0:dd/MM/yyyy}"
						HtmlEncode="false" />
				</Columns>
				<EmptyDataTemplate>
					<asp:Label ID="lblErrNoLocalizzazioni" runat="server"><i>Non sono presenti documenti</i></asp:Label>
				</EmptyDataTemplate>
			</asp:GridView>
		</div>
	</fieldset>
	
	
	<fieldset class="blocco aperto">
		<legend>Endoprocedimenti attivati</legend>
		<div>
			<asp:GridView GridLines="None" runat="server" ID="dgProcedimenti" AutoGenerateColumns="false"
				Width="100%">
				<Columns>
					<asp:TemplateField ItemStyle-Width="16px">
						<ItemTemplate>
							<asp:Image runat="server" CssClass="allegatiProcedimenti" ID="imgAllegati" ImageUrl="~/Images/attachment-icon.png"
								Visible='<%# VerificaEsistenzaAllegatiProcedimento( DataBinder.Eval(Container,"DataItem") ) %>'
								idProcedimento='<%# Eval("CODICEINVENTARIO") %>' />
						</ItemTemplate>
					</asp:TemplateField>
					<asp:TemplateField HeaderText="Procedimento">
						<ItemTemplate>
							<asp:Literal runat="server" ID="ltrProcedimento" Text='<%# DataBinder.Eval(Container.DataItem , "Endoprocedimento.Procedimento")%>' />
						</ItemTemplate>
					</asp:TemplateField>
				</Columns>
				<EmptyDataTemplate>
					<asp:Label ID="lblErrNoLocalizzazioni" runat="server"><i>Non sono presenti procedimenti attivati</i></asp:Label>
				</EmptyDataTemplate>
			</asp:GridView>
		</div>
	</fieldset>
	
	
	<fieldset class="blocco aperto">
		<legend>Oneri</legend>
		<div>
			<asp:GridView GridLines="None" runat="server" ID="dgOneri" AutoGenerateColumns="false"
				Width="100%">
				<Columns>
					<asp:TemplateField HeaderText="Onere">
						<ItemTemplate>
							<asp:Literal runat="server" ID="ltrCausaleOnere" Text='<%# DataBinder.Eval(Container.DataItem,"CausaleOnere.CoDescrizione") %>'></asp:Literal>
						</ItemTemplate>
					</asp:TemplateField>
					<asp:BoundField DataField="PREZZO" HeaderText="Importo" ItemStyle-HorizontalAlign="Right" DataFormatString="€ {0:N2}" HtmlEncode="false"/>
					<asp:BoundField DataField="DATASCADENZA" HeaderText="Data Scadenza" ItemStyle-HorizontalAlign="Center"
						DataFormatString="{0:dd/MM/yyyy}" HtmlEncode="false" />
					<asp:BoundField DataField="DATA" HeaderText="Data Pagamento" ItemStyle-HorizontalAlign="Center"
						DataFormatString="{0:dd/MM/yyyy}" HtmlEncode="false" />
				</Columns>
				<EmptyDataTemplate>
					<asp:Label ID="lblErrNoLocalizzazioni" runat="server"><i>Non sono presenti oneri per la pratica</i></asp:Label>
				</EmptyDataTemplate>
			</asp:GridView>
		</div>
	</fieldset>

	<fieldset class="blocco aperto">
		<legend>Movimenti</legend>
		<div>
			<asp:GridView GridLines="None" runat="server" ID="dgMovimenti" AutoGenerateColumns="false"
				Width="100%">
				<Columns>
					<asp:TemplateField ItemStyle-Width="16px">
						<ItemTemplate>
							<asp:Image runat="server" CssClass="allegatiMovimento" ID="imgAllegati" ImageUrl="~/Images/attachment-icon.png"
								Visible='<%# VerificaEsistenzaAllegatiMovimento( DataBinder.Eval( Container.DataItem, "MovimentiAllegati" ) ) %>'
								idMovimento='<%# Eval("CODICEMOVIMENTO") %>' />
						</ItemTemplate>
					</asp:TemplateField>
					<asp:BoundField DataField="Movimento" HeaderText="Movimento" />
					<asp:BoundField DataField="Data" HeaderText="Data" ItemStyle-HorizontalAlign="Center"
						DataFormatString="{0:dd/MM/yyyy}" HtmlEncode="false" />
					<asp:BoundField DataField="Parere" HeaderText="Parere" />
					<asp:TemplateField HeaderText="Protocollo">
						<ItemTemplate>
							<asp:Literal runat="server" ID="ltrNumeoProtocollo" Text='<%# Bind("NumeroProtocollo" , "n. {0}") %>' />
							<asp:Literal runat="server" ID="ltrDataProtocollo" Text='<%# Bind("DATAPROTOCOLLO"," del {0:dd/MM/yyyy}") %>' />
						</ItemTemplate>
					</asp:TemplateField>
					<%-- 
					le note del movimento sono state rimosse il 13/04/2012 su richiesta del comune di como
					in quanto nelle note potrebbero essere presenti informazioni che il cittadino non dovrebbe vedere
					 --%>
					<%--<asp:BoundField DataField="Note" HeaderText="Note" />--%>
				</Columns>
				<EmptyDataTemplate>
					<asp:Label ID="lblErrNoLocalizzazioni" runat="server"><i>Non sono presenti movimenti attivati</i></asp:Label>
				</EmptyDataTemplate>
			</asp:GridView>
		</div>
	</fieldset>
	<%} %>
	
	<fieldset class="blocco aperto">
		<legend>Autorizzazioni</legend>
		<div>
			<asp:GridView GridLines="None" runat="server" ID="dgAutorizzazioni" AutoGenerateColumns="false"
				Width="100%">
				<Columns>
					<asp:BoundField DataField="AUTORIZDATA" HeaderText="Data Rilascio" DataFormatString="{0:dd/MM/yyyy}" HtmlEncode="false" />
					<asp:TemplateField>
						<ItemTemplate>
							<asp:Literal runat="server" ID="ltrTipoRegistro" Text='<%# DataBinder.Eval(Container.DataItem,"Registro.TR_DESCRIZIONE") %>' />
						</ItemTemplate>
					</asp:TemplateField>
					<asp:BoundField DataField="AUTORIZRESPONSABILE" HeaderText="Note" />
					<asp:BoundField DataField="AUTORIZNUMERO" HeaderText="Numero" />
				</Columns>
				<EmptyDataTemplate>
					<asp:Label ID="lblErrNoLocalizzazioni" runat="server"><i>Non sono presenti autorizzazioni</i></asp:Label>
				</EmptyDataTemplate>
			</asp:GridView>
		</div>
	</fieldset>
	
	<% if (!DaArchivio)	{ %>
	<fieldset class="blocco aperto">
		<legend>Scadenze</legend>
		<div>
			<asp:GridView GridLines="None" runat="server" ID="dgScadenze" DataKeyNames="CodiceScadenza"
				AutoGenerateColumns="false" Width="100%" OnSelectedIndexChanged="dgScadenze_SelectedIndexChanged">
				<Columns>
					<asp:BoundField DataField="DatiMovimento" HeaderText="Movimento precedente"></asp:BoundField>
					<asp:BoundField DataField="DescrMovimentoDaFare" HeaderText="Movimento da fare">
					</asp:BoundField>
					<asp:BoundField DataField="DataScadenza" HeaderText="Scadenza" DataFormatString="{0:dd/MM/yyyy}"
						HtmlEncode="false"></asp:BoundField>
					<asp:BoundField DataField="Procedura" HeaderText="Procedura"></asp:BoundField>
					<asp:ButtonField Text="Effettua movimento" CommandName="Select"></asp:ButtonField>
				</Columns>
				<EmptyDataTemplate>
					<asp:Label ID="lblErrNoLocalizzazioni" runat="server"><i>Non sono presenti scadenze</i></asp:Label>
				</EmptyDataTemplate>
			</asp:GridView>
		</div>
	</fieldset>
	
	<%} %>
	<div id="popupDettagli">
	</div>
</div>
