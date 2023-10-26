package org.fiware.iam.ccs.model;

/**
 * Manipulated the generated models to avoid bug in openapi generator:
 * - When creating a model for collections of objects, the equals/hashcode function are incorrect.
 * - Maps are generated using the additionalProperties field, with rather inconvenient access methods
 *
 * Once this is fixed, the unmanipulated generated models should be used again
 */