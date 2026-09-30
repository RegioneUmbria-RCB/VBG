<%@ Control Language="C#" AutoEventWireup="true" CodeBehind="PaginatoreSchedeDinamiche.ascx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.DatiDinamici.PaginatoreSchedeDinamiche" %>
<asp:Repeater runat="server" ID="rptMolteplicita">
	<HeaderTemplate>
		<ul class="nav nav-tabs nav-justified">
	</HeaderTemplate>
					
	<ItemTemplate>

			<li role="presentation" class="<%# (bool)DataBinder.Eval( Container , "DataItem.IndiceCorrente" ) ? "active" : "" %>">
				<asp:Panel runat="server" ID="pnlSchedaCorrente" Visible='<%# DataBinder.Eval( Container , "DataItem.IndiceCorrente" )  %>'>
					<div>
						<asp:Label runat="server" ID="ltrIndice" Text='<%# DataBinder.Eval( Container , "DataItem.Descrizione", "Scheda {0}" ) %>' />
						<asp:LinkButton runat="server" 
                                        CssClass="elimina-scheda"
                                        ID="lnkElimina" 
                                        ToolTip="Elimina la scheda corrente" 
                                        AlternateText="Elimina la scheda corrente" 
                                        OnClick="OnEliminaScheda" 
                                        OnClientClick="return confirm('Eliminare la scheda corrente (l\'operazione non potrà essere annullata)\?');" 
                                        Text="<i class='glyphicon glyphicon-remove'></i>"/>
					</div>
				</asp:Panel>
			
				<asp:Panel runat="server" ID="pnlAltreSchede" Visible='<%# !(bool)DataBinder.Eval( Container , "DataItem.IndiceCorrente" )  %>'>
					<asp:LinkButton runat="server" CssClass="d2WatchButton" ID="lnkApriIndice" CommandArgument='<%# DataBinder.Eval( Container , "DataItem.Valore" ) %>' Text='<%# DataBinder.Eval(Container, "DataItem.Descrizione", "Scheda {0}" ) %>' OnClick="OnIndiceSelezionato" />
				</asp:Panel>
			</li>
	</ItemTemplate>
					
	<FooterTemplate>
			<li role="presentation" class="<%# IndiceCorrente == IndiceNuovaScheda ? "active" : "" %>">
				<asp:Panel runat="server" id="pnlNuovaSchedaDisabilitato" Visible='<%# IndiceCorrente == IndiceNuovaScheda %>'>
					<div>
						<asp:Label runat="server" ID="ltrNuovaScheda" Text='Nuova scheda' />
					</div>
				</asp:Panel>

				<asp:Panel runat="server" id="pnlNuovaSchedaAbilitato" Visible='<%# IndiceCorrente < IndiceNuovaScheda %>'>
					<asp:LinkButton runat="server" ID="lnkAggiungiScheda"  CssClass="d2WatchButton" Text="Nuova scheda" OnClick="OnNuovaScheda" />
				</asp:Panel>
			</li>
		</ul>
	</FooterTemplate>
</asp:Repeater>