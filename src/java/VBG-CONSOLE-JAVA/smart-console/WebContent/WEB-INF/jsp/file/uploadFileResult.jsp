<h1>File caricato.</h1>
<label>Nome: ${filename }</label><br/>
<label>Dimensione: ${size } byte</label><br/><br/>
<input type="button" value="Apri" onclick="javascript:window.location.href='${pageContext.request.contextPath}/file/ajaxDownload.htm?fileId=${id }'" />
<input type="button" value="Elimina" onclick="javascript:window.location.href='${pageContext.request.contextPath}/file/ajaxDelete.htm?fileId=${id }'" />
<input type="button" value="Chiudi" onclick="javascript:window.close()" />