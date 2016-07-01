<%@ Page Title="" Language="C#" MasterPageFile="~/AreaRiservataPopupMaster.Master"
	EnableViewState="false" AutoEventWireup="true" CodeBehind="MostraDettagliIntervento.aspx.cs"
	Inherits="Init.Sigepro.FrontEnd.Public.MostraDettagliIntervento" %>

<%@ Register TagPrefix="cc1" Assembly="Init.Sigepro.FrontEnd.WebControls" Namespace="Init.Sigepro.FrontEnd.WebControls.Interventi" %>
<%@ Register TagPrefix="uc1" Src="~/Public/AlberelloEndoControl.ascx" TagName="AlberelloEndo" %>

<asp:Content runat="server" id="testa" ContentPlaceHolderID="head">
<%if (Print){ %>
<style media="all" type="text/css">
	BODY{ font-family: Arial; font-size: 12px}	
	A,H1,H2,H3,H4,H5{ font-family: Arial;text-decoration:none;color:#000}
	TABLE{width:100%;border: 1px solid #000; border-collapse:collapse;margin:0px;padding:0px}
	TR{margin: 0px;padding: 0px}
	TD,TH{margin: 0px;padding: 0px;}
	TH{background-color: #ccc;}
	FIELDSET {margin: 0px;padding: 0px;}
	.etichetta{ float:left;font-weight:bold; min-width: 250px;width: 250px}
	H3{margin-bottom: 5px;}
	.famigliaEndo, .tipoEndo{ font-weight: bold}
	UL { margin-left: 10px;padding-left:10px;display: block;list-style-type: none;}
	LI{margin-left: 10px;padding-left:10px;}
	#ctl00_ContentPlaceHolder1_pnlNote{text-align:left}
	.endoRichiesto{ list-style-type: }
</style>
<%} %>
</asp:Content>

<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
	<script type="text/javascript">
		$(function () {
			$('#accordion').accordion({ header: "h3", heightStyle: 'content' });
			$('#accordion table TR:even').addClass('AlternatingItemStyle');
			$('#accordion table TR:odd').addClass('ItemStyle');
		});
	</script>
	<asp:Panel runat="server" ID="pnlDatiIntervento">
		<div class="inputForm">
			<%if (Print){ %>
				<h2>SCHEDA DELL'INTERVENTO</h2>
			<%} else {%>
				<% if (!Print && !NoPrintButton) {%>
				<div style="width: 100%; text-align: right">
					<img src="<%= GetBaseUrlAssoluto() + "Images/print_icon.png" %>" style="vertical-align: middle" /><a
						href='<%= GetUrlDownloadPagina() %>' target="_blank">Stampa scheda</a>
				</div>
				<%} %>
			<%} %>
			<div id="accordion">
				<div>
					<%if (!Print){ %>
					<h3><a href="#">Intervento/Evento selezionato</a></h3>
					<%} %>
					<div>
						<cc1:InterventiTreeRenderer runat="server" ID="treeRenderer" CssClass="treeViewDettagli"
							MostraNote="false" StartCollapsed="false" Interactive="false" />
					</div>
				</div>
				<% if (MostraDefinizioni){ %>
					<div>
						<h3>
							<a href="#">Informazioni varie</a></h3>
						<div>
							<asp:Panel runat="server" ID="pnlNote" CssClass="pannelloNote">
								<asp:Literal runat="server" ID="ltrNote" />
							</asp:Panel>
						</div>
					</div>
				<%} %>
				<asp:Repeater runat="server" ID="rptNormativa">
					<HeaderTemplate>
						<div>
							<h3>
								<a href="#">Normativa</a></h3>
							<div>
								<table width="100%">
									<% if (!Print){%>
										<colgroup>
											<col width="50%" />
											<col width="40%" />
											<col width="5%" />
											<col width="5%" />
										</colgroup>
									<%} %>
									<thead>
										<tr>
											<th>
												Descrizione
											</th>
											<th>
												Tipologia
											</th>
											<% if (!Print){%>
												<th>
													&nbsp;
												</th>
												<th>
													&nbsp;
												</th>
											<%} %>
										</tr>
									</thead>
					</HeaderTemplate>
					<ItemTemplate>
						<tr>
							<td>
								<asp:Literal runat="server" ID="Literal2" Text='<%# Eval("Descrizione") %>' />
							</td>
							<td>
								<asp:Literal runat="server" ID="Literal3" Text='<%# Eval("Categoria") %>' />
							</td>
							<% if (!Print){%>
							<td>
								<%# GetLinkModello( Eval("CodiceOggetto") ) %>
							</td>
							<td>
								<%# GetLinkNormativa(DataBinder.Eval(Container,"DataItem"))%>
							</td>
							<%} %>
						</tr>
					</ItemTemplate>
					<FooterTemplate>
						</table> </div> </div>
					</FooterTemplate>
				</asp:Repeater>
				<asp:Repeater runat="server" ID="rptFasiAttuative">
					<HeaderTemplate>
						<div>
							<h3>
								<a href="#">Fasi attuative</a></h3>
							<div>
								<table>
									<thead>
										<tr>
											<th>
												Titolo
											</th>
											<th>
												Testo
											</th>
										</tr>
									</thead>
					</HeaderTemplate>
					<ItemTemplate>
						<tr>
							<td>
								<asp:Literal runat="server" ID="Literal2" Text='<%# Eval("Descrizione") %>' />
							</td>
							<td>
								<asp:Literal runat="server" ID="Literal3" Text='<%# Eval("DescrizioneEstesa") %>' />
							</td>
						</tr>
					</ItemTemplate>
					<FooterTemplate>
						</table> </div> </div>
					</FooterTemplate>
				</asp:Repeater>
				<asp:Repeater runat="server" ID="rptModulistica">
					<HeaderTemplate>
						<div>
							<h3>
								<a href="#">Modulistica</a></h3>
							<div>
								<table>
									<thead>
										<tr>
											<th colspan="3">
												Descrizione
											</th>
										</tr>
									</thead>
					</HeaderTemplate>
					<ItemTemplate>
						<tr>
							<td>
								<asp:Literal runat="server" ID="Literal2" Text='<%# Eval("Obbligatorio") %>' />
							</td>
							<td>
								<asp:Literal runat="server" ID="Literal3" Text='<%# Eval("Descrizione") %>' />
							</td>
							<td style="text-align: right">
								<%if (!Print){ %>
								<asp:HyperLink runat="server" ID="lnkDownloadModello" ImageUrl='<%# this.Page.ResolveUrl("~/Images/download16x16.png") %>'
									Target="_blank" ToolTip="Scarica Modello" Visible='<%# Eval("SupportaDownloadGenerico") %>'
									NavigateUrl='<%#Eval("LinkGenerico") %>' />
								<asp:HyperLink runat="server" ID="lnkMostraPdf" ImageUrl='<%# this.Page.ResolveUrl("~/Images/pdf16x16.gif") %>'
									Target="_blank" ToolTip='Scarica il modello in formato Pdf' Visible='<%#Eval("SupportaDownloadPdf") %>'
									NavigateUrl='<%#Eval("LinkPdf") %>' />
								<asp:HyperLink runat="server" ID="lnkMostraRtf" ImageUrl='<%# this.Page.ResolveUrl("~/Images/rtf16x16.gif") %>'
									Target="_blank" ToolTip='Scarica il modello in formato Rtf' Visible='<%#Eval("SupportaDownloadRtf") %>'
									NavigateUrl='<%#Eval("LinkRtf") %>' />
								<asp:HyperLink runat="server" ID="lnkMostraDoc" ImageUrl='<%# this.Page.ResolveUrl("~/Images/doc16x16.gif") %>'
									Target="_blank" ToolTip='Scarica il modello in formato Word' Visible='<%#Eval("SupportaDownloadDoc") %>'
									NavigateUrl='<%#Eval("LinkDoc") %>' />
								<asp:HyperLink runat="server" ID="lnkMostraOd" ImageUrl='<%# this.Page.ResolveUrl("~/Images/opn16x16.gif") %>'
									Target="_blank" ToolTip='Scarica il modello in formato Open Document' Visible='<%#Eval("SupportaDownloadOdt") %>'
									NavigateUrl='<%#Eval("LinkOdt") %>' />
								<%} %>
							</td>
						</tr>
					</ItemTemplate>
					<FooterTemplate>
						</table> </div> </div>
					</FooterTemplate>
				</asp:Repeater>
				<uc1:AlberelloEndo runat="server" ID="rptProcedimentiNecessari" Titolo="Procedimenti necessari" />
				<uc1:AlberelloEndo runat="server" ID="rptProcedimentiRicorrenti" Titolo="Procedimenti ricorrenti" />
				<uc1:AlberelloEndo runat="server" ID="rptProcedimentiEventuali" Titolo="Procedimenti eventuali" />
				<asp:Repeater ID="rptOneri" runat="server">
					<HeaderTemplate>
						<div>
							<h3><a href="#">Oneri</a></h3>
							<div>
								<table>
								<thead>
									<tr>
										<th>
											Causale d'onere
										</th>
										<th>
											Importo
										</th>
									</tr>
								</thead>
					</HeaderTemplate>
					<ItemTemplate>
								<tr>
									<td>
										<asp:Literal runat="server" ID="Literal2" Text='<%# Eval("Descrizione") %>' />
									</td>
									<td>
										<asp:Literal runat="server" ID="Literal3" Text='<%# Eval("Importo","€ {0:N2}") %>' />
									</td>
								</tr>
					</ItemTemplate>
					<FooterTemplate>
								</table> 
							</div> 
						</div>
					</FooterTemplate>
				</asp:Repeater>
			</div>
		</div>
	</asp:Panel>
</asp:Content>
