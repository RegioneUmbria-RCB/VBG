<%@ Control Language="C#" AutoEventWireup="true" CodeBehind="FiltriStatisticheDatiDinamici.ascx.cs" Inherits="Sigepro.net.CustomControls.FiltriStatisticheDatiDinamici" %>
<%@ Register Assembly="SIGePro.DatiDinamici" Namespace="Init.SIGePro.DatiDinamici.WebControls.Statistiche" TagPrefix="cc1" %>
<%@ Register tagPrefix="init" namespace="Init.Utils.Web.UI" assembly="Init.Utils"%>

<%@ Register tagPrefix="init" namespace="SIGePro.WebControls.UI" assembly="SIGePro.WebControls"%>
<%@ Register tagPrefix="init" namespace="SIGePro.WebControls.Ajax" assembly="SIGePro.WebControls"%>

<init:LabeledDropDownList ID="ddlModello" runat="server" Descrizione="Modello" OnValueChanged="ddlModello_ValueChanged" Item-AutoPostBack="true" Item-DataTextField="Descrizione"  Item-DataValueField="Id" />
<br />
<br />
<br />
<asp:DataGrid runat="server" ID="dgFiltri" DataKeyField="Id" AutoGenerateColumns="False" OnItemCreated="dgFiltri_ItemCreated">
	<HeaderStyle CssClass="IntestazioneTabella" />
	<Columns>
		<asp:TemplateColumn>
			<ItemTemplate>
				<asp:DropDownList runat="server" ID="ddlConcatenazione" SelectedValue='<%# DataBinder.Eval(Container, "DataItem.Concatenazione") %>'>
					<asp:ListItem Value="And" Text="E" />
					<asp:ListItem Value="Or" Text="O" />
				</asp:DropDownList>
			</ItemTemplate>
		</asp:TemplateColumn>
		<asp:TemplateColumn>
			<ItemTemplate>
				<asp:DropDownList runat="server" ID="ddlParentesiIn" SelectedValue='<%# DataBinder.Eval(Container, "DataItem.ParentesiIn") %>'>
					<asp:ListItem Value="False" Text=""/>
					<asp:ListItem Value="True" Text="("/>
				</asp:DropDownList>
			</ItemTemplate>
		</asp:TemplateColumn>
		<asp:TemplateColumn HeaderText="Campo">
			<ItemTemplate>
				<asp:DropDownList runat="server" ID="ddlIdCampo" DataTextField="Value" DataValueField="Key"  DataSourceID="ListaCampiDataSource" AutoPostBack="true" OnSelectedIndexChanged="OnControlIdChanged" SelectedValue='<%# DataBinder.Eval(Container, "DataItem.IdCampo") %>'>
				</asp:DropDownList>&nbsp;
			</ItemTemplate>
		</asp:TemplateColumn>
		
		<cc1:StatisticheDatiDinamiciTipoConfrontoGridColumn HeaderText="Criterio" DataField="Criterio" ControlTypeDataField="Tipo" />
		<cc1:StatisticheDatiDinamiciGridColumn HeaderText="Valore" DataField="Valore" ControlTypeDataField="Tipo" OnControlPropertiesRequired="OnControlPropertiesRequired" />
		<asp:TemplateColumn>
			<ItemTemplate>
				<asp:DropDownList runat="server" ID="ddlParentesiOut" SelectedValue='<%# DataBinder.Eval(Container, "DataItem.ParentesiOut") %>'>
					<asp:ListItem Value="False" Text=""/>
					<asp:ListItem Value="True" Text=")"/>
				</asp:DropDownList>
			</ItemTemplate>
		</asp:TemplateColumn>
	</Columns>
</asp:DataGrid>
<asp:ObjectDataSource ID="ListaCampiDataSource" runat="server" 
	TypeName="Init.SIGePro.Manager.Dyn2CampiMgr" SelectMethod="FindIdDescrizione" 
	OnSelecting="ListaCampiDataSource_Selecting" 
	OldValuesParameterFormatString="original_{0}" >
	<SelectParameters>
		<asp:QueryStringParameter Name="token" QueryStringField="Token" Type="String" />
		<asp:Parameter Name="idModello"  Type="String" />
		<asp:Parameter Name="firstBlank" Type="Boolean" DefaultValue="true" />
	</SelectParameters>
</asp:ObjectDataSource>

<div class="Bottoni">
<init:SigeproButton ID="cmdAggiungiFiltro" runat="server" Text="Aggiungi filtro" IdRisorsa="AGGIUNGIFILTRO" OnClick="cmdAggiungiFiltro_Click" />
</div>

