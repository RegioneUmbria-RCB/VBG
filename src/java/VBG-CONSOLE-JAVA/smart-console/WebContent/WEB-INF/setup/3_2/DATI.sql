update CLMENU_JAVA set pagina='dyn2modellit/list.htm?software=SOFTWARE', JSP='JAVA' where id=158;
update CLMENU_JAVA set pagina='dyn2modellit/list.htm?software=TT', JSP='JAVA' where id=447;

update CLMENU_JAVA set pagina='dyn2campi/list.htm?software=SOFTWARE' , JSP='JAVA' where id=159;
update CLMENU_JAVA set pagina='dyn2campi/list.htm?software=TT', JSP='JAVA' where id=449;

update ALBEROPROC_ENDO set FLAG_INTERVENTO=0 where FLAG_INTERVENTO is null;

  
update clmenu_JAVA set pagina = 'dyn2regole/list.htm?software=SOFTWARE', jsp='JAVA' WHERE ID=1020;
update clmenu_JAVA set pagina = 'dyn2regole/list.htm?software=TT', jsp='JAVA' WHERE ID=1021;


