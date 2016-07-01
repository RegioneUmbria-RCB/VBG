<%@ Page Title="" Language="C#" EnableViewState="false" MasterPageFile="~/AreaRiservataPopupMaster.Master"
	AutoEventWireup="true" CodeBehind="MostraDettagliEndo.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Public.MostraDettagliEndo" %>

<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
	<script type="text/javascript">
		$(function () {
			$('#accordion').accordion({ header: "h3", heightStyle: 'content' });
			$('#accordion table TR:even').addClass('AlternatingItemStyle');
			$('#accordion table TR:odd').addClass('ItemStyle');

			$('#lnkStampa').click(function (e) {
				e.preventDefault();
				window.open('<%= GetUrlStampaPagina() %>');
				return false;
			});
		});
	</script>
	<asp:Panel runat="server" ID="pnlDatiEndo" >

		<%if (Print){ %>
			<style media="all" type="text/css">
				BODY{font-family: Arial;font-size: 12px;}
				A, H1, H2, H3, H4, H5 {font-family: Arial; text-decoration: none; color: #000;}
				TABLE { width: 100%; border: 1px solid #000; border-collapse: collapse; margin: 0px; padding: 0px; }
				TR { margin: 0px; padding: 0px; }
				TD, TH { margin: 0px; padding: 0px; }
				TH {background-color: #ccc; }
				FIELDSET { margin: 0px; padding: 0px; }
				.etichetta { float: left; font-weight: bold; min-width: 250px; width: 250px; }
				H3 { margin-bottom: 5px; }
			</style>
			<h2>
				SCHEDA DEL PROCEDIMENTO</h2>
		<%} else {%>
			<%if (MostraBottoneStampa){ %>
				<div style="width: 100%; text-align: right">
					<img src='<%=GetBaseUrl() + "Images/print_icon.png" %>' style="vertical-align: middle" /><a
						href='<%= GetUrlDownloadPagina() %>' target="_blank">Stampa scheda</a>
				</div>
			<%} %>
		<%} %>

		<div id="accordion" class="popupDettagliEndo inputForm dettagliIntervento">
			<div>
				<%if (!Print){ %>
					<h3><a href="#">Informazioni</a></h3>
				<%} %>
				<div>
					<fieldset>
						<div>
							<div class='etichetta'>
								Nome procedimento</div>
							<asp:Label runat="server" ID="Literal1" Text='<%# DataSource.Descrizione %>' />
						</div>
						<% if (DataSource.UltimoAggiornamento.HasValue){ %>
						<div>
							<div class='etichetta'>
								Data aggiornamento</div>
							<asp:Label runat="server" ID="ltrdataAggiornamento" Text='<%# DataSource.UltimoAggiornamento.Value.ToString("dd/MM/yyyy") %>' />
						</div>
						<%} %>

						<%if (!String.IsNullOrEmpty(DataSource.Tipologia)) { %>
						<div>
							<div class='etichetta'>
								Tipologia</div>
							<asp:Label runat="server" ID="ltrTipologia" Text='<%# DataSource.Tipologia %>' />
						</div>
						<%} %>

						<%if (!String.IsNullOrEmpty(DataSource.Natura)){ %>
						<div>
							<div class='etichetta'>
								Natura del procedimento</div>
							<asp:Label runat="server" ID="ltrNatura" Text='<%# DataSource.Natura %>' />
						</div>
						<%} %>
								
						<%if (!String.IsNullOrEmpty(DataSource.Amministrazione)){ %>
						<div>
							<div class='etichetta'>
								Amministrazione competente</div>
							<asp:Label runat="server" ID="ltrAmministrazione" Text='<%# DataSource.Amministrazione %>' />
						</div>
						<%} %>
					</fieldset>
				</div>
			</div>

			<% if (DataSource.Normative != null && DataSource.Normative.Length > 0) {%>
			<div>
				<h3>
					<a href="#">Normativa</a>
				</h3>
				<div>
					<asp:Repeater runat="server" ID="rptNormativa" DataSource='<%# DataSource.Normative %>'>
						<HeaderTemplate>
							<table>
								<% if (Print) { %>
									<table width="100%" cellpadding="0" cellspacing="0">
										<col width="70%" />
										<col width="30%" />
								<%} else {%>
									<table>
								<% } %>
									<thead>
										<tr>
											<th>Descrizione</th>
											<th>Tipologia</th>
													
												<%if (!Print){%>
													<th>&nbsp;</th>
													<th>&nbsp;</th>
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
								<%if (!Print){%>
									<td><%# GetLinkModello( Eval("CodiceOggetto") ,"PDF") %></td>
									<td><%# GetLinkNormativa(DataBinder.Eval(Container,"DataItem"))%></td>
								<%} %>
							</tr>
						</ItemTemplate>
						<FooterTemplate>
							</table>
						</FooterTemplate>
					</asp:Repeater>
				</div>
			</div>
			<%} %>


			<% if (!String.IsNullOrEmpty(DataSource.DatiGenerali)){ %>
			<div>
				<h3>
					<a href="#">Descrizione</a></h3>
				<div>
					<asp:Literal runat="server" ID="ltrDatiGenerali" Text='<%#DataSource.DatiGenerali %>' />
				</div>
			</div>
			<%} %>
					
			<% if (!String.IsNullOrEmpty(DataSource.Applicazione)){ %>
			<div>
				<h3>
					<a href="#">Requisiti</a></h3>
				<div>
					<asp:Literal runat="server" ID="ltrApplicazione" Text='<%#DataSource.Applicazione %>' />
				</div>
			</div>
			<%} %>

			<% if (!String.IsNullOrEmpty(DataSource.NormativaUE)){ %>
			<div>
				<h3>
					<a href="#">Normativa UE</a></h3>
				<div>
					<asp:Literal runat="server" ID="ltrNormativaUE" Text='<%#DataSource.NormativaUE %>' />
				</div>
			</div>
			<%} %>

			<% if (!String.IsNullOrEmpty(DataSource.NormativaNazionale)){ %>
			<div>
				<h3>
					<a href="#">Normativa nazionale</a></h3>
				<div>
					<asp:Literal runat="server" ID="ltrNormativaNazionale" Text='<%#DataSource.NormativaNazionale %>' />
				</div>
			</div>
			<%} %>

			<% if (!String.IsNullOrEmpty(DataSource.NormativaRegionale)){ %>
			<div>
				<h3>
					<a href="#">Normativa regionale</a></h3>
				<div>
					<asp:Literal runat="server" ID="ltrNormativaRegionale" Text='<%#DataSource.NormativaRegionale %>' />
				</div>
			</div>
			<%} %>

			<% if (!String.IsNullOrEmpty(DataSource.Regolamenti)){ %>
			<div>
				<h3>
					<a href="#">Regolamenti</a></h3>
				<div>
					<asp:Literal runat="server" ID="ltrRegolamenti" Text='<%#DataSource.Regolamenti %>' />
				</div>
			</div>
			<%} %>

			<% if (!String.IsNullOrEmpty(DataSource.Adempimenti)){ %>
			<div>
				<h3>
					<a href="#">Adempimenti</a></h3>
				<div>
					<asp:Literal runat="server" ID="ltrAdempimenti" Text='<%#DataSource.Adempimenti %>' />
				</div>
			</div>
			<%} %>

			<%--Agiunto su richiesta dal comune di Firenze--%>
			<% if (DataSource.TestiEstesi != null && DataSource.TestiEstesi.Length > 0) { %>
			<div>
				<h3>
					<a href="#">Testi estesi</a></h3>
				<div>
					<asp:Repeater runat="server" ID="rptTestiEstesi" DataSource='<%# DataSource.TestiEstesi %>'>
						<HeaderTemplate>
							<table>
								<thead>
									<tr>
										<th>Testi estesi</th>
										<th>Normativa</th>
										<th>Link</th>
										<th>Documento</th>
									</tr>
								</thead>
						</HeaderTemplate>
						<ItemTemplate>
							<tr>
								<td>
									<asp:Literal runat="server" ID="literal1" Text='<%# Eval("Descrizione") %>' />
								</td>
								<td>
									<asp:Literal runat="server" ID="literal2" Text='<%# Eval("Normativa") %>' />
								</td>
								<td>
									<%# GetLinkEsterno( Eval("Link") ) %>
								</td>
								<td>
									<%# GetLinkModello( Eval("CodiceOggetto") , "pdf" ) %>
								</td>
							</tr>
						</ItemTemplate>
						<FooterTemplate>
							</table>
						</FooterTemplate>
					</asp:Repeater>
				</div>
			</div>
			<%} %>


			<% if (DataSource.Oneri != null && DataSource.Oneri.Length > 0)  { %>
			<div>
				<h3>
					<a href="#">Oneri</a></h3>
				<div>
					<asp:Repeater runat="server" ID="rptOneri" DataSource='<%# DataSource.Oneri %>'>
						<HeaderTemplate>
							<% if (Print){ %>
								<table width="100%" cellpadding="0" cellspacing="0">
									<col width="70%" />
									<col width="30%" />
							<%}else{%>
								<table>
							<% } %>

									<thead>
										<tr>
											<th>Causale d'onere</th>
											<th>Importo</th>
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
						</FooterTemplate>
					</asp:Repeater>
				</div>
			</div>
			<%} %>


			<% if (DataSource.Allegati != null && DataSource.Allegati.Length > 0) { %>
			<div>
				<h3><a href="#">Modulistica e allegati</a></h3>
				<div>
					<asp:Repeater runat="server" ID="Repeater1" DataSource='<%# DataSource.Allegati %>'>
						<HeaderTemplate>
							<table width="100%">
								<%if (!Print){%>
									<colgroup>
										<col width="50%" />
										<col width="45%" />
										<col width="5%" />
									</colgroup>
								<%} %>

								<thead>
								<tr>
									<th>Allegato</th>
									<%if (!Print){%>
										<th>Link</th>
										<th>&nbsp;</th>
									<%} %>
								</tr>
								</thead>
						</HeaderTemplate>
						<ItemTemplate>
							<tr>
								<td>
									<asp:Literal runat="server" ID="literal1" Text='<%# Eval("Descrizione") %>' />
								</td>
								<%if (!Print){%>
									<td><%# GetLinkEsterno( Eval("Link") ) %></td>
									<td><%# GetLinkModello(Eval("CodiceOggetto"), Eval("FormatiDownload"))%></td>
								<%} %>
							</tr>
						</ItemTemplate>
						<FooterTemplate>
							</table>
						</FooterTemplate>
					</asp:Repeater>
				</div>
			</div>
			<%} %>
		</div>
	</asp:Panel>
</asp:Content>
