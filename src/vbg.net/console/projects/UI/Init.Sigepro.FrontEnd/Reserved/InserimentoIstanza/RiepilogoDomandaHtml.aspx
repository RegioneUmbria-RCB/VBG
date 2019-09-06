<%@ Page Language="C#" MasterPageFile="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" AutoEventWireup="true" CodeBehind="RiepilogoDomandaHtml.aspx.cs"
    Inherits="Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.RiepilogoDomandaHtml" Title="Untitled Page" %>

<%@ MasterType VirtualPath="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" %>
<%@ Register TagPrefix="cc1" Namespace="Init.Utils.Web.UI" Assembly="Init.Utils" %>

<asp:Content runat="server" ID="headerContent" ContentPlaceHolderID="head">
</asp:Content>

<asp:Content ID="Content1" ContentPlaceHolderID="stepContent" runat="server">
    <asp:MultiView runat="server" ID="multiView" ActiveViewIndex="0">
        <asp:View runat="server" ID="riepilogoView">

            <script type="text/javascript">

                require(['jquery', 'jquery.ui'], function ($) {
                    $(function () {


						<%if (!RiepilogoRichiedeFirma()) {%>
                        $('#<%=this.Master.BottoniPaginatore.BottoneInviaDomanda.ClientID %>').click(onInvioClick);

					    function onInvioClick(e) {
					        nascondiBottoni();
					        mostraMessaggio();
					    }

					    function nascondiBottoni() {
					        $('.bottoni').css('display', 'none')
					    }

					    function mostraMessaggio() {
					        $('.modal-invio-istanza-in-corso').modal('show');
					    }
					    <%} %>

                        $("form>.container").css("padding-bottom", 0);
                        $(".contenuto-step").css("margin-bottom", 0);
                            /* margin-bottom: 70px; */
                        $("iframe").height($("form>.container").innerHeight() - $("form>.container>h1").height() - 10 - $(".paginatore-step").height())
					});
				});


            </script>

            <div>
                <asp:Literal runat="server" ID="ltrDescrizioneFaseRiepilogo" />
            </div>


            <%if (MostraRiepilogoDomanda) {%>

                <div class="row">

                    <div class="col-md-12">
                        <iframe id="iFrameDomanda" class="riepilogo-domanda-html" src='<%= GetUrlRiepilogoDomanda() %>'></iframe>
                    </div>
                </div>

            <%} %>

            
        </asp:View>

        <asp:View runat="server" ID="erroreInvioView">
            <asp:Label runat="server" ID="lblErroreInvio"></asp:Label>
        </asp:View>
    </asp:MultiView>
</asp:Content>
