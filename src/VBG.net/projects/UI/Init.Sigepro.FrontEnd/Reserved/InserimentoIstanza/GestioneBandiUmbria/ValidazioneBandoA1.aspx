<%@ Page Title="Convalida domanda" Language="C#" MasterPageFile="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" AutoEventWireup="true" CodeBehind="ValidazioneBandoA1.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.GestioneBandiUmbria.ValidazioneBandoA1" %>
<%@ MasterType VirtualPath="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master"%>
<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="stepContent" runat="server">
<div class="inputForm">
	<fieldset>
		<div class="validazione-bandi-umbria">
			<div class="dati-progetto">
				<h2>
					<asp:Literal runat="server" ID="txtTitoloProgetto"/>
				</h2>

				<div>
					<label>Acronimo</label>
					<asp:TextBox runat="server" ID="txtAcronimo" ReadOnly="true"/>
				</div>

				<div>
					<label>Tipologia</label>
					<asp:TextBox runat="server" ID="txtTipologia" ReadOnly="true" Columns="50"/>
				</div>

				<div>
					<label>Durata iniziativa</label>
					<asp:TextBox runat="server" ID="txtDurata" ReadOnly="true" Columns="3"/> mesi
				</div>
			</div>

			<asp:Repeater runat="server" ID="rptErrori">
				<HeaderTemplate>
					<h2>Errori <div>bloccanti per la presentazione della domanda</div></h2>
					<ul class="errori">
				</HeaderTemplate>

				<ItemTemplate>
						<li><asp:Literal runat="server" ID="ltrErrore" Text='<%# DataBinder.Eval(Container, "DataItem") %>' /></li>
				</ItemTemplate>

				<FooterTemplate>
					</ul>
				</FooterTemplate>

			</asp:Repeater>

			<asp:Repeater runat="server" ID="rptAvvisi">
				<HeaderTemplate>
					<h2>Avvisi <div>la domanda può comunque essere presentata</div></h2>

					<ul class="avvisi">
				</HeaderTemplate>

				<ItemTemplate>
						<li><asp:Literal runat="server" ID="ltrAvviso" Text='<%# DataBinder.Eval(Container, "DataItem") %>' /></li>
				</ItemTemplate>

				<FooterTemplate>
					</ul>
				</FooterTemplate>

			</asp:Repeater>

			<asp:Panel runat="server" ID="pnlNessunErrore" Visible="true">
				<h2><div>Non sono stati riscontrati errori o avvertimenti, è possibile procedere con la presentazione della domanda</div></h2>
			</asp:Panel>
		</div>

    </fieldset>
</div>

</asp:Content>
