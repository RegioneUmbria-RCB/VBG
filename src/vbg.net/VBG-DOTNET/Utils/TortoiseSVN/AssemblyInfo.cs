using System.Reflection;
#if DEBUG
[assembly : AssemblyConfiguration("Debug")]
#endif
#if RELEASE
[assembly: AssemblyConfiguration("Release")]
#endif

#if NET48
[assembly : AssemblyDescription("NET48")]
#endif
#if NETSTANDARD
[assembly : AssemblyDescription("NETSTANDARD")]
#endif
#if NET6_0_OR_GREATER
[assembly : AssemblyDescription("NET6_0_OR_GREATER")]
#endif

[assembly : AssemblyVersion("2.127")]
