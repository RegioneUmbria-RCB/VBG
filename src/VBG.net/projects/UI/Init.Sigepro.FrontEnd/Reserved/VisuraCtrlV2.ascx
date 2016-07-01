<%@ Control Language="C#" AutoEventWireup="true" CodeBehind="VisuraCtrlV2.ascx.cs"
	Inherits="Init.Sigepro.FrontEnd.Reserved.VisuraCtrlV2" %>
<%@ Register Src="~/Reserved/VisuraCtrlV2_DettaglioEndo.ascx" TagPrefix="uc1" TagName="VisuraCtrlV2_DettaglioEndo" %>

<div class="inputForm">
	<fieldset>
		<div>
			<asp:Literal ID="ltrIntestazioneDettaglio" runat="server"></asp:Literal>
		</div>
	</fieldset>
<%--	<div id="dettagliVisura">--%>
		<ul>
		</ul>
		<fieldset class="aperto">
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
						<%--<asp:BoundField DataField="Procuratore" HeaderText="Procuratore" />--%>
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
						<asp:TemplateField HeaderText="Indirizzo">
							<ItemTemplate>
								<asp:Literal runat="server" ID="Literal1" Text='<%# DataBinder.Eval(Container.DataItem , "denominazione") %>' />&nbsp;
							</ItemTemplate>
						</asp:TemplateField>
						<asp:BoundField DataField="civico" HeaderText="Civico" />
						<asp:BoundField DataField="esponente" HeaderText="Esp" />
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
								<asp:Literal runat="server" ID="ltrTipoCatasto" Text='<%# Eval("tipoCatasto")%>' />
							</ItemTemplate>
						</asp:TemplateField>
						<asp:BoundField DataField="foglio" HeaderText="Foglio" />
						<asp:BoundField DataField="particella" HeaderText="Particella" />
						<asp:BoundField DataField="sub" HeaderText="Subalterno" />
					</Columns>
					<EmptyDataTemplate>
						<asp:Label ID="lblErrNoLocalizzazioni" runat="server"><i>Non sono presenti dati catastali</i></asp:Label>
					</EmptyDataTemplate>
				</asp:GridView>
			</div>
		</fieldset>
		<% if (!DaArchivio) {%>
		<fieldset class="blocco aperto">
			<legend>Documenti istanza</legend>
			<div>
				<asp:GridView GridLines="None" runat="server" ID="gvAllegati" AutoGenerateColumns="false"
					Width="100%" OnRowDataBound="gvAllegati_RowDataBound">
					<Columns>
						<asp:TemplateField HeaderText="Documento">
							<ItemTemplate>
								<asp:HyperLink runat="server" ID="hlDownloadAllegato" Target="_blank">
									<asp:Literal runat="server" ID="ltrDescrizione" Text='<%# ProcessaDescrizione( DataBinder.Eval( Container.DataItem , "documento" ) ) %>' />
								</asp:HyperLink>
								<div class="md5-allegato">
									[<asp:Literal runat="server" ID="ltrNomeFile" Text='<%# DataBinder.Eval( Container.DataItem , "NomeAllegato" )%>' />
									<asp:Literal runat="server" ID="ltrMd5" Text='<%# DataBinder.Eval( Container.DataItem , "Md5", "- MD5: {0}" )%>' Visible='<%# Eval("HasMd5")%>' />]									
								</div>
                                <div class="note-allegato">
                                    <asp:Literal runat="server" ID="Literal4" Text='<%# Eval( "annotazioni" , "Note: {0}")%>' Visible='<%# Eval("HasAnnotazioni")%>' />
                                </div>
							</ItemTemplate>
						</asp:TemplateField>
					</Columns>
					<EmptyDataTemplate>
						<asp:Label ID="lblErrNoLocalizzazioni" runat="server"><i>Non sono presenti documenti</i></asp:Label>
					</EmptyDataTemplate>
				</asp:GridView>
			</div>
		</fieldset>


        <% if(TempisticheVisibili) {%>
            <fieldset class="blocco aperto">
                <legend>Tempistiche</legend>
                <div>
				    <label>Data inizio</label>
				    <asp:Label runat="server" ID="lblDataInizio" />
                </div>

                <div>
				    <label>Data fine</label>
				    <asp:Label runat="server" ID="lblDataFine" />
                </div>

                <div>
				    <label>Durata</label>
				    <asp:Label runat="server" ID="lblDurataGiorni" />
                </div>
            </fieldset>
        <%} %>



		<fieldset class="blocco aperto">
			<legend>Endoprocedimenti attivati</legend>
			<div>
				<asp:GridView GridLines="None" runat="server" ID="dgProcedimenti" AutoGenerateColumns="false"
					Width="100%">
					<Columns>
						<asp:TemplateField HeaderText="Procedimento">
							<ItemTemplate>
								<uc1:VisuraCtrlV2_DettaglioEndo runat="server" id="dettaglioEndo" DataSource='<%# DataBinder.Eval(Container,"DataItem") %>' />
							</ItemTemplate>
						</asp:TemplateField>
					</Columns>
					<EmptyDataTemplate>
						<asp:Label ID="lblErrNoEndo" runat="server"><i>Non sono presenti procedimenti attivati</i></asp:Label>
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
								<asp:Literal runat="server" ID="ltrCausaleOnere" Text='<%# DataBinder.Eval(Container.DataItem,"causale.causale") %>'></asp:Literal>
							</ItemTemplate>
						</asp:TemplateField>
						<asp:BoundField DataField="importo" HeaderText="Importo" ItemStyle-HorizontalAlign="Right"
							DataFormatString="€ {0:N2}" HtmlEncode="false" />
						<asp:TemplateField HeaderText="Rate">
							<ItemTemplate>
								<asp:Repeater runat="server" ID="rptScadenzeOnere" DataSource='<%# DataBinder.Eval(Container.DataItem , "scadenze") %>'>
									<HeaderTemplate>
										<ul>
									</HeaderTemplate>
									<ItemTemplate>
										<li>Rata n.<asp:Literal runat="server" ID="ltr1" Text='<%# Eval("numeroRata") %>' /><br />
											Importo: <asp:Literal runat="server" ID="Literal2" Text='<%# Eval("importoRata", "{0:N2} €") %>' /><br />
											Scadenza:
											<asp:Literal runat="server" ID="Literal3" Text='<%# Eval("dataScadenza", "{0:dd/MM/yyyy}") %>' /><br />
										</li>
									</ItemTemplate>
									<FooterTemplate>
										</ul>
									</FooterTemplate>
								</asp:Repeater>
							</ItemTemplate>
						</asp:TemplateField>
						<%--<asp:BoundField DataField="DATASCADENZA" HeaderText="Data Scadenza" ItemStyle-HorizontalAlign="Center"
							DataFormatString="{0:dd/MM/yyyy}" HtmlEncode="false" />
						<asp:BoundField DataField="DATA" HeaderText="Data Pagamento" ItemStyle-HorizontalAlign="Center"
							DataFormatString="{0:dd/MM/yyyy}" HtmlEncode="false" />--%>
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
<%--						<asp:TemplateField ItemStyle-Width="16px">
							<ItemTemplate>
								<asp:Image runat="server" CssClass="allegatiMovimento" ID="imgAllegati" ImageUrl="~/Images/attachment-icon.png"
									Visible='<%# VerificaEsistenzaAllegatiMovimento( DataBinder.Eval( Container.DataItem, "MovimentiAllegati" ) ) %>'
									idMovimento='<%# Eval("CODICEMOVIMENTO") %>' />
							</ItemTemplate>
						</asp:TemplateField>--%>
						<asp:BoundField DataField="tipoAttivita.descrizione" HeaderText="Movimento" />
						<asp:BoundField DataField="dataAttivita" HeaderText="Data" ItemStyle-HorizontalAlign="Center"
							DataFormatString="{0:dd/MM/yyyy}" HtmlEncode="false" />
						<asp:BoundField DataField="parere" HeaderText="Parere" />
						<asp:TemplateField HeaderText="Protocollo">
							<ItemTemplate>
								<asp:Literal runat="server" ID="ltrNumeoProtocollo" Text='<%# Bind("numeroProtocolloGenerale" , "n. {0}") %>' />
								<asp:Literal runat="server" ID="ltrDataProtocollo" Text='<%# Bind("dataProtocolloGenerale"," del {0:dd/MM/yyyy}") %>' />
							</ItemTemplate>
						</asp:TemplateField>
						<asp:BoundField DataField="note" HeaderText="Note" />
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
						<asp:BoundField DataField="AUTORIZDATA" HeaderText="Data Rilascio" DataFormatString="{0:dd/MM/yyyy}"
							HtmlEncode="false" />
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
		<% if (!DaArchivio)
	 { %>
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
<%--	</div>--%>
	<%} %>
	<div id="popupDettagli">
	</div>
</div>
