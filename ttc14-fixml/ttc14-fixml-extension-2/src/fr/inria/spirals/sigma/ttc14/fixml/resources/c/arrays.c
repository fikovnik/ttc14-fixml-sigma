#include "arrays.h"

#include <stdarg.h>
#include <stdlib.h>

void** new_array(int size, ...) {
  va_list ap;
  va_start(ap, size);
  
  void** array = malloc(sizeof(void*) * size);
  
  int i;
  
  for (i = 0; i < size; i++) {
    array[i] = va_arg(ap, void*);
  }
  
  va_end(ap);
  
  return array;
}