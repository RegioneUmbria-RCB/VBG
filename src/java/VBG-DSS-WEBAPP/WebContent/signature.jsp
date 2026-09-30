<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" href="css/dss.css" media="all" />
<link rel="shortcut icon" href="images/favicon.ico" />
<link rel="icon" type="image/gif" href="images/favicon_animated.gif" />
<title>DSS</title>
    <script type="text/javascript" src="scripts/jquery.js"></script>
    <script type="text/javascript" src="scripts/jquery-ui-effects.js"></script>
    <script type="text/javascript" src="scripts/detect_browser_version.js"></script>
</head>
<body>
<div id="mainContainer">
	<div id="header">
    	<a href="index.html"><img src="images/dss-logo.png" alt="Back to index" />	</a>
        <h1 style="position:absolute; top:30px; left:130px;">DSS - Standard</h1>
        <a href="mailto:dgmarkt.Project-DSS@arhs-developments.com?subject=Feedback%20to%20SfR" class="bug">Send Feedback</a>
    </div>
    <div id="content">
        <h2>DSS Applet</h2>
        <div class="columnContainer">
        	<div class="column" style="margin-right:1.5%;">
                <script type="text/javascript" src="scripts/deployJava.js"></script>
                <script type="text/javascript">
	                 var attributes = {
	                     code :'eu.europa.ec.markt.dss.applet.SignatureApplet.class',
	                     archive :'signature-applet-r5.jar,bcprov-jdk16.jar,bcmail-jdk16.jar,bctsp-jdk16.jar,sscd-mocca-adapter.jar',
	                     width :400,
	                     height :400
	                 };
	                 var parameters = {
                         <%
                         final String serviceUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/service";
                         %>
	                	 serviceUrl :'<%=serviceUrl%>'
	                 };
	                 var version = '1.6'; 
	
	                 deployJava.runApplet(attributes, parameters, version);
                </script>
            </div>
        	<div class="column" >
                If you don't have access to a SSCD, you can try the signature with a PKCS#12 package.
                <a href="0F.p12" target="p12-download">Here is a sample PKCS#12 file</a> that you can use for signing.
                You can just download the file and select the PKCS#12 signature token API in step 3.
                The password for accessing the certificates inside the file is "password".<br/>
                <br/>
                Note: You should enable showing the Java console via the Java plugin settings.<br/>
                <div id="compatibility_warning" style="display:none;">
                    <br/>
                    <div style="border:red solid 2px;background-color:#ffff99;padding:2px 2px 2px 2px">
                        <u>Warning</u><br/>
                        It seems that your environment does not meet the requirements:<br/>
                        <div id="compatibility_required" class="cite">
                            Java Version: 1.6<br/>
                            Browser: Internet Explorer 6-8 or Mozilla Firefox (3.0)<br/>
                            Architecture: 32 bit<br/>
                        </div>
                        <br/>
                        We found the following information:
                        <div id="compatibility_found" class="cite"></div>
                        <br/>
                        Anyway, we tried to start the applet (should be displayed on the left).<br/>
                    </div>
                    <br/>
                </div>

                <script type="text/javascript">
                    function checkRequirements() {
                        var browser = jQuery.browser;
                        var version = detectBrowserVersion();
                        version = "" + version;
                        if (version.length > 0) {
                            version = version.charAt(0);
                        }

                        var compatibleBrowser = false;
                        if (browser.msie) {
                            if (version != "" && "6|7|8|".indexOf(version+"|") > -1) {
                                compatibleBrowser = true;
                            }
                        } else if (browser.mozilla) {
                            if (version != "" && "3|4|".indexOf(version+"|") > -1) {
                                compatibleBrowser = true;
                            }
                        }
                        var compatibleJava = navigator.javaEnabled();

                        if (compatibleBrowser && compatibleJava) {
                            return;
                        }

                        jQuery("#compatibility_found").html(navigator.userAgent);
                        jQuery("#compatibility_warning").show("bounce", null, "fast");
                    }

                    checkRequirements();
                </script>

            </div>
        </div>


        <h2>Information</h2>
		<p>The DSS Applet has been tested with different browsers and Java versions.<br/>
            The table below depicts the result of these tests. Components not compatible with the requirements are shown in italic.</p>

		<table>
        	<thead>
				<tr>
					<th style="width:26px">&nbsp;</th>
					<th>Browser</th>
					<th>Java version</th>
					<th>Comment</th>
				</tr>
            </thead>
            <tbody>
				<tr>
                    <td><img src="images/ok.gif" alt="OK" /></td>
					<td class="req_no">Chrome</td>
					<td>1.6.0.16</td>
					<td>ok</td>
				</tr>
				<tr>
                    <td><img src="images/ok.gif" alt="OK" /></td>
					<td class="req_no">Chrome 10</td>
					<td>1.6.0.22</td>
					<td>ok</td>
				</tr>
				<tr>
                    <td><img src="images/ok.gif" alt="OK" /></td>
					<td class="req_no">Chrome 10.0.648.133</td>
					<td>1.6.0u21</td>
					<td>ok</td>
				</tr>
				<tr>
                    <td><img src="images/wa.gif" alt="OK" /></td>
					<td class="req_no">FireFox 1.5.0.12</td>
					<td>1.6.0.24</td>
					<td>FireFox 1.5 not supported by Java Plugin.</td>
				</tr>
				<tr>
                    <td><img src="images/ok.gif" alt="OK" /></td>
					<td>FireFox 3.6.15</td>
					<td>1.6.0_22</td>
					<td>ok</td>
				</tr>
				<tr>
                    <td><img src="images/ok.gif" alt="OK" /></td>
					<td>FireFox 3.6.16</td>
					<td>1.6.0u21</td>
					<td>ok</td>
				</tr>
				<tr>
                   <td><img src="images/nok.gif" alt="OK" /></td>
					<td>FireFox 3.6.16</td>
					<td>N/C</td>
					<td>applet doesn't start</td>
				</tr>
				<tr>
                    <td><img src="images/ok.gif" alt="OK" /></td>
					<td class="req_no">FireFox 4</td>
					<td>1.6</td>
					<td>ok</td>
				</tr>
				<tr>
                    <td><img src="images/ok.gif" alt="OK" /></td>
					<td class="req_no">FireFox 4</td>
					<td>1.6.0.24</td>
					<td>ok</td>
				</tr>
				<tr>
                    <td><img src="images/ok.gif" alt="OK" /></td>
					<td class="req_no">FireFox 4</td>
					<td>1.6.0_22</td>
					<td>ok</td>
				</tr>
				<tr>
                    <td><img src="images/ok.gif" alt="OK" /></td>
					<td class="req_no">FireFox 4</td>
					<td>1.6.0_22</td>
					<td>ok</td>
				</tr>
				<tr>
                    <td><img src="images/ok.gif" alt="OK" /></td>
					<td>Internet Explorer 7</td>
					<td>1.6.0.24</td>
					<td>ok</td>
				</tr>
				<tr>
                    <td><img src="images/ok.gif" alt="OK" /></td>
					<td>Internet Explorer 8</td>
					<td>1.6.0u21</td>
					<td>ok</td>
				</tr>
				<tr>
                    <td><img src="images/ok.gif" alt="OK" /></td>
					<td>Internet Explorer 8</td>
					<td>N/C</td>
					<td>ok</td>
				</tr>
				<tr>
                   <td><img src="images/nok.gif" alt="OK" /></td>
					<td class="req_no">Internet Explorer 9</td>
					<td>1.6.0_22</td>
					<td>tab crash</td>
				</tr>
				<tr>
                    <td><img src="images/ok.gif" alt="OK" /></td>
					<td class="req_no">Opera</td>
					<td>1.6.0_22</td>
					<td>ok</td>
				</tr>
				<tr>
                    <td><img src="images/ok.gif" alt="OK" /></td>
					<td class="req_no">Safari</td>
					<td>1.6.0_22</td>
					<td>ok</td>
				</tr>
			</tbody>
		</table>

    </div>
</div>

</body>
</html>