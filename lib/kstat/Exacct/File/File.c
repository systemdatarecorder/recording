/*
 * This file was generated automatically by ExtUtils::ParseXS version 3.16 from the
 * contents of File.xs. Do not edit this file, edit File.xs instead.
 *
 *    ANY CHANGES MADE HERE WILL BE LOST!
 *
 */

#line 1 "File.xs"
/*
 * CDDL HEADER START
 *
 * The contents of this file are subject to the terms of the
 * Common Development and Distribution License, Version 1.0 only
 * (the "License").  You may not use this file except in compliance
 * with the License.
 *
 * You can obtain a copy of the license at usr/src/OPENSOLARIS.LICENSE
 * or http://www.opensolaris.org/os/licensing.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 *
 * When distributing Covered Code, include this CDDL HEADER in each
 * file and include the License file at usr/src/OPENSOLARIS.LICENSE.
 * If applicable, add the following below this CDDL HEADER, with the
 * fields enclosed by brackets "[]" replaced with your own identifying
 * information: Portions Copyright [yyyy] [name of copyright owner]
 *
 * CDDL HEADER END
 */
/*
 * Copyright 2002 Sun Microsystems, Inc.  All rights reserved.
 * Use is subject to license terms.
 *
 * File.xs contains XS code for exacct file manipulation.
 */

#pragma ident	"%Z%%M%	%I%	%E% SMI"

#include <pwd.h>
#include "../exacct_common.xh"

/* Pull in the file generated by extract_defines. */
#include "FileDefs.xi"

/*
 * The XS code exported to perl is below here.  Note that the XS preprocessor
 * has its own commenting syntax, so all comments from this point on are in
 * that form.
 */

#line 53 "File.c"
#ifndef PERL_UNUSED_VAR
#  define PERL_UNUSED_VAR(var) if (0) var = var
#endif

#ifndef dVAR
#  define dVAR		dNOOP
#endif


/* This stuff is not part of the API! You have been warned. */
#ifndef PERL_VERSION_DECIMAL
#  define PERL_VERSION_DECIMAL(r,v,s) (r*1000000 + v*1000 + s)
#endif
#ifndef PERL_DECIMAL_VERSION
#  define PERL_DECIMAL_VERSION \
	  PERL_VERSION_DECIMAL(PERL_REVISION,PERL_VERSION,PERL_SUBVERSION)
#endif
#ifndef PERL_VERSION_GE
#  define PERL_VERSION_GE(r,v,s) \
	  (PERL_DECIMAL_VERSION >= PERL_VERSION_DECIMAL(r,v,s))
#endif
#ifndef PERL_VERSION_LE
#  define PERL_VERSION_LE(r,v,s) \
	  (PERL_DECIMAL_VERSION <= PERL_VERSION_DECIMAL(r,v,s))
#endif

/* XS_INTERNAL is the explicit static-linkage variant of the default
 * XS macro.
 *
 * XS_EXTERNAL is the same as XS_INTERNAL except it does not include
 * "STATIC", ie. it exports XSUB symbols. You probably don't want that
 * for anything but the BOOT XSUB.
 *
 * See XSUB.h in core!
 */


/* TODO: This might be compatible further back than 5.10.0. */
#if PERL_VERSION_GE(5, 10, 0) && PERL_VERSION_LE(5, 15, 1)
#  undef XS_EXTERNAL
#  undef XS_INTERNAL
#  if defined(__CYGWIN__) && defined(USE_DYNAMIC_LOADING)
#    define XS_EXTERNAL(name) __declspec(dllexport) XSPROTO(name)
#    define XS_INTERNAL(name) STATIC XSPROTO(name)
#  endif
#  if defined(__SYMBIAN32__)
#    define XS_EXTERNAL(name) EXPORT_C XSPROTO(name)
#    define XS_INTERNAL(name) EXPORT_C STATIC XSPROTO(name)
#  endif
#  ifndef XS_EXTERNAL
#    if defined(HASATTRIBUTE_UNUSED) && !defined(__cplusplus)
#      define XS_EXTERNAL(name) void name(pTHX_ CV* cv __attribute__unused__)
#      define XS_INTERNAL(name) STATIC void name(pTHX_ CV* cv __attribute__unused__)
#    else
#      ifdef __cplusplus
#        define XS_EXTERNAL(name) extern "C" XSPROTO(name)
#        define XS_INTERNAL(name) static XSPROTO(name)
#      else
#        define XS_EXTERNAL(name) XSPROTO(name)
#        define XS_INTERNAL(name) STATIC XSPROTO(name)
#      endif
#    endif
#  endif
#endif

/* perl >= 5.10.0 && perl <= 5.15.1 */


/* The XS_EXTERNAL macro is used for functions that must not be static
 * like the boot XSUB of a module. If perl didn't have an XS_EXTERNAL
 * macro defined, the best we can do is assume XS is the same.
 * Dito for XS_INTERNAL.
 */
#ifndef XS_EXTERNAL
#  define XS_EXTERNAL(name) XS(name)
#endif
#ifndef XS_INTERNAL
#  define XS_INTERNAL(name) XS(name)
#endif

/* Now, finally, after all this mess, we want an ExtUtils::ParseXS
 * internal macro that we're free to redefine for varying linkage due
 * to the EXPORT_XSUB_SYMBOLS XS keyword. This is internal, use
 * XS_EXTERNAL(name) or XS_INTERNAL(name) in your code if you need to!
 */

#undef XS_EUPXS
#if defined(PERL_EUPXS_ALWAYS_EXPORT)
#  define XS_EUPXS(name) XS_EXTERNAL(name)
#else
   /* default to internal */
#  define XS_EUPXS(name) XS_INTERNAL(name)
#endif

#ifndef PERL_ARGS_ASSERT_CROAK_XS_USAGE
#define PERL_ARGS_ASSERT_CROAK_XS_USAGE assert(cv); assert(params)

/* prototype to pass -Wmissing-prototypes */
STATIC void
S_croak_xs_usage(pTHX_ const CV *const cv, const char *const params);

STATIC void
S_croak_xs_usage(pTHX_ const CV *const cv, const char *const params)
{
    const GV *const gv = CvGV(cv);

    PERL_ARGS_ASSERT_CROAK_XS_USAGE;

    if (gv) {
        const char *const gvname = GvNAME(gv);
        const HV *const stash = GvSTASH(gv);
        const char *const hvname = stash ? HvNAME(stash) : NULL;

        if (hvname)
            Perl_croak(aTHX_ "Usage: %s::%s(%s)", hvname, gvname, params);
        else
            Perl_croak(aTHX_ "Usage: %s(%s)", gvname, params);
    } else {
        /* Pants. I don't think that it should be possible to get here. */
        Perl_croak(aTHX_ "Usage: CODE(0x%"UVxf")(%s)", PTR2UV(cv), params);
    }
}
#undef  PERL_ARGS_ASSERT_CROAK_XS_USAGE

#ifdef PERL_IMPLICIT_CONTEXT
#define croak_xs_usage(a,b)    S_croak_xs_usage(aTHX_ a,b)
#else
#define croak_xs_usage        S_croak_xs_usage
#endif

#endif

/* NOTE: the prototype of newXSproto() is different in versions of perls,
 * so we define a portable version of newXSproto()
 */
#ifdef newXS_flags
#define newXSproto_portable(name, c_impl, file, proto) newXS_flags(name, c_impl, file, proto, 0)
#else
#define newXSproto_portable(name, c_impl, file, proto) (PL_Sv=(SV*)newXS(name, c_impl, file), sv_setpv(PL_Sv, proto), (CV*)PL_Sv)
#endif /* !defined(newXS_flags) */

#line 195 "File.c"

XS_EUPXS(XS_Sun__Solaris__Exacct__File_new); /* prototype to pass -Wmissing-prototypes */
XS_EUPXS(XS_Sun__Solaris__Exacct__File_new)
{
    dVAR; dXSARGS;
    if (items < 3)
       croak_xs_usage(cv,  "class, name, oflags, ...");
    {
	char *	class = (char *)SvPV_nolen(ST(0))
;
	char *	name = (char *)SvPV_nolen(ST(1))
;
	int	oflags = (int)SvIV(ST(2))
;
#line 68 "File.xs"
	int	i;
	/* Assume usernames are <= 32 chars (pwck(1M) assumes <= 8) */
	char	user[33];
	char	*creator = NULL;
	int	aflags   = -1;
	mode_t	mode     = 0666;
#line 217 "File.c"
	ea_file_t *	RETVAL;
#line 75 "File.xs"
	/*
	 * Account for the mandatory parameters,
	 * and the rest must be an even number.
	 */
	i = items - 3;
	if ((i % 2) != 0) {
		croak("Usage: Sun::Solaris::Exacct::File::new"
		    "(class, name, oflags, ...)");
	}

	/* Process any optional parameters. */
	for (i = 3; i < items; i += 2) {
		if (strEQ(SvPV_nolen(ST(i)), "creator")) {
			creator = SvPV_nolen(ST(i + 1));
		} else if (strEQ(SvPV_nolen(ST(i)), "aflags")) {
			aflags = SvIV(ST(i + 1));
		} else if (strEQ(SvPV_nolen(ST(i)), "mode")) {
			mode = SvIV(ST(i + 1));
		} else {
			croak("invalid named argument %s", SvPV_nolen(ST(i)));
		}
	}

	/* Check and default the creator parameter. */
	if (oflags & O_CREAT && creator == NULL) {
		uid_t		uid;
		struct passwd	*pwent;

		uid = getuid();
		if ((pwent = getpwuid(uid)) == NULL) {
			snprintf(user, sizeof (user), "%d", uid);
		} else {
			strlcpy(user, pwent->pw_name, sizeof (user));
		}
		creator = user;
	}

	/* Check and default the aflags parameter. */
	if (aflags == -1) {
		if (oflags == O_RDONLY) {
			aflags = EO_HEAD;
		} else {
			aflags = EO_TAIL;
		}
	}
	RETVAL = ea_alloc(sizeof (ea_file_t));
	PERL_ASSERT(RETVAL != NULL);
	if (ea_open(RETVAL, name, creator, aflags, oflags, mode) == -1) {
		ea_free(RETVAL, sizeof (ea_file_t));
		RETVAL = NULL;
	}
#line 271 "File.c"
	ST(0) = sv_newmortal();
	if (RETVAL == NULL) {
		sv_setsv(ST(0), &PL_sv_undef);
	} else {
		sv_setiv(newSVrv(ST(0), NULL), PTR2IV(RETVAL));
		sv_bless(ST(0), Sun_Solaris_Exacct_File_stash);
		SvREADONLY_on(SvRV(ST(0)));
	}
    }
    XSRETURN(1);
}


XS_EUPXS(XS_Sun__Solaris__Exacct__File_DESTROY); /* prototype to pass -Wmissing-prototypes */
XS_EUPXS(XS_Sun__Solaris__Exacct__File_DESTROY)
{
    dVAR; dXSARGS;
    if (items != 1)
       croak_xs_usage(cv,  "self");
    {
	ea_file_t *	self;

	{
		SV *sv = SvRV(ST(0));
		HV *stash = sv ? SvSTASH(sv) : NULL;
		if (stash == Sun_Solaris_Exacct_File_stash) {
			IV tmp = SvIV(sv);
			self = INT2PTR(ea_file_t *, tmp);
		} else {
			croak("self is not of type Sun::Solaris::Exacct::File");
		}
	}
;
#line 133 "File.xs"
	ea_close(self);
	ea_free(self, sizeof(ea_file_t));
#line 308 "File.c"
    }
    XSRETURN_EMPTY;
}


XS_EUPXS(XS_Sun__Solaris__Exacct__File_creator); /* prototype to pass -Wmissing-prototypes */
XS_EUPXS(XS_Sun__Solaris__Exacct__File_creator)
{
    dVAR; dXSARGS;
    if (items != 1)
       croak_xs_usage(cv,  "self");
    {
	ea_file_t *	self;
#line 143 "File.xs"
	const char	*creator;
#line 324 "File.c"
	SV *	RETVAL;

	{
		SV *sv = SvRV(ST(0));
		HV *stash = sv ? SvSTASH(sv) : NULL;
		if (stash == Sun_Solaris_Exacct_File_stash) {
			IV tmp = SvIV(sv);
			self = INT2PTR(ea_file_t *, tmp);
		} else {
			croak("self is not of type Sun::Solaris::Exacct::File");
		}
	}
;
#line 145 "File.xs"
	if ((creator = ea_get_creator(self)) == NULL) {
		RETVAL = &PL_sv_undef;
	} else {
		RETVAL = newSVpv(creator, 0);
	}
#line 344 "File.c"
	ST(0) = RETVAL;
	sv_2mortal(ST(0));
    }
    XSRETURN(1);
}


XS_EUPXS(XS_Sun__Solaris__Exacct__File_hostname); /* prototype to pass -Wmissing-prototypes */
XS_EUPXS(XS_Sun__Solaris__Exacct__File_hostname)
{
    dVAR; dXSARGS;
    if (items != 1)
       croak_xs_usage(cv,  "self");
    {
	ea_file_t *	self;
#line 160 "File.xs"
	const char	*hostname;
#line 362 "File.c"
	SV *	RETVAL;

	{
		SV *sv = SvRV(ST(0));
		HV *stash = sv ? SvSTASH(sv) : NULL;
		if (stash == Sun_Solaris_Exacct_File_stash) {
			IV tmp = SvIV(sv);
			self = INT2PTR(ea_file_t *, tmp);
		} else {
			croak("self is not of type Sun::Solaris::Exacct::File");
		}
	}
;
#line 162 "File.xs"
	if ((hostname = ea_get_hostname(self)) == NULL) {
		RETVAL = &PL_sv_undef;
	} else {
		RETVAL = newSVpv(hostname, 0);
	}
#line 382 "File.c"
	ST(0) = RETVAL;
	sv_2mortal(ST(0));
    }
    XSRETURN(1);
}


XS_EUPXS(XS_Sun__Solaris__Exacct__File_next); /* prototype to pass -Wmissing-prototypes */
XS_EUPXS(XS_Sun__Solaris__Exacct__File_next)
{
    dVAR; dXSARGS;
    dXSI32;
    if (items != 1)
       croak_xs_usage(cv,  "self");
    PERL_UNUSED_VAR(ax); /* -Wall */
    SP -= items;
    {
	ea_file_t *	self;
#line 181 "File.xs"
	ea_object_type_t		type;
	const char			*type_str;
	ea_object_t			object;
	SV				*sv;
	static const char *const	type_map[] =
	    { "EO_NONE", "EO_GROUP", "EO_ITEM" };
#line 408 "File.c"

	{
		SV *sv = SvRV(ST(0));
		HV *stash = sv ? SvSTASH(sv) : NULL;
		if (stash == Sun_Solaris_Exacct_File_stash) {
			IV tmp = SvIV(sv);
			self = INT2PTR(ea_file_t *, tmp);
		} else {
			croak("self is not of type Sun::Solaris::Exacct::File");
		}
	}
;
#line 188 "File.xs"
	/* Call the appropriate next/last function. */
	if (ix == 0) {
		type = ea_next_object(self, &object);
	} else {
		type = ea_previous_object(self, &object);
	}

	/* Work out the call context. */
	switch (GIMME_V) {
	case G_SCALAR:
		/* In a scalar context, just return the type. */
		EXTEND(SP, 1);
		if (type == EO_ERROR) {
			PUSHs(&PL_sv_undef);
		} else {
			sv = newSVuv(type);
			sv_setpv(sv, type_map[type]);
			SvIOK_on(sv);
			PUSHs(sv_2mortal(sv));
		}
		break;
	case G_ARRAY:
		/* In a list contect, return the type and catalog. */
		EXTEND(SP, 2);
		if (type == EO_ERROR) {
			PUSHs(&PL_sv_undef);
			PUSHs(&PL_sv_undef);
		} else {
			sv = newSVuv(type);
			sv_setpv(sv, type_map[type]);
			SvIOK_on(sv);
			PUSHs(sv_2mortal(sv));
			PUSHs(sv_2mortal(new_catalog(object.eo_catalog)));
		}
		break;
	case G_VOID:
	default:
		/* In a void context, return nothing. */
		break;
	}
#line 462 "File.c"
	PUTBACK;
	return;
    }
}


XS_EUPXS(XS_Sun__Solaris__Exacct__File_get); /* prototype to pass -Wmissing-prototypes */
XS_EUPXS(XS_Sun__Solaris__Exacct__File_get)
{
    dVAR; dXSARGS;
    if (items != 1)
       croak_xs_usage(cv,  "self");
    {
	ea_file_t *	self;
#line 236 "File.xs"
	ea_object_t	*obj;
#line 479 "File.c"
	SV *	RETVAL;

	{
		SV *sv = SvRV(ST(0));
		HV *stash = sv ? SvSTASH(sv) : NULL;
		if (stash == Sun_Solaris_Exacct_File_stash) {
			IV tmp = SvIV(sv);
			self = INT2PTR(ea_file_t *, tmp);
		} else {
			croak("self is not of type Sun::Solaris::Exacct::File");
		}
	}
;
#line 238 "File.xs"
	if ((obj = ea_get_object_tree(self, 1)) != NULL) {
		RETVAL = new_xs_ea_object(obj);
	} else {
		RETVAL = &PL_sv_undef;
	}
#line 499 "File.c"
	ST(0) = RETVAL;
	sv_2mortal(ST(0));
    }
    XSRETURN(1);
}


XS_EUPXS(XS_Sun__Solaris__Exacct__File_write); /* prototype to pass -Wmissing-prototypes */
XS_EUPXS(XS_Sun__Solaris__Exacct__File_write)
{
    dVAR; dXSARGS;
    if (items < 1)
       croak_xs_usage(cv,  "self, ...");
    {
	ea_file_t *	self;
#line 254 "File.xs"
	int		i;
	SV		*sv;
	HV		*stash;
	ea_object_t	*obj;
#line 520 "File.c"
	SV *	RETVAL;

	{
		SV *sv = SvRV(ST(0));
		HV *stash = sv ? SvSTASH(sv) : NULL;
		if (stash == Sun_Solaris_Exacct_File_stash) {
			IV tmp = SvIV(sv);
			self = INT2PTR(ea_file_t *, tmp);
		} else {
			croak("self is not of type Sun::Solaris::Exacct::File");
		}
	}
;
#line 259 "File.xs"
	for (i = 1; i < items; i++) {
		/* Check the value is either an ::Item or a ::Group. */
		sv = SvRV(ST(i));
		stash = sv ? SvSTASH(sv) : NULL;
		if (stash != Sun_Solaris_Exacct_Object_Item_stash &&
		    stash != Sun_Solaris_Exacct_Object_Group_stash) {
			XSRETURN_NO;
		}

		/* Deflate and write the object. */
		obj = deflate_xs_ea_object(ST(i));
		PERL_ASSERT(obj != NULL);
		if (ea_write_object(self, obj) == -1) {
			XSRETURN_NO;
		}
	}
	RETVAL = &PL_sv_yes;
#line 552 "File.c"
	ST(0) = RETVAL;
	sv_2mortal(ST(0));
    }
    XSRETURN(1);
}

#ifdef __cplusplus
extern "C"
#endif
XS_EXTERNAL(boot_Sun__Solaris__Exacct__File); /* prototype to pass -Wmissing-prototypes */
XS_EXTERNAL(boot_Sun__Solaris__Exacct__File)
{
    dVAR; dXSARGS;
#if (PERL_REVISION == 5 && PERL_VERSION < 9)
    char* file = __FILE__;
#else
    const char* file = __FILE__;
#endif

    PERL_UNUSED_VAR(cv); /* -W */
    PERL_UNUSED_VAR(items); /* -W */
#ifdef XS_APIVERSION_BOOTCHECK
    XS_APIVERSION_BOOTCHECK;
#endif
    XS_VERSION_BOOTCHECK;

    {
        CV * cv;

        (void)newXSproto_portable("Sun::Solaris::Exacct::File::new", XS_Sun__Solaris__Exacct__File_new, file, "$$$;@");
        (void)newXSproto_portable("Sun::Solaris::Exacct::File::DESTROY", XS_Sun__Solaris__Exacct__File_DESTROY, file, "$");
        (void)newXSproto_portable("Sun::Solaris::Exacct::File::creator", XS_Sun__Solaris__Exacct__File_creator, file, "$");
        (void)newXSproto_portable("Sun::Solaris::Exacct::File::hostname", XS_Sun__Solaris__Exacct__File_hostname, file, "$");
        cv = newXSproto_portable("Sun::Solaris::Exacct::File::next", XS_Sun__Solaris__Exacct__File_next, file, "$");
        XSANY.any_i32 = 0;
        cv = newXSproto_portable("Sun::Solaris::Exacct::File::previous", XS_Sun__Solaris__Exacct__File_next, file, "$");
        XSANY.any_i32 = 1;
        (void)newXSproto_portable("Sun::Solaris::Exacct::File::get", XS_Sun__Solaris__Exacct__File_get, file, "$");
        (void)newXSproto_portable("Sun::Solaris::Exacct::File::write", XS_Sun__Solaris__Exacct__File_write, file, "$;@");
    }

    /* Initialisation Section */

#line 50 "File.xs"
	{
	init_stashes();
	define_constants(PKGBASE "::File", constants);
	}

#line 602 "File.c"

    /* End of Initialisation Section */

#if (PERL_REVISION == 5 && PERL_VERSION >= 9)
  if (PL_unitcheckav)
       call_list(PL_scopestack_ix, PL_unitcheckav);
#endif
    XSRETURN_YES;
}
