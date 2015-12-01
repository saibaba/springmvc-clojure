<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<html>
<head>
<title>Clojure Shell</title>
<style>
.CodeMirror {width: 100%; height: 300px; font-size:150%;}
textarea.default {width: 100%; height: 150px; background-color:rgb(255,255,255); color:rgb(0,0,255);font-size:150%;}
textarea.scpt {width: 100%; height: 300px; background-color:rgb(255,255,255); color:rgb(0,0,255);font-size:150%;}
</style>
<link rel="stylesheet" href="/springapp-clj/resources/CodeMirror/css/codemirror.css">
<script src="/springapp-clj/resources/CodeMirror/js/codemirror.js"></script>
<script src="/springapp-clj/resources/CodeMirror/mode/clojure.js"></script>

</head>
  <body>
    <form:form method="post" commandName="shell">
      <form:textarea class="scpt" id="clj_script" name="clj_script" path="source" />
      <input id="run" type="submit" value="Run Script" />
       <hr />
       <form:textarea class="default" id="result" readOnly="true" path="result" />
    </form:form>

    <hr />
        <div>Read from a file and evaluate all of them:<br />
        <pre>(eval (with-open [r (java.io.PushbackReader. (clojure.java.io/reader "/tmp/data.clj"))]
             (binding [*read-eval* false]
               (read r))))</pre>
        <br />
        <pre>Sample content in /tmp/data.clj:
            (doall [(+ 1 20) (* 30 9)]) 
        </pre>
        </div>
        <hr />
        <div>Call a static method on Spring Framework:<br />
        <pre>(. org.springframework.web.util.HtmlUtils (htmlUnescape "&amp;Hi&amp;"))</pre>
        <br />
        </div>
        <hr />
        <div>3rd Fib number:<br />
        <pre>(do (def fib (lazy-cat [0 1] (map + fib (rest fib)))) (nth (take 10 fib) 3))</pre>
        <br />
        <hr />
      </div>

<%@ include file="/WEB-INF/jsp/menu.jsp" %>

<script>
  var ta = document.getElementById("clj_script");

  var editor = CodeMirror.fromTextArea(ta, {
    lineNumbers: true,
    matchBrackets: true,
    mode: "text/x-clojure" 
  });
</script>
</body>
</html>
