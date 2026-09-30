<%@ Page Language="C#" MasterPageFile="~/SigeproNetMaster.master" AutoEventWireup="true" Inherits="Archivi_CalcoloOneri_CostoCostruzione_CCTabellaClassiEdificio" Title="Maggiorazione classi edificio" Codebehind="CCTabellaClassiEdificio.aspx.cs" %>
<%@ MasterType VirtualPath="~/SigeproNetMaster.master" %>

<%@ Register tagPrefix="init" namespace="Init.Utils.Web.UI" assembly="Init.Utils"%>

<%@ Register tagPrefix="init" namespace="SIGePro.WebControls.UI" assembly="SIGePro.WebControls"%>
<%@ Register tagPrefix="init" namespace="SIGePro.WebControls.Ajax" assembly="SIGePro.WebControls"%>


<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" Runat="Server">
    <asp:MultiView runat="server" ID="multiView" ActiveViewIndex="0" OnActiveViewChanged="multiView_ActiveViewChanged">
		    <asp:View runat="server" ID="ricercaView">
			    <fieldset>
				    <div>
					    <asp:Label runat="server" ID="lblSrcCodice" Text="Codice" AssociatedControlID="txtSrcCodice" />
					    <init:IntTextBox runat="server" ID="txtSrcCodice" MaxLength="6" Columns="6"/>
				    </div>
				    <div>
					    <asp:Label runat="server" ID="lblSrcDescrizione" Text="Descrizione" AssociatedControlID="txtSrcDescrizione" />
					    <asp:TextBox runat="server" ID="txtSrcDescrizione" MaxLength="200" Columns="80" />
				    </div>
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
			    <init:GridViewEx AllowSorting="true" runat="server" ID="gvLista" AutoGenerateColumns="False" DataKeyNames="Id" OnSelectedIndexChanged="gvLista_SelectedIndexChanged" DataSourceID="ObjectDataSourceCCTabellaClassiEdificio"
			        DefaultSortExpression="Id" DefaultSortDirection="Ascending">
				    <AlternatingRowStyle CssClass="RigaAlternata" />
				    <RowStyle CssClass="Riga" />
				    <HeaderStyle CssClass="IntestazioneTabella" />
				    <EmptyDataRowStyle CssClass="NessunRecordTrovato"/>
				    <Columns>
					    <asp:ButtonField CommandName="Select" DataTextField="Id" HeaderText="Codice" Text="Button" HeaderStyle-HorizontalAlign="Left" sortExpression="Id" />
					    <asp:BoundField DataField="Descrizione" HeaderText="Descrizione" HeaderStyle-HorizontalAlign="Left" sortExpression="Descrizione"/>
					    <asp:BoundField DataField="Da" HeaderText="Da" HeaderStyle-HorizontalAlign="Right" ItemStyle-HorizontalAlign="Right" sortExpression="Da"/>
					    <asp:BoundField DataField="A" HeaderText="A" HeaderStyle-HorizontalAlign="Right" ItemStyle-HorizontalAlign="Right" sortExpression="A"/>
					    <asp:BoundField DataField="Maggiorazione" HeaderText="Maggiorazione (%)" HeaderStyle-HorizontalAlign="Right" ItemStyle-HorizontalAlign="Right" sortExpression="Maggiorazione" />
				    </Columns>
				    <EmptyDataTemplate>
					    <asp:Label ID="Label6" runat="server">Non è stato trovato nessun record corrispondente ai criteri di ricerca.</asp:Label>
				    </EmptyDataTemplate>
			    </init:GridViewEx>
                    <asp:ObjectDataSource ID="ObjectDataSourceCCTabellaClassiEdificio" runat="server"
                        OldValuesParameterFormatString="original_{0}" SelectMethod="Find" TypeName="Init.SIGePro.Manager.CCTabellaClassiEdificioMgr" SortParameterName="sortExpression">
                        <SelectParameters>
                            <asp:QueryStringParameter Name="token" QueryStringField="token" Type="String" />
                            <asp:ControlParameter ControlID="txtSrcCodice" Name="id" PropertyName="ValoreInt" Type="Int32" />
                            <asp:ControlParameter ControlID="txtSrcDescrizione" Name="descrizione" PropertyName="Text"
                                Type="String" />
                            <asp:QueryStringParameter Name="software" QueryStringField="software" Type="String" />
                        </SelectParameters>
                    </asp:ObjectDataSource>
			    </div>
				    <div class="Bottoni">
					    <init:SigeproButton runat="server" ID="cmdCloseList" Text="Chiudi" IdRisorsa="CHIUDI" OnClick="cmdCloseList_Click" />
				    </div>
			    </fieldset>
		    </asp:View>
    		
		    <asp:View runat="server" ID="dettaglioView">
			    <fieldset>
                    <div>
					    <asp:Label runat="server" ID="label3" Text="Codice" AssociatedControlID="lblId" />
					    <asp:Label runat="server" ID="lblId" />
				    </div>
				    <div>
					    <asp:Label runat="server" ID="lblDescrizione" Text="*Descrizione" AssociatedControlID="txtDescrizione" />
					    <asp:TextBox runat="server" ID="txtDescrizione" MaxLength="200" Columns="80"/>
				    </div>
				    <div>
					    <asp:Label runat="server" ID="lblDa" Text="*Maggiore di (>)" AssociatedControlID="txtDa" />
					    <init:IntTextBox runat="server" ID="txtDa" MaxLength="11" Columns="5"/>
                        </div>
                    <div class="DescrizioneCampo">Per intervalli che partono da 0 indicare come primo valore -1.</div>
				    <div>
					    <asp:Label runat="server" ID="lblA" Text="*Minore o uguale a (<=)" AssociatedControlID="txtA" />
					    <init:IntTextBox runat="server" ID="txtA" MaxLength="11" Columns="5"/>
				    </div>
				    <div>
					    <asp:Label runat="server" ID="lblMaggiorazione" Text="*Maggiorazione (%)" AssociatedControlID="txtMaggiorazione" />
					    <init:DoubleTextBox runat="server" ID="txtMaggiorazione" MaxLength="6" Columns="6"/>
				    </div>
				    <div class="Bottoni">
					    <init:SigeproButton runat="server" ID="cmdSalva" Text="Salva" IdRisorsa="OK" OnClick="cmdSalva_Click" />
					    <init:SigeproButton runat="server" ID="cmdElimina" Text="Elimina" IdRisorsa="ELIMINA" OnClick="cmdElimina_Click" />
					    <init:SigeproButton runat="server" ID="cmdChiudiDettaglio" Text="Chiudi" IdRisorsa="CHIUDI" OnClick="cmdChiudiDettaglio_Click" />
				    </div>
			    </fieldset>
		    </asp:View>
	    </asp:MultiView>
</asp:Content>

