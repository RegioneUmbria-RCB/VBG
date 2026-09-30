using System;
using System.Diagnostics;
using System.Reflection;
using System.Text;

namespace Init.Utils
{
    public class CodeProfiler : IDisposable
    {
        public static CodeProfiler Track(MethodBase mb)
        {
#if DEBUG
            return new CodeProfiler($"{mb.DeclaringType.Name}.{mb.Name}");
#else
			return new CodeProfiler(String.Empty);
#endif
        }
        public static CodeProfiler Track(string name)
        {
            return new CodeProfiler(name);
        }

#if DEBUG
        private Stopwatch _stopwatch;
        private readonly string _name = String.Empty;
        private static int counter = 0;
        private static int indent = 0;
#endif

        protected CodeProfiler(string name)
        {
#if DEBUG
            this._stopwatch = new Stopwatch();
            this._name = name;

            this.Start();
#endif
        }

        private protected CodeProfiler() : this(
#if DEBUG
            String.Format("Timer{0}", ++counter)
#else
			String.Empty
#endif
        )
        {
        }

        public void Start()
        {
#if DEBUG
            var sb = new StringBuilder();
            for (int i = 0; i < indent; i++)
            {
                sb.Append("\t");
            }
            Debug.WriteLine($"{sb}Inizio {this._name}");
            this._stopwatch.Start();
            indent++;
#endif
        }

        public void Stop()
        {
#if DEBUG
            this._stopwatch.Stop();
            indent--;

            var sb = new StringBuilder();

            for (int i = 0; i < indent; i++)
            {
                sb.Append("\t");
            }

            Debug.WriteLine($"{sb.ToString()}Fine {this._name} [{this._stopwatch.ElapsedMilliseconds} ms]");
#endif
        }

        #region IDisposable Members

        public void Dispose()
        {
#if DEBUG
            this.Stop();
            this._stopwatch = null;
#endif
        }

        #endregion
    }
}
