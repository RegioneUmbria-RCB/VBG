<%@ Page Language="C#" MasterPageFile="~/SigeproNetMaster.master" AutoEventWireup="true"
	Inherits="Istanze_CalcoloOneri_CostoCostruzione_CCICalcoloCoeffPercentuale" Title="Calcolo della percentuale del coefficiente per stabilire la quota di contributo relativa al costo di costruzione"
	CodeBehind="CCICalcoloCoeffPercentuale.aspx.cs" %>

<%@ MasterType VirtualPath="~/SigeproNetMaster.master" %>
<%@ Register TagPrefix="init" Namespace="Init.Utils.Web.UI" Assembly="Init.Utils" %>
<%@ Register TagPrefix="init" Namespace="SIGePro.WebControls.UI" Assembly="SIGePro.WebControls" %>
<%@ Register TagPrefix="init" Namespace="SIGePro.WebControls.Ajax" Assembly="SIGePro.WebControls" %>
<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="Server">
	<asp:Repeater runat="server" ID="rptErrori">
		<HeaderTemplate>
			<div class="Errori">
				<ul>
		</HeaderTemplate>
		<ItemTemplate>
			<li>
				<%# DataBinder.Eval( Container , "DataItem" ) %></li>
		</ItemTemplate>
		<FooterTemplate>
			</ul></div>
		</FooterTemplate>
	</asp:Repeater>
	<asp:MultiView runat="server" ID="multiView" ActiveViewIndex="0">
		<asp:View runat="server" ID="coefficientiStandardView">
			<asp:ScriptManager ID="ScriptManager1" runat="server">
			</asp:ScriptManager>
			<asp:UpdatePanel ID="UpdatePanel1" runat="server">
				<ContentTemplate>
					<fieldset>
						<asp:Panel runat="server" ID="pnlDestinazioni">
							<asp:Label ID="Label1" runat="server" Text="*Destinazione" AssociatedControlID="ddlDestinazione" />
							<asp:DropDownList ID="ddlDestinazione" runat="server" DataTextField="Descrizione"
								DataValueField="Id" AutoPostBack="True" DataSourceID="DestinazioniDataSource"
								OnSelectedIndexChanged="ddlDestinazione_SelectedIndexChanged" OnDataBound="ddlDestinazione_DataBound" />
							<asp:ObjectDataSource ID="DestinazioniDataSource" runat="server" OldValuesParameterFormatString="original_{0}"
								SelectMethod="GetDestinazioni" TypeName="Init.SIGePro.Manager.CCICalcoloTotMgr">
								<SelectParameters>
									<asp:QueryStringParameter Name="token" QueryStringField="Token" Type="String" />
									<asp:QueryStringParameter Name="idCalcoloTot" QueryStringField="IdCalcoloTot" Type="Int32" />
								</SelectParameters>
							</asp:ObjectDataSource>
						</asp:Panel>
						<% if (BloccoCoefficienteVisibile)
		 {%>
						<legend>
							<%=TitoloBloccoCoefficiente %></legend>
						<asp:Panel runat="server" ID="pnlTipoIntervento">
							<asp:Label ID="Label2" runat="server" Text="*Tipo intervento" AssociatedControlID="ddlTipoIntervento" />
							<asp:DropDownList ID="ddlTipoIntervento" runat="server" DataTextField="Descrizione"
								DataValueField="Id" AutoPostBack="True" OnSelectedIndexChanged="ddlTipoIntervento_SelectedIndexChanged"
								DataSourceID="TipoInterventoDataSource" OnDataBound="ddlTipoIntervento_DataBound" />
							<asp:ObjectDataSource ID="TipoInterventoDataSource" runat="server" OldValuesParameterFormatString="original_{0}"
								SelectMethod="GetTipiInterventoDaIdDestinazione" TypeName="Init.SIGePro.Manager.CCICalcoloTotMgr">
								<SelectParameters>
									<asp:QueryStringParameter Name="token" QueryStringField="Token" Type="String" />
									<asp:QueryStringParameter Name="idCalcoloTot" QueryStringField="IdCalcoloTot" Type="Int32" />
									<asp:ControlParameter ControlID="ddlDestinazione" DefaultValue="-1" Name="idDestinazione"
										PropertyName="SelectedValue" Type="Int32" />
								</SelectParameters>
							</asp:ObjectDataSource>
						</asp:Panel>
						<asp:Panel runat="server" ID="pnlUbicazione">
							<asp:Label ID="Label3" runat="server" Text="*Ubicazione" AssociatedControlID="ddlUbicazione" />
							<asp:DropDownList ID="ddlUbicazione" runat="server" DataTextField="Descrizione" DataValueField="Id"
								AutoPostBack="True" OnSelectedIndexChanged="ddlUbicazione_SelectedIndexChanged"
								DataSourceID="UbicazioniDataSource" OnDataBound="ddlUbicazione_DataBound" />
							<asp:ObjectDataSource ID="UbicazioniDataSource" runat="server" OldValuesParameterFormatString="original_{0}"
								SelectMethod="GetAreeDaIdDestinazioneIdTipoIntervento" TypeName="Init.SIGePro.Manager.CCICalcoloTotMgr">
								<SelectParameters>
									<asp:QueryStringParameter Name="token" QueryStringField="Token" Type="String" />
									<asp:QueryStringParameter Name="idCalcoloTot" QueryStringField="IdCalcoloTot" Type="Int32" />
									<asp:ControlParameter ControlID="ddlDestinazione" DefaultValue="-1" Name="idDestinazione"
										PropertyName="SelectedValue" Type="Int32" />
									<asp:ControlParameter ControlID="ddlTipoIntervento" DefaultValue="-1" Name="idTipoIntervento"
										PropertyName="SelectedValue" Type="Int32" />
								</SelectParameters>
							</asp:ObjectDataSource>
						</asp:Panel>
						<asp:Panel runat="server" ID="pnlAttivita">
							<asp:Label ID="lblAttivita" runat="server" Text="Attivita" AssociatedControlID="ddlAttivita" />
							<asp:DropDownList runat="server" ID="ddlAttivita" DataValueField="Id" DataTextField="Attivita"
								AutoPostBack="True" OnSelectedIndexChanged="ddlAttivita_SelectedIndexChanged">
							</asp:DropDownList>
						</asp:Panel>
						<div>
							<asp:Label ID="Label4" runat="server" Text="Coefficiente" AssociatedControlID="txtCoefficiente" />
							<init:DoubleTextBox runat="server" ID="txtCoefficiente" />
						</div>
						<% } //if ( BloccoCoefficienteVisibile ) %>
						<asp:Repeater runat="server" ID="rptCoefficienti" DataSourceID="ConfigSettoriDataSource"
							OnItemDataBound="rptCoefficienti_ItemDataBound">
							<ItemTemplate>
								<legend>Coefficiente calcolato in base a:
									<%# GetNomeSettore( DataBinder.Eval( Container.DataItem , "FkSeCodicesettore" ) )%></legend>
								<div>
									<asp:Label ID="Label4" runat="server" Text="&nbsp;" AssociatedControlID="ddlAttivita" />
									<asp:DropDownList runat="server" ID="ddlAttivita" DataTextField="Descrizione" DataValueField="Id"
										AutoPostBack="true" OnSelectedIndexChanged="AttivitaSelectedIndexChanged">
									</asp:DropDownList>
									<asp:Panel runat="server" ID="pnlErroreAttivita" CssClass="ErroreCampo">
									</asp:Panel>
								</div>
								<div>
									<asp:Label ID="Label5" runat="server" Text="Coefficiente" AssociatedControlID="txtCoefficienteAttivita" />
									<init:DoubleTextBox runat="server" ID="txtCoefficienteAttivita" FormatString="N2" />
								</div>
							</ItemTemplate>
						</asp:Repeater>
						<asp:ObjectDataSource ID="ConfigSettoriDataSource" runat="server" OldValuesParameterFormatString="original_{0}"
							OnSelecting="ConfigSettoriDataSource_Selecting" SelectMethod="Find" TypeName="Init.SIGePro.Manager.CCConfigurazioneSettoriMgr">
							<SelectParameters>
								<asp:QueryStringParameter Name="token" QueryStringField="Token" Type="String" />
								<asp:Parameter Name="software" Type="String" />
							</SelectParameters>
						</asp:ObjectDataSource>
						<legend>Riepilogo</legend>
						<div class="Bottoni">
							<asp:Label ID="Label5" runat="server" Text="Totale Coefficiente Percentuale:" AssociatedControlID="txtTotaleCoefficiente" />
							<init:DoubleTextBox runat="server" ID="txtTotaleCoefficiente" />
							<init:SigeproButton runat="server" ID="cmdricalcolaTotale" Text="Ricalcola totale"
								IdRisorsa="RICALCOLATOTALE" OnClick="RicalcolaCoefficienteTotale" />
						</div>
					</fieldset>
				</ContentTemplate>
			</asp:UpdatePanel>
			<fieldset>
				<div class="Bottoni">
					<init:SigeproButton runat="server" ID="cmdSalva" Text="Salva" IdRisorsa="SALVA" OnClick="cmdSalva_Click" />
					<init:SigeproButton runat="server" ID="cmdChiudi" Text="Chiudi" IdRisorsa="CHIUDI"
						OnClick="cmdChiudi_Click" />
					<asp:Button runat="server" ID="cmdAliquotaClassiSuperfici" Text="Aliquota in base alle classi di superfici"
						OnClick="cmdAliquotaClassiSuperfici_Click" />
				</div>
			</fieldset>
		</asp:View>
		<asp:View runat="server" ID="coefficientiClassiSiperficieView">
			<fieldset>
				<legend>Calcoli</legend>

				<asp:Repeater runat="server" ID="rptAliquotePerClassiSuperfici">
					<HeaderTemplate>
						<table>
							<tr>
								<th>Classe di superficie</th>
								<th>ST/SU</th>
								<th>Aliquota</th>
								<th>ST/SU*Aliquota</th>
								<th>Costo</th>
							</tr>
					
					</HeaderTemplate>

					<ItemTemplate>
						<tr>
							<td><asp:Label runat="server" id="Label6" Text='<%# Eval("ClasseSuperficie") %>' /></td>
							<td style="text-align:right"><asp:Label runat="server" id="Label7" Text='<%# Eval("RapportoStSu") %>' /></td>
							<td style="text-align:right"><asp:Label runat="server" id="Label8" Text='<%# Eval("ValoreAliquota") %>' /> %</td>
							<td style="text-align:right"><asp:Label runat="server" id="Label9" Text='<%# Eval("AliquotaTot") %>' /> %</td>
							<td style="text-align:right"><asp:Label runat="server" id="Label10" Text='<%# Eval("Contributo") %>' /></td>
						</tr>
					</ItemTemplate>
			
					<FooterTemplate>
						</table>
					</FooterTemplate>
				</asp:Repeater>
			</fieldset>
			<fieldset>
				<legend>Totali</legend>

				<div>
					<label>Totale Aliquota</label>
					<asp:Label runat="server" ID="lblTotaleAliquota" style="display:block; width: 500px; text-align: right;" />
				</div>

				<div>
					<label>Totale Costo Costruzione</label>
					<asp:Label runat="server" ID="lblTotaleCostoCostruzione"  style="display:block; width: 500px; text-align: right;"/>
				</div>

				<div>
					<label>Totale Contributo</label>
					<asp:Label runat="server" ID="lblTotaleContributo"  style="display:block; width: 500px; text-align: right;"/>
				</div>
				


			</fieldset>

			<fieldset>
				<div class="Bottoni">
					<init:SigeproButton runat="server" ID="cmdSalvaAliquotePerClassi" Text="Salva" IdRisorsa="SALVA" OnClick="cmdSalvaAliquotePerClassi_Click" />
					<init:SigeproButton runat="server" ID="SigeproButton2" Text="Chiudi" IdRisorsa="CHIUDI" OnClick="cmdChiudi_Click" />
				</div>
			</fieldset>

		</asp:View>
	</asp:MultiView>
</asp:Content>
