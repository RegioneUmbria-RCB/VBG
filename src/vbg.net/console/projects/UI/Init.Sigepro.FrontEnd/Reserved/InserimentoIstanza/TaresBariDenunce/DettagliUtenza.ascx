<%@ Control Language="C#" AutoEventWireup="true" CodeBehind="DettagliUtenza.ascx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.TaresBariDenunce.DettagliUtenza" %>
<asp:MultiView runat="server" ID="multiview" ActiveViewIndex="0">
    <asp:View runat="server" ID="viewUtenzaEsistente">
        <div class="dettagliUtenzaTares">
	        <div class="datiAnagrafici">
		        <fieldset>
			        <div class="titolo">Dati Anagrafici</div>
			        <div>
				        <asp:Literal runat="server" ID="ltrIdentificativoUtenza" /><br />
				        <asp:Literal runat="server" ID="ltrNominativo" /><br />
				        <asp:Literal runat="server" ID="ltrCodiceFiscale" /><br />
				        <asp:Literal runat="server" ID="ltrDatiNascita" />
			        </div>
			        <div  class="titolo">Residente in</div>
			        <div>
				        <asp:Literal runat="server" ID="ltrIndirizzoResidenza" />				
			        </div>
		        </fieldset>
	        </div>


	        <div class="utenze">
                <fieldset>
                    <legend>Utenze <%=TipoUtenza== global::Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.TaresBariDenunce.TipoUtenzaTaresEnum.Domestica ? "domestiche" : "non domestiche"%> intestate al contribuente</legend>

		            <asp:Repeater runat="server" ID="rptIndirizziUtenze" >
			                <HeaderTemplate>
				                <table class="riferimentiUtenza">
					                <tr>
						                <th>Utenza</th>
						                <th>Indirizzo</th>
						                <th>Superficie</th>
					                </tr>
			                </HeaderTemplate>
			                <ItemTemplate>
				                    <tr>
					                    <td>
						                    <asp:HiddenField runat="server" ID="HiddenField1" Value='<%# Eval("Id") %>' />
						                    <asp:Literal runat="server" ID="Literal1" Text='<%# Eval("Id") %>'/>
					                    </td>
					                    <td>
						                    <asp:Label runat="server" ID="ltrIndirizzo" Text='<%# Eval("Ubicazione") %>' cssclass="indirizzo"/><br />
						                    <asp:Label runat="server" ID="ltrDatiCatastali" Text='<%# Eval("DatiCatastali") %>' cssclass="datiCatastali"/>
						                    <asp:Literal runat="server" ID="ltrTipoUtenza" Text='<%# Eval("CategoriaCatastale") %>'/>
					                    </td>
					                    <td><asp:Literal runat="server" ID="ltrSuperficie" Text='<%# Eval("Superficie") %>'/></td>
				                    </tr>
			                </ItemTemplate>		
			                <FooterTemplate>
				                    </table>
			                </FooterTemplate>
		                </asp:Repeater>

                    <asp:Repeater runat="server" ID="rptIndirizziNonDomestici" >
			            <HeaderTemplate>
				            <table class="riferimentiUtenza">
					            <tr>
						            <th>Utenza</th>
						            <th>Indirizzo</th>
                                    <th>Attività</th>
						            <th>Superfici</th>
					            </tr>
			            </HeaderTemplate>
			            <ItemTemplate>
				                <tr>
					                <td>
						                <asp:HiddenField runat="server" ID="HiddenField1" Value='<%# Eval("Id") %>' />
						                <asp:Literal runat="server" ID="Literal1" Text='<%# Eval("Id") %>'/>
					                </td>
					                <td>
						                <asp:Label runat="server" ID="ltrIndirizzo" Text='<%# Eval("Ubicazione") %>' cssclass="indirizzo"/><br />
						                <asp:Label runat="server" ID="ltrDatiCatastali" Text='<%# Eval("DatiCatastali") %>' cssclass="datiCatastali"/>
					                </td>
                                    <td>
                                        <asp:Literal runat="server" ID="ltrTipoUtenza" Text='<%# Eval("CodiceAttivita") %>'/>
                                    </td>
					                <td>
                                        Tassabile: <asp:Literal runat="server" ID="ltrS1" Text='<%# Eval("SuperficieTassabile") %>'/><br />
                                        Non tassabile: <asp:Literal runat="server" ID="Literal2" Text='<%# Eval("SuperficieIntassabile") %>'/><br />
                                        Rif speciali: <asp:Literal runat="server" ID="Literal3" Text='<%# Eval("SuperficieRifiutiSpeciali") %>'/><br />
                                        Totale: <asp:Literal runat="server" ID="Literal4" Text='<%# Eval("SuperficieTotale") %>'/>                                        
                                    </td>
				                </tr>
			            </ItemTemplate>		
			            <FooterTemplate>
				                </table>
			            </FooterTemplate>
		            </asp:Repeater>
                </fieldset>
	        </div>
         </div>    
    </asp:View>

    <asp:View runat="server" ID="viewNuovaUtenza">
        <div>La domanda che si sta presentando è relativa ad un nuovo contribuente</div>
    </asp:View>

</asp:MultiView>