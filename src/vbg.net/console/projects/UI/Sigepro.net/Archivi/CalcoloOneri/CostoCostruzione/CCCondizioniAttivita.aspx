<%@ Page Language="C#" MasterPageFile="~/SigeproNetMaster.master" AutoEventWireup="true" Inherits="Archivi_CalcoloOneri_CostoCostruzione_CCCondizioniAttivita" Title="Altre tabelle" Codebehind="CCCondizioniAttivita.aspx.cs" %>
<%@ MasterType VirtualPath="~/SigeproNetMaster.master" %>
<%@ Register tagPrefix="init" namespace="Init.Utils.Web.UI" assembly="Init.Utils"%>

<%@ Register tagPrefix="init" namespace="SIGePro.WebControls.UI" assembly="SIGePro.WebControls"%>
<%@ Register tagPrefix="init" namespace="SIGePro.WebControls.Ajax" assembly="SIGePro.WebControls"%>


<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="Server">

	<asp:MultiView runat="server" ID="multiView" ActiveViewIndex="0" OnActiveViewChanged="multiView_ActiveViewChanged">
		<asp:View runat="server" ID="ricercaView">
			<fieldset>
				<div>
					<asp:Label runat="server" ID="label7" Text="Settori" AssociatedControlID="ddlSrcSettori" />
					<asp:DropDownList runat="server" ID="ddlSrcSettori" DataValueField="CODICESETTORE" DataTextField="SETTORE"/>
				</div>
				<div class="Bottoni">
					<init:SigeproButton runat="server" ID="cmdCerca"  Text="Cerca" IdRisorsa="CERCA" OnClick="cmdCerca_Click" />
					<init:SigeproButton runat="server" ID="cmdChiudi" Text="Chiudi" IdRisorsa="CHIUDI" OnClick="cmdChiudi_Click" />
				</div>
			</fieldset>
		</asp:View>
		
		<asp:View runat="server" ID="listaView">
			<fieldset>
			<div class="Informazioni">
				<asp:Label runat="server" CssClass="Valore" ID="lblSettore" />			
			</div>
			<div>
			    <asp:GridView runat="server" ID="gvLista" AutoGenerateColumns="False" DataKeyNames="CodiceIstat" OnRowDataBound="gvLista_RowDataBound" DataSourceID="AttivitaDataSource" OnRowUpdating="gvLista_RowUpdating" OnRowDeleting="gvLista_RowDeleting">
				    <AlternatingRowStyle CssClass="RigaAlternata" />
				    <RowStyle CssClass="Riga" />
				    <HeaderStyle CssClass="IntestazioneTabella" />
				    <EmptyDataRowStyle CssClass="NessunRecordTrovato"/>
				    <Columns>
					    <asp:BoundField ReadOnly="True" DataField="Istat" HeaderText="Voce" ><HeaderStyle HorizontalAlign="Left" /></asp:BoundField>
					    <asp:TemplateField HeaderText="Condizione">
                            <ItemTemplate>
                                <input id="lblId" runat="server" type="hidden"/>
                                <input id="lblCodiceIstat" runat="server" type="hidden"/>
                                <asp:Label ID="lblCondizione" runat="server"></asp:Label>
                            </ItemTemplate>
                            <HeaderStyle HorizontalAlign="Left" />
                            <EditItemTemplate>
                                <input id="lblCodiceIstat" runat="server" type="hidden"/>
                                <asp:TextBox ID="txtCondizione" runat="server" TextMode="MultiLine" Rows="4" Columns="80"></asp:TextBox>
                            </EditItemTemplate>
                        </asp:TemplateField>
					    <asp:TemplateField>
					        <ItemTemplate>
					            <asp:ImageButton runat="server" ID="imgModifica" ImageUrl="~/images/New.gif" AlternateText="Modifica la condizione" CommandName="Edit" />
    				            <asp:ImageButton runat="server" ID="imgElimina" ImageUrl="~/images/Cestino.gif" AlternateText="Elimina la condizione" CommandName="Delete" />
	    			            <asp:ImageButton runat="server" ID="imgUtilizza" ImageUrl="~/images/addnew.gif" AlternateText="Inserisci condizione" CommandName="Edit" />
					        </ItemTemplate>
                            <ItemStyle HorizontalAlign="Right" />
                            <EditItemTemplate>
                                <div class="Bottoni">
					                <asp:ImageButton runat="server" ID="imgSalva" ImageUrl="~/images/Save.gif" AlternateText="Salva la condizione" CommandName="Update" />
					                <asp:ImageButton runat="server" ID="imgAnnulla" ImageUrl="~/images/Delete.gif" AlternateText="Annulla le modifiche" CommandName="Cancel" />
					            </div>
                            </EditItemTemplate>
					    </asp:TemplateField>
				    </Columns>
				    <EmptyDataTemplate>
					    <asp:Label ID="Label6" runat="server">Non è stato trovato nessun record corrispondente ai criteri di ricerca.</asp:Label>
				    </EmptyDataTemplate>
			    </asp:GridView>
                <asp:ObjectDataSource ID="AttivitaDataSource" runat="server" OldValuesParameterFormatString="original_{0}"
                    SelectMethod="Find" TypeName="Init.SIGePro.Manager.AttivitaMgr">
                    <SelectParameters>
                        <asp:QueryStringParameter Name="token" QueryStringField="Token" Type="String" />
                        <asp:ControlParameter ControlID="ddlSrcSettori" Name="codiceSettore" PropertyName="SelectedValue"
                            Type="String" />
                    </SelectParameters>
                </asp:ObjectDataSource>
			</div>
				<div class="Bottoni">
                    <init:SigeproButton runat="server" ID="ImageButton1" Text="Chiudi" IdRisorsa="CHIUDI" OnClick="cmdChiudiLista_Click" />					
				</div>
			</fieldset>

		</asp:View>
		
		<asp:View runat="server" ID="dettaglioView">

		</asp:View>
		
	</asp:MultiView>
</asp:Content>