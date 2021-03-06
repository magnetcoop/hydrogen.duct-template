/**
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/
 */

/**
 * Tell Google Closure compiler not to munge the third party JS
 * libraries below. We are pulling them from CDNs instead of adding
 * them to our code base.
 *
 * @fileoverview This is an externs file.
 * @externs
 */

/**
 * Figwheel expects files with .js extension inside its source
 * directories to be a foreign library. And foreign libraries *MUST*
 * declare a namespace. In fact, figwheel assumes it, and if it
 * doesn't find it and can't map the file back to a source .cljs file,
 * it bombs out with a NullPointerException.
 *
 * So even if this is *NOT* a foreign library, but just an externs file,
 * add a namespace declaration to prevent figwheel from crashing.
 *
 * Note: we use a fixed namespace here, because this file *doesn't*
 * have to use a project namespace. In fact if it does, Google Closure
 * compiler complains that "name <<namespace>> is not defined in the
 * externs". So we use a namespace that is highly unlikely to be an
 * actual project namespace.
 */
goog.provide('magnet.hydrogen.client.externs');

/**
 * Javascript interop with browser clipboard. Prevent name mangling.
 */
var navigator;
navigator.clipboard = function(){};
navigator.clipboard.prototype.writeText = function(){};
