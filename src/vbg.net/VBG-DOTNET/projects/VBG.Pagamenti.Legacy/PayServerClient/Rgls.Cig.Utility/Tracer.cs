using log4net;
using log4net.Config;
using System;
using System.Reflection;

namespace Rgls.Cig.Utility
{
	public class Tracer
	{
		private ILog log;

		private static bool bConfLoaded = false;

		private bool bAddClassName = true;

		public Tracer()
		{
			this.log = LogManager.GetLogger(MethodBase.GetCurrentMethod().DeclaringType);
		}

		public Tracer(string sLoggerName)
		{
			if (!Tracer.bConfLoaded)
			{
				XmlConfigurator.Configure();
				Tracer.bConfLoaded = true;
			}
			if (this.isValidString(sLoggerName))
			{
				this.log = LogManager.GetLogger(sLoggerName);
				this.bAddClassName = !sLoggerName.StartsWith("it.");
			}
			else
			{
				this.log = LogManager.GetLogger(MethodBase.GetCurrentMethod().DeclaringType);
			}
		}

		private string formatMsg(object obj, string msg, string cat)
		{
			string result;
			if (obj == null)
			{
				result = string.Concat(new string[]
				{
					DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss,fff"),
					" - ",
					cat,
					" > static member - ",
					msg
				});
			}
			else
			{
				result = string.Concat(new string[]
				{
					DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss,fff"),
					" - ",
					cat,
					" > ",
					obj.GetType().FullName,
					" - ",
					msg
				});
			}
			return result;
		}

		private string formatMsg(object obj, string msg)
		{
			string sObjName = "";
			if (this.bAddClassName)
			{
				if (obj == null)
				{
					sObjName = "static - ";
				}
				else
				{
					sObjName = obj.GetType().Name + " - ";
				}
			}
			string result;
			if (msg == null)
			{
				result = sObjName + "empty messsage";
			}
			else
			{
				result = sObjName + msg;
			}
			return result;
		}

		public void traceDebug(object obj, string msg)
		{
			this.log.Debug(this.formatMsg(obj, msg));
		}

		public void traceDebug(string msg)
		{
			this.log.Debug(msg);
		}

		public void traceInfo(object obj, string msg)
		{
			this.log.Info(this.formatMsg(obj, msg));
		}

		public void traceInfo(string msg)
		{
			this.log.Info(msg);
		}

		public void traceWarning(object obj, string msg)
		{
			this.log.Warn(this.formatMsg(obj, msg));
		}

		public void traceWarning(string msg)
		{
			this.log.Warn(msg);
		}

		public void traceError(object obj, string msg)
		{
			this.log.Error(this.formatMsg(obj, msg));
		}

		public void traceError(string msg)
		{
			this.log.Error(msg);
		}

		public void traceException(object obj, string msg, Exception e)
		{
			this.traceException(this.formatMsg(obj, msg), e);
		}

		public void traceException(string msg, Exception e)
		{
			if (e != null)
			{
				this.log.Fatal(msg, e);
			}
			else
			{
				this.log.Fatal(msg);
			}
		}

		public void traceException(Exception e, string msg)
		{
			if (e != null)
			{
				this.log.Fatal(msg, e);
			}
			else
			{
				this.log.Fatal(msg);
			}
		}

		private void traceStackException(Exception e)
		{
			if (e != null)
			{
				this.log.Fatal("StackTrace: ", e);
			}
		}

		private bool isValidString(string s)
		{
			return s != null && s.Length > 0;
		}
	}
}
