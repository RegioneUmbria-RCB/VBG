<%@ Page Language="C#" MasterPageFile="~/SigeproNetMaster.master" AutoEventWireup="true" CodeBehind="IstanzeCalcoloCanoni.aspx.cs" Inherits="Sigepro.net.Istanze.CalcoloCanoni.IstanzeCalcoloCanoni" Title="Calcolo del canone" %>
<%@ MasterType VirtualPath="~/SigeproNetMaster.master" %>

<%@ Register tagPrefix="init" namespace="Init.Utils.Web.UI" assembly="Init.Utils"%>

<%@ Register tagPrefix="init" namespace="SIGePro.WebControls.UI" assembly="SIGePro.WebControls"%>
<%@ Register tagPrefix="init" namespace="SIGePro.WebControls.Ajax" assembly="SIGePro.WebControls"%>


<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" Runat="Server">
    <script language="javascript" type="text/javascript">
        
        function Stampa( url )
        {
            var features = "width=550,height=420,top=20,left=20,menubar=yes,scrollbar=auto,resizable=yes";
            var w = window.open( url, "stampa", features );
            return false;
        }
    </script>
    <asp:ScriptManager ID="ScriptManager1" runat="server"></asp:ScriptManager>
	<asp:MultiView runat="server" ID="multiView" ActiveViewIndex="0" OnActiveViewChanged="multiView_ActiveViewChanged">
		<asp:View runat="server" ID="risultatoView">
			<fieldset>
			    <asp:GridView runat="server" AutoGenerateColumns="false" DataKeyNames="Id" ID="gvLista" OnRowDataBound="gvLista_RowDataBound" OnRowDeleting="gvLista_RowDeleting" OnSelectedIndexChanged="gvLista_SelectedIndexChanged">
				    <AlternatingRowStyle CssClass="RigaAlternata" />
				    <RowStyle CssClass="Riga" />
				    <HeaderStyle CssClass="IntestazioneTabella" />
				    <EmptyDataRowStyle CssClass="NessunRecordTrovato"/> 
                    <Columns>
                        <asp:ButtonField CommandName="Select" DataTextField="Descrizione" HeaderText="Descrizione" Text="Button" />
					    <asp:TemplateField>
						    <ItemTemplate>
							    <asp:ImageButton ID="cmdElimina" runat="server" CommandName="Delete" ImageUrl="~/images/Delete.gif" />
						    </ItemTemplate>
					    </asp:TemplateField>
                    </Columns>
                    <EmptyDataTemplate>
					    <asp:Label ID="Label6" runat="server"></asp:Label>
				    </EmptyDataTemplate>
			    </asp:GridView>
				<div class="Bottoni">
					<init:SigeproButton runat="server" ID="cmdNuovo" Text="Nuovo" IdRisorsa="NUOVO" OnClick="cmdNuovo_Click"/>
					<init:SigeproButton runat="server" ID="cmdChiudiLista" Text="Chiudi" IdRisorsa="CHIUDI" OnClick="cmdChiudiLista_Click"/>
				</div>
			</fieldset>
		</asp:View>
		<asp:View runat="server" ID="dettaglioView">
		    <fieldset>
		        <asp:UpdatePanel ID="UpdatePanel2" runat="server" UpdateMode="Conditional" >
		            <ContentTemplate>
		                <init:LabeledLabel runat="server" ID="lblCodice" Descrizione="Codice" />
		                <init:LabeledDropDownList runat="server" ID="ddlConfigurazione" Descrizione="*Configurazione utilizzata" Item-AutoPostBack="true" OnValueChanged="ddlConfigurazione_ValueChanged" />
		                <asp:RequiredFieldValidator runat="server" ID="rfvConfigurazione" ControlToValidate="ddlConfigurazione" ErrorMessage="*" />
		                <div>
		                    <init:LabeledDropDownList runat="server" ID="ddlAree" Descrizione="*Zona di riferimento" HelpControl="hdAree"/>
		                    <init:HelpDiv runat="server" ID="hdAree">Serve a determinare, in base alla configurazione, quale coefficiente verrà applicato al calcolo dell'importo delle pertinenze</init:HelpDiv>
		                </div>
		                <init:LabeledTextBox runat="server" ID="txDescrizione" Descrizione="*Descrizione" Item-Columns="60" />
		                <asp:RequiredFieldValidator runat="server" ID="rfvDescrizione" ControlToValidate="txDescrizione" ErrorMessage="*" />
		                <init:LabeledDoubleTextBox runat="server" ID="txPercAddizRegionale" Descrizione="Percentuale addizionale regionale" Item-Columns="6" />
		                <init:LabeledDoubleTextBox runat="server" ID="txPercAddizComunale" Descrizione="Percentuale addizionale comunale" Item-Columns="6" />
		                <init:LabeledLabel runat="server" ID="lblImportoMinimo" Descrizione="Canone minimo" />
		                <init:LabeledLabel runat="server" ID="lblTipoCalcolo" Descrizione="*Tipo di calcolo" />
		                
		                <asp:DropDownList runat="server" ID="ddlTipoCalcolo">
		                    <asp:ListItem Text="" Value=""></asp:ListItem>
		                    <asp:ListItem Text="A" Value="ANNUALE"></asp:ListItem>
		                    <asp:ListItem Text="S" Value="STAGIONALE"></asp:ListItem>
				        </asp:DropDownList>
				        <asp:RequiredFieldValidator runat="server" ID="rfvTipoCalcolo" ControlToValidate="ddlTipoCalcolo" ErrorMessage="*" />
		                
                    </ContentTemplate>
			        <Triggers>
				        <asp:AsyncPostBackTrigger ControlID="ddlConfigurazione" EventName="ValueChanged" />
			        </Triggers>
		        </asp:UpdatePanel>
                <asp:Panel runat="server" ID="pnlDettaglioCalcolo">
				    <asp:UpdatePanel ID="UpdatePanel1" runat="server" UpdateMode="Conditional" >
				    <ContentTemplate>
                        <table border="0" cellspacing="0" cellpadding="0">
		                    <colgroup width="13%"></colgroup>   <!--CATEGORIA-->
		                    <colgroup width="33%"></colgroup>   <!--SUPERFICIE-->
		                    <colgroup width="10%"></colgroup>    <!--MQ-->
		                    <colgroup width="5%"></colgroup>    <!--IMPORTO MQ-->
		                    <colgroup width="8%"></colgroup>    <!--DA-->
		                    <colgroup width="8%"></colgroup>    <!--A-->
		                    <colgroup width="11%"></colgroup>   <!--PERIODO-->
		                    <colgroup width="5%"></colgroup>   <!--PERC. RIDUZIONE-->
		                    <colgroup width="5%"></colgroup>    <!--TOTALE PARZIALE-->
		                    <colgroup width="2%"></colgroup>    <!--&NBSP;-->
                            <tr class="intestazionetabella">
                                <th>*Categoria</th>
                                <th>*Superficie</th>
                                <th>*Mq.</th>
                                <th>Importo al mq.</th>
                                <th>Da</th>
                                <th>A</th>
                                <th>Periodo</th>
                                <th>% Riduzione</th>
                                <th>Totale parziale</th>
                                <th>&nbsp;</th>
                            </tr>
				            <asp:Repeater ID="rptDettaglio" runat="server" OnItemDataBound="rptDettaglio_ItemDataBound" OnItemCommand="rptDettaglio_ItemCommand">
				                <ItemTemplate>
				                    <tr>
				                        <td><asp:Label runat="server" ID="lblCategoria" Text=""></asp:Label></td>
				                        <td><asp:Label runat="server" ID="lblTipoSuperficie" Text=""></asp:Label></td>
				                        <td><asp:Label runat="server" ID="lblMq" Text=""></asp:Label></td>
				                        <td><asp:Label runat="server" ID="lblImportoUnitario" Text=""></asp:Label></td>
				                        <td><asp:Label runat="server" ID="lblDa" Text=""></asp:Label></td>
				                        <td><asp:Label runat="server" ID="lblA" Text=""></asp:Label></td>
				                        <td><asp:Label runat="server" ID="lblPeriodo" Text=""></asp:Label></td>
				                        <td><asp:Label runat="server" ID="lblPercRiduzione" Text=""></asp:Label>&nbsp;%</td>
				                        <td style="text-align:right;"><asp:Label runat="server" ID="lblTotParziale" Text=""></asp:Label></td>
				                        <td><asp:ImageButton runat="server" ID="cmdCancellaDettaglio" ImageUrl="~/Images/delete.gif" Visible="false" /></td>
				                    </tr>
				                </ItemTemplate>
				            </asp:Repeater>
                            <tr>
                                <td class="nowrap"><asp:RequiredFieldValidator runat="server" ID="rfvCategorie" ControlToValidate="ddlCategorie" ErrorMessage="*" ValidationGroup="Dettaglio" /><asp:DropDownList runat="server" ID="ddlCategorie" DataTextField="Descrizione" DataValueField="Id" AutoPostBack="true" OnSelectedIndexChanged="ddlCategorie_SelectedIndexChanged" /></td>
                                <td class="nowrap"><asp:RequiredFieldValidator runat="server" ID="rfvTipiSuperfici" ControlToValidate="ddlTipiSuperfici" ErrorMessage="*" ValidationGroup="Dettaglio" /><asp:DropDownList runat="server" ID="ddlTipiSuperfici" DataTextField="TipoSuperficie" DataValueField="Id" AutoPostBack="true" OnSelectedIndexChanged="ddlTipiSuperfici_SelectedIndexChanged" /></td>
                                <td class="nowrap"><asp:RequiredFieldValidator runat="server" ID="rfvMq" ControlToValidate="txMq" ErrorMessage="*" ValidationGroup="Dettaglio" /><init:DoubleTextBox runat="server" ID="txMq" Columns="6" AutoPostBack="True" OnTextChanged="txMq_TextChanged" /></td>
                                <td class="nowrap"><init:DoubleTextBox runat="server" AutoPostBack="true" ID="txImportoUnitario" Columns="15" FormatString="N8" OnTextChanged="txImportoUnitario_TextChanged" /></td>
                                <td class="nowrap"><init:DateTextBox runat="server" ID="dtbDa" AutoPostBack="true" OnTextChanged="dtbDa_TextChanged"/></td>
                                <td class="nowrap"><init:DateTextBox runat="server" ID="dtbA" AutoPostBack="true" OnTextChanged="dtbA_TextChanged"/></td>
                                <td class="nowrap"><init:DoubleTextBox runat="server" ID="txPeriodo" Columns="4" AutoPostBack="True" OnTextChanged="txPeriodo_TextChanged" /></td>
                                <td class="nowrap"><init:DoubleTextBox runat="server" ID="txPercRiduzione" Columns="4" AutoPostBack="True" OnTextChanged="txPercRiduzione_TextChanged"/></td>
                                <td class="nowrap" style="text-align:right;" align="right"><init:DoubleTextBox runat="server" ID="txTotParziale" Columns="15" FormatString="N2" /></td>
                                <td class="nowrap"><asp:ImageButton runat="server" ID="cmdSalvaDettaglio" ImageUrl="~/Images/save.gif" Visible="True" OnClick="cmdSalvaDettaglio_Click" ValidationGroup="Dettaglio" /></td>
                            </tr>
		                    <tr class="intestazionetabella"><th colspan="10">&nbsp;</th></tr>
		                    <tr>
		                        <td colspan="2" align="right">Tot. Mq</td>
		                        <td align="left"><asp:Label runat="server" ID="lblTotMetriq" /></td>
		                        <td colspan="5" align="right"><asp:Label runat="server" ID="lblDescTotale" Text="Totale" /></td>
		                        <td align="right"><asp:Label runat="server" ID="lblTotale" />€</td>
		                        <td></td>
		                    </tr>
		                    <tr>
		                        <td colspan="8" align="right">Addizionale regionale</td>
		                        <td align="right"><asp:Label runat="server" ID="lblAddizionaleRegionale" />€</td>
   		                        <td>
		                            <init:HelpIcon runat="server" ID="hiAddizionaleRegionale" HelpControl="hdAddizionaleRegionale" />
		                            <init:HelpDiv runat="server" ID="hdAddizionaleRegionale">Il totale è inferiore al canone minimo perciò l'eventuale addizionale regionale verrà calcolata sull'importo del canone minimo</init:HelpDiv>
		                        </td>
		                    </tr>
		                    <tr>
		                        <td colspan="8" align="right">Addizionale comunale</td>
		                        <td align="right"><asp:Label runat="server" ID="lblAddizionaleComunale" />€</td>
   		                        <td>
		                            <init:HelpIcon runat="server" ID="hiAddizionaleComunale" HelpControl="hdAddizionaleComunale" />
		                            <init:HelpDiv runat="server" ID="hdAddizionaleComunale">Il totale è inferiore al canone minimo perciò l'eventuale addizionale comunale verrà calcolata sull'importo del canone minimo</init:HelpDiv>
		                        </td>
		                    </tr>
		                    <tr>
		                        <td colspan="8" align="right">Totale dovuto</td>
		                        <td align="right"><asp:Label runat="server" ID="lblTotaleDovuto" />€</td>
   		                        <td>
		                            <init:HelpIcon runat="server" ID="hiTotale" HelpControl="hdTotale" />
		                            <init:HelpDiv runat="server" ID="hdTotale">Attenzione, il totale dei canoni è inferiore al minimo specificato.<br>Come totale verrà preso il canone minimo al quale verranno applicate eventuali addizionali ( regionali e/o comunali )</init:HelpDiv>
		                        </td>
		                    </tr>
                        </table>
                        </ContentTemplate>
				        <Triggers>
					        <asp:AsyncPostBackTrigger ControlID="ddlCategorie" EventName="SelectedIndexChanged" />
					        <asp:AsyncPostBackTrigger ControlID="ddlTipiSuperfici" EventName="SelectedIndexChanged" />
				        </Triggers>
                    </asp:UpdatePanel>
				</asp:Panel>
				<div class="Bottoni">
					<init:SigeproButton runat="server" ID="cmdSalva" Text="Salva" IdRisorsa="SALVA" OnClick="cmdSalva_Click"/>
                    <init:SigeproButton runat="server" ID="cmdEliminaCalcolo" Text="Elimina calcolo" IdRisorsa="ELIMINACALCOLO" OnClick="cmdEliminaCalcolo_Click"/>
                    <init:SigeproButton runat="server" ID="cmdRiportaOneri" Text="Riporta oneri" IdRisorsa="COPIAONERI" OnClick="cmdRiportaOneri_Click"/>
                    <init:SigeproButton runat="server" ID="cmdStampa" Text="Sampa" IdRisorsa="STAMPA"/>
					<init:SigeproButton runat="server" ID="cmdChiudi" Text="Chiudi" IdRisorsa="CHIUDI" OnClick="cmdChiudi_Click" CausesValidation="false"/>
				</div>
		    </fieldset>
		</asp:View>
	</asp:MultiView>
</asp:Content>

