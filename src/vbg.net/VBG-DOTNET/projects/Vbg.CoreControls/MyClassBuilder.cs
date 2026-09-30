//using Basic.Reference.Assemblies;
//using Microsoft.CodeAnalysis;
//using Microsoft.CodeAnalysis.CSharp;
//using System.Reflection;

//namespace Vbg.CoreControls
//{
//    public class MyClassBuilder
//    {
//        private readonly AssemblyName assemblyName;
//        private readonly List<Method> methods;

//        public MyClassBuilder(string ClassName, List<Method> methods)
//        {
//            this.assemblyName = new AssemblyName(ClassName);
//            this.methods = methods;
//        }

//        public object InvokeMethod(string MethodName)
//        {
//            Method method = this.methods.Find(m => m.Name == MethodName);

//            if (method == null)
//                return null;

//            var syntaxTree = CSharpSyntaxTree.ParseText(string.Format(@"
//                using System;
//                namespace RoslynCompileSample
//                {{
//                    public class {4}
//                    {{
//                        public {0} {1}({2})
//                        {{
//                            {3}
//                        }}
//                    }}
//                }}",
//                method.ReturnType == null ? "void" : method.ReturnType.Name,
//                method.Name,
//                method.Parameters.Select(kvp => kvp.Value.FullName + " " + kvp.Key).Aggregate((x, y) => x + ", " + y),
//                method.ScriptBody,
//                this.assemblyName.Name
//            ));

//            var assemblyPath = Path.GetDirectoryName(typeof(object).Assembly.Location);

//            List<MetadataReference> metadataReferences = new List<MetadataReference>();
//            metadataReferences.AddRange(NetStandard20.All);
//            metadataReferences.AddRange(method.MetadataReference);

//            var compilation = CSharpCompilation.Create(this.assemblyName.Name)
//                .WithOptions(new CSharpCompilationOptions(OutputKind.DynamicallyLinkedLibrary,
//                    optimizationLevel: OptimizationLevel.Release,
//                    assemblyIdentityComparer: DesktopAssemblyIdentityComparer.Default))
//                .AddReferences(metadataReferences)
//                .AddSyntaxTrees(syntaxTree);

//            //CSharpCompilation compilation = CSharpCompilation.Create(
//            //    assemblyName.Name,
//            //    syntaxTrees: new[] { syntaxTree },
//            //    references: NetStandard20.All,
//            //    options: new CSharpCompilationOptions(OutputKind.DynamicallyLinkedLibrary,
//            //        optimizationLevel: OptimizationLevel.Release,
//            //        assemblyIdentityComparer: DesktopAssemblyIdentityComparer.Default));

//            using (var ms = new MemoryStream())
//            {
//                var result = compilation.Emit(ms);

//                if (!result.Success)
//                {
//                    IEnumerable<Diagnostic> failures = result.Diagnostics.Where(diagnostic =>
//                        diagnostic.IsWarningAsError ||
//                        diagnostic.Severity == DiagnosticSeverity.Error);

//                    foreach (Diagnostic diagnostic in failures)
//                    {
//                        //Console.Error.WriteLine("{0}: {1}", diagnostic.Id, diagnostic.GetMessage());
//                    }

//                    return null;
//                }
//                else
//                {
//                    //Console.WriteLine("Compilation done without any error.");

//                    ms.Seek(0, SeekOrigin.Begin);
//                    var assembly = Assembly.Load(ms.ToArray());

//                    Type type = assembly.GetType($"RoslynCompileSample.{this.assemblyName.Name}");
//                    object obj = Activator.CreateInstance(type);
//                    return type.InvokeMember(
//                        method.Name,
//                        BindingFlags.Default | BindingFlags.InvokeMethod,
//                        null,
//                        obj,
//                        method.ParameterValues);
//                }
//            }
//        }
//    }

//    public class Method
//    {
//        public string Name { get; set; }
//        public Type ReturnType { get; set; }
//        public Dictionary<string, Type> Parameters { get; set; }
//        public object[] ParameterValues { get; set; }
//        public string ScriptBody { get; set; }
//        public List<MetadataReference> MetadataReference { get; set; }

//        public Method(string name, Type returnType, Dictionary<string, Type> parameters, object[] parameterValues, string scriptBody, List<MetadataReference> References = null)
//        {
//            this.Name = name;
//            this.ReturnType = returnType;
//            this.Parameters = parameters;
//            this.ParameterValues = parameterValues;
//            this.ScriptBody = scriptBody;
//            this.MetadataReference = References;
//        }

//    }
//}
