<%@ Page Language="C#" MasterPageFile="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" AutoEventWireup="true" CodeBehind="RiepilogoDomandaHtml.aspx.cs"
    Inherits="Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.RiepilogoDomandaHtml" Title="Untitled Page" %>

<%@ MasterType VirtualPath="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" %>
<%@ Register TagPrefix="cc1" Namespace="Init.Utils.Web.UI" Assembly="Init.Utils" %>

<asp:Content runat="server" ID="headerContent" ContentPlaceHolderID="head">
    <style media="all">
        .contenitore-riepilogo {
            width: 100%;
        }

        .riepilogo-domanda-html {
            border-style: none;
            width: 100%;
        }
    </style>
</asp:Content>

<asp:Content ID="Content1" ContentPlaceHolderID="stepContent" runat="server">
    <asp:MultiView runat="server" ID="multiView" ActiveViewIndex="0">
        <asp:View runat="server" ID="riepilogoView">

            <script type="text/javascript">

                $(function () {
                    var marginBottom = 15;

                    $('iframe').height($('.paginatore-step').position().top - $('iframe').position().top - marginBottom);
                    $('.contenuto-step').css('margin-bottom', '0');

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

                });


            </script>

            <div>
                <asp:Literal runat="server" ID="ltrDescrizioneFaseRiepilogo" />
            </div>


            <%if (MostraRiepilogoDomanda)
                {%>


            <div class="contenitore-riepilogo">
                <iframe id="iFrameDomanda" class="riepilogo-domanda-html" src='<%= GetUrlRiepilogoDomanda() %>'></iframe>
            </div>

            <%} %>

            <%--            <div class="bottoni">
                <asp:Button runat="server" ID="cmdAnnulla" Text="Modifica dati" OnClick="cmdAnnulla_Click" />
                <asp:Button runat="server" ID="cmdProcedi" Text="Procedi" OnClick="cmdProcedi_Click" />
            </div>--%>
        </asp:View>

        <asp:View runat="server" ID="erroreInvioView">
            <asp:Label runat="server" ID="lblErroreInvio"></asp:Label>
        </asp:View>
    </asp:MultiView>
</asp:Content>
