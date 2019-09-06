<%@ Page Language="C#" MasterPageFile="~/SigeproNetMaster.master" AutoEventWireup="True" Inherits="Archivi_DatiDinamici_Dyn2AlberoProcModelli" Title="Modelli dinamici" Codebehind="Dyn2AlberoProcModelli.aspx.cs" %>
<%@ MasterType VirtualPath="~/SigeproNetMaster.master" %>


<%@ Register tagPrefix="init" namespace="Init.Utils.Web.UI" assembly="Init.Utils"%>

<%@ Register tagPrefix="init" namespace="SIGePro.WebControls.UI" assembly="SIGePro.WebControls"%>
<%@ Register tagPrefix="init" namespace="SIGePro.WebControls.Ajax" assembly="SIGePro.WebControls"%>



<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" Runat="Server">
<asp:ScriptManager ID="ScriptManager1" runat="server"></asp:ScriptManager>
<fieldset>
	<div class="Informazioni">
	    <asp:Label CssClass="Etichetta" ID="lblTitolo1" runat="server" Text="Procedimento:"></asp:Label>
	    <asp:Label CssClass="Valore" ID="lblDescrizione1" runat="server" Text=""></asp:Label>
	</div>
</fieldset>
	<asp:MultiView runat="server" ID="multiView" ActiveViewIndex="0" OnActiveViewChanged="multiView_ActiveViewChanged">
		<asp:View runat="server" ID="listaView">
			<fieldset>
			<div>
			    <init:GridViewEx runat="server" ID="gvLista"  AutoGenerateColumns="False" DataKeyNames="FkScId,FkD2mtId" DatabindOnFirstLoad="True" AllowSorting="true" DefaultSortDirection="Ascending" DefaultSortExpression="" OnRowDeleting="gvLista_RowDeleting" OnRowDataBound="gvLista_RowDataBound" >
				    <AlternatingRowStyle CssClass="RigaAlternata" />
				    <RowStyle CssClass="Riga" />
				    <HeaderStyle CssClass="IntestazioneTabella" />
				    <EmptyDataRowStyle CssClass="NessunRecordTrovato"/>
				    
				    <EmptyDataTemplate>
					    <asp:Label ID="Label6" runat="server"></asp:Label>
				    </EmptyDataTemplate>
                    <Columns>
                        <asp:BoundField DataField="Dyn2ModelliT" HeaderText="Modello" SortExpression="Dyn2ModelliT" />
                        <asp:TemplateField>
                            <itemtemplate>
                                <asp:ImageButton id="cmdElimina" runat="server" AlternateText="Elimina" ImageUrl="~/images/Delete.gif" CommandName="Delete" /> 
                            </itemtemplate>
                        </asp:TemplateField>
                    </Columns>
			    </init:GridViewEx>
                &nbsp;
			    
			</div>
				<div class="Bottoni">
	    			<init:SigeproButton runat="server" ID="cmdNuovo"  Text="Nuovo" IdRisorsa="NUOVO" OnClick="cmdNuovo_Click"/>
                    <init:SigeproButton runat="server" ID="cmdChiudiLista"  Text="Chiudi" IdRisorsa="CHIUDI"/>					
				</div>
			</fieldset>
		</asp:View>
		<asp:View runat="server" ID="dettaglioView">
    		<fieldset>
				<init:LabeledLabel runat="server" ID="lblId" Descrizione="Codice" />
				<div>
					<asp:Label runat="server" ID="Label23" AssociatedControlID="rplModelloDinamico" Text="*Modello"/>
					<init:RicerchePlusCtrl ID="rplModelloDinamico" runat="server" ColonneCodice="4" ColonneDescrizione="50" CompletionInterval="300" CompletionListCssClass="RicerchePlusLista" CompletionListHighlightedItemCssClass="RicerchePlusElementoSelezionatoLista" CompletionListItemCssClass="RicerchePlusElementoLista" CompletionSetCount="10" DataClassType="Init.SIGePro.Data.Dyn2ModelliT" DescriptionPropertyNames="Descrizione" LoadingIcon="~/Images/ajaxload.gif" MaxLengthCodice="10" MaxLengthDescrizione="150" MinimumPrefixLength="1" TargetPropertyName="Id" ServicePath="~/WebServices/WSSiGePro/RicerchePlus.asmx" AutoSelect="True" ServiceMethod="GetCompletionList" />
				</div>
                <div class="Bottoni">
					<init:SigeproButton runat="server" ID="cmdSalva"  Text="Ok" IdRisorsa="OK" OnClick="cmdSalva_Click" />
					<init:SigeproButton runat="server" ID="cmdChiudiDettaglio"  Text="Chiudi" IdRisorsa="CHIUDI" OnClick="cmdChiudiDettaglio_Click" />
				</div>
			</fieldset>
		</asp:View>
		
	</asp:MultiView>
</asp:Content>