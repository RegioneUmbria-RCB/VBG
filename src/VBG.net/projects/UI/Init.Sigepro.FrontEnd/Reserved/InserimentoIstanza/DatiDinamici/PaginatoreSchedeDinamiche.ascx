<%@ Control Language="C#" AutoEventWireup="true" CodeBehind="PaginatoreSchedeDinamiche.ascx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.DatiDinamici.PaginatoreSchedeDinamiche" %>
<asp:Repeater runat="server" ID="rptMolteplicita">
	<HeaderTemplate>
		<ul class="listaSchede">
	</HeaderTemplate>
					
	<ItemTemplate>
			<li>
				<asp:Panel runat="server" ID="pnlSchedaCorrente" Visible='<%# DataBinder.Eval( Container , "DataItem.IndiceCorrente" )  %>'>
					<div>
						<asp:Label runat="server" ID="ltrIndice" Text='<%# DataBinder.Eval( Container , "DataItem.Descrizione", "Scheda {0}" ) %>' />
						<asp:ImageButton runat="server" ID="lnkElimina" ImageUrl="~/Images/close-icon.png" ToolTip="Elimina la scheda corrente" AlternateText="Elimina la scheda corrente" OnClick="OnEliminaScheda" OnClientClick="return confirm('Eliminare la scheda corrente (l\'operazione non potrà essere annullata)\?');" />
					</div>
				</asp:Panel>
			
				<asp:Panel runat="server" ID="pnlAltreSchede" Visible='<%# !(bool)DataBinder.Eval( Container , "DataItem.IndiceCorrente" )  %>'>
					<asp:LinkButton runat="server" CssClass="d2WatchButton" ID="lnkApriIndice" CommandArgument='<%# DataBinder.Eval( Container , "DataItem.Valore" ) %>' Text='<%# DataBinder.Eval(Container, "DataItem.Descrizione", "Scheda {0}" ) %>' OnClick="OnIndiceSelezionato" />
				</asp:Panel>
			</li>
	</ItemTemplate>
					
	<FooterTemplate>
			<li>
				<asp:Panel runat="server" id="pnlNuovaSchedaDisabilitato" Visible='<%# IndiceCorrente == IndiceNuovaScheda %>'>
					<div>
						<asp:Label runat="server" ID="ltrNuovaScheda" Text='Nuova scheda' />
					</div>
				</asp:Panel>

				<asp:Panel runat="server" id="pnlNuovaSchedaAbilitato" Visible='<%# IndiceCorrente < IndiceNuovaScheda %>'>
					<asp:LinkButton runat="server" ID="lnkAggiungiScheda"  CssClass="d2WatchButton" Text="Nuova scheda" OnClick="OnNuovaScheda" />
				</asp:Panel>
			</li>
			<%--<asp:ImageButton runat="server" ID="lnkNuovaScheda" ToolTip="Aggiungi una nuova copia della scheda corrente" AlternateText="Aggiungi" ImageUrl="~/Images/nuovo.gif" OnClick="NuovaSchedaMultipla" />
			<asp:ImageButton runat="server" ID="lnkEliminaScheda" ToolTip="Elimina i valori della scheda corrente" AlternateText="Elimina" ImageUrl="~/Images/delete.gif" OnClick="EliminaSchedaMultipla" onclientclick="return confirm('Eliminare questa copia della scheda\?')" />--%>
		</ul>
	</FooterTemplate>
</asp:Repeater>