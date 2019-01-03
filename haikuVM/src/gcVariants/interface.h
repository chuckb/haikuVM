#if   HAIKU_GC==HAIKU_NoGC
#elif HAIKU_GC==HAIKU_IncrementalGC
#elif HAIKU_GC==HAIKU_ConservativGC
#elif HAIKU_GC==HAIKU_CompactingGC
#else
#error "unknown HAIKU_GC variant. Use e.g.: HAIKU_ConservativGC"
#endif

extern void native_java_lang_System_gc_V();

extern jobject GCalloc(jclass clazz, jheapsize size);
