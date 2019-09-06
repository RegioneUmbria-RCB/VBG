<%@ Control Language="C#" AutoEventWireup="true" CodeBehind="DatiComuneCtrl.ascx.cs"
	Inherits="Init.Sigepro.FrontEnd.Contenuti.DatiComuneCtrl" %>
<div id="boxComune">
	
	<% if (!String.IsNullOrEmpty(this.DataSource.TestoRegione)){ %>
		<script type="text/javascript">
			$(function() {
				$('#intestazioneComune > a > h1')
					.attr('title', $('#testoHelpRegione').html())
					.hoverbox({ id: 'tooltipRegione', left: -330 });
			});
		</script>
	<%} %>
	

	<div id="intestazioneComune">
		<% if (!String.IsNullOrEmpty(this.DataSource.LinkRegione)){ %>
			<a href='<%= this.DataSource.LinkRegione%>' target="_blank">
				<h1>
					<% = this.DataSource.IntestazioneLogo%>
				</h1>
			</a>
		<%} else {%>
			<h1><% = this.DataSource.IntestazioneLogo%></h1>
		<%} %>	
		
		<h2><% = this.DataSource.NomeComune%></h2>
		<h3>
			<div class="listaIdentificativi">
				<asp:MultiView runat="server" ID="mvSottotitoloComune" ActiveViewIndex="0">
					<asp:View runat="server" ID="vwSottotitolo">
						<% = this.DataSource.NomeComune2%>		
					</asp:View>
					<asp:View runat="server" ID="vwAccreditamentoSingolo">
						Identificativo <% = this.DataSource.CodiciAccreditamento.ElementAt(0).Codice%>
					</asp:View>
					<asp:View runat="server" ID="vwAccreditamentoMultiplo">
						<div>
							<h4 data-dropdown="#listaCodiciAccreditamento">Lista identificativi</h4>

							<div id="listaCodiciAccreditamento" class="dropdown">
								<ul class="dropdown-menu">
									<asp:Repeater runat="server" DataSource='<%# this.DataSource.CodiciAccreditamento %>'>
										<ItemTemplate>
											<li>
												<span class="nomeComune">
													<asp:Literal runat="server" ID="ltrNomeComune" Text='<%# Eval("Comune") %>' />
													<span class="codiceComune">
														<asp:Literal runat="server" ID="Literal1" Text='<%# Eval("Codice") %>' />
													</span>
												</span>
											</li>
										</ItemTemplate>
									</asp:Repeater>
								</ul>
							</div>
						</div>
					</asp:View>
				</asp:MultiView>
			</div>		
		</h3>
		<img src='<% = String.Format("{0}?alias={1}&Software={2}", ResolveClientUrl("~/Contenuti/logoComune.ashx"), AliasComune, Software) %>'
			id="logoComune" />
	</div>
	
	
	
	<% if (!String.IsNullOrEmpty(this.DataSource.TestoRegione)){ %>
		<div id="testoHelpRegione" class="contenutiTooltip"><%=this.DataSource.TestoRegione%></div>
	<%} %>
	
	<div id="datiComune">
		<div id="top"></div>
		<dl>
			

			<dt>Responsabile di Sportello</dt>
			<dd><% = this.DataSource.NomeResponsabile %></dd>
			
			<dt><%= this.DataSource.PecPresente ? "Indirizzo PEC" : "Indirizzo E-Mail"%></dt>
			<dd><% = this.DataSource.IndirizzoPec%></dd>
			
			<dt>Telefono</dt>
			<dd><% = this.DataSource.Telefono%></dd>

			<% if (!String.IsNullOrEmpty(DataSource.LinkServizi) && this.DataSource.EsistonoInterventiAttivabili ){ %>
			<dt>Servizi online</dt>			
				<dd><a href='<% = this.DataSource.LinkServizi%>' target="_blank">Accedi</a></dd>
			<%} %>
		</dl>
		<div id="bottom"></div>
	</div>
</div>