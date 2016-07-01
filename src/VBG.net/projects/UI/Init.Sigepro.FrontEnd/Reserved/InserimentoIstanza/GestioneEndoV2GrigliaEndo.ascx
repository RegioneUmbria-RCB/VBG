<%@ Control Language="C#" AutoEventWireup="true" CodeBehind="GestioneEndoV2GrigliaEndo.ascx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.GestioneEndoV2GrigliaEndo" %>
<div id="alberoEndo">
<asp:Repeater runat="server" ID="rptFamiglieEndo">
	<HeaderTemplate>
		<%if (MostraFamiglia){ %><ul><%} %>	
	</HeaderTemplate>
	
	<ItemTemplate>
			<%if (MostraFamiglia){ %>
				<li>
					<div class="famigliaEndo">
						<asp:Literal runat="server" ID="ltrFamiglia" Text='<%# Eval("Descrizione") %>'/>
					</div>
			<%} %>
			<asp:Repeater runat="server" ID="rptTipiEndo" DataSource='<%# Eval("TipiEndoprocedimenti") %>'>
			
				<HeaderTemplate>
					<% if (MostraTipoEndo){%><ul><%} %>					
				</HeaderTemplate>
				
				<ItemTemplate>
					
						<% if (MostraTipoEndo){%>
							<li>
							<div class="tipoEndo">
								<asp:Literal runat="server" ID="ltrTipoEndo"  Text='<%# Eval("Descrizione") %>'/>
							</div>
						<%} %>
						
						<asp:Repeater runat="server" ID="rptEndo" DataSource='<%# Eval("Endoprocedimenti") %>'>
					
						
							<HeaderTemplate>
								<ul>
							</HeaderTemplate>
						
							<ItemTemplate>
								<li>
									<div class="endo">
										<asp:HiddenField runat="server" ID="hidEndo" Value='<%# Eval("Codice")%>' />
										<asp:HiddenField runat="server" ID="hidPrincipale" Value='<%# Eval("Principale")%>' />
										<asp:CheckBox runat="server" ID="chkEndo" Checked='<%# EndoSelezionato( DataBinder.Eval( Container.DataItem,"Codice")) %>' Enabled='<%# ModificaProcedimentiProposti %>' />
										<asp:Literal runat="server" ID="ltrDescrizione"  Text='<%# Eval("Descrizione") %>' />
										<img src="../../Images/help_interventi.gif" alt="Fare click per ulteriori informazioni" idEndo='<%# DataBinder.Eval(Container.DataItem,"Codice")%>' />								
									</div>
								</li>
							</ItemTemplate>
						
							<FooterTemplate>
								</ul>
							</FooterTemplate>
						
						</asp:Repeater>
						
						<% if (MostraTipoEndo){%></li><%} %>		
				</ItemTemplate>
				
				<FooterTemplate>
					<% if (MostraTipoEndo){%></ul><%} %>		
				</FooterTemplate>
			
			</asp:Repeater>
		
			<%if (MostraFamiglia){ %></li><%} %>	
	</ItemTemplate>
	
	<FooterTemplate>
		<%if (MostraFamiglia){ %></ul><%} %>	
	</FooterTemplate>
</asp:Repeater>
</div>