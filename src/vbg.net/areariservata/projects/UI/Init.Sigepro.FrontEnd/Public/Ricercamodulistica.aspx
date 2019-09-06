<%@ Page Language="C#" AutoEventWireup="true" MasterPageFile="~/Public/PaginaRicercaFoMaster.Master" CodeBehind="RicercaModulistica.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Public.Ricercamodulistica" %>

<asp:Content runat="server" ID="header" ContentPlaceHolderID="head">

    <style>
        .container-ricerca-modulistica {
        }

            .container-ricerca-modulistica h2 {
                font-weight: bold;
                font-size: 18px;
                border-bottom: 1px solid #eee;
                margin-bottom: 10px;
            }

            .container-ricerca-modulistica .dati-modulistica {
                margin-bottom: 48px;
            }

        .box-ricerca-testuale {
            width: 100%;
            margin-bottom: 18px;
        }

            .box-ricerca-testuale > input {
                width: 50%;
            }

        .descrizione-modulistica {
            /*margin-bottom: 10px;*/
        }

        .categoria-modulistica > ul {
        }

        .categoria-modulistica.collapsed > ul {
            visibility: hidden;
            opacity: 0;
            position: absolute;
        }

        .categoria-modulistica.collapsed > h2 > i.ion-minus-round {
            display: none;
        }

        .categoria-modulistica.collapsed > h2 > i.ion-plus-round {
            display: inline;
        }

        .categoria-modulistica.expanded > ul {
            /*display: block;*/
            visibility: visible;
            opacity: 1;
            transition: all 0.3s ease;
            position: relative
        }

        .categoria-modulistica.expanded > h2 > i.ion-minus-round {
            display: inline;
        }

        .categoria-modulistica.expanded > h2 > i.ion-plus-round {
            display: none;
        }
    </style>

    <%=LoadScripts(new[] {
    "~/public/RicercaModulistica.js",
    }) %>

    <script>

        $(function () {
            var ricercaModulistica = new RicercaModulistica();

            ricercaModulistica.collapseAll();
            ricercaModulistica.handleTextSearch();
            ricercaModulistica.collegaHandlerCollapse();
        });

    </script>

</asp:Content>

<asp:Content runat="server" ID="body" ContentPlaceHolderID="ContentPlaceHolder1">

    <div class="container-ricerca-modulistica">

        <div class="box-ricerca-testuale">
            <input type="text" id="ricercaTestuale" placeholder="Cosa stai cercando?" />
        </div>

        <asp:Repeater runat="server" ID="rptCategorie" OnItemDataBound="rptCategorie_ItemDataBound">
            <HeaderTemplate>
                <ul>
            </HeaderTemplate>

            <ItemTemplate>
                <li>
                    <div class="categoria-modulistica">
                        <h2>
                            <i class="ion-plus-round" aria-hidden="true"></i>
                            <i class="ion-minus-round" aria-hidden="true"></i>
                            <asp:Literal runat="server" Text='<%#Eval("Descrizione") %>' />
                        </h2>
                        <ul>
                            <asp:Repeater runat="server" ID="rptModulistica" OnItemCommand="rptModulistica_ItemCommand">

                                <ItemTemplate>
                                    <li>
                                        <div class="dati-modulistica">
                                            <h3>
                                                <asp:Literal runat="server" Text='<%#Eval("Titolo") %>' />
                                            </h3>
                                            <div class="descrizione-modulistica">
                                                <asp:Literal runat="server" Text='<%#Eval("Descrizione") %>' />
                                            </div>


                                            <asp:Literal runat="server" ID="Literal1" Text="<i class='ion-link' ></i>" Visible='<%# !String.IsNullOrEmpty(DataBinder.Eval(Container.DataItem, "Url").ToString()) %>' />
                                            <asp:HyperLink runat="server" Style="display: inline" NavigateUrl='<%#Eval("Url") %>' Target="_blank" Visible='<%# !String.IsNullOrEmpty(DataBinder.Eval(Container.DataItem, "Url").ToString()) %>'>
                                                    <asp:Literal runat="server" Text='<%#Eval("Url") %>' /><br />
                                            </asp:HyperLink>

                                            <asp:Literal runat="server" ID="ltrDownload" Text="<i class='ion-android-download'></i>" Visible='<%# ((int?)Eval("CodiceOggetto")).HasValue %>' />
                                            <asp:LinkButton runat="server" Text="Scarica" Visible='<%# ((int?)Eval("CodiceOggetto")).HasValue %>' CommandName="Download" CommandArgument='<%#Eval("CodiceOggetto") %>' />


                                        </div>
                                    </li>
                                </ItemTemplate>


                            </asp:Repeater>
                        </ul>
                    </div>
                </li>
            </ItemTemplate>

            <FooterTemplate>
                </ul>
            </FooterTemplate>
        </asp:Repeater>

        <div id="nessun-risultato" style="display: none; font-style: italic"><i class="ion-sad-outline"></i>Nessun risultato trovato</div>

    </div>
</asp:Content>
