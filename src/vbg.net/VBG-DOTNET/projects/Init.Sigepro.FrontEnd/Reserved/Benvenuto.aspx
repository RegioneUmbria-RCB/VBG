<%@ Page Language="C#" MasterPageFile="~/AreaRiservataMaster.Master" AutoEventWireup="true"
    CodeBehind="Benvenuto.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.Benvenuto"
    Title="Benvenuto nel sistema di presentazione on-line delle pratiche" %>

<%@ MasterType VirtualPath="~/AreaRiservataMaster.Master" %>
<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">


    <div class="pagina-benvenuto">

        <!-- Main jumbotron for a primary marketing message or call to action -->
        <%if (this.MostraDescrizionePagina)
            {%>
        <div class="jumbotron">
            <p>
                <asp:Literal runat="server" ID="descrizionePagina" />
            </p>
        </div>
        <%} %>

        <%--        <asp:Repeater runat="server" ID="rptMenu" OnItemDataBound="rptSubMenu_ItemDataBound">
            <ItemTemplate>
                <div class="row">

                    <asp:Repeater runat="server" ID="rptSubMenu">

                        <ItemTemplate>

                            <div class="col-md-6 feature">

                                <div class="row">
                                    <div class="col-md-4" aria-hidden="true">
                                        <div class="benvenuto-icona">
                                            <div>
                                                <asp:HyperLink runat="server" ID="HyperLink1" NavigateUrl='<%# Eval("Url") %>' Target='<%#Eval("Target") %>' aria-hidden="true">
                                                    <div class="sr-only" ><%# Eval("Titolo") %></div>
                                                    <asp:Literal runat="server" ID="literal1" Text='<%# Eval("GlyphIcon") %>' Visible='<%#Eval("UseGliph") %>' />
                                                    <asp:Image runat="server" ID="imgIcona" ImageUrl='<%#Eval("UrlIcona") %>' Visible='<%#Eval("UseIcona") %>' />
                                                </asp:HyperLink>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-md-8">
                                        <h2>
                                            <asp:HyperLink runat="server" ID="hlTitolo" Text='<%# Eval("Titolo") %>' NavigateUrl='<%# Eval("Url") %>' Target='<%#Eval("Target") %>' />
                                        </h2>
                                        <asp:Literal runat="server" ID="ltrDescrizioneStep" Text='<%# Eval("DescrizioneEstesa") %>' />
                                    </div>
                                </div>

                            </div>

                        </ItemTemplate>

                    </asp:Repeater>

                </div>

            </ItemTemplate>
        </asp:Repeater>--%>
        <style>
           
        </style>


        <div class="tile-container">
            <asp:Repeater runat="server" ID="rptMenu" OnItemDataBound="rptSubMenu_ItemDataBound">
                <ItemTemplate>


                    <asp:Repeater runat="server" ID="rptSubMenu">

                        <ItemTemplate>



                            <asp:HyperLink runat="server" ID="HyperLink1" NavigateUrl='<%# Eval("Url") %>' Target='<%#Eval("Target") %>' CssClass="vbg-tile">

                                <div class="tile-icon">
                                    <div class="sr-only"><%# Eval("Titolo") %></div>
                                    <asp:Literal runat="server" ID="literal1" Text='<%# Eval("GlyphIcon") %>' Visible='<%#Eval("UseGliph") %>' />
                                    <asp:Image runat="server" ID="imgIcona" ImageUrl='<%#Eval("UrlIcona") %>' Visible='<%#Eval("UseIcona") %>' />
                                </div>

                                <div class="tile-text">

                                    <h2>
                                        <asp:Literal runat="server" Text='<%# Eval("Titolo") %>' />
                                    </h2>
                                    <asp:Literal runat="server" ID="ltrDescrizioneStep" Text='<%# Eval("DescrizioneEstesa") %>' />
                                </div>
                            </asp:HyperLink>


                        </ItemTemplate>

                    </asp:Repeater>



                </ItemTemplate>
            </asp:Repeater>
        </div>
    </div>
</asp:Content>
