<%@ Page Language="C#" MasterPageFile="~/SigeproNetMaster.master" AutoEventWireup="True" CodeBehind="RegistraPresenzeMercato.aspx.cs" Inherits="Sigepro.net.Istanze.Mercati.RegistraPresenzeMercato" Title="Assegnazione delle presenze di una giornata di mercato" %>

<%@ Register Src="PresenzaPosteggio.ascx" TagName="PresenzaPosteggio" TagPrefix="uc1" %>
<%@ MasterType VirtualPath="~/SigeproNetMaster.master" %>


<%@ Register tagPrefix="init" namespace="Init.Utils.Web.UI" assembly="Init.Utils"%>

<%@ Register tagPrefix="init" namespace="SIGePro.WebControls.UI" assembly="SIGePro.WebControls"%>
<%@ Register tagPrefix="init" namespace="SIGePro.WebControls.Ajax" assembly="SIGePro.WebControls"%>



<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="Server">
<script type="text/javascript">
var features = "width=785,height=520,top=20,left=20,menubar=yes,scrollbars=yes,resizable=yes,status=yes";

function AggiungiSpuntista( idtestata, codicemercato, codiceuso )
{
    var token = "<%=AuthenticationInfo.Token%>";
    var software = "<%=Software%>";
    
    var url = "AggiungiSpuntista.aspx?Token=" + token + "&Software=" + software + "&IdTestata=" + idtestata + "&CodiceMercato=" + codicemercato + "&CodiceUso=" + codiceuso;
    var w = window.open( url, "Spuntisti", features );
}
function RegistraContabilita( idtestata )
{

    var token = "<%=AuthenticationInfo.Token%>";
    var software = "<%=Software%>";
    
    var url = "RegistrazioneContabile.aspx?Token=" + token + "&Software=" + software + "&IdTestata=" + idtestata;
    var w = window.open( url, "Contabilità", features );

}
</script>
<asp:ScriptManager ID="ScriptManager1" runat="server"></asp:ScriptManager>
	<asp:MultiView runat="server" ID="multiView" ActiveViewIndex="0" OnActiveViewChanged="multiView_ActiveViewChanged">
		<asp:View runat="server" ID="ricercaView">
		    <fieldset>
		        <div class="Intervallo">
			        <div class="Da">
				        <asp:Label runat="server" ID="label3" Text="Dalla data" AssociatedControlID="dtDataRegistrazioneDa" />
				        <init:DateTextBox runat="server" ID="dtDataRegistrazioneDa"  />
			        </div>
			        <div class="A">
				        <asp:Label CssClass="StessaRiga" runat="server" ID="label4" Text="alla data:" AssociatedControlID="dtDataRegistrazioneA" />
				        <init:DateTextBox runat="server" ID="dtDataRegistrazioneA"/>
			        </div>
		        </div>
			    <div>
			        <asp:Label runat="server" ID="lblRicercaMercato" Text="Mercato" AssociatedControlID="rplRicercaMercato"/>
			        <init:RicerchePlusCtrl AutoPostBack="true" ID="rplRicercaMercato" runat="server" ColonneCodice="4" ColonneDescrizione="50" CompletionInterval="300" CompletionListCssClass="RicerchePlusLista" CompletionListHighlightedItemCssClass="RicerchePlusElementoSelezionatoLista" CompletionListItemCssClass="RicerchePlusElementoLista" CompletionSetCount="10" DataClassType="Init.SIGePro.Data.Mercati" DescriptionPropertyNames="Descrizione" LoadingIcon="~/Images/ajaxload.gif" MaxLengthCodice="10" MaxLengthDescrizione="150" MinimumPrefixLength="1" TargetPropertyName="CodiceMercato" ServicePath="~/WebServices/WSSIGePro/RicerchePlus.asmx" AutoSelect="True" ServiceMethod="GetCompletionList" OnValueChanged="rplRicercaMercato_ValueChanged"/>
			    </div>
			    <div>
					<asp:Label runat="server" ID="lblRicercaMercatiUso" Text="Giorno" AssociatedControlID="rplRicercaMercatiUso"/>
					<asp:UpdatePanel ID="UpdatePanel2" runat="server" UpdateMode="Conditional" >
						<ContentTemplate>
							<init:RicerchePlusCtrl ID="rplRicercaMercatiUso" runat="server" ColonneCodice="4" ColonneDescrizione="50" CompletionInterval="300" CompletionListCssClass="RicerchePlusLista" CompletionListHighlightedItemCssClass="RicerchePlusElementoSelezionatoLista" CompletionListItemCssClass="RicerchePlusElementoLista" CompletionSetCount="10" DataClassType="Init.SIGePro.Data.Mercati_Uso" DescriptionPropertyNames="Descrizione" LoadingIcon="~/Images/ajaxload.gif" MaxLengthCodice="10" MaxLengthDescrizione="150" MinimumPrefixLength="1" TargetPropertyName="Id" ServicePath="" AutoSelect="True" ServiceMethod="GetCompletionListUsi"/>
						</ContentTemplate>
						<Triggers>
							<asp:AsyncPostBackTrigger ControlID="rplRicercaMercato" EventName="ValueChanged" />
						</Triggers>
					</asp:UpdatePanel>
			    </div>
                <init:LabeledTextBox runat="server" ID="txRicercaDescrizione" Descrizione="Descrizione" Item-MaxLength="1000" Item-Columns="100"/>
				<div class="Bottoni">
					<init:SigeproButton runat="server" ID="cmdCerca"  Text="Cerca" IdRisorsa="CERCA" OnClick="cmdCerca_Click" />
					<init:SigeproButton runat="server" ID="cmdNuovo"  Text="Nuovo" IdRisorsa="NUOVO" OnClick="cmdNuovo_Click" />
					<init:SigeproButton runat="server" ID="cmdChiudi" Text="Chiudi" IdRisorsa="CHIUDI" OnClick="cmdChiudi_Click" />
				</div>
		    </fieldset>
		</asp:View>
		<asp:View runat="server" ID="listaView">
		    <fieldset>
		        <div>
                    <init:GridViewEx AllowSorting="True" runat="server" ID="gvLista" AutoGenerateColumns="False" OnSelectedIndexChanged="gvLista_SelectedIndexChanged" DefaultSortDirection="Ascending" DefaultSortExpression="Id" DataKeyNames="Id" DatabindOnFirstLoad="False" DataSourceID="ObjectDataSource1">
                        <EmptyDataRowStyle CssClass="NessunRecordTrovato"></EmptyDataRowStyle>
                        <Columns>
						    <asp:ButtonField HeaderText="Codice" SortExpression="Id" Text="Button" DataTextField="Id" CommandName="Select"></asp:ButtonField>
                            <asp:BoundField DataField="DataRegistrazione" SortExpression="DataRegistrazione" HeaderText="Data" DataFormatString="{0:dd/MM/yyyy}" HtmlEncode="False"></asp:BoundField>
                            <asp:TemplateField HeaderText="Mercato" SortExpression="Mercato">
                                <itemtemplate>
<asp:Label runat="server" Text='<%#DataBinder.Eval( ((Init.SIGePro.Data.MercatiPresenzeT)Container.DataItem).Mercato,"Descrizione")%>' id="Label2"></asp:Label>
</itemtemplate>
                            </asp:TemplateField>
                            <asp:TemplateField HeaderText="Giorno" SortExpression="MercatiUsoo">
                                <itemtemplate>
<asp:Label runat="server" Text='<%#DataBinder.Eval( ((Init.SIGePro.Data.MercatiPresenzeT)Container.DataItem).MercatiUso,"Descrizione")%>' id="Label1"></asp:Label>
</itemtemplate>
                            </asp:TemplateField>
                            <asp:BoundField DataField="Descrizione" SortExpression="Descrizione" HeaderText="Descrizione"></asp:BoundField>
	                    </Columns>
						<RowStyle CssClass="Riga"></RowStyle>
						<EmptyDataTemplate>
						    <asp:Label ID="Label6" runat="server">Non è stato trovato nessun record corrispondente ai criteri di ricerca.</asp:Label>
                        </EmptyDataTemplate>
						<HeaderStyle CssClass="IntestazioneTabella"></HeaderStyle>
						<AlternatingRowStyle CssClass="RigaAlternata"></AlternatingRowStyle>
					</init:GridViewEx>
                    <asp:ObjectDataSource ID="ObjectDataSource1" runat="server" SortParameterName="sortexpression" OldValuesParameterFormatString="original_{0}" SelectMethod="Find" TypeName="Init.SIGePro.Manager.MercatiPresenzeTMgr">
                        <SelectParameters>
                            <asp:QueryStringParameter Name="token" QueryStringField="token" Type="String" />
                            <asp:QueryStringParameter Name="software" QueryStringField="software" Type="String" />
                            <asp:ControlParameter ControlID="dtDataRegistrazioneDa" Name="dataDa" PropertyName="DateValue" Type="DateTime" />
                            <asp:ControlParameter ControlID="dtDataRegistrazioneA" Name="dataA" PropertyName="DateValue" Type="DateTime" />
                            <asp:ControlParameter ControlID="rplRicercaMercato" Name="codicemercato" PropertyName="Value" Type="Int32" />
                            <asp:ControlParameter ControlID="rplRicercaMercatiUso" Name="codiceuso" PropertyName="Value" Type="Int32" />
                            <asp:ControlParameter ControlID="txRicercaDescrizione" Name="descrizione" PropertyName="Value" Type="String" />
                            <asp:Parameter Name="sortExpression" Type="String" />
                        </SelectParameters>
                    </asp:ObjectDataSource>
		        </div>
				<div class="Bottoni">
					<init:SigeproButton runat="server" ID="cmdChiudiLista" Text="Chiudi" IdRisorsa="CHIUDI" OnClick="cmdChiudiLista_Click" />
				</div>
		    </fieldset>
		</asp:View>
		<asp:View runat="server" ID="dettaglioView">
			<fieldset>
			    <init:LabeledLabel ID="lblId" runat="server" Descrizione="Codice"></init:LabeledLabel>
			    <init:LabeledDropDownList ID="ddlTipologia" runat="server" Descrizione="*Tipologia di registrazione"/>
		        <div class="Intervallo">
			        <div class="Da">
				        <asp:Label runat="server" ID="label7" Text="*Data di registrazione" AssociatedControlID="dtDallaDataRegistrazione" />
				        <init:DateTextBox runat="server" ID="dtDallaDataRegistrazione"  />
			        </div>
			        <div class="A">
				        <asp:Label CssClass="StessaRiga" runat="server" ID="label8" Text="alla data:" AssociatedControlID="dtAllaDataRegistrazione" />
				        <init:DateTextBox runat="server" ID="dtAllaDataRegistrazione" HelpDiv="hdDataRegistrazione" />
				        <init:HelpDiv ID="hdDataRegistrazione" runat="server">Il campo "alla data" è obbligatorio solamente se la Tipologia di Registrazione è <b>Periodica</b></init:HelpDiv>
			        </div>
		        </div>
			    <div>
			        <asp:Label runat="server" ID="lblMercato" Text="*Mercato" AssociatedControlID="rplMercato"/>
			        <init:RicerchePlusCtrl AutoPostBack="true" ID="rplMercato" runat="server" ColonneCodice="4" ColonneDescrizione="50" CompletionInterval="300" CompletionListCssClass="RicerchePlusLista" CompletionListHighlightedItemCssClass="RicerchePlusElementoSelezionatoLista" CompletionListItemCssClass="RicerchePlusElementoLista" CompletionSetCount="10" DataClassType="Init.SIGePro.Data.Mercati" DescriptionPropertyNames="Descrizione" LoadingIcon="~/Images/ajaxload.gif" MaxLengthCodice="10" MaxLengthDescrizione="150" MinimumPrefixLength="1" TargetPropertyName="CodiceMercato" ServicePath="~/WebServices/WsSIGePro/RicerchePlus.asmx" AutoSelect="True" ServiceMethod="GetCompletionList" OnValueChanged="rplMercato_ValueChanged"/>
			    </div>
			    <div>
					<asp:Label runat="server" ID="lblUso" Text="*Giorno" AssociatedControlID="rplMercatiUso"/>
					<asp:UpdatePanel ID="UpdatePanel1" runat="server" UpdateMode="Conditional" >
						<ContentTemplate>
							<init:RicerchePlusCtrl ID="rplMercatiUso" runat="server" ColonneCodice="4" ColonneDescrizione="50" CompletionInterval="300" CompletionListCssClass="RicerchePlusLista" CompletionListHighlightedItemCssClass="RicerchePlusElementoSelezionatoLista" CompletionListItemCssClass="RicerchePlusElementoLista" CompletionSetCount="10" DataClassType="Init.SIGePro.Data.Mercati_Uso" DescriptionPropertyNames="Descrizione" LoadingIcon="~/Images/ajaxload.gif" MaxLengthCodice="10" MaxLengthDescrizione="150" MinimumPrefixLength="1" TargetPropertyName="Id" ServicePath="" AutoSelect="True" ServiceMethod="GetCompletionListUsi"/>
						</ContentTemplate>
						<Triggers>
							<asp:AsyncPostBackTrigger ControlID="rplMercato" EventName="ValueChanged" />
						</Triggers>
					</asp:UpdatePanel>
			    </div>
			    <init:LabeledTextBox runat="server" ID="txDescrizione" Descrizione="*Descrizione" Item-MaxLength="1000" Item-Columns="100" HelpControl="hdDescrizione" />
			    <init:HelpDiv ID="hdDescrizione" runat="server">Se lasciato vuoto verrà compilato automaticamente con la data, il mercato e il giorno specificati.</init:HelpDiv>
			    <div>
                <asp:UpdatePanel ID="UpdatePanel3" runat="server" UpdateMode="Conditional" >
                    <ContentTemplate>
                        <asp:Panel runat="server" ID="pnlSpuntisti">
                            <table cellpadding="0" cellspacing="0" border="0">
                                <colgroup width="2%"></colgroup>
                                <colgroup width="98%"></colgroup>
                                <tr>
                                    <th><asp:ImageButton ID="imgExpSpuntisti" runat="server" ImageUrl="~/Images/tee_1.gif" OnClick="imgExpSpuntisti_Click"/></th>
                                    <th>Gestione spuntisti</th>
                                </tr>
                                <tr>
                                    <td>&nbsp;</td>
                                    <td>
                                        <asp:Panel runat="server" ID="pnlDettSpuntisti">
                                            <asp:Repeater ID="rptSpuntisti" runat="server" OnItemDataBound="rptSpuntisti_ItemDataBound">
                                                <HeaderTemplate>
                                                    <table border="0" cellspacing="0" cellpadding="0">
		                                                <colgroup width="35%"></colgroup>
		                                                <colgroup width="5%"></colgroup>
		                                                <colgroup width="5%"></colgroup>
		                                                <colgroup width="5%"></colgroup>
		                                                <colgroup width="35%"></colgroup>
		                                                <colgroup width="5%"></colgroup>
		                                                <colgroup width="5%"></colgroup>
		                                                <colgroup width="5%"></colgroup>
		                                                <tr class="intestazionetabella">
		                                                    <th>Spuntista</th>
		                                                    <th>Pres.</th>
		                                                    <th>Pos.</th>
		                                                    <th></th>
		                                                    <th>Spuntista</th>
		                                                    <th>Pres.</th>
		                                                    <th>Pos.</th>
		                                                    <th></th>
		                                                </tr>
		                                        </HeaderTemplate>
                                                <ItemTemplate>
		                                                <%if (IndiceSpuntisti % 2 == 0){%>
                                                            <tr>
		                                                <%}%>
		                                                <td><asp:TextBox ID="txCodiceAnagrafe" runat="server" Text='<%# Bind("CodiceAnagrafe")%>' Columns="3" Visible="false"></asp:TextBox><%#DataBinder.Eval(((Init.SIGePro.Data.MercatiPresenzeD)Container.DataItem).Anagrafe, "NOMINATIVO")%>&nbsp;<%#DataBinder.Eval(((Init.SIGePro.Data.MercatiPresenzeD)Container.DataItem).Anagrafe, "NOME")%></td>
		                                                <td><init:IntTextBox ID="txPresenze" runat="server" ValoreInt='<%#DataBinder.Eval(Container.DataItem,"NumeroPresenze")%>' Columns="2"></init:IntTextBox></td>
		                                                <td><asp:TextBox ID="txPosteggio" runat="server" Text='<%#DataBinder.Eval(((Init.SIGePro.Data.MercatiPresenzeD)Container.DataItem).Posteggio,"CodicePosteggio")%>' Columns="4"></asp:TextBox></td>
		                                                <td align="center"><asp:ImageButton ID="imgCancella" runat="server" ImageUrl="~/Images/delete.gif" CommandArgument='<%# Bind("Id") %>' OnClick="DeleteItem"/></td>
		                                                <%if (IndiceSpuntisti % 2 == 1) {%>
		                                                </tr>
		                                                <%}%>
		                                                <% IndiceSpuntisti++; %>
		                                            </ItemTemplate>
		                                            <FooterTemplate>
		                                                    <tr class="intestazionetabella"><td colspan="8"></td></tr>
		                                                </table>
		                                            </FooterTemplate>
				                            </asp:Repeater>
				                            <div class="Bottoni">
					                            <init:SigeproButton runat="server" ID="cmdNuovoSpuntista" Text="Aggiungi spuntista" IdRisorsa="AGGIUNGISPUNTISTA"/>
				                            </div>
			                            </asp:Panel>
                                        &nbsp;
                                    </td>
                                </tr>
                            </table>
                        </asp:Panel>
                    </ContentTemplate>
                    <Triggers>
                        <asp:AsyncPostBackTrigger ControlID="imgExpSpuntisti" EventName="Click"/>
                    </Triggers>
                </asp:UpdatePanel>
                <asp:UpdatePanel ID="UpdatePanel4" runat="server" UpdateMode="Conditional" >
                    <ContentTemplate>
                        <asp:Panel runat="server" ID="pnlPosteggi">
                            <table cellpadding="0" cellspacing="0" border="0">
                                <colgroup width="2%"></colgroup>
                                <colgroup width="98%"></colgroup>
                                <tr>
                                    <th><asp:ImageButton ID="imgExpOccupanti" runat="server" ImageUrl="~/Images/tee_0.gif" OnClick="imgExpOccupanti_Click"/></th>
                                    <th>Composizione mercato</th>
                                </tr>
                                <tr>
                                    <td>&nbsp;</td>
                                    <td>
                                        <asp:Panel runat="server" ID="pnlDettPosteggi">
			                                <div>
                                                <asp:Repeater ID="rptDettaglio" runat="server" OnItemDataBound="rptDettaglio_ItemDataBound">
		                                            <HeaderTemplate>
		                                                <table>
		                                                    <colgroup width="25%"></colgroup>
		                                                    <colgroup width="25%"></colgroup>
		                                                    <colgroup width="25%"></colgroup>
		                                                    <colgroup width="25%"></colgroup>
		                                                    <tr class="intestazionetabella">
		                                                        <th colspan="4">Elenco posteggi</th>
		                                                    </tr>
		                                            </HeaderTemplate>
		                                            <ItemTemplate>
		                                                <% if ( IndiceRecord % 4 == 0 ) {%>
                                                            <tr>
		                                                <%} %>
		                                                    <td style="border: solid 1px #000000;vertical-align:top;"><uc1:PresenzaPosteggio ID="PresenzaPosteggio1" runat="server"/>
		                                                    </td>
		                                                <% if ( IndiceRecord % 4 == 3 ) {%>
		                                                </tr>
		                                                <%} %>
		                                                <% IndiceRecord++; %>
		                                            </ItemTemplate>
		                                            <FooterTemplate>
		                                                    <tr class="intestazionetabella"><td colspan="4"></td></tr>
		                                                </table>
		                                            </FooterTemplate>
		                                        </asp:Repeater>		                
		                                    </div>
                                        </asp:Panel>
                                    </td>
                                </tr>
                            </table>
                        </asp:Panel>
                    </ContentTemplate>
                    <Triggers>
                        <asp:AsyncPostBackTrigger ControlID="imgExpOccupanti" EventName="Click"/>
                    </Triggers>
                </asp:UpdatePanel>
                </div>
                <div class="Bottoni">
					<init:SigeproButton runat="server" ID="cmdSalva" Text="Salva" IdRisorsa="SALVA" OnClick="cmdSalva_Click"/>
					<init:SigeproButton runat="server" ID="cmdElimina" Text="Elimina" IdRisorsa="ELIMINA" OnClick="cmdElimina_Click"/>
					<init:SigeproButton runat="server" ID="cmdRegContabile" Text="Registrazione Contabile" IdRisorsa="REGCONTABILE" OnClick="cmdRegContabile_Click" />
					<init:SigeproButton runat="server" ID="cmdChiudiDettaglio" Text="Chiudi" IdRisorsa="CHIUDI" OnClick="cmdChiudiDettaglio_Click"/>
				</div>
			</fieldset>
		</asp:View>
		<asp:View runat="server" ID="regMultiplaView">
		    <fieldset>
		        <legend>Riepilogo</legend>
		        <div class="Intervallo">
			        <div class="Da">
				        <asp:Label runat="server" ID="label10" Text="Dalla data" AssociatedControlID="dtMultiDallaData" />
				        <init:DateTextBox runat="server" ID="dtMultiDallaData"  />
			        </div>
			        <div class="A">
				        <asp:Label CssClass="StessaRiga" runat="server" ID="label11" Text="alla data:" AssociatedControlID="dtMultiAllaData" />
				        <init:DateTextBox runat="server" ID="dtMultiAllaData" />
			        </div>
		        </div>
                <div>
			        <asp:Label runat="server" ID="Label5" Text="*Mercato" AssociatedControlID="rplMultiMercato"/>
			        <init:RicerchePlusCtrl ID="rplMultiMercato" runat="server" ColonneCodice="4" ColonneDescrizione="50" CompletionInterval="300" CompletionListCssClass="RicerchePlusLista" CompletionListHighlightedItemCssClass="RicerchePlusElementoSelezionatoLista" CompletionListItemCssClass="RicerchePlusElementoLista" CompletionSetCount="10" DataClassType="Init.SIGePro.Data.Mercati" DescriptionPropertyNames="Descrizione" LoadingIcon="~/Images/ajaxload.gif" MaxLengthCodice="10" MaxLengthDescrizione="150" MinimumPrefixLength="1" TargetPropertyName="CodiceMercato" ServicePath="~/WebServices/WsSIGePro/RicerchePlus.asmx" AutoSelect="True" ServiceMethod="GetCompletionList" ReadOnly="true"/>
			    </div>
			    <div>
					<asp:Label runat="server" ID="Label9" Text="*Giorno" AssociatedControlID="rplMultiMercatiUso"/>
                    <init:RicerchePlusCtrl ID="rplMultiMercatiUso" runat="server" ColonneCodice="4" ColonneDescrizione="50" CompletionInterval="300" CompletionListCssClass="RicerchePlusLista" CompletionListHighlightedItemCssClass="RicerchePlusElementoSelezionatoLista" CompletionListItemCssClass="RicerchePlusElementoLista" CompletionSetCount="10" DataClassType="Init.SIGePro.Data.Mercati_Uso" DescriptionPropertyNames="Descrizione" LoadingIcon="~/Images/ajaxload.gif" MaxLengthCodice="10" MaxLengthDescrizione="150" MinimumPrefixLength="1" TargetPropertyName="Id" ServicePath="" AutoSelect="True" ServiceMethod="GetCompletionListUsi" ReadOnly="true"/>
			    </div>
                <init:LabeledTextBox runat="server" ID="txMultiDescrizione" Descrizione="*Descrizione" Item-MaxLength="1000" Item-Columns="100" HelpControl="hdMultiDescrizione" />
			    <init:HelpDiv ID="hdMultiDescrizione" runat="server">Se lasciato vuoto verrà compilato automaticamente con la data, il mercato e il giorno specificati.</init:HelpDiv>
			    <asp:Repeater ID="rptGiorniMercato" runat="server" OnItemDataBound="rptGiorniMercato_ItemDataBound">
			        <HeaderTemplate>
	                    <table border="0" cellspacing="0" cellpadding="0">
                            <colgroup width="2%"></colgroup>
                            <colgroup width="23%"></colgroup>
                            <colgroup width="2%"></colgroup>
                            <colgroup width="23%"></colgroup>
                            <colgroup width="2%"></colgroup>
                            <colgroup width="23%"></colgroup>
                            <colgroup width="2%"></colgroup>
                            <colgroup width="23%"></colgroup>
                            <tr class="intestazionetabella">
		                        <th colspan="8">Date di svolgimento del mercato</th>
		                    </tr>
			        </HeaderTemplate>
			        <ItemTemplate>
			            <% if ( IndiceDateSvolgimento % 4 == 0 ) {%>
			            <tr>
			            <%} %>
			                <td>
			                    <asp:CheckBox ID="chkDataMercato" runat="server"/>
			                </td>
			                <td>
                                <asp:Label ID="lblDataMercato" runat="server" Text=""></asp:Label>
			                    <init:DateTextBox ID="dtDataMercato" runat="server" Visible="false" />
			                </td>
			            <% if ( IndiceDateSvolgimento % 4 == 3 ) {%>
			            </tr>
			            <%} %>
			            <% IndiceDateSvolgimento++; %>
			        </ItemTemplate>
                    <FooterTemplate>
                            <tr class="intestazionetabella"><td colspan="8"></td></tr>
                        </table>
                    </FooterTemplate>
			    </asp:Repeater>
                <div class="Bottoni">
					<init:SigeproButton runat="server" ID="cmdMultiConferma" Text="Conferma" IdRisorsa="CONFERMA" OnClick="cmdMultiConferma_Click"/>
					<init:SigeproButton runat="server" ID="cmdMultiChiudi" Text="Chiudi" IdRisorsa="CHIUDI" OnClick="cmdMultiChiudi_Click"/>
				</div>
			</fieldset>
		</asp:View>
	</asp:MultiView>
</asp:Content>

