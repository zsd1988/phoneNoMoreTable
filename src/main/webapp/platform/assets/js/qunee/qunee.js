window.Q = function (t, i, n) {
    "use strict";

    function e(t, i, n) {
        if (t.hasChildren()) {
            var s = t._f5 || t[zh]();
            if (s) {
                s = s._j8 || s;
                for (var h = 0, r = s[Hh]; r > h; h++) if (i[Yh](n, s[h]) === !1 || e(s[h], i, n) === !1) return !1;
                return !0
            }
        }
    }
    function s(t) {
        if (!t[Uh]()) return t instanceof RY ? t : null;
        for (var i, n = t._f5._j8, e = n[Hh] - 1; e >= 0;) {
            if (i = n[e], i = s(i)) return i;
            e--
        }
        return null
    }
    function h(t, i, n, e) {
        return e ? r(t, i, n) : a(t, i, n)
    }
    function r(t, i, n) {
        t = t._j8 || t;
        for (var e, s = 0, h = t.length; h > s; s++) if (e = t[s], e[Uh]() && !r(e.children, i, n) || i[Yh](n, e) === !1) return !1;
        return !0
    }
    function a(t, i, n) {
        t = t._j8 || t;
        for (var e, s = 0, h = t.length; h > s; s++) if (e = t[s], i[Yh](n, e) === !1 || e.hasChildren() && !a(e[Wh], i, n)) return !1;
        return !0
    }
    function o(t, i, n, e) {
        return e ? f(t, i, n) : u(t, i, n)
    }
    function f(t, i, n) {
        t = t._j8 || t;
        for (var e, s = t[Hh], h = s - 1; h >= 0; h--) if (e = t[h], e[Uh]() && !f(e.children, i, n) || i[Yh](n, e) === !1) return !1;
        return !0
    }
    function u(t, i, n) {
        t = t._j8 || t;
        for (var e, s = t[Hh], h = s - 1; h >= 0; h--) if (e = t[h], i[Yh](n, e) === !1 || e[Uh]() && !u(e[Wh], i, n)) return !1;
        return !0
    }
    function c(t, i, n) {
        for (var e, s = (t._j8 || t)[Vh](0); s[Hh];) {
            e = s[0],
            s = s.splice(1);
            var h = i[Yh](n, e);
            if (h === !1) return !1;
            if (e.hasChildren()) {
                var r = e.children;
                r = r._j8 || r,
                s = s.concat(r)
            }
        }
        return !0
    }
    function _(t, i, n) {
        for (var e, s = (t._j8 || t)[Vh](0); s[Hh];) {
            e = s[s[Hh] - 1],
            s = s[Xh](0, s[Hh] - 1);
            var h = i.call(n, e);
            if (h === !1) return !1;
            if (e.hasChildren()) {
                var r = e[Wh];
                r = r._j8 || r,
                s = s[Kh](r)
            }
        }
        return !0
    }
    function d(t, i) {
        function n(t, n) {
            for (var e = t[Hh], s = n[Hh], h = e + s, r = new Array(h), a = 0, o = 0, f = 0; h > f;) r[f++] = a === e ? n[o++] : o === s || i(t[a], n[o]) <= 0 ? t[a++] : n[o++];
            return r
        }
        function e(t) {
            var i = t[Hh],
                s = Math[Zh](i / 2);
            return 1 >= i ? t : n(e(t[Vh](0, s)), e(t[Vh](s)))
        }
        return e(t)
    }
    function l(t, i, n, e) {
        t instanceof dG && (t = t._j8);
        for (var s = 0, h = (t._j8 || t).length; h > s; s++) {
            var r = i.call(n, t[s], s, e);
            if (r === !1) return !1
        }
        return !0
    }
    function v(t, i, n) {
        for (var e = t instanceof dG, s = t._j8 || t, h = 0, r = s[Hh]; r > h; h++) {
            var a = s[h];
            i.call(n, a) && (e ? t[Jh](a) : t.splice(h, 1), h--, r--)
        }
    }
    function b(t, i, n, e) {
        t instanceof dG && (t = t._j8);
        for (var s = (t._j8 || t)[Hh] - 1; s >= 0; s--) {
            var h = i.call(n, t[s], s, e);
            if (h === !1) return !1
        }
        return !0
    }
    function g(t) {
        if (t[Qh] instanceof Function) return t[Qh](!0);
        var i, n = [];
        return l(t, function (t) {
            i = t && t[Qh] instanceof Function ? t[Qh]() : t,
            n[tr](i)
        }, this),
        n
    }
    function y(t, i, e) {
        e === n || 0 > e ? t[tr](i) : t[Xh](e, 0, i)
    }
    function m(t, i) {
        var n = t.indexOf(i);
        return 0 > n || n >= t[Hh] ? !1 : t[Xh](n, 1)
    }
    function E(t, i) {
        var n = !1;
        return l(t, function (t) {
            return i == t ? (n = !0, !1) : void 0
        }),
        n
    }
    function x(t, i) {
        var n = t;
        for (var e in i) if (i.__lookupGetter__) {
            var s = i.__lookupGetter__(e),
                h = i.__lookupSetter__(e);
            s || h ? (s && n.__defineGetter__(e, s), h && n.__defineSetter__(e, h)) : n[e] = i[e]
        } else n[e] = i[e];
        return n
    }
    function p(t, i, n) {
        if (!(t instanceof Function)) throw new Error("subclass must be type of Function");
        var e = null;
        ir == typeof i && (e = i, i = t, t = function () {
            i[nr](this, arguments)
        });
        var s = t[er],
            h = function () {};
        return h.prototype = i[er],
        t[er] = new h,
        t.superclass = i[er],
        t[sr].constructor = i,
        x(t[er], s),
        e && x(t.prototype, e),
        n && x(t.prototype, n),
        t[er][hr] = t,
        t
    }
    function w(t, i, n) {
        return T(t, i, "constructor", n)
    }
    function T(t, i, n, e) {
        var s = i[sr];
        if (s) {
            var h = s[n];
            return h ? h.apply(t, e) : void 0
        }
    }
    function k(t, i, n, e) {
        if ("constructor" == n) return O(t, i, e);
        if (i[rr] instanceof Function) {
            var s = i[rr].prototype[n];
            return s instanceof Function ? s[nr](t, e) : void 0
        }
    }
    function O(t, i, n) {
        return i[rr] instanceof Function ? i.super_.apply(t, n) : void 0
    }
    function j(t, i) {
        return t[rr] = i,
        t[er] = Object.create(i[er], {
            super_: {
                value: i,
                enumerable: !1
            },
            constructor: {
                value: t,
                enumerable: !1
            }
        }),
        t
    }
    function M(t, i, n) {
        if (!(t instanceof Function) && t instanceof Object) {
            i = t.super;
            var e;
            return t.hasOwnProperty("constructor") ? (e = t.constructor, delete t.constructor) : e = i ?
            function () {
                i[nr](this, arguments)
            } : function () {},
            M(e, i, t)
        }
        if (i && !(i instanceof Function) && i instanceof Object) return M(t, i.super, i);
        if (i && j(t, i), n) {
            var s = t[er];
            for (var h in n) s[h] = n[h]
        }
        return t
    }
    function S(t, i, e, s, h) {
        if (s) return void Object.defineProperty(t, i, {
            value: e,
            enumerable: !0
        });
        var r = {
            configurable: !0,
            enumerable: !0
        },
            a = ar + i;
        e !== n && (t[a] = e),
        r.get = function () {
                return this[a]
            },
        r.set = function (t) {
                var n = this[a];
                if (n == t) return !1;
                var e = new LG(this, i, t, n);
                return this.beforeEvent(e) ? (this[a] = t, h && h[Yh](this, t, n), this[or](e), !0) : !1
            },
        Object[fr](t, i, r)
    }
    function I(t, i) {
        for (var n = 0, e = i[Hh]; e > n; n++) {
            var s = i[n];
            S(t, s[ur] || s, s.defaultValue || s[cr], s[_r], s[dr])
        }
    }
    function A(t, i, n) {
        return i instanceof Object ? t = t[lr](i) : i && !n && (n = parseInt(i)),
        i && !n && (n = parseInt(i)),
        n ? setTimeout(t, n) : setTimeout(t)
    }
    function L(i, n) {
        return n && (i = i[lr](n)),
        t[vr](i)
    }
    function C(t, i) {
        return t.className = i,
        t
    }
    function D(t, i) {
        if (!t.hasOwnProperty(br)) {
            var n = t[gr](hr);
            if (!n) return C(t, i);
            for (var e = n.split(yr), s = 0, h = e[Hh]; h > s; s++) if (e[s] == i) return;
            return n += yr + i,
            C(t, n)
        }
        t[br].add(i)
    }
    function R(t, i) {
        if (!t.hasOwnProperty(br)) {
            var n = t[gr](hr);
            if (!n || !n.indexOf(i)) return;
            for (var e = "", s = n[mr](yr), h = 0, r = s.length; r > h; h++) s[h] != i && (e += s[h] + yr);
            return C(t, e)
        }
        t[br][Jh](i)
    }
    function P(t) {
        return !isNaN(t) && t instanceof Number || Er == typeof t
    }
    function N(t) {
        return t !== n && (t instanceof String || xr == typeof t)
    }
    function B(t) {
        return t !== n && (t instanceof Boolean || pr == typeof t)
    }
    function $(t) {
        return Array[wr](t)
    }
    function F(i) {
        i || (i = t[Tr]),
        i.preventDefault ? i.preventDefault() : i[kr] = !1
    }
    function q(i) {
        i || (i = t[Tr]),
        i[Or] ? i[Or]() : i[jr] || (i[jr] = !0)
    }
    function G(t) {
        F(t),
        q(t)
    }
    function z(t) {
        return Math[Mr](Math[Sr]() * t)
    }
    function H() {
        return Math[Sr]() >= .5
    }
    function Y(t) {
        var i = !0;
        for (var n in t) {
            i = !1;
            break
        }
        return i
    }
    function U(t) {
        if (t && t > 0 && 1 > t) {
            var i = Math.floor(16777215 * Math[Sr]());
            return Ir + (i >> 16 & 255) + Ar + (i >> 8 & 255) + Ar + (255 & i) + Ar + t.toFixed(2) + Lr
        }
        return X(Math[Mr](16777215 * Math.random()))
    }
    function W(t) {
        return t > 0 ? Math.floor(t) : Math.ceil(t)
    }
    function V(t) {
        return t > 0 ? Math.ceil(t) : Math.floor(t)
    }
    function X(t) {
        return 16777216 > t ? Cr + (Dr + t.toString(16))[Vh](-6) : Ir + (t >> 16 & 255) + Ar + (t >> 8 & 255) + Ar + (255 & t) + Ar + ((t >> 24 & 255) / 255)[Rr](2) + Lr
    }
    function K(t, i, n) {
        ir != typeof n || n.hasOwnProperty(Pr) || (n.enumerable = !0),
        Object[fr](t, i, n)
    }
    function Z(t, i) {
        for (var n in i) if (Nr != n[0]) {
            var e = i[n];
            ir != typeof e || e.hasOwnProperty(Pr) || (e[Pr] = !0)
        }
        Object[Br](t, i)
    }
    function J(i, n) {
        n || (n = t);
        for (var e = i[mr]($r), s = 0, h = e[Hh]; h > s; s++) {
            var r = e[s];
            n = n[r]
        }
        return n
    }
    function Q(t) {
        return t instanceof MouseEvent || t instanceof Object && t[Fr] !== n
    }
    function ti(i) {
        t.console && console.log(i)
    }
    function ii(i) {
        t[qr] && console.trace(i)
    }
    function ni(i) {
        t[qr] && console[Gr](i)
    }
    function ei(t, i, n) {
        var e, s, h;
        0 == t._mq ? (e = -1, h = 0, s = i) : 0 == t._mu ? (e = 0, h = 1, s = n) : (e = -1 / t._mq, s = (t._mq - e) * i + t._ms, h = 1);
        var r = new yG;
        return r._mq = e,
        r._ms = s,
        r._mu = h,
        r._mm = i,
        r._mk = n,
        r._kd = Math[zr](e, h),
        r._muos = Math.cos(r._kd),
        r[Hr] = Math.sin(r._kd),
        r
    }
    function si(t, i, n, e, s) {
        var h, r;
        i > e ? h = -1 : e > i && (h = 1),
        n > s ? r = -1 : s > n && (r = 1);
        var a, o;
        if (!h) return o = 0 > r ? t.y : t[Yr],
        {
            x: i,
            y: o
        };
        if (!r) return a = 0 > h ? t.x : t.right,
        {
            x: a,
            y: n
        };
        var f = (n - s) / (i - e),
            u = n - f * i,
            c = 0 > h ? i - t.x : i - t.right,
            _ = 0 > r ? n - t.y : n - t.bottom;
        return Math.abs(f) >= Math.abs(_ / c) ? (o = 0 > r ? t.y : t[Yr], a = (o - u) / f) : (a = 0 > h ? t.x : t[Ur], o = f * a + u),
        {
                x: a,
                y: o
            }
    }
    function hi(t, i, n, e, s, h, r, a) {
        return 0 >= r || 0 >= a || 0 >= n || 0 >= e ? !1 : (r += s, a += h, n += t, e += i, (s > r || r > t) && (h > a || a > i) && (t > n || n > s) && (i > e || e > h))
    }
    function ri(t, i, n, e, s, h) {
        return s >= t && t + n >= s && h >= i && i + e >= h
    }
    function ai(t, i, n, e, s, h, r, a, o) {
        return o && (t -= o, i -= o, n += o + o, e += o + o),
        s >= t && h >= i && t + n >= s + r && i + e >= h + a
    }
    function oi(t, i, n, e, s, h, r, a) {
        var o = t;
        o += n;
        var f = i;
        f += e;
        var u = s;
        u += r;
        var c = h;
        return c += a,
        s > t && (t = s),
        h > i && (i = h),
        o > u && (o = u),
        f > c && (f = c),
        o -= t,
        f -= i,
        0 > o || 0 > f ? null : new EG(t, i, o, f)
    }
    function fi(t, i, e) {
        if (N(t) && (t = pG[Wr](t)), !t) return {
            x: 0,
            y: 0
        };
        if (t.x !== n) return {
            x: t.x,
            y: t.y
        };
        var s, h, r = t[Vr],
            a = t[Xr];
        switch (r) {
            case wG:
                s = 0;
                break;
            case kG:
                s = i;
                break;
            default:
                s = i / 2
            }
        switch (a) {
            case OG:
                h = 0;
                break;
            case MG:
                h = e;
                break;
            default:
                h = e / 2
            }
        return {
                x: s,
                y: h
            }
    }
    function ui(t, i, n) {
        t[Wh].add(i, n),
        t.onChildAdd(i, n)
    }
    function ci(t, i) {
        t._f5 && (t._f5[Jh](i), t[Kr](i))
    }
    function _i(t) {
        return t[Zr](/^-ms-/, Jr)[Zr](/-([\da-z])/gi, function (t, i) {
            return i[Qr]()
        })
    }
    function di(t) {
        return t[Zr](/[A-Z]/g, function (t) {
            return ta + t.toLowerCase()
        })[Zr](/^ms-/, ia)
    }
    function li(t, i) {
        var n = t[na];
        if (!n) return !1;
        var e, s;
        for (e in i) i.hasOwnProperty(e) && (s = VG(e)) && (n[s] = i[e]);
        return t
    }
    function vi(t) {
        var i, n, e = "";
        for (i in t) t.hasOwnProperty(i) && (n = VG(i)) && (e += di(n) + ea + t[i] + sa);
        return e ? e.substring(0, e[Hh] - 1) : e
    }
    function bi(t, i, n) {
        (i = VG(i)) && (t[na][i] = n)
    }
    function gi(t, i) {
        return UG ? (i && !N(i) && (i = vi(i)), UG[ha] ? void UG.insertRule(t + ra + i + aa, 0) : void(UG[oa] && UG[oa](t, i, 0))) : !1
    }
    function yi(i, n) {
        i[Fr] && (i = i.changedTouches && i[fa][Hh] ? i.changedTouches[0] : i[Fr][0]);
        var e = n[ua](),
            s = i[ca] || 0,
            h = i[_a] || 0;
        return uG && hG && (t[da] && s == i[la] && (s -= t[da]), t[va] && h == i[ba] && (h -= t.pageYOffset)),
        {
                x: s - e.left,
                y: h - e.top
            }
    }
    function mi(t, i, n) {
        this._lq = t,
        this[ga] = n,
        this._handler = i,
        this[ya] = new xi,
        this._install()
    }
    function Ei(t) {
        return rG && t[ma] || !rG && t[Ea]
    }
    function xi() {
        this[xa] = []
    }
    function pi(t, i, n, e, s) {
        Ti(t, function (e) {
            if (i) {
                var s = e.responseXML;
                if (!s) return void(n || Oz)(pa + t + wa);
                i(s)
            }
        }, n, e, s)
    }
    function wi(t, i, n, e, s) {
        Ti(t, function (e) {
            if (i) {
                var s, h = e[Ta];
                if (!h) return (n || Oz)(pa + t + ka),
                s = new Error(pa + t + ka),
                i(h, s);
                try {
                    h = JSON.parse(h)
                } catch (r) {
                    (n || Oz)(r),
                    s = r
                }
                i(h, s)
            }
        }, n, e, s)
    }
    function Ti(t, i, n, e, s) {
        (n === !1 || e === !1) && (s = !1);
        try {
            var h = new XMLHttpRequest,
                r = encodeURI(t);
            if (s !== !1) {
                    var a;
                    a = r.indexOf(Oa) > 0 ? "&" : Oa,
                    r += a + ja + Date.now()
                }
            h.open(Ma, r),
            h[Sa] = function () {
                    return 4 == h[Ia] ? h[Aa] && 200 != h[Aa] ? void(n || Oz)(pa + t + La) : void(i && i(h)) : void 0
                },
            h.send(e)
        } catch (o) {
            (n || Oz)(pa + t + La, o)
        }
    }
    function hi(t, i, n, e, s, h, r, a) {
        return 0 >= r || 0 >= a || 0 >= n || 0 >= e ? !1 : (r += s, a += h, n += t, e += i, (s > r || r > t) && (h > a || a > i) && (t > n || n > s) && (i > e || e > h))
    }
    function ai(t, i, n, e, s, h, r, a) {
        return s >= t && h >= i && t + n >= s + r && i + e >= h + a
    }
    function ki(t, i, n) {
        return t instanceof Object && t.x ? ji(t, i, 0, 0) : Oi(t, i, n, 0, 0)
    }
    function Oi(t, i, n, e, s) {
        var h = Math.sin(n),
            r = Math.cos(n),
            a = t - e,
            o = i - s;
        return t = a * r - o * h + e,
        i = a * h + o * r + s,
        new bG(t, i, n)
    }
    function ji(t, i, n, e) {
        n = n || 0,
        e = e || 0;
        var s = Math.sin(i),
            h = Math.cos(i),
            r = t.x - n,
            a = t.y - e;
        return t.x = r * h - a * s + n,
        t.y = r * s + a * h + e,
        t
    }
    function Mi(t, i, n) {
        return Si(t, i, n, 0, 0)
    }
    function Si(t, i, n, e, s) {
        var h = Oi(t.x, t.y, i, e, s),
            r = Oi(t.x + t.width, t.y, i, e, s),
            a = Oi(t.x + t[Ca], t.y + t.height, i, e, s),
            o = Oi(t.x, t.y + t[Da], i, e, s);
        return n ? n[Ra]() : n = new EG,
        n.addPoint(h),
        n[Pa](r),
        n[Pa](a),
        n.addPoint(o),
        n
    }
    function Ii(t, i) {
        var n = this[Na] || 1;
        this[na].width = t + Ba,
        this.style[Da] = i + Ba,
        this[Ca] = t * n,
        this.height = i * n
    }
    function Ai(t) {
        var i = t.webkitBackingStorePixelRatio || t.mozBackingStorePixelRatio || t[$a] || t.oBackingStorePixelRatio || t[Fa] || 1;
        return Sz / i
    }
    function Li(t, n, e) {
        var s = i[qa](Ga);
        if (s.g = s.getContext(za), t !== !0 && !e) return t && n && (s.width = t, s[Da] = n),
        s;
        var h = s.g;
        return h[Na] = s.ratio = Ai(h),
        s.setSize = Ii,
        h._kh = function () {
            this[Ga].width = this[Ga][Ca]
        },
        t && n && s[Ha](t, n),
        s
    }
    function Ci(t, i) {
        return Iz || (Iz = Li()),
        t && i && (Iz[Ca] = t, Iz[Da] = i),
        Iz.g
    }
    function Di(t, i, n) {
        return (n || _G.FONT_STYLE) + yr + (i || _G[Ya]) + Ua + (t || _G[Wa])
    }
    function Ri(t, i, n, e, s, h, r, a, o, f) {
        if (t[Va](), t.translate(n, e), Az && Lz > r) {
            var u = r / Lz;
            t.scale(u, u),
            r = Lz,
            f = null
        }
        o || (o = _G[Xa]),
        r || (r = _G.FONT_SIZE),
        o *= r,
        t.font = f || Di(h, r, a),
        t[Ka] = s,
        t.textBaseline = Za;
        for (var c = o / 2, _ = i.split(Ja), d = 0, l = _[Hh]; l > d; d++) {
            var v = _[d];
            t[Qa](v, 0, c),
            c += o
        }
        t[to]()
    }
    function Pi(t, i, n, e, s, h) {
        if (!t) return {
            width: 0,
            height: 0
        };
        if (i || (i = _G[Ya]), Az && Lz > i) {
            var r = i / Lz,
                a = Pi(t, Lz, n, e, s);
            return a.width *= r,
            a[Da] *= r,
            a
        }
        var o = Ci();
        o.font = h || Di(n, i, e),
        s || (s = _G[Xa]);
        for (var f = i * s, u = 0, c = 0, _ = t[mr](Ja), d = 0, l = _.length; l > d; d++) {
            var v = _[d];
            u = Math.max(o[io](v)[Ca], u),
            c += f
        }
        return {
            width: u,
            height: c
        }
    }
    function Ni(t, i, n, e, s) {
        var h;
        if (Jq) try {
            h = t[no](i, n, e, s)
        } catch (r) {} else h = t.getImageData(i, n, e, s);
        return h
    }
    function Bi(t) {
        return Math.log(t + Math[eo](t * t + 1))
    }
    function $i(t, i) {
        i = i || t(1);
        var n = 1 / i,
            e = .5 * n,
            s = Math.min(1, i / 100);
        return function (h) {
                if (0 >= h) return 0;
                if (h >= i) return 1;
                for (var r = h * n, a = 0; a++ < 10;) {
                    var o = t(r),
                        f = h - o;
                    if (Math.abs(f) <= s) return r;
                    r += f * e
                }
                return r
            }
    }
    function Fi(t, i, n) {
        var e = 1 - t,
            s = e * e * i[0] + 2 * e * t * i[2] + t * t * i[4],
            h = e * e * i[1] + 2 * e * t * i[3] + t * t * i[5];
        if (n) {
                var r = (i[0] + i[4] - 2 * i[2]) * t + i[2] - i[0],
                    a = (i[1] + i[5] - 2 * i[3]) * t + i[3] - i[1];
                return {
                        x: s,
                        y: h,
                        rotate: Math.atan2(a, r)
                    }
            }
        return {
                t: t,
                x: s,
                y: h
            }
    }
    function qi(t, i, n) {
        var e = t - 2 * i + n;
        return 0 != e ? (t - i) / e : -1
    }
    function Gi(t, i) {
        i.add(t[4], t[5]);
        var n = qi(t[0], t[2], t[4]);
        if (n > 0 && 1 > n) {
            var e = Fi(n, t);
            i.add(e.x, e.y)
        }
        var s = qi(t[1], t[3], t[5]);
        if (s > 0 && 1 > s) {
            var e = Fi(s, t);
            i.add(e.x, e.y)
        }
        return i
    }
    function zi(t, i) {
        return Math.abs(t - i) < 1e-7
    }
    function Hi(t) {
        if (zi(t[1], t[3]) && (zi(t[0], t[2]) || zi(t[1], t[5]))) {
            var i = t[0],
                n = t[1],
                e = t[4],
                s = t[5],
                h = Math[eo](Cz(i, n, e, s));
            return function (t) {
                    return h * t
                }
        }
        var r = t[0],
            a = t[2],
            o = t[4],
            f = r - 2 * a + o,
            u = 2 * a - 2 * r;
        r = t[1],
        a = t[3],
        o = t[5];
        var c = r - 2 * a + o,
            _ = 2 * a - 2 * r,
            d = 4 * (f * f + c * c),
            l = 4 * (f * u + c * _),
            v = u * u + _ * _,
            h = 4 * d * v - l * l,
            b = 1 / h,
            g = .125 * Math.pow(d, -1.5),
            y = 2 * Math[eo](d),
            m = (h * Bi(l / Math.sqrt(h)) + 2 * Math[eo](d) * l * Math.sqrt(v)) * g;
        return function (t) {
                var i = l + 2 * t * d,
                    n = i / Math[eo](h),
                    e = i * i * b;
                return (h * Math.log(n + Math[eo](e + 1)) + y * i * Math[eo](v + t * l + t * t * d)) * g - m
            }
    }
    function Yi(t, i, n) {
        var e = 1 - t,
            s = i[0],
            h = i[2],
            r = i[4],
            a = i[6],
            o = s * e * e * e + 3 * h * t * e * e + 3 * r * t * t * e + a * t * t * t;
        if (n) var f = 3 * t * t * a + (6 * t - 9 * t * t) * r + (9 * t * t - 12 * t + 3) * h + (-3 * t * t + 6 * t - 3) * s;
        s = i[1],
        h = i[3],
        r = i[5],
        a = i[7];
        var u = s * e * e * e + 3 * h * t * e * e + 3 * r * t * t * e + a * t * t * t;
        if (n) {
                var c = 3 * t * t * a + (6 * t - 9 * t * t) * r + (9 * t * t - 12 * t + 3) * h + (-3 * t * t + 6 * t - 3) * s;
                return {
                    x: o,
                    y: u,
                    rotate: Math.atan2(c, f)
                }
            }
        return {
                x: o,
                y: u
            }
    }
    function Ui(t, i, n, e) {
        var s = -t + 3 * i - 3 * n + e;
        if (0 == s) return [(t - i) / (2 * n - 4 * i + 2 * t)];
        var h = 2 * t - 4 * i + 2 * n,
            r = i - t,
            a = h * h - 4 * s * r;
        return 0 > a ? void 0 : 0 == a ? [-h / (2 * s)] : (a = Math.sqrt(a), [(a - h) / (2 * s), (-a - h) / (2 * s)])
    }
    function Wi(t, i) {
        i.add(t[6], t[7]);
        var n = Ui(t[0], t[2], t[4], t[6]);
        if (n) for (var e = 0; e < n[Hh]; e++) {
            var s = n[e];
            if (!(0 >= s || s >= 1)) {
                var h = Yi(s, t);
                i.add(h.x, h.y)
            }
        }
        if (n = Ui(t[1], t[3], t[5], t[7])) for (var e = 0; e < n[Hh]; e++) {
            var s = n[e];
            if (!(0 >= s || s >= 1)) {
                var h = Yi(s, t);
                i.add(h.x, h.y)
            }
        }
    }
    function Vi(t) {
        var i = {
            x: t[0],
            y: t[1]
        },
            n = {
                x: t[2],
                y: t[3]
            },
            e = {
                x: t[4],
                y: t[5]
            },
            s = {
                x: t[6],
                y: t[7]
            },
            h = i.x - 0,
            r = i.y - 0,
            a = n.x - 0,
            o = n.y - 0,
            f = e.x - 0,
            u = e.y - 0,
            c = s.x - 0,
            _ = s.y - 0,
            d = 3 * (-h + 3 * a - 3 * f + c),
            l = 6 * (h - 2 * a + f),
            v = 3 * (-h + a),
            b = 3 * (-r + 3 * o - 3 * u + _),
            g = 6 * (r - 2 * o + u),
            y = 3 * (-r + o),
            m = function (t) {
                var i = d * t * t + l * t + v,
                    n = b * t * t + g * t + y;
                return Math[eo](i * i + n * n)
            },
            E = (m(0) + 4 * m(.5) + m(1)) / 6;
        return E
    }
    function Xi(t, i) {
        function n(t, i, n, e) {
            var s = -t + 3 * i - 3 * n + e,
                h = 2 * t - 4 * i + 2 * n,
                r = i - t;
            return function (t) {
                    return 3 * (s * t * t + h * t + r)
                }
        }
        function e(t, i) {
            var n = s(t),
                e = h(t);
            return Math[eo](n * n + e * e) * i
        }
        var s = n(t[0], t[2], t[4], t[6]),
            h = n(t[1], t[3], t[5], t[7]);
        i = i || 100;
        var r = 1 / i;
        return function (t) {
                if (!t) return 0;
                for (var i, n = 0, s = 0;;) {
                    if (i = n + r, i >= t) return s += e(n, i - n);
                    s += e(n, r),
                    n = i
                }
            }
    }
    function Ki(t, i, n) {
        return Cz(i, n, t.cx, t.cy) <= t._squareR + Dz
    }
    function Zi(t, i, n, e) {
        return n = n || Ji(t, i),
        new Qi((t.x + i.x) / 2, (t.y + i.y) / 2, n / 2, t, i, null, e)
    }
    function Ji(t, i) {
        return gG(t.x, t.y, i.x, i.y)
    }
    function Qi(t, i, n, e, s, h, r) {
        this.cx = t,
        this.cy = i,
        this.r = n,
        this._squareR = n * n,
        this.p1 = e,
        this.p2 = s,
        this.p3 = h,
        this[so] = r
    }
    function tn(t, i, n, e) {
        this.cx = t,
        this.cy = i,
        this[Ca] = n,
        this[Da] = e
    }
    function nn(t) {
        var i = t[0],
            n = t[1],
            e = t[2],
            s = Qi[ho](i, n, e);
        return sn(t, i, n, e, s)
    }
    function en(t, i) {
        i = i || hn(t);
        for (var n, e = i.width / i[Da], s = [], h = t[Hh], r = 0; h > r; r++) n = t[r],
        s[tr]({
            x: n.x,
            y: n.y * e
        });
        var a = nn(s);
        return a ? new tn(a.cx, a.cy / e, 2 * a.r, 2 * a.r / e) : void 0
    }
    function sn(t, i, n, e, s) {
        for (var h, r, a = t[Hh], o = s[ro], f = 0; a > f; f++) if (h = t[f], h != i && h != n && h != e) {
            var u = Cz(s.cx, s.cy, h.x, h.y);
            u - Dz > o && (o = u, r = h)
        }
        if (!r) return s;
        var c, _ = Qi[ho](r, i, n),
            d = Qi[ho](r, i, e),
            l = Qi._j4Circle(r, e, n);
        return Ki(_, e.x, e.y) && (c = _),
        Ki(d, n.x, n.y) && (!c || c.r > d.r) && (c = d),
        Ki(l, i.x, i.y) && (!c || c.r > l.r) && (c = l),
        i = c.p1,
        n = c.p2,
        e = c.p3 || c[so],
        sn(t, i, n, e, c)
    }
    function hn(t) {
        for (var i, n = t.length, e = new EG, s = 0; n > s; s++) i = t[s],
        e.add(i.x, i.y);
        return e
    }
    function rn(t, i, n, e, s) {
        this._5s && this[ao]();
        var h = s ? this[oo](s) : this[fo],
            r = n / h[Ca],
            a = t - r * h.x,
            o = e / h[Da],
            f = i - o * h.y,
            u = this._f8,
            c = [];
        return l(u, function (t) {
                var i = t.clone(),
                    n = i[xa];
                if (n && n[Hh]) {
                        for (var e = n[Hh], s = [], h = 0; e > h; h++) {
                            var u = n[h];
                            h++;
                            var _ = n[h];
                            u = r * u + a,
                            _ = o * _ + f,
                            s[tr](u),
                            s[tr](_)
                        }
                        i.points = s
                    }
                c.push(i)
            }, this),
        new cH(c)
    }
    function an(t, i, n, e, s, h) {
        if (s = s || 0, n = n || 0, !s && !h) return !1;
        if (!e) {
            var r = this[oo](s);
            if (!r.intersectsPoint(t, i, n)) return !1
        }
        var a = Math[uo](2 * n) || 1,
            o = Ci(a, a),
            f = (o[Ga], -t + n),
            u = -i + n;
        if (o[co](1, 0, 0, 1, f, u), !o[_o]) {
                this._l5(o),
                s && o[lo](),
                h && o.fill();
                var c = Ni(o, 0, 0, a, a);
                if (!c) return !1;
                c = c[vo];
                for (var _ = c[Hh] / 4; _ > 0;) {
                    if (c[4 * _ - 1] > uH) return !0;
                    --_
                }
                return !1
            }
        return o[bo] = (s || 0) + 2 * n,
        this._l5(o),
        s && o[_o](n, n) ? !0 : h ? o.isPointInPath(n, n) : !1
    }
    function on(t, i, n) {
        if (!this._il) return null;
        var e = this._f8;
        if (e.length < 2) return null;
        n === !1 && (t += this._il);
        var s = e[0];
        if (0 >= t) return Gs(s[xa][0], s.points[1], e[1][xa][0], e[1][xa][1], t, i);
        if (t >= this._il) {
            s = e[e[Hh] - 1];
            var h, r, a = s.points,
                o = a[Hh],
                f = a[o - 2],
                u = a[o - 1];
            if (o >= 4) h = a[o - 4],
            r = a[o - 3];
            else {
                    s = e[e.length - 2];
                    var c = s[go];
                    h = c.x,
                    r = c.y
                }
            return Gs(f, u, f + f - h, u + u - r, t - this._il, i)
        }
        for (var _, d = 0, l = 1, o = e[Hh]; o > l; l++) if (_ = e[l], _._il) {
            if (!(d + _._il < t)) {
                var v, c = s[go];
                if (_[yo] == aH) {
                    var b = _[xa];
                    v = fn(t - d, _, c.x, c.y, b[0], b[1], b[2], b[3], _._r)
                } else {
                    if (!_._lf) return Gs(c.x, c.y, _[xa][0], _[xa][1], t - d, i);
                    var g = $i(_._lf, _._il)(t - d),
                        b = _[xa];
                    v = _[yo] == rH && 6 == b[Hh] ? Yi(g, [c.x, c.y].concat(b), !0) : Fi(g, [c.x, c.y][Kh](b), !0)
                }
                return i && (v.x -= i * Math.sin(v[mo] || 0), v.y += i * Math.cos(v[mo] || 0)),
                v
            }
            d += _._il,
            s = _
        } else s = _
    }
    function fn(t, i, n, e, s, h, r, a) {
        if (t <= i._l1) return Gs(n, e, s, h, t, t);
        if (t >= i._il) return t -= i._il,
        Gs(i._p2x, i[Eo], r, a, t, t);
        if (t -= i._l1, i._o) {
            var o = t / i._r;
            i[xo] && (o = -o);
            var f = Oi(i[po], i[wo], o, i._o.x, i._o.y);
            return f[mo] += i[To] || 0,
            f[mo] += Math.PI,
            f
        }
        return Gs(i[po], i[wo], i[ko], i._p2y, t, t)
    }
    function ei(t, i, n) {
        var e, s, h;
        0 == t._mq ? (e = -1, h = 0, s = i) : 0 == t._mu ? (e = 0, h = 1, s = n) : (e = -1 / t._mq, s = (t._mq - e) * i + t._ms, h = 1);
        var r = new yG;
        return r._mq = e,
        r._ms = s,
        r._mu = h,
        r._mm = i,
        r._mk = n,
        r
    }
    function un(t) {
        return t %= 2 * Math.PI,
        0 > t && (t += 2 * Math.PI),
        t
    }
    function cn(t, i, n, e, s, h, r, a) {
        var o = gG(i, n, e, s),
            f = gG(e, s, h, r);
        if (!o || !f) return t._d = 0,
        t._r = 0,
        t._l1 = o,
        t._l2 = f,
        t._il = 0;
        var u = dn(e, s, i, n),
            c = dn(e, s, h, r);
        t[To] = u,
        t[Oo] = c;
        var _ = u - c;
        _ = un(_),
        _ > Math.PI && (_ = 2 * Math.PI - _, t[xo] = !0);
        var d = Math.PI - _,
            l = Math.tan(_ / 2),
            v = a / l,
            b = Math.min(o, f);
        v > b && (v = b, a = l * v);
        var g, y = e + Math.cos(u) * v,
            m = s + Math.sin(u) * v,
            E = e + Math.cos(c) * v,
            x = s + Math.sin(c) * v,
            p = new yG(i, n, e, s),
            w = new yG(e, s, h, r),
            T = ei(p, y, m),
            k = ei(w, E, x),
            O = T._3f(k),
            j = Math[zr](m - O.y, y - O.x),
            M = Math.atan2(x - O.y, E - O.x);
        g = t[xo] ? M : j;
        for (var S, I = 0; 4 > I;) {
                var A = I * lG;
                if (un(A - g) <= d) {
                    var L, C;
                    if (S ? S++ : S = 1, 0 == I ? (L = O.x + a, C = O.y) : 1 == I ? (L = O.x, C = O.y + a) : 2 == I ? (L = O.x - a, C = O.y) : (L = O.x, C = O.y - a), t[jo + S] = {
                        x: L,
                        y: C
                    }, 2 == S) break
                }
                I++
            }
        return t[po] = y,
        t._p1y = m,
        t[ko] = E,
        t[Eo] = x,
        t._o = O,
        t._d = v,
        t._r = a,
        t._l1 = o - v,
        t._l2 = f - v,
        t._il = t._l1 + d * a
    }
    function _n(t, i, n, e, s, h, r) {
        var a = dn(n, e, t, i),
            o = dn(n, e, s, h),
            f = a - o;
        return r ? f : (0 > f && (f = -f), f > Math.PI && (f -= Math.PI), f)
    }
    function dn(t, i, n, e) {
        return Math[zr](e - i, n - t)
    }
    function ln(t) {
        var i = Pz[Mo](t);
        if (i) return i[1];
        var n = t[So]($r);
        return n >= 0 && n < t[Hh] - 1 ? t[Io](n + 1) : void 0
    }
    function vn(t) {
        if (!t) return null;
        if (t instanceof cH) return zz;
        if (t[Ao] instanceof Function) return Gz;
        if (N(t)) {
            var i = ln(t);
            if (i) {
                if (!Jq && Nz[Lo](i)) return qz;
                if (Bz[Lo](i)) return Fz
            }
            return $z
        }
    }
    function bn(t, i, n) {
        if (this._ls = vn(t), !this._ls) throw new Error("the image format is not supported", t);
        this._lr = t,
        this._9w = i,
        this._9j = n,
        this[Ca] = i || _G[Co],
        this[Da] = n || _G[Do],
        this._iz = {}
    }
    function gn(t, i, n, e) {
        return i ? (Wz[t] = new bn(i, n, e), t) : void delete Wz[t]
    }
    function yn(t) {
        if (t._km) return t._km;
        var i = N(t);
        if (!i && !t[ur]) return t._km = new bn(t);
        var n = t.name || t;
        return n in Wz ? Wz[n] : Wz[n] = new bn(t)
    }
    function mn(t) {
        return t in Wz
    }
    function En(t, i, n) {
        n = n || {};
        var e = t[oo](n[bo]);
        if (!e.width || !e.height) return !1;
        var s = i.getContext(za),
            h = i[Na] || 1,
            r = n[Ro] || Po,
            a = /full/i [Lo](r),
            o = /uniform/i.test(r),
            f = 1,
            u = 1;
        if (a) {
                var c = i[Ca],
                    _ = i.height,
                    d = n[No],
                    l = 0,
                    v = 0;
                if (d) {
                        var b, g, y, m;
                        P(d) ? b = g = y = m = d : (b = d.top || 0, g = d[Yr] || 0, y = d[Bo] || 0, m = d.right || 0),
                        c -= y + m,
                        _ -= b + g,
                        l += y,
                        v += b
                    }
                f = c / e.width,
                u = _ / e[Da],
                o && (f > u ? (l += (c - u * e.width) / 2, f = u) : u > f && (v += (_ - f * e.height) / 2, u = f)),
                (l || v) && s[$o](l, v)
            }
        s[$o](-e.x * f, -e.y * u),
        t[Ao](s, h, n, f, u, !0)
    }
    function xn(t, i, n) {
        var e = yn(t);
        return e ? (e.validate(), (e._ls == qz || e._5y()) && e._9x(function (t) {
            t.source && (this.width = this.width, En(t[Fo], this, n))
        }, i), void En(e, i, n)) : (jz[Gr](qo + t), !1)
    }
    function pn(t, i, e, s) {
        var h = t.length;
        if (h && !(0 > h)) {
            s = s || 1;
            for (var r, a, o, f = [], u = 0; u++ < h;) if (r = t[Go](u, 0), r && gG(i, e, r.x, r.y) <= s) {
                a = u,
                o = r[mo];
                break
            }
            if (a !== n) {
                for (var r, c, _, d = 0, u = 0, l = t._f8[Hh]; l > u; u++) {
                    if (r = t._f8[u], !c && (d += r._il || 0, d > a)) if (c = !0, r.type == sH || r[yo] == oH) f[tr](new fH(sH, [i, e]));
                    else {
                        var v = Math.max(10, r._il / 6),
                            b = v * Math.sin(o),
                            g = v * Math.cos(o);
                        if (r[yo] == rH) {
                                var y = r[xa][0],
                                    m = r[xa][1];
                                if (_) {
                                        var E = new yG(i, e, i + g, e + b),
                                            x = E._3f(new yG(_[go].x, _[go].y, r[xa][0], r[xa][1]));
                                        x.x !== n && (y = x.x, m = x.y)
                                    }
                                f[tr](new fH(rH, [y, m, i - g, e - b, i, e]))
                            } else f.push(new fH(hH, [i - g, e - b, i, e]));
                        if (r[xa]) if (r[yo] == rH) {
                                r[xa][0] = i + g,
                                r[xa][1] = e + b;
                                var E = new yG(i, e, i + g, e + b),
                                    x = E._3f(new yG(r[xa][2], r[xa][3], r[xa][4], r[xa][5]));
                                x.x !== n && (r[xa][2] = x.x, r[xa][3] = x.y)
                            } else if (r[yo] == hH) {
                                r[yo] = rH,
                                r[xa] = [i + g, e + b][Kh](r[xa]);
                                var E = new yG(i, e, i + g, e + b),
                                    x = E._3f(new yG(r[xa][2], r[xa][3], r[xa][4], r[xa][5]));
                                x.x !== n && (r.points[2] = x.x, r.points[3] = x.y)
                            } else r.type == sH && (r[yo] = hH, r[xa] = [i + g, e + b].concat(r[xa]), u == l - 1 && (r.invalidTerminal = !0))
                    }
                    f.push(r),
                    _ = r
                }
                return f
            }
        }
    }
    function wn(t) {
        var i = t[Ca],
            n = t[Da],
            e = Ni(t.g, 0, 0, i, n);
        return e ? kn(e[vo], i, n) : void 0
    }
    function Tn(t, i, n) {
        this._15(t, i, n)
    }
    function kn(t, i, n) {
        return new Tn(t, i, n)
    }
    function On(t) {
        if (Cr == t[0]) {
            if (t = t[Io](1), 3 == t[Hh]) t = t[0] + t[0] + t[1] + t[1] + t[2] + t[2];
            else if (6 != t[Hh]) return;
            return t = parseInt(t, 16),
            [t >> 16 & 255, t >> 8 & 255, 255 & t]
        }
        if (/^rgb/i [Lo](t)) {
            var i = t[zo](Ho),
                n = t.indexOf(Lr);
            if (0 > i || i > n) return;
            if (t = t[Io](i + 1, n), t = t.split(Ar), t[Hh] < 3) return;
            var e = parseInt(t[0]),
                s = parseInt(t[1]),
                h = parseInt(t[2]),
                r = 3 == t[Hh] ? 255 : parseInt(t[3]);
            return [e, s, h, r]
        }
    }
    function jn(t, i, n) {
        return n || (n = _G[Yo]),
        n == Mz[Uo] ? t * i : n == Mz[Wo] ? Math.min(t, i) : n == Mz[Vo] ? 1 - (1 - i) / t : n == Mz.BLEND_MODE_LINEAR_BURN ? t + i - 1 : n == Mz.BLEND_MODE_LIGHTEN ? Math.max(t, i) : n == Mz[Xo] ? t + i - t * i : i
    }
    function Mn(t, i, n) {
        var e = On(i);
        if (!e) return void jz[Gr](Ko + i + Zo);
        var s = Ni(t.g, 0, 0, t[Ca], t[Da]);
        if (s) {
            var h = s[vo];
            if (n instanceof Function) h = n(t, h, e) || h;
            else {
                var r = e[0] / 255,
                    a = e[1] / 255,
                    o = e[2] / 255;
                if (n == Mz[Jo]) for (var f = 0, u = h.length; u > f; f += 4) {
                        var c = 77 * h[f] + 151 * h[f + 1] + 28 * h[f + 2] >> 8;
                        h[f] = c * r | 0,
                        h[f + 1] = c * a | 0,
                        h[f + 2] = c * o | 0
                    } else for (var f = 0, u = h[Hh]; u > f; f += 4) h[f] = 255 * jn(r, h[f] / 255, n) | 0,
                h[f + 1] = 255 * jn(a, h[f + 1] / 255, n) | 0,
                h[f + 2] = 255 * jn(o, h[f + 2] / 255, n) | 0
            }
            var t = Li(t[Ca], t[Da]);
            return t.g[Qo](s, 0, 0),
            t
        }
    }
    function Sn(t, i, n, e) {
        return 1 > n && (n = 1),
        In(t - n, i - n, 2 * n, 2 * n, e)
    }
    function In(t, i, n, e, s) {
        n = Math.round(n) || 1,
        e = Math.round(e) || 1;
        var h = Ci(n, e);
        h[co](1, 0, 0, 1, -t, -i),
        s[Ao](h);
        var r = Ni(h, 0, 0, n, e);
        if (!r) return !1;
        r = r.data;
        for (var a = r.length / 4; a-- > 0;) if (r[4 * a - 1] > uH) return !0;
        return !1
    }
    function An(t, i, n, e, s, h) {
        t -= s.$x,
        i -= s.$y;
        var r = s._fa.intersection(t, i, n, e);
        if (!r) return !1;
        t = r.x * h,
        i = r.y * h,
        n = r.width * h,
        e = r[Da] * h,
        n = Math[uo](n) || 1,
        e = Math.round(e) || 1;
        var a = Ci(),
            o = a[Ga];
        o[Ca] < n || o[Da] < e ? (o[Ca] = n, o.height = e) : (a[co](1, 0, 0, 1, 0, 0), a[tf](0, 0, n, e)),
        a[co](1, 0, 0, 1, -t - s.$x * h, -i - s.$y * h),
        a[nf](h, h),
        s._ir(a, 1);
        var f = Ni(a, 0, 0, n, e);
        if (!f) return !1;
        f = f.data;
        for (var u = f[Hh] / 4; u-- > 0;) if (f[4 * u - 1] > uH) return !0;
        return !1
    }
    function Ln(t, i, n, e, s, h, r, a, o) {
        if (ri(t, i, n, e, a, o)) return null;
        var f, u, c, _ = new fH(sH, [t + n - s, i]),
            d = new fH(hH, [t + n, i, t + n, i + h]),
            l = new fH(sH, [t + n, i + e - h]),
            v = new fH(hH, [t + n, i + e, t + n - s, i + e]),
            b = new fH(sH, [t + s, i + e]),
            g = new fH(hH, [t, i + e, t, i + e - h]),
            y = new fH(sH, [t, i + h]),
            m = new fH(hH, [t, i, t + s, i]),
            E = (new fH(oH), [_, d, l, v, b, g, y, m]),
            x = new EG(t + s, i + h, n - s - s, e - h - h);
        t > a ? (f = wG, c = 5) : a > t + n ? (f = kG, c = 1) : (f = TG, c = 0),
        i > o ? (u = OG, f == wG && (c = 7)) : o > i + e ? (u = MG, f == kG ? c = 3 : f == TG && (c = 4)) : (u = jG, f == wG ? c = 6 : f == kG && (c = 2));
        var p = Bn(c, t, i, n, e, s, h, r, a, o, x),
            w = p[0],
            T = p[1],
            k = new cH,
            O = k._f8;
        O[tr](new fH(eH, [w.x, w.y])),
        O[tr](new fH(sH, [a, o])),
        O.push(new fH(sH, [T.x, T.y])),
        T._m3 && (O.push(T._m3), T._m3NO++);
        for (var j = T._m3NO % 8, M = w[ef]; O[tr](E[j]), ++j, j %= 8, j != M;);
        return w._m3 && O.push(w._m3),
        k[sf](),
        k
    }
    function Cn(t, i, e, s, h, r, a, o, f, u, c, _, d, l) {
        var v = new yG(_, d, e, s),
            b = new yG(i[0], i[1], i[4], i[5]),
            g = b._3f(v, c),
            y = g[0],
            m = g[1];
        if (y[hf] !== n) {
                y._m3NO = (t - 1) % 8,
                m[ef] = (t + 1) % 8;
                var E = y[hf];
                7 == t ? (y.y = r + u + Math.min(l[Da], E), m.x = h + f + Math.min(l.width, E)) : 5 == t ? (y.x = h + f + Math.min(l[Ca], E), m.y = r + o - u - Math.min(l.height, E)) : 3 == t ? (y.y = r + o - u - Math.min(l[Da], E), m.x = h + a - f - Math.min(l.width, E)) : 1 == t && (y.x = h + a - f - Math.min(l[Ca], E), m.y = r + u + Math.min(l.height, E))
            } else {
                v._ma(v._mm, v._mk, y.x, y.y),
                y = v._$e(i),
                v._ma(v._mm, v._mk, m.x, m.y),
                m = v._$e(i);
                var x = $n(i, [y, m]),
                    p = x[0],
                    w = x[2];
                y[ef] = t,
                m[ef] = t,
                y._m3 = new fH(hH, p[Vh](2)),
                m._m3 = new fH(hH, w[Vh](2))
            }
        return [y, m]
    }
    function Dn(t, i, n, e, s, h, r, a, o, f) {
        var u, c;
        if (o - a >= i + h) u = {
            y: n,
            x: o - a
        },
        u[ef] = 0;
        else {
            u = {
                y: n + r,
                x: Math.max(i, o - a)
            };
            var _ = [i, n + r, i, n, i + h, n],
                d = new yG(o, f, u.x, u.y);
            if (u = d._$e(_)) {
                    $(u) && (u = u[0].t > u[1].t ? u[0] : u[1]);
                    var l = $n(_, [u]);
                    l = l[0],
                    l && (u._m3 = new fH(hH, l[Vh](2))),
                    u[ef] = 7
                } else u = {
                    y: n,
                    x: i + h
                },
            u._m3NO = 0
        }
        if (i + e - h >= o + a) c = {
            y: n,
            x: o + a
        },
        c[ef] = 0;
        else {
            c = {
                y: n + r,
                x: Math.min(i + e, o + a)
            };
            var v = [i + e - h, n, i + e, n, i + e, n + r],
                d = new yG(o, f, c.x, c.y);
            if (c = d._$e(v)) {
                    $(c) && (c = c[0].t < c[1].t ? c[0] : c[1]);
                    var l = $n(v, [c]);
                    l && l[l[Hh] - 1] && (c._m3 = new fH(hH, l[l[Hh] - 1][Vh](2))),
                    c[ef] = 1
                } else c = {
                    y: n,
                    x: i + e - h
                },
            c[ef] = 0
        }
        return [u, c]
    }
    function Rn(t, i, n, e, s, h, r, a, o, f) {
        var u, c;
        if (f - a >= n + r) u = {
            x: i + e,
            y: f - a
        },
        u._m3NO = 2;
        else {
            u = {
                x: i + e - h,
                y: Math.max(n, f - a)
            };
            var _ = [i + e - h, n, i + e, n, i + e, n + r],
                d = new yG(o, f, u.x, u.y);
            if (u = d._$e(_)) {
                    $(u) && (u = u[0].t > u[1].t ? u[0] : u[1]);
                    var l = $n(_, [u]);
                    l = l[0],
                    l && (u._m3 = new fH(hH, l[Vh](2))),
                    u._m3NO = 1
                } else u = {
                    x: i + e,
                    y: n + r
                },
            u[ef] = 2
        }
        if (n + s - r >= f + a) c = {
            x: i + e,
            y: f + a
        },
        c[ef] = 2;
        else {
            c = {
                x: i + e - h,
                y: Math.min(n + s, f + a)
            };
            var v = [i + e, n + s - r, i + e, n + s, i + e - h, n + s],
                d = new yG(o, f, c.x, c.y);
            if (c = d._$e(v)) {
                    $(c) && (c = c[0].t < c[1].t ? c[0] : c[1]);
                    var l = $n(v, [c]);
                    l[1] && (c._m3 = new fH(hH, l[1][Vh](2))),
                    c[ef] = 3
                } else c = {
                    x: i + e,
                    y: n + s - r
                },
            c._m3NO = 2
        }
        return [u, c]
    }
    function Pn(t, i, n, e, s, h, r, a, o, f) {
        var u, c;
        if (o - a >= i + h) c = {
            y: n + s,
            x: o - a
        },
        c[ef] = 4;
        else {
            c = {
                y: n + s - r,
                x: Math.max(i, o - a)
            };
            var _ = [i + h, n + s, i, n + s, i, n + s - r],
                d = new yG(o, f, c.x, c.y);
            if (c = d._$e(_)) {
                    $(c) && (c = c[0].t < c[1].t ? c[0] : c[1]);
                    var l = $n(_, [c]);
                    l = l[l[Hh] - 1],
                    l && (c._m3 = new fH(hH, l[Vh](2))),
                    c._m3NO = 5
                } else c = {
                    y: n + s,
                    x: i + h
                },
            c[ef] = 4
        }
        if (i + e - h >= o + a) u = {
            y: n + s,
            x: o + a
        },
        u[ef] = 4;
        else {
            u = {
                y: n + s - r,
                x: Math.min(i + e, o + a)
            };
            var v = [i + e, n + s - r, i + e, n + s, i + e - h, n + s],
                d = new yG(o, f, u.x, u.y);
            if (u = d._$e(v)) {
                    $(u) && (u = u[0].t > u[1].t ? u[0] : u[1]);
                    var l = $n(v, [u]);
                    l[0] && (u._m3 = new fH(hH, l[0][Vh](2))),
                    u[ef] = 3
                } else u = {
                    y: n + s,
                    x: i + e - h
                },
            u[ef] = 4
        }
        return [u, c]
    }
    function Nn(t, i, n, e, s, h, r, a, o, f) {
        var u, c;
        if (f - a >= n + r) c = {
            x: i,
            y: f - a
        },
        c._m3NO = 6;
        else {
            c = {
                x: i + h,
                y: Math.max(n, f - a)
            };
            var _ = [i, n + r, i, n, i + h, n],
                d = new yG(o, f, c.x, c.y);
            if (c = d._$e(_)) {
                    $(c) && (c = c[0].t < c[1].t ? c[0] : c[1]);
                    var l = $n(_, [c]);
                    l = l[l[Hh] - 1],
                    l && (c._m3 = new fH(hH, l[Vh](2)))
                } else c = {
                    x: i,
                    y: n + r
                };
            c[ef] = 7
        }
        if (n + s - r >= f + a) u = {
            x: i,
            y: f + a
        },
        u[ef] = 6;
        else {
            u = {
                x: i + h,
                y: Math.min(n + s, f + a)
            };
            var v = [i + h, n + s, i, n + s, i, n + s - r],
                d = new yG(o, f, u.x, u.y);
            if (u = d._$e(v)) {
                    $(u) && (u = u[0].t > u[1].t ? u[0] : u[1]);
                    var l = $n(v, [u]);
                    l[0] && (u._m3 = new fH(hH, l[0][Vh](2))),
                    u[ef] = 5
                } else u = {
                    x: i,
                    y: n + s - r
                },
            u._m3NO = 6
        }
        return [u, c]
    }
    function Bn(t, i, n, e, s, h, r, a, o, f, u) {
        var c = a / 2;
        switch (h = h || 1e-4, r = r || 1e-4, t) {
        case 7:
            var _ = [i, n + r, i, n, i + h, n],
                d = i + h,
                l = n + r;
            return Cn(t, _, d, l, i, n, e, s, h, r, a, o, f, u);
        case 5:
            return _ = [i + h, n + s, i, n + s, i, n + s - r],
            d = i + h,
            l = n + s - r,
            Cn(t, _, d, l, i, n, e, s, h, r, a, o, f, u);
        case 3:
            return _ = [i + e, n + s - r, i + e, n + s, i + e - h, n + s],
            d = i + e - h,
            l = n + s - r,
            Cn(t, _, d, l, i, n, e, s, h, r, a, o, f, u);
        case 1:
            return _ = [i + e - h, n, i + e, n, i + e, n + r],
            d = i + e - h,
            l = n + r,
            Cn(t, _, d, l, i, n, e, s, h, r, a, o, f, u);
        case 0:
            return Dn(t, i, n, e, s, h, r, c, o, f, u);
        case 2:
            return Rn(t, i, n, e, s, h, r, c, o, f, u);
        case 4:
            return Pn(t, i, n, e, s, h, r, c, o, f, u);
        case 6:
            return Nn(t, i, n, e, s, h, r, c, o, f, u)
        }
    }
    function $n(t, i) {
        for (var e, s, h, r, a, o, f = t[0], u = t[1], c = t[2], _ = t[3], d = t[4], l = t[5], v = [], b = 0; b < i[Hh]; b++) a = i[b],
        o = a.t,
        0 != o && 1 != o ? (e = f + (c - f) * o, s = u + (_ - u) * o, h = c + (d - c) * o, r = _ + (l - _) * o, v[tr]([f, u, e, s, a.x, a.y]), f = a.x, u = a.y, c = h, _ = r) : v.push(null);
        return h !== n && v[tr]([a.x, a.y, h, r, d, l]),
        v
    }
    function Fn(t) {
        return this[rf] && this[Oo] && (t.x -= this._mq2.x, t.y -= this[Oo].y),
        this[af] && ji(t, this[af]),
        t.x += this[of] || 0,
        t.y += this[ff] || 0,
        this[uf] && this.$_hostRotate ? ji(t, this[cf]) : t
    }
    function qn(t) {
        return this[uf] && this[cf] && ji(t, -this[cf]),
        t.x -= this[of] || 0,
        t.y -= this[ff] || 0,
        this.$rotate && ji(t, -this.$rotate),
        this[rf] && this._mq2 && (t.x += this[Oo].x, t.y += this[Oo].y),
        t
    }
    function Gn() {
        var t = this[_f];
        this[_f] && (this[_f] = !1, this[df] = !0, this._7i.setByRect(this._iv), this[lf] && this._7i[vf](this[lf]), this[bf] && this._7i[vf](this.$border));
        var i = this._$q();
        if (i) var n = this.showPointer && this.$pointerWidth;
        return this[df] && this[rf] && (this[df] = !1, n && (t = !0), this[Oo] = fi(this[gf], this._7i[Ca], this._7i[Da]), this[Oo].x += this._7i.x, this[Oo].y += this._7i.y),
        i ? (t && (this._msackgroundGradientInvalidateFlag = !0, zn[Yh](this, n)), this[yf] && (this[yf] = !1, this[mf] = this[Ef] && this[xf] && this[xf][fo] ? Vz[er].generatorGradient[Yh](this.backgroundGradient, this[xf].bounds) : null), t) : (this.__lyPointer = !1, t)
    }
    function zn(t) {
        var i = this._7i.x + this.$border / 2,
            n = this._7i.y + this.$border / 2,
            e = this._7i[Ca] - this.$border,
            s = this._7i.height - this.$border,
            h = 0,
            r = 0;
        if (this[pf] && (P(this.$borderRadius) ? h = r = this[pf] : (h = this[pf].x || 0, r = this[pf].y || 0), h = Math.min(h, e / 2), r = Math.min(r, s / 2)), t && (this[wf] = this[Oo].x - this[of] + this[Tf], this._pointerY = this._mq2.y - this[ff] + this[kf], !this._7i.intersectsPoint(this._pointerX, this[Of]))) {
                var a = new dH(i, n, e, s, h, r, this.$pointerWidth, this[wf], this[Of]);
                return this[xf] = a._m3,
                this._l6Shape[fo].set(i, n, e, s),
                void(this[jf] = !0)
            }
        this[xf] && this._l6Shape[Ra](),
        this._l6Shape = NY[Mf](i, n, e, s, h, r, this[xf]),
        this[xf][fo].set(i, n, e, s)
    }
    function Hn(t, i, n, e) {
        return e && (t[Ca] < 0 || t.height < 0) ? (t.x = i, t.y = n, void(t.width = t[Da] = 0)) : (i < t.x ? (t.width += t.x - i, t.x = i) : i > t.x + t[Ca] && (t.width = i - t.x), void(n < t.y ? (t[Da] += t.y - n, t.y = n) : n > t.y + t[Da] && (t[Da] = n - t.y)))
    }
    function Yn(t, i, e) {
        var s, h = t[Sf],
            r = t.layoutByPath === n ? this[If] : t[If];
        return this[Af] instanceof cH && r ? (s = Rz._mq4(h, this.$data, this[bo], i, e), s.x *= this._jd, s.y *= this._j6) : (s = fi(h, this._7i[Ca], this._7i.height), s.x += this._7i.x, s.y += this._7i.y),
        Fn[Yh](this, s)
    }
    function Un(t, i) {
        if (i) if (i._7i[Lf]()) t.$x = i.$x,
        t.$y = i.$y;
        else {
            var n = Yn[Yh](i, t);
            t.$x = n.x,
            t.$y = n.y,
            t[Cf] = n.rotate
        } else t.$x = 0,
        t.$y = 0;
        t.$invalidateRotate && bH.call(t)
    }
    function Wn(t) {
        if (t[Df] === n) {
            var i, e;
            if (t.setLineDash) i = t.getLineDash,
            e = t[Rf];
            else {
                var s;
                if (t[Pf] !== n) s = Pf;
                else {
                    if (t.webkitLineDash === n) return !1;
                    s = Nf
                }
                e = function (t) {
                    this[s] = t
                },
                i = function () {
                    return this[s]
                }
            }
            K(t, Df, {
                get: function () {
                    return i[Yh](this)
                },
                set: function (t) {
                    e[Yh](this, t)
                }
            })
        }
        if (t.lineDashOffset === n) {
            var h;
            if (t[Bf] !== n) h = Bf;
            else {
                if (t[$f] === n) return;
                h = $f
            }
            K(t, Ff, {
                get: function () {
                    return this[h]
                },
                set: function (t) {
                    this[h] = t
                }
            })
        }
    }
    function Vn(t, i, n, e, s) {
        var h, r, a, o, f, u, c, _, d = function (t) {
            return function (i) {
                t(i)
            }
        },
            l = function () {
                r = null,
                a = null,
                o = f,
                f = null,
                u = null
            },
            v = function (t) {
                h = t,
                c || (c = Li()),
                c.width = h.width,
                c[Da] = h[Da],
                i.width = h[Ca],
                i[Da] = h[Da]
            },
            b = function (t) {
                g(),
                l(),
                r = t[qf] ? t[Gf] : null,
                a = 10 * t.delayTime,
                f = t.disposalMethod
            },
            g = function () {
                if (u) {
                    var t = u.getImageData(0, 0, h.width, h[Da]),
                        n = {
                            data: t,
                            _pixels: kn(t.data, h[Ca], h[Da]),
                            delay: a
                        };
                    s.call(i, n)
                }
            },
            y = function (t) {
                u || (u = c[zf](za));
                var i = t[Hf] ? t.lct : h.gct,
                    n = u[no](t[Yf], t[Uf], t[Ca], t[Da]);
                t[Wf][Vf](function (t, e) {
                        r !== t ? (n[vo][4 * e + 0] = i[t][0], n[vo][4 * e + 1] = i[t][1], n.data[4 * e + 2] = i[t][2], n[vo][4 * e + 3] = 255) : (2 === o || 3 === o) && (n[vo][4 * e + 3] = 0)
                    }),
                u[Qo](n, t[Yf], t[Uf])
            },
            m = function () {},
            E = {
                hdr: d(v),
                gce: d(b),
                com: d(m),
                app: {
                    NETSCAPE: d(m)
                },
                img: d(y, !0),
                eof: function () {
                    g(),
                    n.call(i)
                }
            },
            x = new XMLHttpRequest;
        Jq || x[Xf]("text/plain; charset=x-userSystem-defined"),
        x.onload = function () {
                _ = new xH(x[Ta]);
                try {
                    wH(_, E)
                } catch (t) {
                    e.call(i, Kf)
                }
            },
        x.onerror = function () {
                e[Yh](i, Zf)
            },
        x.open(Ma, t, !0),
        x[Jf]()
    }
    function Xn(t) {
        var i = [51, 10, 10, 100, 101, 109, 111, 46, 113, 117, 110, 101, 101, 46, 99, 111, 109, 44, 109, 97, 112, 46, 113, 117, 110, 101, 101, 46, 99, 111, 109, 44, 99, 110, 46, 113, 117, 110, 101, 101, 46, 99, 111, 109, 10, 50, 46, 48, 10, 49, 52, 57, 50, 54, 55, 54, 49, 48, 53, 50, 50, 48, 10, 10, 10];
        return i[Vf](function (n, e) {
            i[e] = t(n)
        }),
        i[Qf]("")
    }
    function Kn(t, i) {
        try {
            if (null == t || t[Hh] < 8) return;
            if (null == i || i[Hh] <= 0) return;
            for (var n = "", e = 0; e < i[Hh]; e++) n += i[tu](e).toString();
            var s = Math.floor(n[Hh] / 5),
                h = parseInt(n.charAt(s) + n[iu](2 * s) + n[iu](3 * s) + n.charAt(4 * s) + n[iu](5 * s), 10),
                r = Math[uo](i.length / 2),
                a = Math.pow(2, 31) - 1,
                o = parseInt(t[Io](t[Hh] - 8, t[Hh]), 16);
            for (t = t[Io](0, t.length - 8), n += o; n.length > 10;) n = (parseInt(n.substring(0, 10), 10) + parseInt(n[Io](10, n.length), 10)).toString();
            n = (h * n + r) % a;
            for (var f = "", u = "", e = 0; e < t[Hh]; e += 2) f = parseInt(parseInt(t[Io](e, e + 2), 16) ^ Math[Mr](n / a * 255), 10),
            u += String[nu](f),
            n = (h * n + r) % a;
            return 0 | u[0] ? tY = SH[eu + LH + su](u) : null
        } catch (c) {}
    }
    function Zn() {
        var t = kH;
        if (!t) return void(rY = !0);
        QH = t;
        var i;
        t = t[mr](Ar);
        for (var n = 0; n < t[Hh] && (i = Kn(t[n], jH), !(i && i[mr](Ja).length >= 8));) 1 == t[Hh] && (i = Kn(t[n], hu)),
        n++;
        if (!i || i[mr](Ja)[Hh] < 8) return eY = !0,
        "" === jH || jH == ru + PH + au + NH + ou || jH == fu + RH + uu ? (sY = uY, rY = !1, oY = !1, void(JH = !1)) : (sY = uY, void(rY = !0));
        JH = i[mr](Ja);
        var e = JH[3];
        if (e != lW) return eY = !0,
        void(oY = !0);
        rY = !1,
        oY = !1;
        var s = JH[0];
        (cu == s || _u == s) && (eY = !1);
        var h = JH[5];
        hY = h;
        var r = JH[6];
        sY = r
    }
    function Jn() {
        var t = QH;
        if (t) {
            var i;
            t = t[mr](Ar);
            for (var n = 0; n < t[Hh] && (i = cY(t[n], jH), !(i && i[mr](Ja).length >= 8));) 1 == t[Hh] && (i = cY(t[n], hu)),
            n++;
            if (i[mr](Ja)[Hh] >= 8) return void(aY = !1)
        }
        return jH && jH != ru + PH + au + NH + ou && jH != fu + RH + uu ? void(aY = !0) : void(aY = !1)
    }
    function Qn() {
        if (eY) {
            var t = uh[GH + yo]._ir,
                i = nY;
            uh[GH + yo]._ir = function () {
                    t.apply(this, arguments),
                    i.call(this[du], this.g)
                };
            var n = kY[GH + yo]._gf;
            kY[GH + yo]._gf = function (t) {
                    n[nr](this, arguments),
                    i[Yh](this, t)
                }
        }
    }
    function te() {
        if (hY !== !0 && hY) {
            var t = hY.split($r);
            if (3 != t[Hh]) return void($Y.prototype._ir = null);
            var i = parseInt(t[0], 10),
                n = parseInt(t[1], 10),
                e = parseInt(t[2], 10),
                s = 3,
                h = (365.2425 * (i - 2e3 + 10 * s) + (n - 1) * s * 10 + e) * s * 8 * s * 1200 * 1e3;
            OH > h && ($Y.prototype._ir = null)
        }
    }
    function ie() {
        var t = 0 | sY;
        t && (dG[GH + yo]._k3 = function (i, e) {
            var s = i.id;
            return s === n || this[lu](s) ? !1 : this._j8[Hh] > t ? !1 : (y(this._j8, i, e), this._la[s] = i, i)
        })
    }
    function ne() {
        rY && (dG[GH + yo]._k3 = dG[GH + yo]._f7)
    }
    function ee() {
        aY && (kY[GH + yo]._ir = kY[GH + yo][vu])
    }
    function se() {
        fY && (SY.prototype._fi = SY[er]._e5)
    }
    function he() {
        oY && (TY[GH + yo][vu] = kY[GH + yo]._ir)
    }
    function re() {
        JH === n && (kY[GH + yo]._ij = EG.equals)
    }
    function ae(t) {
        var i = Li(!0);
        return Wn(i.g),
        i.onselectstart = function () {
            return !1
        },
        t[bu](i),
        i[gu] = bY,
        i
    }
    function d(t, i) {
        function n(t, n) {
            for (var e = t[Hh], s = n.length, h = e + s, r = new Array(h), a = 0, o = 0, f = 0; h > f;) r[f++] = a === e ? n[o++] : o === s || i(t[a], n[o]) <= 0 ? t[a++] : n[o++];
            return r
        }
        function e(t) {
            var i = t.length,
                s = Math[Zh](i / 2);
            return 1 >= i ? t : n(e(t[Vh](0, s)), e(t[Vh](s)))
        }
        return e(t)
    }
    function oe(t) {
        t[Ca] = t.width
    }
    function fe(t) {
        pY || (pY = "imageSmoothingEnabled" in CanvasRenderingContext2D[er] ? "imageSmoothingEnabled" : "mozImageSmoothingEnabled" in CanvasRenderingContext2D.prototype ? "mozImageSmoothingEnabled" : "msImageSmoothingEnabled" in CanvasRenderingContext2D.prototype ? "msImageSmoothingEnabled" : "webkitImageSmoothingEnabled" in CanvasRenderingContext2D.prototype ? "webkitImageSmoothingEnabled" : "imageSmoothingEnabled"),
        t[pY] = !1
    }
    function ue(t, i, n, e, s) {
        e = V(i + e) - (i = W(i)),
        s = V(n + s) - (n = W(n)),
        t[tf](i, n, e, s),
        t.rect(i, n, e, s)
    }
    function W(t) {
        return Math[Mr](t)
    }
    function V(t) {
        return Math[Zh](t)
    }
    function ce(t) {
        var i = [];
        return t[Vf](function (t) {
            i[tr](-t)
        }),
        i
    }
    function _e(t) {
        return t %= jY,
        0 > t && (t += jY),
        t
    }
    function de(t, i, n, e, s, h, r, a) {
        var o = ((t * e - i * n) * (s - r) - (t - n) * (s * a - h * r)) / ((t - n) * (h - a) - (i - e) * (s - r)),
            f = ((t * e - i * n) * (h - a) - (i - e) * (s * a - h * r)) / ((t - n) * (h - a) - (i - e) * (s - r));
        if (isNaN(o) || isNaN(f)) return !1;
        if (t >= n) {
                if (!(o >= n && t >= o)) return !1
            } else if (!(o >= t && n >= o)) return !1;
        if (i >= e) {
                if (!(f >= e && i >= f)) return !1
            } else if (!(f >= i && e >= f)) return !1;
        if (s >= r) {
                if (!(o >= r && s >= o)) return !1
            } else if (!(o >= s && r >= o)) return !1;
        if (h >= a) {
                if (!(f >= a && h >= f)) return !1
            } else if (!(f >= h && a >= f)) return !1;
        return !0
    }
    function le(t, i) {
        for (var n = 0, e = t[Hh]; e > n;) {
            for (var s = t[n], h = t[(n + 1) % e], r = 0; 4 > r;) {
                var a = i[r],
                    o = i[(r + 1) % e];
                if (de(s[0], s[1], h[0], h[1], a[0], a[1], o[0], o[1])) return !0;
                r++
            }
            n++
        }
        return !1
    }
    function ve(t, i, n, e) {
        return [t * e - i * n, t * n + i * e]
    }
    function be(t) {
        return t.parent ? (t = t[yu], t._di ? t._di : t instanceof BY && t._g6 === !1 ? t : null) : null
    }
    function ge(t, i, n) {
        if (n = n || i.toAgent, n == t) return !1;
        var e = t[mu](n);
        return e || (e = new eW(t, n), t._linkedNodes[n.id] = e),
        e._hw(i, t)
    }
    function ye(t, i, n) {
        if (n = n || i[Eu], n == t) return !1;
        var e = t[mu](n);
        return e ? e[xu](i, t) : void 0
    }
    function me(t, i, e) {
        return e === n && (e = i[Eu]),
        e != t ? (t._7l || (t._7l = new dG), t._7l.add(i) === !1 ? !1 : void t._8n++) : void 0
    }
    function Ee(t, i, n) {
        return t._7l && t._7l[Jh](i) !== !1 ? (t._8n--, void ye(t, i, n)) : !1
    }
    function xe(t, i) {
        return i[pu] != t ? (t._8m || (t._8m = new dG), t._8m.add(i) === !1 ? !1 : void t[wu]++) : void 0
    }
    function pe(t, i) {
        return t._8m && t._8m[Jh](i) !== !1 ? (t[wu]--, void ye(i[pu], i, t)) : !1
    }
    function we(t, i) {
        if (i === n && (i = t instanceof DY), i) {
            if (t[Tu]()) return null;
            var e = we(t[ku], !1);
            if (t[Ou]()) return e;
            for (var s = we(t.to, !1); null != e && null != s;) {
                if (e == s) return e;
                if (e[ju](s)) return s;
                if (s.isDescendantOf(e)) return e;
                e = we(e, !1),
                s = we(s, !1)
            }
            return null
        }
        for (var h = t[yu]; null != h;) {
            if (h._hh()) return h;
            h = h[yu]
        }
        return null
    }
    function Te(t, i, n) {
        t._hh() && t.hasChildren() && t[Wh][Vf](function (t) {
            t instanceof RY && i.add(t) && Te(t, i, n)
        }, this),
        t.hasFollowers() && t._dh[Vf](function (t) {
            (null == n || n[Mu](t)) && i.add(t) && Te(t, i, n)
        })
    }
    function ke(t, i) {
        i[yu] ? i[yu][Su](i, i[yu][Iu] - 1) : t.roots[Au](i, t[Lu][Hh] - 1)
    }
    function Oe(t, i) {
        i[yu] ? i[yu][Su](i, 0) : t[Lu].setIndex(i, 0)
    }
    function je(t, i) {
        if (i instanceof DY) return void(i[Tu]() || Se(t, i));
        for (ke(t, i); i = i.parent;) ke(t, i)
    }
    function Me(t, i) {
        if (i instanceof DY) return void(i[Tu]() || Ie(t, i));
        for (Oe(t, i); i = i.parent;) Oe(t, i)
    }
    function Se(t, i) {
        var n = i[pu];
        if (i[Ou]()) ke(t, n);
        else {
            var e = i[Eu];
            ke(t, n),
            ke(t, e)
        }
    }
    function Ie(t, i) {
        var n = i[pu];
        if (i[Ou]()) Oe(t, n);
        else {
            var e = i.toAgent;
            Oe(t, n),
            Oe(t, e)
        }
    }
    function Ae(t, i) {
        return t._8n++,
        t._g2 ? (i._i2 = t._hq, t._hq._i4 = i, void(t._hq = i)) : (t._g2 = i, void(t._hq = i))
    }
    function Le(t, i) {
        t._8n--,
        t._hq == i && (t._hq = i._i2),
        i._i2 ? i._i2._i4 = i._i4 : t._g2 = i._i4,
        i._i4 && (i._i4._i2 = i._i2),
        i._i2 = null,
        i._i4 = null,
        ye(t, i, i.$to)
    }
    function Ce(t, i) {
        return t._mqk++,
        t._hr ? (i._jp = t._j2, t._j2._jr = i, void(t._j2 = i)) : (t._hr = i, void(t._j2 = i))
    }
    function De(t, i) {
        t[wu]--,
        t._j2 == i && (t._j2 = i._jp),
        i._jp ? i._jp._jr = i._jr : t._hr = i._jr,
        i._jr && (i._jr._jp = i._jp),
        i._jp = null,
        i._jr = null
    }
    function Re(t, i) {
        return i = i || new dG,
        t[Cu](function (t) {
            i.add({
                id: t.id,
                edge: t,
                fromAgent: t[Du]._di,
                toAgent: t.$to._di
            })
        }),
        t[Ru](function (t) {
            t instanceof RY && Re(t, i)
        }),
        i
    }
    function Pe(t, i, n) {
        return Be(t, i, n) === !1 ? !1 : Ne(t, i, n)
    }
    function Ne(t, i, n) {
        if (t._g2) for (var e = t._g2; e;) {
            if (i[Yh](n, e) === !1) return !1;
            e = e._i4
        }
    }
    function Be(t, i, n) {
        if (t._hr) for (var e = t._hr; e;) {
            if (i[Yh](n, e) === !1) return !1;
            e = e._jr
        }
    }
    function $e(t, i, e, s, h, r, a) {
        return r || a ? (r = r || 0, a = a === n ? r : a || 0, r = Math.min(r, s / 2), a = Math.min(a, h / 2), t[Pu](i + r, e), t[Nu](i + s - r, e), t[Bu](i + s, e, i + s, e + a), t[Nu](i + s, e + h - a), t[Bu](i + s, e + h, i + s - r, e + h), t[Nu](i + r, e + h), t[Bu](i, e + h, i, e + h - a), t.lineTo(i, e + a), t.quadTo(i, e, i + r, e), t[sf](), t) : (t[Pu](i, e), t[Nu](i + s, e), t.lineTo(i + s, e + h), t[Nu](i, e + h), t[sf](), t)
    }
    function Fe(t, i) {
        var n = i.r || 1,
            e = i.cx || 0,
            s = i.cy || 0,
            h = n * Math.tan(Math.PI / 8),
            r = n * Math.sin(Math.PI / 4);
        t[Pu](e + n, s),
        t[Bu](e + n, s + h, e + r, s + r),
        t.quadTo(e + h, s + n, e, s + n),
        t[Bu](e - h, s + n, e - r, s + r),
        t[Bu](e - n, s + h, e - n, s),
        t[Bu](e - n, s - h, e - r, s - r),
        t[Bu](e - h, s - n, e, s - n),
        t.quadTo(e + h, s - n, e + r, s - r),
        t[Bu](e + n, s - h, e + n, s)
    }
    function qe(t, i, n, e, s) {
        i instanceof tn && (e = i[Ca], s = i[Da], n = i.cy - s / 2, i = i.cx - e / 2);
        var h = .5522848,
            r = e / 2 * h,
            a = s / 2 * h,
            o = i + e,
            f = n + s,
            u = i + e / 2,
            c = n + s / 2;
        return t[Pu](i, c),
        t.curveTo(i, c - a, u - r, n, u, n),
        t.curveTo(u + r, n, o, c - a, o, c),
        t[$u](o, c + a, u + r, f, u, f),
        t[$u](u - r, f, i, c + a, i, c),
        t
    }
    function Ge(t, i, n, e, s) {
        var h = 2 * e,
            r = 2 * s,
            a = i + e / 2,
            o = n + s / 2;
        return t[Pu](a - h / 4, o - r / 12),
        t[Nu](i + .306 * e, n + .579 * s),
        t.lineTo(a - h / 6, o + r / 4),
        t[Nu](i + e / 2, n + .733 * s),
        t.lineTo(a + h / 6, o + r / 4),
        t[Nu](i + .693 * e, n + .579 * s),
        t[Nu](a + h / 4, o - r / 12),
        t.lineTo(i + .611 * e, n + .332 * s),
        t[Nu](a + 0, o - r / 4),
        t.lineTo(i + .388 * e, n + .332 * s),
        t.closePath(),
        t
    }
    function ze(t, i, n, e, s) {
        return t.moveTo(i, n),
        t.lineTo(i + e, n + s / 2),
        t[Nu](i, n + s),
        t.closePath(),
        t
    }
    function He(t, i, n, e, s) {
        return t[Pu](i, n + s / 2),
        t[Nu](i + e / 2, n),
        t[Nu](i + e, n + s / 2),
        t[Nu](i + e / 2, n + s),
        t[sf](),
        t
    }
    function Ye(t, i, n, e, s, h) {
        return t[Pu](i, n),
        t[Nu](i + e, n + s / 2),
        t.lineTo(i, n + s),
        h || (t[Nu](i + .25 * e, n + s / 2), t[sf]()),
        t
    }
    function Ue(t, i, n, e, s) {
        if (!t || 3 > t) throw new Error("edge number must greater than 2");
        t = 0 | t,
        e = e || 50,
        s = s || 0,
        i = i || 0,
        n = n || 0;
        for (var h, r, a = 0, o = 2 * Math.PI / t, f = new cH; t > a;) h = i + e * Math.cos(s),
        r = n + e * Math.sin(s),
        a ? f.lineTo(h, r) : f[Pu](h, r),
        ++a,
        s += o;
        return f.closePath(),
        f
    }
    function We() {
        var t = new cH;
        return t[Pu](75, 40),
        t.curveTo(75, 37, 70, 25, 50, 25),
        t[$u](20, 25, 20, 62.5, 20, 62.5),
        t[$u](20, 80, 40, 102, 75, 120),
        t[$u](110, 102, 130, 80, 130, 62.5),
        t.curveTo(130, 62.5, 130, 25, 100, 25),
        t.curveTo(85, 25, 75, 37, 75, 40),
        t
    }
    function Ve() {
        var t = new cH;
        return t.moveTo(20, 0),
        t.lineTo(80, 0),
        t.lineTo(100, 100),
        t.lineTo(0, 100),
        t[sf](),
        t
    }
    function Xe() {
        var t = new cH;
        return t.moveTo(100, 0),
        t.lineTo(100, 80),
        t[Nu](0, 100),
        t.lineTo(0, 20),
        t[sf](),
        t
    }
    function Ke() {
        var t = new cH;
        return t[Pu](20, 0),
        t[Nu](100, 0),
        t[Nu](80, 100),
        t[Nu](0, 100),
        t.closePath(),
        t
    }
    function Ze() {
        var t = new cH;
        return t[Pu](43, 23),
        t[Nu](28, 10),
        t[Nu](37, 2),
        t.lineTo(63, 31),
        t[Nu](37, 59),
        t[Nu](28, 52),
        t[Nu](44, 38),
        t[Nu](3, 38),
        t.lineTo(3, 23),
        t[sf](),
        t
    }
    function Je() {
        var t = new cH;
        return t.moveTo(1, 8),
        t[Nu](7, 2),
        t[Nu](32, 26),
        t[Nu](7, 50),
        t[Nu](1, 44),
        t.lineTo(18, 26),
        t.closePath(),
        t[Pu](27, 8),
        t[Nu](33, 2),
        t[Nu](57, 26),
        t[Nu](33, 50),
        t[Nu](27, 44),
        t[Nu](44, 26),
        t[sf](),
        t
    }
    function Qe() {
        var t = new cH;
        return t[Pu](0, 15),
        t[Nu](23, 15),
        t[Nu](23, 1),
        t[Nu](47, 23),
        t.lineTo(23, 43),
        t[Nu](23, 29),
        t[Nu](0, 29),
        t.closePath(),
        t
    }
    function ts() {
        var t = new cH;
        return t[Pu](0, 21),
        t[Nu](30, 21),
        t[Nu](19, 0),
        t[Nu](25, 0),
        t[Nu](47, 25),
        t[Nu](25, 48),
        t[Nu](19, 48),
        t[Nu](30, 28),
        t.lineTo(0, 28),
        t.closePath(),
        t
    }
    function is() {
        var t = new cH;
        return t[Pu](0, 0),
        t[Nu](34, 24),
        t[Nu](0, 48),
        t.lineTo(14, 24),
        t[sf](),
        t
    }
    function ns() {
        var t = new cH;
        return t[Pu](20, 0),
        t[Nu](34, 14),
        t[Nu](20, 28),
        t[Nu](22, 18),
        t[Nu](1, 25),
        t.lineTo(10, 14),
        t[Nu](1, 3),
        t.lineTo(22, 10),
        t[sf](),
        t
    }
    function es() {
        var t = new cH;
        return t[Pu](4, 18),
        t.lineTo(45, 18),
        t[Nu](37, 4),
        t[Nu](83, 25),
        t[Nu](37, 46),
        t[Nu](45, 32),
        t.lineTo(4, 32),
        t[sf](),
        t
    }
    function ss() {
        var t = new cH;
        return t[Pu](17, 11),
        t.lineTo(27, 11),
        t[Nu](42, 27),
        t.lineTo(27, 42),
        t[Nu](17, 42),
        t[Nu](28, 30),
        t[Nu](4, 30),
        t[Nu](4, 23),
        t[Nu](28, 23),
        t[sf](),
        t
    }
    function hs() {
        NY[Fu](Mz[qu], qe(new cH, 0, 0, 100, 100)),
        NY[Fu](Mz[Gu], $e(new cH, 0, 0, 100, 100)),
        NY[Fu](Mz[zu], $e(new cH, 0, 0, 100, 100, 20, 20)),
        NY[Fu](Mz[Hu], Ge(new cH, 0, 0, 100, 100)),
        NY[Fu](Mz[Yu], ze(new cH, 0, 0, 100, 100)),
        NY[Fu](Mz[Uu], Ue(5)),
        NY.register(Mz[Wu], Ue(6)),
        NY.register(Mz[Vu], He(new cH, 0, 0, 100, 100)),
        NY[Fu](Mz[Xu], We()),
        NY[Fu](Mz[Ku], Ve()),
        NY[Fu](Mz[Zu], Xe()),
        NY[Fu](Mz[Ju], Ke());
        var t = new cH;
        t.moveTo(20, 0),
        t[Nu](40, 0),
        t[Nu](40, 20),
        t[Nu](60, 20),
        t[Nu](60, 40),
        t[Nu](40, 40),
        t[Nu](40, 60),
        t[Nu](20, 60),
        t[Nu](20, 40),
        t[Nu](0, 40),
        t[Nu](0, 20),
        t[Nu](20, 20),
        t[sf](),
        NY.register(Mz.SHAPE_CROSS, t),
        NY.register(Mz.SHAPE_ARROW_STANDARD, Ye(new cH, 0, 0, 100, 100)),
        NY[Fu](Mz[Qu], Ze()),
        NY[Fu](Mz.SHAPE_ARROW_2, Je()),
        NY[Fu](Mz[tc], Qe()),
        NY[Fu](Mz[ic], ts()),
        NY.register(Mz[nc], is()),
        NY[Fu](Mz.SHAPE_ARROW_6, ns()),
        NY[Fu](Mz.SHAPE_ARROW_7, es()),
        NY[Fu](Mz.SHAPE_ARROW_8, ss()),
        NY[Fu](Mz[ec], Ye(new cH, 0, 0, 100, 100, !0))
    }
    function rs() {
        w(this, rs, arguments),
        this[sc] = !0
    }
    function as() {
        w(this, as),
        this._$u = new BG
    }
    function os() {
        if (this._g6 === !0) {
            var t = this._7l,
                i = this._8m;
            if (t) for (t = t._j8; t[Hh];) {
                    var n = t[0];
                    Ee(this, n, n[Eu])
                }
            if (i) for (i = i._j8; i[Hh];) {
                    var n = i[0];
                    pe(this, n, n.fromAgent)
                }
            return void this[Ru](function (t) {
                    t._hh() && os[Yh](t)
                })
        }
        var e = Re(this);
        e.forEach(function (t) {
            t = t[hc];
            var i = t.$from,
                n = t.$to,
                e = i[ju](this),
                s = n[ju](this);
            e && !s ? (me(this, t), ge(this, t)) : s && !e && (xe(this, t), ge(t[pu], t, this))
        }, this)
    }
    function fs() {
        w(this, fs, arguments),
        this[rc] = null
    }
    function us(t, i, n, e) {
        return t[i] = n,
        e ? {
            get: function () {
                return this[i]
            },
            set: function (t) {
                if (t !== this[i]) {
                    this[i] = t,
                    !this[ac],
                    this._1e = !0;
                    for (var n = e[Hh]; --n >= 0;) this[e[n]] = !0
                }
            }
        } : {
            get: function () {
                return this[i]
            },
            set: function (t) {
                t !== this[i] && (this[i] = t)
            }
        }
    }
    function cs(t, i) {
        var n = {},
            e = {};
        for (var s in i) {
                var h = i[s];
                h.validateFlags && h.validateFlags[Vf](function (t, i, n) {
                    n[i] = oc + t,
                    e[t] = !0
                }),
                n[s] = us(t, ar + s, h.value, h[fc])
            }
        for (var r in e) t[oc + r] = !0;
        Object[Br](t, n)
    }
    function _s(t, i, n, e) {
        if (Array[wr](i)) for (var s = i[Hh]; --s >= 0;) _s(t, i[s], n, e);
        else {
            var h = i.target;
            if (h) {
                if (h instanceof $Y || (h = t[h]), !h) return
            } else h = t;
            if (e || (e = t[uc](i.property, n)), i.bindingProperty && (h[i[cc]] = e), i[_c]) {
                var r = i.callback;
                r instanceof Function || (r = t[r]),
                r instanceof Function && r[Yh](t, e, h)
            }
        }
    }
    function ds() {
        FY[Vf](function (t) {
            this[t] = {}
        }, this)
    }
    function ls(t, i, n, e) {
        return e == Mz[dc] ? void(t[n] = i) : e == Mz[lc] ? void t.set(n, i) : e == Mz[vc] ? void t[bc](n, i) : !1
    }
    function vs() {
        w(this, vs, arguments)
    }
    function bs() {
        w(this, bs, arguments)
    }
    function gs(t, i, n, e) {
        var s = ys(t, i, n, e),
            h = [];
        if (ps(t)) ms(s, i, n, h, e[gc](qY.EDGE_EXTEND));
        else {
                Ls(t, i, n, h, s, e);
                var r = Es(t, e),
                    a = r ? js(t, s, i, n, e[gc](qY[yc])) : e[gc](qY[mc]);
                0 == a && (s = !s)
            }
        return h
    }
    function ys(t, i, n) {
        if (null != t) {
            if (t == Mz[Ec] || t == Mz.EDGE_TYPE_ORTHOGONAL_HORIZONTAL || t == Mz[xc] || t == Mz.EDGE_TYPE_EXTEND_LEFT || t == Mz[pc]) return !0;
            if (t == Mz[wc] || t == Mz.EDGE_TYPE_ORTHOGONAL_VERTICAL || t == Mz[Tc] || t == Mz.EDGE_TYPE_EXTEND_TOP || t == Mz[kc]) return !1
        }
        var e = ks(i, n),
            s = Os(i, n);
        return e >= s
    }
    function ms(t, i, n, e, s) {
        t ? Ns(i, n, e, s) : Bs(i, n, e, s)
    }
    function Es(t, i) {
        return i[gc](qY.EDGE_SPLIT_BY_PERCENT)
    }
    function xs(t) {
        return null != t && (t == Mz.EDGE_TYPE_EXTEND_TOP || t == Mz[Oc] || t == Mz[kc] || t == Mz.EDGE_TYPE_EXTEND_RIGHT)
    }
    function ps(t) {
        return t && (t == Mz.EDGE_TYPE_ELBOW || t == Mz[Ec] || t == Mz[wc])
    }
    function ws(t, i, n, e, s) {
        if (t == Mz[xc] || t == Mz.EDGE_TYPE_VERTICAL_HORIZONTAL) return new bG(e.x + e[Ca] / 2, e.y + e[Da] / 2);
        var h;
        if (xs(t)) {
            var r = Math.min(n.y, e.y),
                a = Math.min(n.x, e.x),
                o = Math.max(n[Yr], e.bottom),
                f = Math.max(n[Ur], e[Ur]);
            if (h = s[gc](qY.EDGE_EXTEND), t == Mz.EDGE_TYPE_EXTEND_TOP) return new bG((a + f) / 2, r - h);
            if (t == Mz.EDGE_TYPE_EXTEND_LEFT) return new bG(a - h, (r + o) / 2);
            if (t == Mz[kc]) return new bG((a + f) / 2, o + h);
            if (t == Mz[pc]) return new bG(f + h, (r + o) / 2)
        }
        var u = Es(t, s);
        if (h = u ? js(t, i, n, e, s[gc](qY[yc])) : s[gc](qY[mc]), h == Number[jc] || h == Number.POSITIVE_INFINITY) return new bG(e.x + e.width / 2, e.y + e[Da] / 2);
        if (0 == h) return new bG(n.x + n[Ca] / 2, n.y + n.height / 2);
        if (i) {
            var c = n.x + n[Ur] < e.x + e[Ur];
            return new bG(Is(c, h, n.x, n.width), n.y + n[Da] / 2)
        }
        var _ = n.y + n[Yr] < e.y + e[Yr];
        return new bG(n.x + n.width / 2, Is(_, h, n.y, n[Da]))
    }
    function Ts(t, i, n, e) {
        var s = Math.max(i, e) - Math.min(t, n);
        return s - (i - t + e - n)
    }
    function ks(t, i) {
        var n = Math.max(t.x + t[Ca], i.x + i[Ca]) - Math.min(t.x, i.x);
        return n - t.width - i[Ca]
    }
    function Os(t, i) {
        var n = Math.max(t.y + t.height, i.y + i[Da]) - Math.min(t.y, i.y);
        return n - t[Da] - i[Da]
    }
    function js(t, i, n, e, s) {
        var h = Ms(s, i, n, e, null);
        return h * s
    }
    function Ms(t, i, n, e) {
        return i ? Ss(t, n.x, n[Ur], e.x, e[Ur]) : Ss(t, n.y, n[Yr], e.y, e[Yr])
    }
    function Ss(t, i, n, e, s) {
        var h = Ts(i, n, e, s),
            r = e + s > i + n;
        if (h > 0) {
                if (1 == t) return h + (s - e) / 2;
                if (t >= 0 && 1 > t) return h;
                if (0 > t) return r ? e - i : n - s
            }
        return Math.abs(r && t > 0 || !r && 0 > t ? n - s : i - e)
    }
    function Is(t, i, n, e) {
        return t == i > 0 ? n + e + Math.abs(i) : n - Math.abs(i)
    }
    function As(t, i) {
        var n = t[Hh];
        if (!(3 > n)) {
            var e = i[gc](qY[Mc]);
            if (e != Mz[Sc]) {
                var s = i.getStyle(qY[Ic]),
                    h = 0,
                    r = 0;
                s && (P(s) ? h = r = s : (h = s.x || 0, r = s.y || 0));
                for (var a, o, f, u, c = t[0], _ = t[1], d = null, l = 2; n > l; l++) {
                        var v = t[l],
                            b = _.x - c.x,
                            g = _.y - c.y,
                            E = v.x - _.x,
                            x = v.y - _.y,
                            p = !b || b > -Dz && Dz > b,
                            w = !g || g > -Dz && Dz > g,
                            T = !E || E > -Dz && Dz > E,
                            k = !x || x > -Dz && Dz > x,
                            O = w;
                            (p && k || w && T) && (O ? (a = Math.min(2 == l ? Math.abs(b) : Math.abs(b) / 2, h), o = Math.min(l == n - 1 ? Math.abs(x) : Math.abs(x) / 2, r), f = new bG(_.x - (b > 0 ? a : -a), _.y), u = new bG(_.x, _.y + (x > 0 ? o : -o))) : (a = Math.min(l == n - 1 ? Math.abs(E) : Math.abs(E) / 2, h), o = Math.min(2 == l ? Math.abs(g) : Math.abs(g) / 2, r), f = new bG(_.x, _.y - (g > 0 ? o : -o)), u = new bG(_.x + (E > 0 ? a : -a), _.y)), m(t, _), l--, n--, (f.x != c.x || f.y != c.y) && (y(t, f, l), l++, n++), e == Mz.EDGE_CORNER_BEVEL ? (y(t, u, l), l++, n++) : e == Mz[Ac] && (y(t, [_, u], l), l++, n++)),
                        c = _,
                        _ = v
                    }
                null != d && u.x == _.x && u.y == _.y && m(t, _)
            }
        }
    }
    function Ls(t, i, n, e, s, h) {
        var r = h[gc](qY[Lc]),
            a = null == r;
        if (null != r) {
                var o = (new EG)[Cc](i)[Cc](n);
                o[Dc](r) || (s = Cs(r.x, r.y, o.y, o.x, o.bottom, o[Ur]))
            } else r = ws(t, s, i, n, h);
        s ? Ps(i, n, r, e, a) : Rs(i, n, r, e, a)
    }
    function Cs(t, i, n, e, s, h) {
        return n > i && n - i > e - t && n - i > t - h || i > s && i - s > e - t && i - s > t - h ? !1 : !0
    }
    function Ds(t, i, n) {
        return i >= t.x && i <= t[Ur] && n >= t.y && n <= t[Yr]
    }
    function Rs(t, i, n, e, s) {
        var h = Math.max(t.y, i.y),
            r = Math.min(t.y + t[Da], i.y + i[Da]),
            a = null != n ? n.y : r + (h - r) / 2,
            o = t.x + t[Ca] / 2,
            f = i.x + i[Ca] / 2;
        if (0 == s && null != n && (n.x >= t.x && n.x <= t.x + t[Ca] && (o = n.x), n.x >= i.x && n.x <= i.x + i[Ca] && (f = n.x)), Ds(i, o, a) || Ds(t, o, a) || e[tr](new bG(o, a)), Ds(i, f, a) || Ds(t, f, a) || e[tr](new bG(f, a)), 0 == e[Hh]) if (null != n) Ds(i, n.x, a) || Ds(t, n.x, a) || e[tr](new bG(n.x, a));
        else {
                var u = Math.max(t.x, i.x),
                    c = Math.min(t.x + t[Ca], i.x + i[Ca]);
                e.push(new bG(u + (c - u) / 2, a))
            }
    }
    function Ps(t, i, n, e, s) {
        var h = Math.max(t.x, i.x),
            r = Math.min(t.x + t[Ca], i.x + i.width),
            a = null != n ? n.x : r + (h - r) / 2,
            o = t.y + t.height / 2,
            f = i.y + i[Da] / 2;
        if (0 == s && null != n && (n.y >= t.y && n.y <= t.y + t[Da] && (o = n.y), n.y >= i.y && n.y <= i.y + i[Da] && (f = n.y)), Ds(i, a, o) || Ds(t, a, o) || e[tr](new bG(a, o)), Ds(i, a, f) || Ds(t, a, f) || e.push(new bG(a, f)), 0 == e[Hh]) if (null != n) Ds(i, a, n.y) || Ds(t, a, n.y) || e.push(new bG(a, n.y));
        else {
                var u = Math.max(t.y, i.y),
                    c = Math.min(t.y + t[Da], i.y + i[Da]);
                e.push(new bG(a, u + (c - u) / 2))
            }
    }
    function Ns(t, i, n, e) {
        var s = i.x + i[Ca] < t.x,
            h = t.x + t[Ca] < i.x,
            r = s ? t.x : t.x + t[Ca],
            a = t.y + t.height / 2,
            o = h ? i.x : i.x + i[Ca],
            f = i.y + i[Da] / 2,
            u = e,
            c = s ? -u : u,
            _ = new bG(r + c, a);
        c = h ? -u : u;
        var d = new bG(o + c, f);
        if (s == h) {
                var l = s ? Math.min(r, o) - e : Math.max(r, o) + e;
                n[tr](new bG(l, a)),
                n[tr](new bG(l, f))
            } else if (_.x < d.x == s) {
                var v = a + (f - a) / 2;
                n[tr](_),
                n.push(new bG(_.x, v)),
                n[tr](new bG(d.x, v)),
                n.push(d)
            } else n[tr](_),
        n[tr](d)
    }
    function Bs(t, i, n, e) {
        var s = i.y + i.height < t.y,
            h = t.y + t[Da] < i.y,
            r = t.x + t.width / 2,
            a = s ? t.y : t.y + t[Da],
            o = i.x + i.width / 2,
            f = h ? i.y : i.y + i[Da],
            u = e,
            c = s ? -u : u,
            _ = new bG(r, a + c);
        c = h ? -u : u;
        var d = new bG(o, f + c);
        if (s == h) {
                var l = s ? Math.min(a, f) - e : Math.max(a, f) + e;
                n[tr](new bG(r, l)),
                n[tr](new bG(o, l))
            } else if (_.y < d.y == s) {
                var v = r + (o - r) / 2;
                n[tr](_),
                n.push(new bG(v, _.y)),
                n[tr](new bG(v, d.y)),
                n.push(d)
            } else n[tr](_),
        n[tr](d)
    }
    function $s(t) {
        return t == Mz[Rc] || t == Mz[Pc] || t == Mz[xc] || t == Mz.EDGE_TYPE_ORTHOGONAL_VERTICAL || t == Mz[Tc] || t == Mz[Nc] || t == Mz.EDGE_TYPE_EXTEND_LEFT || t == Mz.EDGE_TYPE_EXTEND_BOTTOM || t == Mz[pc] || t == Mz[Bc] || t == Mz[Ec] || t == Mz.EDGE_TYPE_ELBOW_VERTICAL
    }
    function Fs(t, i) {
        var n, e;
        i && i.width && i[Da] ? (n = i.width, e = i[Da]) : n = e = isNaN(i) ? _G[$c] : i;
        var s = NY[Fc](t, -n, -e / 2, n, e);
        return s || (s = new cH, s[Pu](-n, -e / 2), s[Nu](0, 0), s[Nu](-n, e / 2)),
        s
    }
    function qs(t, i) {
        var n = Math.sin(i),
            e = Math.cos(i),
            s = t.x,
            h = t.y;
        return t.x = s * e - h * n,
        t.y = s * n + h * e,
        t
    }
    function Gs(t, i, n, e, s, h) {
        var r = Math.atan2(e - i, n - t),
            a = new bG(s, h);
        return a[mo] = r,
        qs(a, r),
        a.x += t,
        a.y += i,
        a
    }
    function zs(t, i, e, s, h) {
        i = si(s, i.x, i.y, e.x, e.y),
        e = si(h, e.x, e.y, i.x, i.y);
        var r = Math.PI / 2 + Math[zr](e.y - i.y, e.x - i.x),
            a = t * Math.cos(r),
            o = t * Math.sin(r),
            f = e.x - i.x,
            u = e.y - i.y,
            c = i.x + .25 * f,
            _ = i.y + .25 * u,
            d = i.x + .75 * f,
            l = i.y + .75 * u;
        return [new fH(rH, [c + a, _ + o, d + a, l + o, n, n])]
    }
    function Hs(t, i, e) {
        if (y(t, new fH(eH, [i.x, i.y]), 0), e) {
            if (t[Hh] > 1) {
                var s = t[t[Hh] - 1];
                if (hH == s[yo] && (s[qc] || s[xa][2] === n || null === s.points[2])) return s[xa][2] = e.x,
                s[xa][3] = e.y,
                void(s[qc] = !0);
                if (rH == s[yo] && (s[qc] || s[xa][4] === n || null === s[xa][4])) return s[xa][4] = e.x,
                s[xa][5] = e.y,
                void(s[qc] = !0)
            }
            t.push(new fH(sH, [e.x, e.y]))
        }
    }
    function Ys(t, i, n, e, s, h, r, a) {
        return i[Gc]() ? void(n._f8 = i._9d.toDatas()) : e == s ? void t[zc](n, e, h, r) : void t[Hc](n, e, s, h, r, a)
    }
    function Us(t, i, n, e, s) {
        var h = e == s,
            r = t[Yc][Uc](e),
            a = h ? r : t[Yc][Uc](s);
        if (r && a) {
                var o = i[Wc],
                    f = r.bodyBounds[Qh](),
                    u = h ? f : a[Vc].clone(),
                    c = t.getStyle(qY[Xc]),
                    _ = t[gc](qY[Kc]);
                c && (f.x += c.x || 0, f.y += c.y || 0),
                _ && (u.x += _.x || 0, u.y += _.y || 0);
                var d = i[Gc]();
                if (!h && !o && !d) {
                        var l = e[sc],
                            v = s[sc];
                        if (l != v) {
                                var b, g, y, m, E = i[Zc];
                                l ? (b = r, g = f, y = a, m = u) : (b = a, g = u, y = r, m = f);
                                var x = Js(g, b, l, y, m, E);
                                if (x && 2 == x[Hh]) {
                                    var p = x[0],
                                        w = x[1];
                                    return n[Pu](p.x, p.y),
                                    w.x == p.x && w.y == p.y && (w.y += .01),
                                    n.lineTo(w.x, w.y),
                                    void(n._5s = !0)
                                }
                            }
                    }
                Ys(t, i, n, r, a, o, f, u),
                (!h || d) && Ws(t, i, n, r, a, o, f, u),
                n._5s = !0
            }
    }
    function Ws(t, i, e, s, h, r, a, o) {
        var f = a.center,
            u = o[Jc],
            c = t.fromAtEdge,
            _ = t[Qc];
        if (!c && !_) return void Hs(e._f8, f, u);
        var d = e._f8;
        if (d[Hh]) {
                if (c) {
                    var l = d[0],
                        v = l[t_];
                    a[i_](v.x, v.y) && (l[yo] == rH ? (f = v, v = {
                            x: l.points[2],
                            y: l[xa][3]
                        }, l[xa] = l[xa][Vh](2), l[yo] = hH) : l[yo] == hH && (f = v, v = {
                            x: l[xa][0],
                            y: l[xa][1]
                        }, l[xa] = l[xa].slice(2), l[yo] = sH)),
                    Vs(s, a, v, f, n, n)
                }
                if (_) {
                    var b, g = d[d[Hh] - 1],
                        y = g.lastPoint,
                        m = g.points[Hh],
                        E = y.x === n || y.y === n;
                    m >= 4 && (E || o[i_](y.x, y.y)) && (E || (u = y), b = !0, y = {
                            x: g[xa][m - 4],
                            y: g[xa][m - 3]
                        }, o[i_](y.x, y.y) && (u = y, m >= 6 ? (y = {
                            x: g[xa][m - 6],
                            y: g[xa][m - 5]
                        }, g[xa] = g[xa][Vh](0, 4), g[yo] = hH) : 1 == d[Hh] ? (y = {
                            x: f.x,
                            y: f.y
                        }, g.points = g[xa][Vh](0, 2), g.type = sH) : (g = d[d.length - 2], y = g.lastPoint))),
                    Vs(h, o, y, u, n, n),
                    b && (m = g[xa][Hh], g[xa][m - 2] = u.x, g[xa][m - 1] = u.y, u = null)
                }
            } else {
                var x = Math[zr](u.y - f.y, u.x - f.x),
                    p = Math.cos(x),
                    w = Math.sin(x);
                c && Vs(s, a, u, f, p, w),
                _ && Vs(h, o, f, u, -p, -w)
            }
        Hs(e._f8, f, u)
    }
    function Vs(t, i, e, s, h, r) {
        if (h === n) {
            var a = Math[zr](e.y - s.y, e.x - s.x);
            h = Math.cos(a),
            r = Math.sin(a)
        }
        for (e = {
            x: e.x,
            y: e.y
        }, i[i_](e.x, e.y) || (e = si(i, s.x, s.y, e.x, e.y));;) {
            if (!i[i_](e.x, e.y)) return s;
            if (t.hitTest(e.x - h, e.y - r, _G.LOOKING_EDGE_ENDPOINT_TOLERANCE)) {
                s.x = e.x - h / 2,
                s.y = e.y - r / 2;
                break
            }
            e.x -= h,
            e.y -= r
        }
        return s
    }
    function Xs(t, i, n, e, s, h, r, a) {
        if (i[Gc]()) return i._9d;
        var o = i[Wc];
        if ($s(o)) {
            var f = gs(o, n, e, t, s, h);
            if (!f || !f[Hh]) return null;
            y(f, r, 0),
            f[tr](a),
            o != Mz[Bc] && As(f, t);
            for (var u = [], c = f.length, _ = 1; c - 1 > _; _++) {
                var d = f[_];
                u[tr]($(d) ? new fH(hH, [d[0].x, d[0].y, d[1].x, d[1].y]) : new fH(sH, [d.x, d.y]))
            }
            return u
        }
        if (i.$bundleEnabled) {
            var l = t._2d();
            if (!l) return;
            return zs(l, r, a, n, e)
        }
    }
    function Ks(t, i, n) {
        var e = t.getStyle(qY.EDGE_LOOPED_EXTAND),
            s = t._2d(),
            h = e + .2 * s,
            r = i.x + i.width - h,
            a = i.y,
            o = i.x + i[Ca],
            f = i.y + h;
        e += s;
        var u = .707,
            c = -.707,
            _ = i.x + i.width,
            d = i.y,
            l = _ + u * e,
            v = d + c * e,
            b = {
                x: r,
                y: a
            },
            g = {
                x: l,
                y: v
            },
            y = {
                x: o,
                y: f
            },
            m = b.x,
            E = g.x,
            x = y.x,
            p = b.y,
            w = g.y,
            T = y.y,
            k = ((T - p) * (w * w - p * p + E * E - m * m) + (w - p) * (p * p - T * T + m * m - x * x)) / (2 * (E - m) * (T - p) - 2 * (x - m) * (w - p)),
            O = ((x - m) * (E * E - m * m + w * w - p * p) + (E - m) * (m * m - x * x + p * p - T * T)) / (2 * (w - p) * (x - m) - 2 * (T - p) * (E - m)),
            h = Math[eo]((m - k) * (m - k) + (p - O) * (p - O)),
            j = Math[zr](b.y - O, b.x - k),
            M = Math.atan2(y.y - O, y.x - k),
            S = M - j;
        return 0 > S && (S += 2 * Math.PI),
        Zs(k, O, j, S, h, h, !0, n)
    }
    function Zs(t, i, n, e, s, h, r, a) {
        var o, f, u, c, _, d, l, v, b, g, y;
        if (Math.abs(e) > 2 * Math.PI && (e = 2 * Math.PI), _ = Math[Zh](Math.abs(e) / (Math.PI / 4)), o = e / _, f = o, u = n, _ > 0) {
            d = t + Math.cos(u) * s,
            l = i + Math.sin(u) * h,
            moveTo ? a[Pu](d, l) : a.lineTo(d, l);
            for (var m = 0; _ > m; m++) u += f,
            c = u - f / 2,
            v = t + Math.cos(u) * s,
            b = i + Math.sin(u) * h,
            g = t + Math.cos(c) * (s / Math.cos(f / 2)),
            y = i + Math.sin(c) * (h / Math.cos(f / 2)),
            a[Bu](g, y, v, b)
        }
    }
    function Js(t, i, e, s, h, r) {
        var a = h.cx,
            o = h.cy,
            f = a < t.x,
            u = a > t.right,
            c = o < t.y,
            _ = o > t[Yr],
            d = t.cx,
            l = t.cy,
            v = f || u,
            b = c || _,
            g = r === n || null === r;
        g && (r = Math[zr](o - l, a - d), v || b || (r += Math.PI));
        var y = Math.cos(r),
            m = Math.sin(r),
            E = th(i, t, {
                x: a,
                y: o
            }, -y, -m);
        E || (r = Math[zr](o - l, a - d), v || b || (r += Math.PI), y = Math.cos(r), m = Math.sin(r), E = th(i, t, {
                x: a,
                y: o
            }, -y, -m) || {
                x: d,
                y: l
            });
        var x = th(s, h, {
                x: E.x,
                y: E.y
            }, -E[n_] || y, -E[e_] || m, !1) || {
                x: a,
                y: o
            };
        return e ? [E, x] : [x, E]
    }
    function Qs(t, i, n, e, s, h) {
        var r = i < t.x,
            a = i > t.right,
            o = n < t.y,
            f = n > t[Yr];
        if (r && e > 0) {
                var u = t.x - i,
                    c = n + u * s / e;
                if (c >= t.y && c <= t[Yr]) return {
                        x: t.x,
                        y: c,
                        perX: e,
                        perY: s
                    }
            }
        if (a && 0 > e) {
                var u = t[Ur] - i,
                    c = n + u * s / e;
                if (c >= t.y && c <= t.bottom) return {
                        x: t[Ur],
                        y: c,
                        perX: e,
                        perY: s
                    }
            }
        if (o && s > 0) {
                var _ = t.y - n,
                    d = i + _ * e / s;
                if (d >= t.x && d <= t[Ur]) return {
                        x: d,
                        y: t.y,
                        perX: e,
                        perY: s
                    }
            }
        if (f && 0 > s) {
                var _ = t[Yr] - n,
                    d = i + _ * e / s;
                if (d >= t.x && d <= t[Ur]) return {
                        x: d,
                        y: t[Yr],
                        perX: e,
                        perY: s
                    }
            }
        return h !== !1 ? Qs(t, i, n, -e, -s, !1) : void 0
    }
    function th(t, i, n, e, s, h) {
        if (!i.contains(n.x, n.y)) {
            if (n = Qs(i, n.x, n.y, e, s, h), !n) return;
            return ih(t, i, n, n[n_], n.perY)
        }
        return h === !1 ? ih(t, i, n, e, s) : ih(t, i, {
            x: n.x,
            y: n.y,
            perX: e,
            perY: s
        }, e, s) || ih(t, i, n, -e, -s)
    }
    function ih(t, i, n, e, s) {
        for (;;) {
            if (!i[i_](n.x, n.y)) return;
            if (t[s_](n.x + e, n.y + s)) break;
            n.x += e,
            n.y += s
        }
        return n
    }
    function nh(t) {
        return mn(t) ? t : t[h_](/.(gif|jpg|jpeg|png)$/gi) ? t : (t = J(t), t instanceof Object && t.draw ? t : void 0)
    }
    function eh(t) {
        for (var i = t[yu]; i;) {
            if (i[r_]) return i;
            i = i[yu]
        }
        return null
    }
    function sh() {
        w(this, sh, arguments)
    }
    function hh(t, n, e, s, h, r, a) {
        var o = i[qa](a_);
        o[gu] = o_,
        li(o, hU),
        n && li(o, n);
        var f = i.createElement(f_);
        return r && (uG && (f[u_] = r), iz || (f[c_] = r)),
        f.name = a,
        f.src = e,
        li(f, rU),
        h && li(f, h),
        s && bi(f, __, d_),
        o[l_] = f,
        o[bu](f),
        t[bu](o),
        o
    }
    function rh(t, n) {
        this[v_] = i[qa](a_),
        this._navPane.className = b_,
        li(this[v_], {
            "background-color": g_,
            overflow: y_,
            "user-select": m_,
            position: E_
        }),
        this._top = hh(this._navPane, {
            width: x_
        }, _G[p_], !1, null, n, w_),
        this[T_] = hh(this[v_], {
            height: x_
        }, _G[k_], !1, aU, n, Bo),
        this._right = hh(this._navPane, {
            height: x_,
            right: O_
        }, _G.NAVIGATION_IMAGE_LEFT, !0, aU, n, Ur),
        this._msottom = hh(this._navPane, {
            width: x_,
            bottom: O_
        }, _G.NAVIGATION_IMAGE_TOP, !0, null, n, Yr),
        t.appendChild(this[v_])
    }
    function ah(t, i) {
        if (!_G[k_]) {
            var n = Li(20, 40),
                e = n.g;
            e[nf](e[Na], e[Na]),
            e[Pu](16, 4),
            e[Nu](4, 20),
            e[Nu](16, 36),
            e[bo] = 3,
            e[j_] = uo,
            e[M_] = uo,
            e[S_] = I_,
            e[A_] = L_,
            e[C_] = 5,
            e.stroke(),
            _G.NAVIGATION_IMAGE_LEFT = n.toDataURL();
            var s = Li(n.height, n.width, !1);
            s.g[$o](s[Ca], 0),
            s.g[mo](Math.PI / 2),
            s.g[D_](n, 0, 0),
            _G.NAVIGATION_IMAGE_TOP = s.toDataURL()
        }
        this[du] = t;
        var h = function (i) {
            G(i);
            var n, e, s = i[R_],
                h = s[ur];
            if (Bo == h) n = 1;
            else if (Ur == h) n = -1;
            else if (w_ == h) e = 1;
            else {
                    if (Yr != h) return;
                    e = -1
                }
            uG && (s[gu] = P_, setTimeout(function () {
                    s[gu] = ""
                }, 100)),
            G(i),
            t._k7._91(n, e)
        };
        rh[Yh](this, i, h),
        this._36(i[N_], i.clientHeight)
    }
    function oh(t, i) {
        this[du] = t,
        this.init(i, t)
    }
    function fh() {
        w(this, fh, arguments)
    }
    function uh(t, i) {
        this._msaseCanvas = t,
        this._ik = ae(i),
        this.g = this._ik.g,
        this._92 = new dG
    }
    function ch(t) {
        var i = t[B_],
            n = [];
        return t[$_][Vf](function (i) {
                t.isVisible(i) && t.isSelectable(i) && n[tr](i)
            }),
        i.set(n)
    }
    function _h(t, i, n) {
        var e = t[fo];
        n = n || e,
        i = i || 1;
        var s = null;
        s && n[Ca] * n[Da] * i * i > s && (i = Math[eo](s / n[Ca] / n[Da]));
        var h = Li();
        Wn(h.g),
        h[Ca] = n[Ca] * i,
        h.height = n.height * i,
        t._88._gf(h.g, i, n);
        var r = null;
        try {
            r = h[F_](q_)
        } catch (a) {
            jz[Gr](a)
        }
        return {
            canvas: h,
            data: r,
            width: h.width,
            height: h[Da]
        }
    }
    function dh(t) {
        this[Yc] = t,
        this.topCanvas = t[G_]
    }
    function lh(t, i) {
        this[z_] = t,
        this[H_] = i || Y_
    }
    function vh() {
        w(this, vh, arguments)
    }
    function bh(t, i) {
        if (!t) return i;
        var e = {};
        for (var s in t) e[s] = t[s];
        for (var s in i) e[s] === n && (e[s] = i[s]);
        return e
    }
    function gh() {
        w(this, gh, arguments)
    }
    function yh() {
        w(this, yh, arguments)
    }
    function mh() {
        w(this, mh, arguments)
    }
    function Eh() {
        w(this, Eh, arguments)
    }
    function xh(i, n, e) {
        i += t.pageXOffset,
        n += t.pageYOffset;
        var s = e[ua]();
        return {
            x: i + s[Bo],
            y: n + s.top
        }
    }
    function ph(t, i, n) {
        var e = t[U_],
            s = t[W_];
        t[na][Bo] = i - e / 2 + Ba,
        t[na].top = n - s / 2 + Ba
    }
    function wh(t) {
        var n = i[qa](Ga),
            e = n[zf](za),
            s = getComputedStyle(t, null),
            h = s.font;
        h || (h = s.fontStyle + yr + s.fontSize + yr + s[V_]),
        e[X_] = h;
        var r = t[cr],
            a = r[mr](Ja),
            o = parseInt(s[K_]),
            f = 0,
            u = 0;
        return jz.forEach(a, function (t) {
                var i = e[io](t)[Ca];
                i > f && (f = i),
                u += 1.2 * o
            }),
        {
                width: f,
                height: u
            }
    }
    function Th(t, n) {
        if (Er == typeof t[Z_] && Er == typeof t[J_]) {
            var e = t[cr],
                s = t[Z_];
            t.value = e[Vh](0, s) + n + e.slice(t[J_]),
            t.selectionEnd = t.selectionStart = s + n[Hh]
        } else if (Q_ != typeof i[td]) {
            var h = i.selection[id]();
            h.text = n,
            h.collapse(!1),
            h.select()
        }
    }
    function kh(i) {
        if (Jq) {
            var n = t[nd] || t[da],
                e = t[ed] || t[va];
            return i.select(),
            void t.scrollTo(n, e)
        }
        i[sd]()
    }
    function Oh() {}
    function jh(t) {
        this[Yc] = t,
        this[G_] = t.topCanvas,
        this[hd] = uG ? 8 : 5
    }
    function Mh(t) {
        return t[yo] == hH || t[yo] == rH
    }
    function Sh(t) {
        this[Yc] = t,
        this[G_] = t[G_],
        this[hd] = uG ? 8 : 4,
        this._rotateHandleLength = uG ? 30 : 20
    }
    function Ih(t, i) {
        var n = new EG;
        return n[Pa](Fn.call(t, {
            x: i.x,
            y: i.y
        })),
        n.addPoint(Fn[Yh](t, {
            x: i.x + i.width,
            y: i.y
        })),
        n.addPoint(Fn[Yh](t, {
            x: i.x + i[Ca],
            y: i.y + i.height
        })),
        n[Pa](Fn[Yh](t, {
            x: i.x,
            y: i.y + i[Da]
        })),
        n
    }
    function Ah(t) {
        t %= 2 * Math.PI;
        var i = Math[uo](t / uU);
        return 0 == i || 4 == i ? "ew-resize" : 1 == i || 5 == i ? "nwse-resize" : 2 == i || 6 == i ? "ns-resize" : rd
    }
    function Lh(n, e, s) {
        var h = i[ad],
            r = new jz[od](t.pageXOffset, t[va], h.clientWidth - 2, h[fd] - 2),
            a = n[U_],
            o = n[W_];
        e + a > r.x + r.width && (e = r.x + r.width - a),
        s + o > r.y + r[Da] && (s = r.y + r[Da] - o),
        e < r.x && (e = r.x),
        s < r.y && (s = r.y),
        n.style[Bo] = e + Ba,
        n.style.top = s + Ba
    }
    function Ch(t, i, n, e, s) {
        this[Fo] = t,
        this.type = ud,
        this[cd] = i,
        this[Tr] = n,
        this.data = e,
        this.datas = s
    }
    function Dh(t) {
        this._3r = {},
        this._k7 = t,
        this._k7._1g[_d](this._99, this),
        this[dd] = Mz.INTERACTION_MODE_DEFAULT
    }
    function Rh(t) {
        return t >= 100 && 200 > t
    }
    function Ph(t) {
        return t == SU || t == NU || t == PU || t == LU || t == FU || t == qU
    }
    function Nh() {
        var t, i, n = {},
            e = [],
            s = 0,
            h = {},
            r = 0;
        this[Yc].forEach(function (a) {
                if (this[ld](a)) if (a instanceof RY) {
                    var o = {
                        node: a,
                        id: a.id,
                        x: a.x,
                        y: a.y
                    };
                    for (this[vd] && this[vd](a, o), n[a.id] = o, e[tr](o), s++, i = a.parent; i instanceof BY;) {
                        t || (t = {});
                        var f = t[i.id];
                        f || (f = t[i.id] = {
                            id: i.id,
                            children: []
                        }),
                        f[Wh].push(o),
                        i = i[yu]
                    }
                } else if (a instanceof DY && !a[Ou]() && a[pu] && a[Eu]) {
                    var o = {
                        edge: a
                    };
                    h[a.id] = o,
                    r++
                }
            }, this);
        var a = {};
        for (var o in h) {
                var f = h[o],
                    u = f[hc],
                    c = u[pu],
                    _ = u[Eu],
                    d = c.id + ta + _.id,
                    l = _.id + ta + c.id;
                if (n[c.id] && n[_.id] && !a[d] && !a[l]) {
                        var v = n[c.id],
                            b = n[_.id];
                        f[ku] = v,
                        f.to = b,
                        a[d] = f,
                        this.appendEdgeInfo && this[bd](u, f)
                    } else delete h[o],
                r--
            }
        return {
                groups: t,
                nodesArray: e,
                nodes: n,
                nodeCount: s,
                edges: h,
                edgeCount: r,
                minEnergy: this[gd](s, r)
            }
    }
    function Bh(t) {
        this[Yc] = t,
        this.currentMovingNodes = {}
    }
    function $h() {
        w(this, $h, arguments)
    }
    function Fh(t, i, n, e, s) {
        e ? t.forEachEdge(function (e) {
            var h = e[yd](t);
            h != n && h[md] != s && i(h, t)
        }, this, !0) : t[Ed](function (e) {
            var h = e[Eu];
            h != n && h[md] != s && i(h, t)
        })
    }
    var qh = "20ad489f41c485a3497ddb65636acb759b5d8350a7f9a6b9883e6655e4c01f6c3b5fcd16b18cdfde1690f5c541e2f829e15875f61577321bd34d15d3c638c66a747b8124185d0f1ebe24e4d41f2ae45dd2ed666e70fb104154d8e16f0fa1766bc1b7a65eff16426a69e160fce1dd2500a04b2c1c48c3e0c5e52ca9fc2eadc7bd2be279a2e713e165d4fdcc1cae11e0086c140ee91695b734ec80c87655d1750c92ecd288595be631c455e5d7a16f7040f6f1ff11758e29dbb51fda61447492291c0a2ee717afa69eb54fa0db10bfabc1cbc01b56d40b628f5b1d7c24b6171fbc6984bee39075a5cab5f4aac5750919dcf98df9965b8b7bb4c02f560a6b6ace823c801e85d7b2a89f1669cd227968a59d66fee68dd9e6d1296496e8484eb5af99efd4baea1a953a980cb27882c5d1518621ecbace25def7a245ab058dd59ffdc22abd4968f47bffbedeafcbd729dcb83f511d506f6eb3f9dc078233a3077e0b595e7675f4ffcb49958c8d65847ceb443472ebdca18720c60f444c6455f15286d74b511da0be87d9dcc20a57187837e94d86f4e2f30d480a7dfe8385200076beb9a4d6707a08b8162387d69ba93ac0c1c52b7841f47eec5a0c12c96cf0a84558fd2a0d277705f30bfa0eff958e8fcc76dee0fb25dbdbd36071117afbf08506a1f744fe28b36644aa2476936f6e9aee0bd950b1b47cd328701ae870b81151d31d7ab0d2c775508220e800741b7b428a33523b71634bf2c42d05013bb5443f0f61555c125458ec74192908717151014bbc07044e1c1307efead837eaaa0e00317e8f1957afbc8607854437981ae0e301e8a2f858d1b074590f2c1e948b9c244dcb4ecb1a72518bb74450e7bb44fd7936a059db66cb0adfb43956767a2f0bcea7b199bad1cc47e95e620e7ea8e749c361819b6ae3bd8e06d673e9e5537e108def30787415bf66860291667ae43a459685a86a17119a38b0bcbd4cc48815c390c5cdd40e9707c34b5fcac3809249001f06bd9d28998a3ec663d010fedf4c69a026b35ef3ea25fb2929f1310c6b455aa4fe9ea936c4986fb26597d81eacebe5e35e3bef1fa5a47d14b0283ab945f5088254cdea2a10239dc99b928b09a2875eb42fcf4ac8a983d294eccf2b7aab9dab352b58bcf8110c74656ec15c7b0ab07117286daf298c9d0e94d26ddaa973ded5f0438a08f2fd697dcf2351e922199cbf9645f372306fb71850ff90c2580011b94ef5dbdbab13198e293531582c89a31a4632601c4117798deb7d5524b05349d8fbe0d5d26c1a79adb81d81c094293f483e90e4253843b10662dca46a9376af0782674635941a229e4b7377166564e77c964790e3056966eea85024ce7acfd0a6dd9b3e7af5f1093d152ceab5e04b508e31c01e3e19193d4994007630f434a2b823058f30e836c3fd05339c7998eb9c1f61c849c4015003fc41667049c5f9d7cb1881b15596742fe34aa3505e90bf88bdddb441ee379e9af350d4223f1e7d540dc3c911046ecd3b757903707da306992d7c8bc59d2c49f43d891621cf5483a84c08278773321d4b0176922eb4fd29117ccfa4461076df36efc76c0721237b238c351d49c43a26e9108cd8c23a206660b83cc189611b694f0ff40d069303776f8b4d6cf6832a383fc9aa567796749d86db9968dd6f8dde1ef4f713ef2b5ba748aa6874fb3ad5195945b106b9caec405a42dada8a01c7537598dda5582c2c85093b18b60c10923334aeb634beb8613a95d8dfd7bcc3f3b53fd0b8ba102f5707038540986b181aa8f08efcf1a12ff7d959e4e57be3dbce38beb11efb4ce9fc60fd665e2e047d51b96086f78ba5832a34b42d490362eb6df673c5f6e1f7e7d8c59244eb55a6f1476b5e08263b448e4b7197aee5576ac950b0940c0fead0a567024793f18b9472b426c8ec601fbb9d50ba7e712faa2f899aaa65724739341b2d5641fa578a2bb2890a10ba73cf39690304163be0f13e60347030573ef5021cbc6db0bcfd443b7d221f035d43476581f3fbfca3be49186eb21a70e1a1051d25c0412dcf46bf25b192825dd96d6ee886d02bcbd86d78a9f967f437342a88aaf96b043de59acd26b150efe4f8d6ae8f3ac719265c6054a5cff2f797da5dd677d7d230f9b6238eef5561ef52d5deabb8f32c9b8fddf3c781628ea8cba6760a2da1b56ba044db7f21bfe5b71c4b5e57d3f57c0bcfc6f7cfa737d7ad07489a2a3e9234d935a3d1e7b4496d4544c6de9d569eaf48719c31a8e55bbe7d31332cbbe957542c6348fd579ca4a66e95b93325dc25ba624b2b672ebf0053480707d6e633852ea806219a0fe797366651289ffa25f69ff8f2b33d3e379d217ea7901c2ffe8f68b256920f0dfbccd42a9c046a23e946acd417207792dae5992345b2d3f9470079839791e3accb36438c4fb4a91f5ba098ec7d536e0fc7fd7e4eeed11b3589945382f5e1d475a08a8b23604b8d80c4e2dabfb28284bc4c41fb412bea7ee914c5bf1ae263ed26cbdf46b11087ee79c6d7f367843160e1f6437537fdd6d4b609a438fce0f67f2d26cd5df7284d7fb201d6e1703fcc1729efaaeae1ef7b0012aeee2098235ad80441bbbd2d0241450a09665e50d1def362f727344c0a1cdb07ad7b693f7947e7863d1bb7bd4335bf04e9b27a2f2c3fa1771a4903b5db121832b8bd8124fb51a40c56f20725b25be46e6295b01306515e8b508d9fcba287a63e5ba21436755ab5b961dbc1d8c3e10c9c3d103d6feaf994b5713c179c72a37bf0a2e72052072780898fa1d777687863d0788e8f28c91a1c6cb2da7850ca93104b223d21417fb2a9ac0cec7d52bcff1eb1cabd27812be51d310fc0e2e01645a2875863b0120f43a4b5c591d9322c1480073365d6c987e47f6473f972e3d0d870b6f578674a49189d63ca9ecf439aaa3931df03597e52b37051a8482a43ed2cacdf92378b7524e8762c7405cb2096b34469787c160480569b10152187f742535999b3924b03bf3311e987b28cbfe51d665b64317e6f498fc232cc5db272edb721242f6cb3d3405ea4faa5047da4d7bf76bd7454b70679170375ecfbe7bd9ac0d67d206f9272b14ad579c6f56ca708e5ae7b9c7da2288ccf7940172d7424a629b8264c791174b793a5f7c0866920705b9d02217d14e3faf3788eb523825b493fcf0f0dc5ac0237c65ba4147bd01d0c024a614388dbf706faead4f78695f6e8549192d0d5cd07b233a5059a7bf640d3ac4d3d72a7b479301dc133d62256e8532559b0a320631d1c160fed4a48cfbb51fcccc37ca65bc00e03870c55f3eb675117d885af1bea632b21e823424d13bb907c3953cb0c479a77c4e75d4593196f9f98dc8c22e57dad52b0e4d423b27946c31ba9f8309466d360093375f963ce07094a89485996518435d9ef9ba9dfe390d53807ea2e8da87189027b55dce3523aca58eca09484f1b020b859809c3d1680340e5e48c21ab339d5856dce3001bd89fc286738adf4544f73d85edf9aab067ba3274d600c59ea65522040aee3c7d07d919405d06808cd779f8a8a791c918c47f5b51bafd6d7e7135318a5974ad1ddfaa5e91fe2ee35178e13cde63fdb254a0280ff5153cae55998a67f45ebad0d9c57ce13456c20e648037e9f71d54345395d0f3b4163a95a9e36abc0d117d785570247a238585b623afd64b395b8340c020ce9cc799afa7c24d52ad0f919eb9657b81923a7857c3816a86c75951bc3bb37c1b85aa5eccd3dad5865c32a2e538f318baf9e65ba7331929e957ae96bf6b31bac011ba34e52c99ae097361557dbdf61d70f51a7a8096eb5c371b696595c4378553e98e68921b2b4c50f57c39a807f1140feba17010d322a260d739f42d866aa61f25297ccedea5c44aa499701a110ad1555cf457940b0b2a477f6dc192d8441e30683e95f2221804af0a382b7f594b469d7be69764615b58159c0b00eef1cd2f970d0c6187ea5f3731503f696483c615c1116957ecef6e3f2a86029217b79d2e072427ba66c43a74a7a1f30465db435f2d683636af4d6c4344cf0e12a4dd642c4ea0be2138fee6c5e670e2107b8746eb40fe6fbdb6249d29c21c21894aa3c67af0487d5a43ba4a4b852be9c62e639189209944318222ba43e554172d5b68cdc668f81ecd03916350c368e5125472a9d4f67821692d99cfcd22cdb635abe58672300a8877b71fda46731d11c6a837d5aa140f92144fe6a1001ebf14e3f75154068e22b017f33621f68e886bfd2486a474818f58c73876e316a89ec179c133b5bbf4eebf4bb330f07b2b5758acea3798e895520d0f8b713047c9ba4a1902c4dfba6c7ec1932f6a0bafb0a04e4604d7abe8225159c95f70d1ab45223858e5735a1d6c32b3dcca057b48ba08e086107e67bb784ef48f1a008745fea339be1a0f0e7f127fa1218a8e9ea7a8e6e6a50c36b47c88f2eabf164daf492784e6c3840182c736f7d56d436104551d145beb05e86b50851252849a3dd44544fa14b5ac0209d052d05803adf4e4895bd74d8289d2eb4dbfe01e0ad01b559e3ff489b83117dbb5cb49712a736030dc64d1982dabee7669f467a38f84ae648f44a4d309ced22399ad76e47262d15d62ba985316030fb6ae74b3ffeb9aeb54961a6e37a98de351012695384156422b10e68eae4e8a22d99e17fb731ddfe64cab2cc46d5c7e6bea9a4e48bc252755ad3b3e7ddc49db4436610d0dd2eec7b6c51fd20a45aadf1bde2da0a9d0e71c927a498b7ee7ac0df0cebf1ff3b610021a2667674ca3725e537a05699ae2757305bc31834a9aa707f5ae25c07a90dee4f587796aef0aabfc68167d1e2e16609898ac6194a7c82b9893e78b7fa3e0aa594e5f1631a40a83aca271fe94fcef375c38666e7adfddb7b65914bf05964188d2efe43772faa3e5f56390bc3082330656436f3391214bf63d1543991f8120e3c11a4911a865a166c721580e2105ab299d159edc61e78e712ea3254abf4867cff5fe5ad24aa9f74ed63ad871dd45caab5b70e382da2530926683bc6d7fb5f662ac0a155b92af1773068ea076d2176cf215d7757c2275493e0dea4781dfd4554c58158f99fd0079c532b5ca8bf8243a679b074ef733f3bb6ebe5674cd2adf621ab15883c7aadcb748c927c08f83eebe23eb48157a3a6932c5e43518341a15359166c282dec51f6c63500473f953efa9abbfe1dc3b72464eee7530c464ea0570c945121692aa3b3d212fbf44422f73044b6962091c4b12a928433f282c5b1281b483ae99a689e52a6917cfbb1f19544d0427405d8fcb0997c43acbfb7a0ded53f45b7803d1ba535bd5bf0e303858bd31cc4308346e87c06858434b71029748e7e9265bdcb6605f8fcce8b54410aefcf4c1b87a0a56efe36651a5e5a50bea8d188f31a16117765f40ba1954e9054191699a671ef75e973bff47d4b8f78625da3702ace293f740686ccd50968cb75658513bc490dee1e4c210d74fc80895b1087eacd0eb34ec53f83a0e597b4dde19ab629ff916603cb912891c1d0a876f773d57faf52da52c3b393a4b3c603cff1b64b90d1ab3568d956d710dba59672aada83e7f331b1b3903789528d313d1be261c2d2895247d436381eb7f3330cb3dcdbacdd68806350728d0494a32613e3cb0f49b946c01335afcaaa3b724b00acc3c81deb82c4f5b4b63e27cec7fdd34fc479aeaf00fc426f40a42d944f096e45f6eb7e0f96b4a80a286104e458c4cc15942dfc5819617e9f7ab12afb1c940051bd0b874d40ead6165402e1537be4300573cad3ea4e24e5e5c147cccfb243f00d3f62ba936e25991b74a392ccd8c2d020e05b7d5a69f7ad288dbdd258f55710b8f735cb3b844461e83125773192d7e81c3aad1387f02ca7a302cf9ec4078cb2d0409732de201fd942c2cda95c4f3fcae30789d223e8cf8cbbe1f79d868ee70749838b984f8e28028f4710df5785c1a2aca2065ffa3fa6a58778c789d940a448f84a36541fa6a5212a5fb60d0cb92b0b3bb75903497b37d52dc438a129ec4dcc676520a1c851fbb22375f2b5d4295e80e46bb1baa288f25500a6a756c88dac4dbf7c6caa42bf6f85785d812957f4f73d6e42e17fc3fd733ee3358b6be90348f29d670b881d0e595d08072cf5dab06e1733b30397b4f4d044b97aeaa14954eb39df92fed28978c46f0951a296a6650cc5b3e38157573151532753a3e67677c5cda6b0a74d98624b4b42ad93f6084dd8138367a5508287ec4dea90ca4790c9a1ca8a7b42f080cb81c569cf80d021b3f9089a94d445db617b2fac4e367bda8261fac81804aea40a9005949215b059227f89ec96de06357fa13a911b538e6a2e74c1652875f4b85808339176edb69586b4e0b43b8ec9832b8d98fd903866d4bd5e4049143595e28033b560e3fe000c1035ee9bd337a619b53a9e061976327fc733c11e299fe980dc081b7413cb7b1f1ee08cd325b31854c8ed226faf0301d776dbf2f4b1c36a9cc4aa876fb408ff6e702f170b3bc59486567edce3549f88de93579bf22aa1073e890bb855a45bd5c284f08d35057ec38422b0a4075be0051398dae04cabc7893841dd2baab06ca120332bb00c1f701e407f1bc0f3567568e8f5832e1ac3734ab7b8a9b77082797634d1d2aea3b08e95f32a78e15c769ebb59ce3abf3d0f1e2e5630ec0f7acccec3e977716c1fc96a5e49ced443cfcc092f73f9c0aa06cc54049030bbd4450a50fadd4aa7aba660fcc2f7d52ece5a75ae2f15f6389aec6cbfcd47d857ba3a928607f3f0f49eccf88b578035c0bde5b83e85ec9b3ae32716cc33c0988455fc9a3ae4abc2c22dd9d22c10458f60c7995cf38b909b8a24448adfc07b71a478a6b6a7234d35441ea453cfbd3c683b0b3eda574b32518ca3ffacde37c208f3c32d2951a1b7a5ba021c50d5f7cf7334fa99df84e8ac63d8857d565f141a17c16a7bdbd020fea746afb714768e5e8401289773aafc68a38ba296b209932c534ec647821df3e92f58817c967f8b6a5622a87a990cc74b1b0fe2fdffe44d032d0dfa568d603fc9e50edd524c8f46e212d843e7000092e11d82a91a58664878c710f820acef26916ae469df4da73ba36821d21935866ea043e1a7a7922384b215f3e4bef0538edb1fab37167531a0f49c67a34aed761212a4232a6d2d39ef036dde7afde3efe2eaba81987807cf594378c3187d02171e602286906f9151f1990b0825c76d19336b4afc4fc877514d0975e9100d9cb6002d01a1beabd0763d906478f83d7cb2d95a65a3deb7e2fd4013cfa2ed18cc3b50936bbc3c11e74cda8b73613519394afac071896c0d762e9ba03c6009bc0036e8e7a8c2f8cf7e66416051cc4a7da697acfab49978a2abd1f0278bfb75fa070cd1b9af80890abe406d380852bcfba40719e90e5936a929609a8a7840315943d6d960c33a47a10947fda1643e5301884c409daca65dc326631dcbce63f29fdabc06b676240fcc480b7af2cc86032c1ef72c2e96adce752df2bf68d6519e6c0bcecb385034e7ad481e18308655c7f402f4069a2e7137ad3f48154862f7c4aa2e4aea04362ec66aa8767b8e58056f6816cf14e386a1cd1cb34c0d136fbe28acb6debdfba58d1221ed333dc6c9b4753f129f4ea5c7e240316b75cb56c133c2b7c4f6dbe75cc9ac21db03c5fd5f7e929f6eb384934ce921bd4865850f7f879481908fb7d241422efea4f75cea2f760536d6fe1ac518b83e133581eb4a6bb9e76a28ae88a044bba42438447fa83b9e97bb6e2883a952226426f1aca5c70d84c0e83cb346e846016241ae37aa9c21b148bb5bcb4602b846189097abd85b62ac9223a33709d16d389796c262c43d3d1fda7d8637449678cb91331f811d796cf283b07a25d0454484c29b0fe163881eea7317b4e857f02d1028c17b373927671dfd6499f6e779f8b1f59f4bf168226a37106639de8981a8dfe8fca92e84cd1b3c583f8e65d24207837691ac7cb169a59f2ad21b6120479dacd4fb69b5a6743c80188653ee69c86829249d37af0044542f0722ce67d5560b7bcfc6d1c58196d9074156e2e78ed41eb3bfc66c579a29c5ef4b37ca51923915d530355c7c212a0b34cee25d658521318a13efd2647da83dff1780001656c8fd575df2c86bf3a0d5c432e78a4dfb7f25835364b265b13b2ce05f4efc7e6ef21ae040d4f9d1cab74a667af63e226dd31795529bd40d7a44952163197822f230137d9911c2c4ed28e482d2b7561c8fe9fa40fe36ff1fc173b24daa2bb09ea58ca32bad6c5d7bf364bf686bbcbeca94c066c91e3edb54cb7aa38108761e423d0486b8415c37d720d7b34d9404834e80b7ec413e6d5f8bda94412fdfac66e62a592173e94b28a16de0f48297c233405e894ced0d9e908fedd29e21eb2303b086a78a5c6fb2825c7fd231643004de547a58b736864b55b5be22c01eace5a89cb3b29d78327bef435d1bcda159d028c7c64a00aad4d8538738f15918f44ab3f267203039da1e8c1abe58e0bc933dbab790958a0d4c490d975111de88d2126d9f11c4bade4d933dc433502101068b8b6e79c106a1813d95804018777f63d2825c46bb60989e6829f811356bcd1123adb4d756ea7e27e818e3be7758a2402f51d05aabde5dfdbf8f5dec5f593ae6eb21c62ec461ad1831a7ebaa3a851fde635ebf69b5e5ea794b86936a00ce0821e8273b10f800d21fa910c2f1a27d91aedb336cefc9163186f0c065142ef6304c554d40387b86b59ef1b9d9bda92f414411c38758b9ff36d82f67c1ca4f352b79660c8d53cf2f0077628201241d1207b29fa2d4911c40b0aa24f9012731d83fbeba6494f01bf07f4737ea5ba0ea6fd2e69d8c717730f338141e5fcc9cd622d671d15e14465a993c5792cb6ee1292b138a6975cd1365eb4a3dff807db7f8ebb4d81c73c5d8154e552fc0a0d6237e71c4a18e960c1c2e97c357c5b42b161bb193ff9d67c663d3a50bc6fa2958a865278fe34a8932f816a37e8e6550d1a7be45a17ad8b770bca8ae45bd75b71c95c4cb20687ca8e91d2b50e6abffa061d16f27f6557b866c77bb2cd8f642f14a67680270dae234d3b7dbe651d7904be7ba5c1f5052bb7da7b4f5365e1b3df66e1cc5e48ab333365d4b2d85b089329bda81361b2b87c3762cc5c937f37cdab9e68553522b7d9bbad0f1c8433fec1ddd840c8882e8f2e619e7227ac15da0ac342f108e4eb960978dadd1e0905bc5a7dfd20c325245cf35f75c269a400b7bf73c013c96a556ba5a9206885c2cfaee247b1c49e349a17566d8d619eb8a532696cca25bbdeed4efd5f583f57aa6bdc87574ae0e93210004f938003417c1572fcaad08b09c2305e084e21350ac6b722007ce11fbb3a374ba5970984dd8e8bc88d00c72c8c22c62b3df3f406c224c7d4fc2fcdde913bc07cb01450752ecd5e8de67d59cf1e2c8789a47b94c56372f54d82b9b09d4973c8d64329fd9a22a821d95e2de3cbd5f065b38c339042ee4a927b0e9c13233f3d885db1bfd0a418998360eb98c7b0f69d46ed823a7daab996c55fe9f190baea06ea3b22a4ab86987c3ad00cb4c7bb51f5dca9dc8b3eec16f6676c763e65f222f0fc212482a378111626c8449550e6089e86d5ada5e4a529231960890a4cdb4d3e80c9bb8383084d237c0f957bfcd1468d9dc71e81b4f0f38ebad5686a5193d3fd2f3dea612946f27a2680e1f82b12cba7452210589fe774fa3f86027f9987d67fc8e4904838ad310b74cd59baa07db0caf64a049e29708ca3be58b9590a35752f5d219ac65c95c4bf8764eb01269b05209975415ab28a425225c552e214612add4f974daeef27cae13f8030b89e34132833eacdb2d9833be48725ad66dee4f01bff4e193d6e9e0c60b487a22e745269e36aeb0a979f00c79c3e5d19a16a4c548b8641912f047cd5e186bfadc2a6e4dc6116c20ff20476205b4b75263f7d7e0b832f1c91b49fe7bc6492801af3e2c5a92765b0e4d65a014f0b9d42462f607e91af7539310ef7989a69e3fda446f7d535cf5e681c4b207ea8da375b874f65fe648363a93c141ba9e6b1c2d0afd03606f515d94995ac7745d6a605793383b8d0e61dcc376da66144a7495b6d9552794e3cf6b12789d735f4b12649de9fb6a6df76ee53f968e86d3c34f21e7b04112beba5a7a94bc16e27114f5920a10a68eda6b24f3755371dd5b29dfa8c3a6b241917dbbf8f28855ba5c23f966c31a1124b088814a06205290a0dcf58582f3199b75f7c75715423341a9e99e3519cd03826d725372d6133067666ae492f0766884d0263585791362a512e055ffa818714a16e8e54721c87f7c5216e024541611e58a8c0aadfd2a72d473a00f134b366b80066c6a838e76e2c7dc7be5c3c99dd940bb6693e4701f4793b21557735806dce2de26e1b0e0cde2a1dea62b9a325c6ee772eabd146b60941820edf9c1e9f8ee69ce149247a7884436d969338c031f006086c6385a9a341ce2f95aa10aa6ade180577bb8e1de8708d668e3a5b1f013069c4a7d25a42262111d2df3fe4419de713cfb56b56c9dedbbc6a9c212e58c69b5a9bc256e50121557936c4f8af5440b3d9ebd73964407b3aa3386c08563edff9d265ef2d468414528621c751efa3e3e43243e60e47c039b14de642bac770964f03e780b7124d740203aa90b88b2870fda5a84255df1eab455a01d37443a5477fd45b07d714a2dff16d369309bc66c624042766e318801bf585cc4b3f035c9285622fd94bc92ce46f5d2ae9e2462755aeea8b42c172593773dd50e7b55479f132411ce03a0f32d08ba437aa280cff5ba7a813374bde439928e93af7d032f8a74b29bea91fed6e5b4db4a32546196b58159380cce82126849c863b864a1eb694bb9bcbeeae0ee967776892fd1f6015b89ffd654acaed8a2e102aa778e7e6582e711e5ae173ba3abbada8dfad53f57c5628f70240ef9ac07bcf4eb903d52bc0b033d6e346795213446df1cbb05c80c2fe13f45cbe5a42ba62e585a136a3603daa557cae621040512e376d313dcce028cd34b67817361078f82512778684b936795e657cde631edc113dc12d113cf868c41369a9cf57394888686f6c67371d1ae490f2ce28bba26c190329249e8ade13c91dcf18aae8e5c24135fd361ab1bba4355c4546a37d404625abcf07a3b810b423c467375eaa620c433fa7767fafa11c88380ed65daafed10d777cd8547512f2e29547f4af8e13c95ecb0af249ebc794faaac24c9e7011b78114868eec2f31e9335c6b52cc88279187f0dbaca7a6b5c363ca181cf18c0c555e927d98deeb915eca851ec2fb7bcca79466413e0b40b5dd312aeaf485bf7d0f57864c32466374d40bb268206e2d75bd194c5535c029e473105a709513ab21ae5ef4ba88b4bb32df76f04cf8cbdc3290da673d517ca6f572ff77aa0f97c116e3ef0b97692db0fb535ef7e8ccb8cc970edc0cba1959814872ade1beb8607bd3db9762662a3f6a6c7ec8354aa00c2bb26ab48328db6ffbaf8aea476fddd570763fb3ef613df08087a3cfff2e1be4e93112f1fda9f77996b97c65c1dac86067f28b1d37e7303dc5deef956ca11d5c32c61307ea6ed9c1c3322b293bba5ea244ae7f9415bb00a38d4a6ec5fedf1e4d69c108fd7116577c92440bc2e1a61e3da519ba85042ed593e1919fd602fe10e92ee5544041b763fb32441597ae2f7e0f7138abe8ed55bf5cbe1ad8b40bcf023254988eb489d47cd235948c9a3661be8e0a959b9948564312e6fc2cac2a4d8c2bc3dde785e63df085e0649e390a18eeebbc1c9120d2b5cc5d5dc295053f60a0d160aeaa7f18da8309d468ee42a9c8ac35363dde683f4f9b06a8f62e302e64b0c6c8eb3f5ab09f24c627d0da4dfbf96ca46304b3cb092d6d0197cad37bc3bbe44e485cf33fa542916625a4eeb44314b186696ab1f693013209f8a4579acb31a0afdd7cd3ac1d97c80d1f21d11164fec743192d457f0701f6b660f9698a1d67d65d274cb034a154d3e11c1a512b6d6da18330e603167a469d2b990a6d86bf246edb6df6a191a04cbe31b40782d3a8d7739402f91df9f63f7f10af76c986a7cb0c1b0c6aa3006763c0c09bb66a92e1c38db73ccd55bfc21b37b7093e4077b37ae6279d3f81491b3cb73314b428bcf5fd74c70a9bca78ba9f626a6d499b43e5f95d88dbdd5af6d799ca5ccc2734a853825e7139087c7c8702ec3c97142ff34eb44bd052e3168d6304fd699bdaaa06022feed0d164c0b12dab08cbda15b0930d37dba628016d51446929fbdc88845b250f8d1de8ebac2234032493bf9c259a7792f42117e122abecdda4a4e8e3503ce585da2cb429821fe5b0ba5c4ca0f8bbae065deae5b4e6f14b2444274d60db389aa2ec772b5ae91a1469425401594dadd7c50d0e1d79f88ab1b4707b1321788659b23b2d9d4f565e8f6056ba07dd373c34e5002815c0fa58fe303175f90efd77d51a5a1779bd464a5a200c2eb39fe14db07c58e12b4510c172f24d02e4021c234ae750f66aa1fb9c0b5b95cce8ba8fc98f43f9b120da826be4ec3bfbcd2da1dc7b242fffd10f624ca06102fd2989606b7b372a14534999861846783f02fc07638774ec9b863c51000eb83c907dd65b6a8739bcb91f18e3fe3d75243f1a632a7f48364dd2c80c617d811494d88bcd4504c02fe7538d7cc28f4a1635972e77f5b1a4151b1ce8746920fcfb18f254d9de9864eae0c05bd22e1d09db14c944ea2240764f34b5af13212582084ff6fa287a2b05c43cb440ee99d57d8a055316a22b89c9c3ce30b284a9634e188dcf254c637abecce11d6b5368a9dd6a2a1ed20f1b2b896c94ca0bc5158d0422118f31e232ca361738c40d15a39ae22261fd42609cd8beec9811d2723c44e00375ab1406c8342295a9b510936414fd5e36433c9127f3a91f5a2004abaebda61e97e197cfd941c2db31984d9a522dbc39b80eaf5dbfe2ab7a094dd4cc19ed4127871d622278027468f62fe0d53f92188857f69ea8ed91e4f1d3392bec422cd54bd9d6a6e3074827d7fcbe31caf6dd8be3dffd307f7e8090b48b983f433539742655a357e8399a5715e45aeafa8f7ae163726bd9c7c95f00e259d5038199dbdebded61b5ce6f76045ccba77f40103be94065590f2247e8e86c0d8543acda7dc9811e85525f5835f1b9d4dd5363198f987e37e3a2a64cfa1ed8e20b06141714ca2ef471fa916443ef88d0d04115f49b26cda81709fe0a7b256a241737e7569f3a3165df44e830d989eb81b501fec2117cc9648902fe177b07626e9037a2f9f265baf41d06ad7915ae4c8d44a6496f421fa84b6aaa8eac4f85692be1b1eae9ac53b7fe5c28df01b9c8415690588b61a5a094b3f9008b33e6a2e48c3eeeae8819d321e6dfeff6655c656426a0aa69957d63be00f8070b6b88f6eaa6b884cb1914adb6d2c3bb663985efca9e9045eac646ff039feca954f0a8ca257fb7ff395cf496a98d7f2c6011fef0f96fd3e2c42062f0b66b49c0c397fee285cc6f0d2fa6550d3bd6756c5caf351baab0a8fd50b7f5a0e1a667eb65d6f08410773e275bccd397a30572b40eccbe5c6428546260261fada263a3f3e470cba78943bfcf44477b50401f759b550e40a0138dc73cbd5034fd8c2e25a2e2d6d39baf9d19f04c2c16469fa02cccdcc27fb733399d2a7cd704212d82e5c26ccb227e68123f64eadc284048defcf16b59ab186fc7432f320823be2c40b54657985a0cbbdf25c495a8fdb44a5301eaa816f1eb657cce5c9b15191354a1dc8ec7b3de37d52aff47a81506be0dbbdef50e2b85b46ed207c9dd08e72587af25b46e1dec1591659bc6be7bc0bc88e567dc896a5d05477564053f90c8bd2186b26056fc89bb0140a256f37b5eff46dd62edf532b6320217cf45c92b5a0afc7841df5ef554c3e891df6456059c15b457e8a85cdbf1d127f79eadad84c78447d35b6dd621ecec5b5b9cda61426a59b0e522a195dc678e2feb1b2f0ab29794426e1ddb68cf03f8b72744d0c9f737a4da77b89621b4a331f0601fcda48d093d7ec9856f4955d129fc895f1d52a886650369a3bd1b4a2e39c386afd3697c0b7ad3b8371e09f59ca9ca63e90b2032224dcba02eb8b1b64f3efcaf4e4396e93e56bab981893737a29fb135ee8d8e14d9fd28488e3dccc387daf742f0161686d6fbee43ccbaaf81a91fa0a01e8998a213d08bb4f32608938e3108f55418b07c2077213763874c8d117efc8ec49b4b32834962e418a4f69ede6486ef540113b5789335fdfbccb03e16c15d4432c3017fc9bf2a370d48b4cac85a52e1ddb252fb1ba140237568e149536b7fb01eea2f2664fe7ea01a20c9d786e9f4e0ee6e10c0ac63b0c1f603dce5925e5a73bda969302a9d63fc95743ef7c412913c0454e85e595292ee17b8fc916d9d01a92e74728feafe8f8468d1e4e56fc6ae3bfb8c39240a75804889e0e6d1999b42c05602a7bfe53fc4b042624adb8e64064b09974786c2c7319586059afcbfa21a138fd3dd8f70f15c6bf06fe11d022509cd2bbd2d584e8847b244abf566d5ec2950e9e301543947d61b787001dd2394943e57dafeab5d179714f3e950fe8e4a749a8242ad4b22209525e3ffb90d7e0a72cc074ec4e1b5c9d29cabae42de3be5e35a1bd7c23fe2bf4e0f36ce654dc9899b0958f6a94beb7713fe413f0ba239513c2d5513e287392ac0e2a16f6e9ff018dfa5ee62b7a327c954a874eb27ed05e7c3df62a2ccb8e97106b07bdf09310e3a2dbce3232c00ecc57055bf41c2f464e2443a15e566564d92ad90d6189b06d0fa9187d43d6656db4b4e016edcfa80bbdc60910b55ecaa309fd2aa150c519a24cf6b60c33ea657cc266b3ebebfeeb7d5f5ea5f47b0cc686730fa3352c603962bddb83ed33e2fd51e72d380a283d2fb8881accec08fcf5b439f2ea97f8307d90948df1dc6e57df2cf9e05aa43e8a12a95e6a2c8fb2c086ef7cc761992bda811ec5ec38901ffea1326395ee0949843d9538702ac748342d752bf602f871080d8e7d9e2b1135ceb3bfcedbf702c11cc33b741373511db5e3ab21d4ee65fb056109c650357fcbd44cb1c4ad0ba60c984deaf4f1cf8b602cb04bd5bd9bb40598b5e3778326a3118f5c0f1e9130263066b63560d3dceedea0794317394ba6f5dfbb87fdd698f6acf8f5ec49efc31a6d4d3912b9613b68498deb883575f4d92cbd6c1b3cf5ece0dc9ddf986f5afd6e5d61a52185af4fc2f9515f712a26c0fd6dcd97251bfafc82d842a09dab00349c6d6193ed7913a8e60e41dfcacac2b0ac362c2d14e228bad58a1c29bb3c04a31afa222978436ae6f961330388c8c2ca6eac058b356f7a8d75b96488b14593fb8fbd60d1343f6f92f372e547dae4b965ef8e0ebe36fb27a4a2d2b473ce6ab3ad173534d0bf6c630ef8b724834d85217fc3ea90df110f1f90ad401c2af199473b4c308b0df92c332c3010822bf54ea093cb4a70dbbab3b2094c8bfd5d2aedeea3a4485e552b6c8ceafa3155cf0f6a321ccd481b0031f5dcc8fabee0f787320fa72142b229e21c0099fbc72f27d93bdd65f1731cd9d6268e8071a035fa05660f624428236395a81e2dc6e45bef91aeec985f49ba0a38773d24d5111bfce2c82b02bacd11f64e4eca616b09c8d3c78fa59ba74da93e2b7e541aeeddfc76bfc55004fec3a6529f807c1fcf13e549ddfa5cb3719d23525475fb8414941f1201d6a5144624b02dee2ba838f32afd13d9b264f7657c90a125a1cb3104b7f7b665d0b27d4cc3b6d50943a26d5b5393c4b3cf8b915b231df30eb1215ef09a59a26492a3471bc91b286b1c5a6a7437cf67b0beb78425cbcd3826fb82672bdf32bb0e3bf4d6a88ef08ecd6035f49342785b70d2c6136e557d68c14909abbad3c7e8ed9f34c9e5df04c0991896b8c5cc09238abd14a8f9e625f1f5115df9706d811e815379bbf3208ccd9e3f42dec688d6619f2ea483b60dd69b6d4d79e3ef0bb57167e3783cf1fc2e62f1adf4eb1027d572d7f55e0111ee60539180f28c20b3c6b28ac57a8f641f3d58057c853d",
        Gh = "[a,w,s,cf,f,ge,c,sa,Chil,A,WS,34,sd]";
    !
    function (t) {
            function i(t, i) {
                for (var n = "", e = 0; e < i.length; e++) n += i.charCodeAt(e).toString();
                var s = Math.floor(n.length / 5),
                    h = parseInt(n.charAt(s) + n.charAt(2 * s) + n.charAt(3 * s) + n.charAt(4 * s) + n.charAt(5 * s)),
                    r = Math.round(i.length / 2),
                    a = Math.pow(2, 31) - 1,
                    o = parseInt(t.substring(t.length - 8, t.length), 16);
                for (t = t.substring(0, t.length - 8), n += o; n.length > 10;) 
                	n = (parseInt(n.substring(0, 10)) + parseInt(n.substring(10, n.length))).toString();
                n = (h * n + r) % a;
                for (var f = "", u = "", e = 0; e < t.length; e += 2) 
                	f = parseInt(parseInt(t.substring(e, e + 2), 16) ^ Math.floor(n / a * 255)),
                u += String.fromCharCode(f),
                n = (h * n + r) % a;
                return u
            }
            console.log(String.fromCharCode(91) + t + String.fromCharCode(93));
            t = i(t, "QUNEE");
            Gh = JSON.parse(String.fromCharCode(91) + t + String.fromCharCode(93));//添加左中括号和右中括号
            alert(Gh.length);
            Gh.push("www"， "phone");
            console.log(Gh);
        }(qh);
    var zh = Gh[0] + Gh[1] + Gh[2],
        Hh = Gh[3],
        Yh = Gh[4],
        Uh = Gh[5] + Gh[1] + Gh[2],
        Wh = Gh[6],
        Vh = Gh[7],
        Xh = Gh[8],
        Kh = Gh[9],
        Zh = Gh[10],
        Jh = Gh[11],
        Qh = Gh[12],
        tr = Gh[13],
        ir = Gh[14],
        nr = Gh[15],
        er = Gh[16],
        sr = Gh[17],
        hr = Gh[18],
        rr = Gh[19] + Gh[20],
        ar = Gh[21],
        or = Gh[22] + Gh[23] + Gh[24],
        fr = Gh[25] + Gh[26] + Gh[27],
        ur = Gh[28],
        cr = Gh[29],
        _r = Gh[30] + Gh[31] + Gh[32],
        dr = Gh[22] + Gh[33] + Gh[34],
        lr = Gh[35],
        vr = Gh[36] + Gh[37] + Gh[38] + Gh[39] + Gh[40],
        br = Gh[18] + Gh[41] + Gh[42],
        gr = Gh[0] + Gh[37] + Gh[43],
        yr = Gh[44],
        mr = Gh[45],
        Er = Gh[46],
        xr = Gh[47],
        pr = Gh[48],
        wr = Gh[49] + Gh[37] + Gh[50],
        Tr = Gh[51],
        kr = Gh[52] + Gh[53] + Gh[54],
        Or = Gh[55] + Gh[26] + Gh[56],
        jr = Gh[57] + Gh[58] + Gh[59],
        Mr = Gh[60],
        Sr = Gh[61],
        Ir = Gh[62] + Gh[63],
        Ar = Gh[64],
        Lr = Gh[65],
        Cr = Gh[66],
        Dr = Gh[67],
        Rr = Gh[68] + Gh[39] + Gh[69],
        Pr = Gh[70],
        Nr = Gh[20],
        Br = Gh[25] + Gh[26] + Gh[71],
        $r = Gh[72],
        Fr = Gh[73],
        qr = Gh[74],
        Gr = Gh[75],
        zr = Gh[76] + Gh[77],
        Hr = Gh[20] + Gh[78],
        Yr = Gh[79],
        Ur = Gh[80],
        Wr = Gh[81] + Gh[33] + Gh[82],
        Vr = Gh[83] + Gh[26] + Gh[84],
        Xr = Gh[85] + Gh[26] + Gh[84],
        Kr = Gh[22] + Gh[1] + Gh[86] + Gh[87] + Gh[88],
        Zr = Gh[89],
        Jr = Gh[90] + Gh[91],
        Qr = Gh[68] + Gh[92] + Gh[93] + Gh[1] + Gh[94],
        ta = Gh[91],
        ia = Gh[91] + Gh[90] + Gh[91],
        na = Gh[95],
        ea = Gh[96],
        sa = Gh[97],
        ha = Gh[98] + Gh[87] + Gh[99],
        ra = Gh[100],
        aa = Gh[101],
        oa = Gh[102] + Gh[87] + Gh[99],
        fa = Gh[103] + Gh[104] + Gh[105],
        ua = Gh[0] + Gh[58] + Gh[106] + Gh[1] + Gh[107] + Gh[87] + Gh[108],
        ca = Gh[109] + Gh[110],
        _a = Gh[109] + Gh[111],
        da = Gh[112] + Gh[113] + Gh[114],
        la = Gh[112] + Gh[110],
        va = Gh[112] + Gh[115] + Gh[114],
        ba = Gh[112] + Gh[111],
        ga = Gh[20] + Gh[116],
        ya = Gh[20] + Gh[117] + Gh[26] + Gh[118],
        ma = Gh[119] + Gh[120] + Gh[121],
        Ea = Gh[122] + Gh[120] + Gh[121],
        xa = Gh[123],
        pa = Gh[124],
        wa = Gh[125] + Gh[126] + Gh[44] + Gh[127] + Gh[44] + Gh[75] + Gh[72],
        Ta = Gh[128] + Gh[104] + Gh[129],
        ka = Gh[125] + Gh[130] + Gh[44] + Gh[127] + Gh[44] + Gh[75] + Gh[72],
        Oa = Gh[131],
        ja = Gh[132] + Gh[133] + Gh[134],
        Ma = Gh[135],
        Sa = Gh[136],
        Ia = Gh[137] + Gh[33] + Gh[138],
        Aa = Gh[139],
        La = Gh[125] + Gh[140] + Gh[44] + Gh[75],
        Ca = Gh[141],
        Da = Gh[142],
        Ra = Gh[143],
        Pa = Gh[102] + Gh[26] + Gh[144],
        Na = Gh[145],
        Ba = Gh[146],
        $a = Gh[90] + Gh[58] + Gh[147] + Gh[33] + Gh[148] + Gh[26] + Gh[149] + Gh[87] + Gh[150],
        Fa = Gh[151] + Gh[33] + Gh[148] + Gh[26] + Gh[149] + Gh[87] + Gh[150],
        qa = Gh[152] + Gh[23] + Gh[153],
        Ga = Gh[154],
        za = Gh[77] + Gh[155],
        Ha = Gh[156] + Gh[33] + Gh[157],
        Ya = Gh[158] + Gh[20] + Gh[159],
        Ua = Gh[146] + Gh[44],
        Wa = Gh[158] + Gh[20] + Gh[160],
        Va = Gh[161],
        Xa = Gh[162] + Gh[20] + Gh[163],
        Ka = Gh[164] + Gh[37] + Gh[165],
        Za = Gh[166],
        Ja = Gh[167],
        Qa = Gh[168] + Gh[104] + Gh[129],
        to = Gh[169],
        io = Gh[170] + Gh[104] + Gh[129],
        no = Gh[0] + Gh[171] + Gh[172] + Gh[173] + Gh[174],
        eo = Gh[175],
        so = Gh[20] + Gh[176] + Gh[26] + Gh[144],
        ho = Gh[20] + Gh[177] + Gh[178] + Gh[1] + Gh[179],
        ro = Gh[20] + Gh[180] + Gh[87],
        ao = Gh[181],
        oo = Gh[0] + Gh[58] + Gh[182],
        fo = Gh[183],
        uo = Gh[184],
        co = Gh[156] + Gh[104] + Gh[185],
        _o = Gh[49] + Gh[26] + Gh[144] + Gh[171] + Gh[186] + Gh[33] + Gh[187],
        lo = Gh[188],
        vo = Gh[189],
        bo = Gh[190] + Gh[191] + Gh[192],
        go = Gh[193] + Gh[26] + Gh[144],
        yo = Gh[194],
        mo = Gh[195],
        Eo = Gh[20] + Gh[196] + Gh[77] + Gh[197],
        xo = Gh[20] + Gh[198],
        po = Gh[20] + Gh[196] + Gh[199] + Gh[200],
        wo = Gh[20] + Gh[196] + Gh[199] + Gh[197],
        To = Gh[20] + Gh[201] + Gh[199],
        ko = Gh[20] + Gh[196] + Gh[77] + Gh[200],
        Oo = Gh[20] + Gh[201] + Gh[77],
        jo = Gh[21] + Gh[202] + Gh[26] + Gh[144],
        Mo = Gh[203],
        So = Gh[193] + Gh[171] + Gh[204] + Gh[31] + Gh[205],
        Io = Gh[206],
        Ao = Gh[207],
        Lo = Gh[208],
        Co = Gh[209] + Gh[20] + Gh[210],
        Do = Gh[209] + Gh[20] + Gh[163],
        Ro = Gh[211] + Gh[212] + Gh[213],
        Po = Gh[214] + Gh[72] + Gh[215],
        No = Gh[216],
        Bo = Gh[217],
        $o = Gh[218],
        Fo = Gh[219],
        qo = Gh[207] + Gh[44] + Gh[220] + Gh[44] + Gh[75] + Gh[221],
        Go = Gh[0] + Gh[41] + Gh[222],
        zo = Gh[223] + Gh[31] + Gh[205],
        Ho = Gh[63],
        Yo = Gh[224] + Gh[20] + Gh[225],
        Uo = Gh[224] + Gh[20] + Gh[225] + Gh[20] + Gh[226],
        Wo = Gh[224] + Gh[20] + Gh[225] + Gh[20] + Gh[227],
        Vo = Gh[224] + Gh[20] + Gh[225] + Gh[20] + Gh[228] + Gh[20] + Gh[229],
        Xo = Gh[224] + Gh[20] + Gh[225] + Gh[20] + Gh[230],
        Ko = Gh[231] + Gh[44] + Gh[75] + Gh[232],
        Zo = Gh[233],
        Jo = Gh[224] + Gh[20] + Gh[225] + Gh[20] + Gh[234],
        Qo = Gh[235] + Gh[171] + Gh[172] + Gh[173] + Gh[174],
        tf = Gh[143] + Gh[87] + Gh[108],
        nf = Gh[211],
        ef = Gh[20] + Gh[236] + Gh[237] + Gh[238],
        sf = Gh[239] + Gh[26] + Gh[240],
        hf = Gh[20] + Gh[241],
        rf = Gh[21] + Gh[242] + Gh[58] + Gh[197] + Gh[37] + Gh[243] + Gh[26] + Gh[144],
        af = Gh[21] + Gh[195],
        of = Gh[21] + Gh[244] + Gh[110],
        ff = Gh[21] + Gh[244] + Gh[111],
        uf = Gh[21] + Gh[245],
        cf = Gh[246] + Gh[247] + Gh[87] + Gh[248],
        _f = Gh[21] + Gh[249] + Gh[33] + Gh[157],
        df = Gh[21] + Gh[249] + Gh[37] + Gh[243] + Gh[26] + Gh[144],
        lf = Gh[21] + Gh[216],
        vf = Gh[250],
        bf = Gh[21] + Gh[251],
        gf = Gh[21] + Gh[252] + Gh[26] + Gh[84],
        yf = Gh[20] + Gh[253] + Gh[254] + Gh[255] + Gh[171] + Gh[256] + Gh[39] + Gh[257],
        mf = Gh[20] + Gh[253] + Gh[254] + Gh[255],
        Ef = Gh[258] + Gh[254] + Gh[255],
        xf = Gh[20] + Gh[259] + Gh[260] + Gh[33] + Gh[261],
        pf = Gh[21] + Gh[251] + Gh[87] + Gh[262],
        wf = Gh[20] + Gh[263] + Gh[110],
        Tf = Gh[21] + Gh[263] + Gh[110],
        kf = Gh[21] + Gh[263] + Gh[111],
        Of = Gh[20] + Gh[263] + Gh[111],
        jf = Gh[132] + Gh[264] + Gh[26] + Gh[265],
        Mf = Gh[0] + Gh[87] + Gh[108],
        Sf = Gh[266],
        If = Gh[242] + Gh[58] + Gh[197] + Gh[26] + Gh[240],
        Af = Gh[21] + Gh[189],
        Lf = Gh[49] + Gh[23] + Gh[267],
        Cf = Gh[20] + Gh[247] + Gh[87] + Gh[248],
        Df = Gh[190] + Gh[173] + Gh[268],
        Rf = Gh[156] + Gh[41] + Gh[269] + Gh[173] + Gh[268],
        Pf = Gh[270] + Gh[173] + Gh[268],
        Nf = Gh[271] + Gh[41] + Gh[269] + Gh[173] + Gh[268],
        Bf = Gh[270] + Gh[173] + Gh[268] + Gh[31] + Gh[114],
        $f = Gh[271] + Gh[41] + Gh[269] + Gh[173] + Gh[268] + Gh[31] + Gh[114],
        Ff = Gh[190] + Gh[173] + Gh[268] + Gh[31] + Gh[114],
        qf = Gh[272] + Gh[254] + Gh[273],
        Gf = Gh[272] + Gh[171] + Gh[204],
        zf = Gh[0] + Gh[1] + Gh[274],
        Hf = Gh[275] + Gh[39] + Gh[257],
        Yf = Gh[217] + Gh[26] + Gh[276],
        Uf = Gh[277] + Gh[26] + Gh[276],
        Wf = Gh[278],
        Vf = Gh[279] + Gh[23] + Gh[280],
        Xf = Gh[281] + Gh[212] + Gh[282] + Gh[104] + Gh[283],
        Kf = Gh[284],
        Zf = Gh[285],
        Jf = Gh[286],
        Qf = Gh[287],
        tu = Gh[288] + Gh[1] + Gh[213] + Gh[37] + Gh[289],
        iu = Gh[288] + Gh[37] + Gh[289],
        nu = Gh[81] + Gh[1] + Gh[290] + Gh[1] + Gh[213],
        eu = Gh[291] + Gh[92],
        su = Gh[292],
        hu = Gh[293],
        ru = Gh[294],
        au = Gh[295],
        ou = Gh[289],
        fu = Gh[296],
        uu = Gh[297] + Gh[72] + Gh[297] + Gh[72] + Gh[199],
        cu = Gh[77],
        _u = Gh[237],
        du = Gh[20] + Gh[298] + Gh[1] + Gh[299],
        lu = Gh[300] + Gh[58] + Gh[197] + Gh[171] + Gh[155],
        vu = Gh[301],
        bu = Gh[302] + Gh[1] + Gh[86],
        gu = Gh[18] + Gh[303] + Gh[304],
        yu = Gh[305],
        mu = Gh[0] + Gh[23] + Gh[306] + Gh[58] + Gh[307],
        Eu = Gh[68] + Gh[37] + Gh[308],
        xu = Gh[20] + Gh[309],
        pu = Gh[81] + Gh[37] + Gh[308],
        wu = Gh[20] + Gh[310],
        Tu = Gh[49] + Gh[171] + Gh[311],
        ku = Gh[81],
        Ou = Gh[49] + Gh[41] + Gh[312],
        ju = Gh[49] + Gh[173] + Gh[313] + Gh[31] + Gh[205],
        Mu = Gh[314],
        Su = Gh[156] + Gh[1] + Gh[86] + Gh[171] + Gh[204],
        Iu = Gh[6] + Gh[1] + Gh[315],
        Au = Gh[156] + Gh[171] + Gh[204],
        Lu = Gh[316],
        Cu = Gh[279] + Gh[23] + Gh[280] + Gh[23] + Gh[306],
        Du = Gh[21] + Gh[81],
        Ru = Gh[279] + Gh[23] + Gh[280] + Gh[1] + Gh[86],
        Pu = Gh[317] + Gh[104] + Gh[318],
        Nu = Gh[190] + Gh[104] + Gh[318],
        Bu = Gh[319] + Gh[104] + Gh[318],
        $u = Gh[320] + Gh[104] + Gh[318],
        Fu = Gh[321],
        qu = Gh[322] + Gh[20] + Gh[323],
        Gu = Gh[322] + Gh[20] + Gh[324],
        zu = Gh[322] + Gh[20] + Gh[325],
        Hu = Gh[322] + Gh[20] + Gh[326],
        Yu = Gh[322] + Gh[20] + Gh[327],
        Uu = Gh[322] + Gh[20] + Gh[328],
        Wu = Gh[322] + Gh[20] + Gh[329],
        Vu = Gh[322] + Gh[20] + Gh[330],
        Xu = Gh[322] + Gh[20] + Gh[331],
        Ku = Gh[322] + Gh[20] + Gh[332],
        Zu = Gh[322] + Gh[20] + Gh[333],
        Ju = Gh[322] + Gh[20] + Gh[334],
        Qu = Gh[322] + Gh[20] + Gh[335] + Gh[20] + Gh[199],
        tc = Gh[322] + Gh[20] + Gh[335] + Gh[20] + Gh[237],
        ic = Gh[322] + Gh[20] + Gh[335] + Gh[20] + Gh[178],
        nc = Gh[322] + Gh[20] + Gh[335] + Gh[20] + Gh[336],
        ec = Gh[322] + Gh[20] + Gh[335] + Gh[20] + Gh[337],
        sc = Gh[338] + Gh[41] + Gh[339],
        hc = Gh[340],
        rc = Gh[21] + Gh[220],
        ac = Gh[20] + Gh[201] + Gh[297],
        oc = Gh[21] + Gh[249],
        fc = Gh[181] + Gh[39] + Gh[341],
        uc = Gh[0] + Gh[26] + Gh[27],
        cc = Gh[342] + Gh[26] + Gh[27],
        _c = Gh[343],
        dc = Gh[344] + Gh[20] + Gh[345] + Gh[20] + Gh[346],
        lc = Gh[344] + Gh[20] + Gh[345] + Gh[20] + Gh[347],
        vc = Gh[344] + Gh[20] + Gh[345] + Gh[20] + Gh[348],
        bc = Gh[156] + Gh[33] + Gh[349],
        gc = Gh[0] + Gh[33] + Gh[349],
        yc = Gh[350] + Gh[20] + Gh[351] + Gh[20] + Gh[352],
        mc = Gh[350] + Gh[20] + Gh[351] + Gh[20] + Gh[353],
        Ec = Gh[350] + Gh[20] + Gh[345] + Gh[20] + Gh[354] + Gh[20] + Gh[355],
        xc = Gh[350] + Gh[20] + Gh[345] + Gh[20] + Gh[355] + Gh[20] + Gh[356],
        pc = Gh[350] + Gh[20] + Gh[345] + Gh[20] + Gh[357] + Gh[20] + Gh[358],
        wc = Gh[350] + Gh[20] + Gh[345] + Gh[20] + Gh[354] + Gh[20] + Gh[356],
        Tc = Gh[350] + Gh[20] + Gh[345] + Gh[20] + Gh[356] + Gh[20] + Gh[355],
        kc = Gh[350] + Gh[20] + Gh[345] + Gh[20] + Gh[357] + Gh[20] + Gh[359],
        Oc = Gh[350] + Gh[20] + Gh[345] + Gh[20] + Gh[357] + Gh[20] + Gh[360],
        jc = Gh[361] + Gh[20] + Gh[362],
        Mc = Gh[350] + Gh[20] + Gh[363],
        Sc = Gh[350] + Gh[20] + Gh[363] + Gh[20] + Gh[364],
        Ic = Gh[350] + Gh[20] + Gh[363] + Gh[20] + Gh[365],
        Ac = Gh[350] + Gh[20] + Gh[363] + Gh[20] + Gh[366],
        Lc = Gh[350] + Gh[20] + Gh[367] + Gh[20] + Gh[368],
        Cc = Gh[369],
        Dc = Gh[370],
        Rc = Gh[350] + Gh[20] + Gh[345] + Gh[20] + Gh[371],
        Pc = Gh[350] + Gh[20] + Gh[345] + Gh[20] + Gh[371] + Gh[20] + Gh[355],
        Nc = Gh[350] + Gh[20] + Gh[345] + Gh[20] + Gh[357] + Gh[20] + Gh[372],
        Bc = Gh[350] + Gh[20] + Gh[345] + Gh[20] + Gh[354],
        $c = Gh[335] + Gh[20] + Gh[159],
        Fc = Gh[0] + Gh[33] + Gh[261],
        qc = Gh[373] + Gh[104] + Gh[374],
        Gc = Gh[5] + Gh[26] + Gh[240] + Gh[33] + Gh[375],
        zc = Gh[207] + Gh[41] + Gh[312] + Gh[23] + Gh[306],
        Hc = Gh[207] + Gh[23] + Gh[306],
        Yc = Gh[376],
        Uc = Gh[0] + Gh[377],
        Wc = Gh[340] + Gh[104] + Gh[283],
        Vc = Gh[378] + Gh[58] + Gh[182],
        Xc = Gh[350] + Gh[20] + Gh[379] + Gh[20] + Gh[380],
        Kc = Gh[350] + Gh[20] + Gh[381] + Gh[20] + Gh[380],
        Zc = Gh[382],
        Jc = Gh[383],
        Qc = Gh[68] + Gh[37] + Gh[289] + Gh[23] + Gh[306],
        t_ = Gh[384] + Gh[26] + Gh[144],
        i_ = Gh[300],
        n_ = Gh[385] + Gh[110],
        e_ = Gh[385] + Gh[111],
        s_ = Gh[386] + Gh[104] + Gh[387],
        h_ = Gh[388],
        r_ = Gh[389] + Gh[33] + Gh[390] + Gh[303] + Gh[391],
        a_ = Gh[392],
        o_ = Gh[393] + Gh[91] + Gh[254] + Gh[394] + Gh[91] + Gh[303] + Gh[395] + Gh[91] + Gh[58] + Gh[396],
        f_ = Gh[397],
        u_ = Gh[398],
        c_ = Gh[399],
        __ = Gh[400],
        d_ = Gh[195] + Gh[63] + Gh[401] + Gh[402] + Gh[65],
        l_ = Gh[20] + Gh[397],
        v_ = Gh[20] + Gh[403] + Gh[26] + Gh[404],
        b_ = Gh[393] + Gh[91] + Gh[254] + Gh[394] + Gh[91] + Gh[303] + Gh[395],
        g_ = Gh[62] + Gh[63] + Gh[297] + Gh[405] + Gh[297] + Gh[405] + Gh[297] + Gh[405] + Gh[297] + Gh[65],
        y_ = Gh[406],
        m_ = Gh[407],
        E_ = Gh[408],
        x_ = Gh[409] + Gh[410],
        p_ = Gh[411] + Gh[20] + Gh[209] + Gh[20] + Gh[372],
        w_ = Gh[277],
        T_ = Gh[20] + Gh[217],
        k_ = Gh[411] + Gh[20] + Gh[209] + Gh[20] + Gh[360],
        O_ = Gh[297] + Gh[146],
        j_ = Gh[190] + Gh[1] + Gh[412],
        M_ = Gh[190] + Gh[413] + Gh[414],
        S_ = Gh[188] + Gh[33] + Gh[349],
        I_ = Gh[66] + Gh[415],
        A_ = Gh[416] + Gh[1] + Gh[417],
        L_ = Gh[66] + Gh[418],
        C_ = Gh[416] + Gh[58] + Gh[419],
        D_ = Gh[207] + Gh[171] + Gh[172],
        R_ = Gh[420],
        P_ = Gh[421],
        N_ = Gh[109] + Gh[191] + Gh[192],
        B_ = Gh[422] + Gh[212] + Gh[423],
        $_ = Gh[376] + Gh[212] + Gh[423],
        F_ = Gh[68] + Gh[173] + Gh[174] + Gh[424],
        q_ = Gh[220] + Gh[425] + Gh[426],
        G_ = Gh[277] + Gh[1] + Gh[299],
        z_ = Gh[427],
        H_ = Gh[428] + Gh[1] + Gh[429],
        Y_ = Gh[428],
        U_ = Gh[244] + Gh[191] + Gh[192],
        W_ = Gh[244] + Gh[430] + Gh[431],
        V_ = Gh[432] + Gh[39] + Gh[433],
        X_ = Gh[432],
        K_ = Gh[432] + Gh[33] + Gh[157],
        Z_ = Gh[422] + Gh[33] + Gh[434],
        J_ = Gh[422] + Gh[23] + Gh[435],
        Q_ = Gh[436],
        td = Gh[422],
        id = Gh[152] + Gh[87] + Gh[437],
        nd = Gh[438] + Gh[110],
        ed = Gh[438] + Gh[111],
        sd = Gh[439],
        hd = Gh[440] + Gh[33] + Gh[157],
        rd = Gh[441] + Gh[91] + Gh[442],
        ad = Gh[443] + Gh[23] + Gh[153],
        od = Gh[87] + Gh[108],
        fd = Gh[109] + Gh[430] + Gh[431],
        ud = Gh[444],
        cd = Gh[445],
        _d = Gh[102] + Gh[41] + Gh[446],
        dd = Gh[447] + Gh[212] + Gh[213],
        ld = Gh[49] + Gh[41] + Gh[448],
        vd = Gh[302] + Gh[303] + Gh[213] + Gh[171] + Gh[449],
        bd = Gh[302] + Gh[23] + Gh[306] + Gh[171] + Gh[449],
        gd = Gh[450] + Gh[23] + Gh[451] + Gh[39] + Gh[452],
        yd = Gh[176] + Gh[303] + Gh[213],
        md = Gh[20] + Gh[453],
        Ed = Gh[279] + Gh[23] + Gh[280] + Gh[31] + Gh[454] + Gh[23] + Gh[306],
        xd = Gh[455],
        pd = Gh[456] + Gh[37] + Gh[308],
        wd = Gh[271] + Gh[87] + Gh[457] + Gh[37] + Gh[38] + Gh[39] + Gh[40],
        Td = Gh[318] + Gh[87] + Gh[457] + Gh[37] + Gh[38] + Gh[39] + Gh[40],
        kd = Gh[90] + Gh[87] + Gh[457] + Gh[37] + Gh[38] + Gh[39] + Gh[40],
        Od = Gh[156] + Gh[104] + Gh[458],
        jd = Gh[57] + Gh[37] + Gh[38] + Gh[39] + Gh[40],
        Md = Gh[143] + Gh[104] + Gh[458],
        Sd = Gh[66] + Gh[459],
        Id = Gh[20] + Gh[432] + Gh[33] + Gh[349],
        Ad = Gh[460],
        Ld = Gh[20] + Gh[432] + Gh[33] + Gh[157],
        Cd = Gh[20] + Gh[432] + Gh[39] + Gh[433],
        Dd = Gh[20] + Gh[432] + Gh[1] + Gh[461],
        Rd = Gh[20] + Gh[432],
        Pd = Gh[158] + Gh[20] + Gh[348],
        Nd = Gh[0] + Gh[58] + Gh[197] + Gh[171] + Gh[204],
        Bd = Gh[125] + Gh[462] + Gh[44] + Gh[463],
        $d = Gh[0] + Gh[58] + Gh[197] + Gh[171] + Gh[155],
        Fd = Gh[20] + Gh[464],
        qd = Gh[11] + Gh[58] + Gh[197] + Gh[171] + Gh[155],
        Gd = Gh[384] + Gh[1] + Gh[86],
        zd = Gh[465] + Gh[303] + Gh[304],
        Hd = Gh[466] + Gh[33] + Gh[467],
        Yd = Gh[466] + Gh[23] + Gh[153] + Gh[33] + Gh[467],
        Ud = Gh[468] + Gh[104] + Gh[283],
        Wd = Gh[26] + Gh[144] + Gh[63],
        Vd = Gh[405],
        Xd = Gh[469],
        Kd = Gh[20] + Gh[470],
        Zd = Gh[33] + Gh[157] + Gh[63],
        Jd = Gh[370] + Gh[87] + Gh[108],
        Qd = Gh[370] + Gh[26] + Gh[144],
        tl = Gh[102] + Gh[87] + Gh[108],
        il = Gh[471] + Gh[20] + Gh[353],
        nl = Gh[472],
        el = Gh[473],
        sl = Gh[474] + Gh[303] + Gh[304],
        hl = Gh[259],
        rl = Gh[475],
        al = Gh[476],
        ol = Gh[236],
        fl = Gh[477],
        ul = Gh[360] + Gh[20] + Gh[372],
        cl = Gh[360] + Gh[20] + Gh[478],
        _l = Gh[360] + Gh[20] + Gh[359],
        dl = Gh[479] + Gh[20] + Gh[372],
        ll = Gh[479] + Gh[20] + Gh[478],
        vl = Gh[479] + Gh[20] + Gh[359],
        bl = Gh[358] + Gh[20] + Gh[359],
        gl = Gh[358] + Gh[20] + Gh[372],
        yl = Gh[358] + Gh[20] + Gh[478],
        ml = Gh[480],
        El = Gh[481],
        xl = Gh[219] + Gh[482],
        pl = Gh[405] + Gh[194] + Gh[482],
        wl = Gh[405] + Gh[445] + Gh[482],
        Tl = Gh[483] + Gh[53] + Gh[54],
        kl = Gh[484] + Gh[104] + Gh[283],
        Ol = Gh[484] + Gh[72] + Gh[485],
        jl = Gh[405] + Gh[484] + Gh[303] + Gh[304] + Gh[482],
        Ml = Gh[405] + Gh[483] + Gh[53] + Gh[54] + Gh[482],
        Sl = Gh[405] + Gh[29] + Gh[482],
        Il = Gh[484] + Gh[303] + Gh[304],
        Al = Gh[486] + Gh[171] + Gh[204],
        Ll = Gh[483] + Gh[171] + Gh[204],
        Cl = Gh[0] + Gh[1] + Gh[86] + Gh[171] + Gh[204],
        Dl = Gh[487] + Gh[72] + Gh[102],
        Rl = Gh[487] + Gh[72] + Gh[11],
        Pl = Gh[487],
        Nl = Gh[487] + Gh[72] + Gh[223],
        Bl = Gh[488],
        $l = Gh[489] + Gh[23] + Gh[24],
        Fl = Gh[490],
        ql = Gh[491],
        Gl = Gh[116],
        zl = Gh[11] + Gh[41] + Gh[446],
        Hl = Gh[492],
        Yl = Gh[223],
        Ul = Gh[405] + Gh[189] + Gh[482],
        Wl = Gh[405] + Gh[223] + Gh[482],
        Vl = Gh[405] + Gh[483] + Gh[171] + Gh[204] + Gh[482],
        Xl = Gh[493] + Gh[20] + Gh[494],
        Kl = Gh[102],
        Zl = Gh[493] + Gh[20] + Gh[495],
        Jl = Gh[493] + Gh[20] + Gh[496] + Gh[20] + Gh[497],
        Ql = Gh[223] + Gh[72] + Gh[485],
        tv = Gh[20] + Gh[498],
        iv = Gh[20] + Gh[499],
        nv = Gh[22] + Gh[26] + Gh[500] + Gh[1] + Gh[461],
        ev = Gh[22] + Gh[1] + Gh[86] + Gh[37] + Gh[501],
        sv = Gh[68] + Gh[1] + Gh[2],
        hv = Gh[20] + Gh[502] + Gh[237],
        rv = Gh[493] + Gh[20] + Gh[503],
        av = Gh[20] + Gh[205] + Gh[504],
        ov = Gh[68] + Gh[173] + Gh[505],
        fv = Gh[492] + Gh[1] + Gh[506] + Gh[173] + Gh[507],
        uv = Gh[422] + Gh[1] + Gh[506] + Gh[173] + Gh[507],
        cv = Gh[20] + Gh[422] + Gh[212] + Gh[423],
        _v = Gh[189] + Gh[1] + Gh[506] + Gh[173] + Gh[507],
        dv = Gh[489] + Gh[173] + Gh[174] + Gh[26] + Gh[27] + Gh[1] + Gh[506],
        lv = Gh[305] + Gh[1] + Gh[506] + Gh[173] + Gh[507],
        vv = Gh[487] + Gh[171] + Gh[204] + Gh[1] + Gh[506] + Gh[173] + Gh[507],
        bv = Gh[21] + Gh[316],
        gv = Gh[20] + Gh[508] + Gh[171] + Gh[204] + Gh[39] + Gh[257],
        yv = Gh[509],
        mv = Gh[0] + Gh[23] + Gh[510],
        Ev = Gh[11] + Gh[1] + Gh[86],
        xv = Gh[132] + Gh[336] + Gh[511],
        pv = Gh[189] + Gh[512],
        wv = Gh[513],
        Tv = Gh[20] + Gh[514] + Gh[1] + Gh[506] + Gh[41] + Gh[446],
        kv = Gh[515],
        Ov = Gh[104] + Gh[185],
        jv = Gh[68] + Gh[41] + Gh[516] + Gh[1] + Gh[94],
        Mv = Gh[164] + Gh[425] + Gh[517],
        Sv = Gh[293] + Gh[91] + Gh[518],
        Iv = Gh[519],
        Av = Gh[520],
        Lv = Gh[521],
        Cv = Gh[102] + Gh[23] + Gh[24] + Gh[41] + Gh[446],
        Dv = Gh[11] + Gh[23] + Gh[24] + Gh[41] + Gh[446],
        Rv = Gh[522] + Gh[20] + Gh[523] + Gh[20] + Gh[524] + Gh[20] + Gh[525],
        Pv = Gh[526] + Gh[64] + Gh[527] + Gh[64] + Gh[528] + Gh[64] + Gh[529],
        Nv = Gh[530],
        Bv = Gh[531] + Gh[532] + Gh[33] + Gh[533],
        $v = Gh[534] + Gh[64] + Gh[535] + Gh[64] + Gh[536] + Gh[64] + Gh[537] + Gh[64] + Gh[538] + Gh[64] + Gh[539] + Gh[64] + Gh[540] + Gh[64] + Gh[541] + Gh[64],
        Fv = Gh[64] + Gh[526] + Gh[64] + Gh[527] + Gh[64] + Gh[528] + Gh[64] + Gh[529],
        qv = Gh[104] + Gh[542] + Gh[23] + Gh[24],
        Gv = Gh[543] + Gh[20] + Gh[544] + Gh[20] + Gh[524],
        zv = Gh[545] + Gh[173] + Gh[546],
        Hv = Gh[20] + Gh[547] + Gh[171] + Gh[548],
        Yv = Gh[20] + Gh[22] + Gh[191] + Gh[549] + Gh[212] + Gh[532] + Gh[212] + Gh[550],
        Uv = Gh[20] + Gh[22] + Gh[191] + Gh[549] + Gh[212] + Gh[532] + Gh[92] + Gh[196],
        Wv = Gh[132] + Gh[551],
        Vv = Gh[539],
        Xv = Gh[536],
        Kv = Gh[133] + Gh[33] + Gh[552],
        Zv = Gh[132] + Gh[553],
        Jv = Gh[20] + Gh[68] + Gh[554] + Gh[24],
        Qv = Gh[20] + Gh[521] + Gh[23] + Gh[24],
        tb = Gh[22],
        ib = Gh[132] + Gh[555] + Gh[26] + Gh[556] + Gh[104] + Gh[557],
        nb = Gh[132] + Gh[22] + Gh[41] + Gh[558] + Gh[26] + Gh[556] + Gh[39] + Gh[452],
        eb = Gh[132] + Gh[559] + Gh[1] + Gh[560],
        sb = Gh[20] + Gh[561] + Gh[23] + Gh[24],
        hb = Gh[562],
        rb = Gh[563] + Gh[77],
        ab = Gh[563],
        ob = Gh[20] + Gh[559] + Gh[41] + Gh[558] + Gh[26] + Gh[556] + Gh[104] + Gh[557],
        fb = Gh[132] + Gh[564] + Gh[104] + Gh[542] + Gh[23] + Gh[24],
        ub = Gh[132] + Gh[565] + Gh[1] + Gh[315] + Gh[1] + Gh[506],
        cb = Gh[20] + Gh[566],
        _b = Gh[132] + Gh[561] + Gh[212] + Gh[567] + Gh[104] + Gh[542] + Gh[23] + Gh[24],
        db = Gh[20] + Gh[211],
        lb = Gh[568] + Gh[33] + Gh[569],
        vb = Gh[132] + Gh[570],
        bb = Gh[20] + Gh[22] + Gh[23] + Gh[24],
        gb = Gh[571],
        yb = Gh[20] + Gh[572],
        mb = Gh[573],
        Eb = Gh[574],
        xb = Gh[20] + Gh[575],
        pb = Gh[20] + Gh[576],
        wb = Gh[20] + Gh[521] + Gh[104] + Gh[542] + Gh[23] + Gh[24],
        Tb = Gh[535],
        kb = Gh[537],
        Ob = Gh[20] + Gh[49] + Gh[173] + Gh[577] + Gh[1] + Gh[560],
        jb = Gh[132] + Gh[399],
        Mb = Gh[538],
        Sb = Gh[534],
        Ib = Gh[578],
        Ab = Gh[579] + Gh[173] + Gh[580],
        Lb = Gh[581],
        Cb = Gh[20] + Gh[440],
        Db = Gh[582],
        Rb = Gh[583],
        Pb = Gh[584] + Gh[110],
        Nb = Gh[584] + Gh[111],
        Bb = Gh[20] + Gh[561] + Gh[173] + Gh[585] + Gh[173] + Gh[586],
        $b = Gh[20] + Gh[587],
        Fb = Gh[132] + Gh[588] + Gh[104] + Gh[557],
        qb = Gh[589],
        Gb = Gh[132] + Gh[590],
        zb = Gh[566] + Gh[77],
        Hb = Gh[566],
        Yb = Gh[576] + Gh[77],
        Ub = Gh[576],
        Wb = Gh[591] + Gh[77],
        Vb = Gh[591],
        Xb = Gh[20] + Gh[592] + Gh[173] + Gh[585] + Gh[171] + Gh[449],
        Kb = Gh[572] + Gh[77],
        Zb = Gh[572],
        Jb = Gh[0] + Gh[1] + Gh[593] + Gh[33] + Gh[594],
        Qb = Gh[575] + Gh[77],
        tg = Gh[575],
        ig = Gh[0] + Gh[173] + Gh[174],
        ng = Gh[154] + Gh[26] + Gh[595],
        eg = Gh[20] + Gh[596] + Gh[173] + Gh[174],
        sg = Gh[20] + Gh[597] + Gh[41] + Gh[598],
        hg = Gh[22] + Gh[23] + Gh[153] + Gh[87] + Gh[599],
        rg = Gh[22] + Gh[1] + Gh[600],
        ag = Gh[20] + Gh[601] + Gh[511],
        og = Gh[20] + Gh[602] + Gh[171] + Gh[603],
        fg = Gh[20] + Gh[604] + Gh[171] + Gh[605] + Gh[41] + Gh[598],
        ug = Gh[20] + Gh[606] + Gh[33] + Gh[607],
        cg = Gh[132] + Gh[22] + Gh[23] + Gh[24],
        _g = Gh[132] + Gh[521] + Gh[23] + Gh[24],
        dg = Gh[608],
        lg = Gh[20] + Gh[602] + Gh[171] + Gh[605],
        vg = Gh[20] + Gh[561] + Gh[110],
        bg = Gh[20] + Gh[561] + Gh[111],
        gg = Gh[609],
        yg = Gh[610] + Gh[110],
        mg = Gh[610] + Gh[111],
        Eg = Gh[611] + Gh[173] + Gh[580] + Gh[110],
        xg = Gh[611] + Gh[173] + Gh[580] + Gh[111],
        pg = Gh[91] + Gh[271] + Gh[91] + Gh[612] + Gh[91] + Gh[613],
        wg = Gh[91] + Gh[271] + Gh[91] + Gh[612] + Gh[91] + Gh[614],
        Tg = Gh[91] + Gh[271] + Gh[91] + Gh[615],
        kg = Gh[91] + Gh[271] + Gh[91] + Gh[616],
        Og = Gh[91] + Gh[270] + Gh[91] + Gh[612] + Gh[91] + Gh[613],
        jg = Gh[91] + Gh[270] + Gh[91] + Gh[612] + Gh[91] + Gh[614],
        Mg = Gh[91] + Gh[270] + Gh[91] + Gh[615],
        Sg = Gh[91] + Gh[270] + Gh[91] + Gh[616],
        Ig = Gh[617],
        Ag = Gh[317],
        Lg = Gh[618] + Gh[63] + Gh[189] + Gh[96] + Gh[220] + Gh[425] + Gh[619] + Gh[97] + Gh[620] + Gh[621] + Gh[64] + Gh[622] + Gh[623] + Gh[624] + Gh[297] + Gh[625] + Gh[626] + Gh[627] + Gh[628] + Gh[629] + Gh[630] + Gh[631] + Gh[205] + Gh[632] + Gh[425] + Gh[633] + Gh[628] + Gh[634] + Gh[477] + Gh[77] + Gh[635] + Gh[297] + Gh[155] + Gh[77] + Gh[39] + Gh[197] + Gh[636] + Gh[633] + Gh[622] + Gh[637] + Gh[477] + Gh[638] + Gh[186] + Gh[639] + Gh[259] + Gh[640] + Gh[336] + Gh[641] + Gh[642] + Gh[643] + Gh[303] + Gh[644] + Gh[425] + Gh[26] + Gh[645] + Gh[26] + Gh[624] + Gh[212] + Gh[646] + Gh[647] + Gh[205] + Gh[648] + Gh[197] + Gh[649] + Gh[186] + Gh[425] + Gh[177] + Gh[650] + Gh[651] + Gh[652] + Gh[643] + Gh[37] + Gh[653] + Gh[254] + Gh[630] + Gh[110] + Gh[178] + Gh[654] + Gh[655] + Gh[656] + Gh[236] + Gh[657] + Gh[624] + Gh[199] + Gh[1] + Gh[658] + Gh[659] + Gh[259] + Gh[37] + Gh[199] + Gh[197] + Gh[37] + Gh[178] + Gh[660] + Gh[120] + Gh[628] + Gh[111] + Gh[624] + Gh[58] + Gh[661] + Gh[662] + Gh[663] + Gh[664] + Gh[665] + Gh[666] + Gh[630] + Gh[667] + Gh[630] + Gh[668] + Gh[632] + Gh[628] + Gh[425] + Gh[178] + Gh[669] + Gh[670] + Gh[671] + Gh[672] + Gh[336] + Gh[673] + Gh[674] + Gh[632] + Gh[44] + Gh[632] + Gh[64] + Gh[617],
        Cg = Gh[675] + Gh[171] + Gh[186],
        Dg = Gh[20] + Gh[676],
        Rg = Gh[20] + Gh[36] + Gh[677],
        Pg = Gh[20] + Gh[22] + Gh[33] + Gh[678],
        Ng = Gh[20] + Gh[294],
        Bg = Gh[297] + Gh[72] + Gh[297],
        $g = Gh[49] + Gh[104] + Gh[542] + Gh[33] + Gh[679],
        Fg = Gh[300] + Gh[87] + Gh[108],
        qg = Gh[33] + Gh[157],
        Gg = Gh[26] + Gh[144],
        zg = Gh[171] + Gh[680],
        Hg = Gh[26] + Gh[27] + Gh[1] + Gh[506] + Gh[23] + Gh[24],
        Yg = Gh[41] + Gh[42] + Gh[23] + Gh[24],
        Ug = Gh[173] + Gh[507],
        Wg = Gh[26] + Gh[84],
        Vg = Gh[173] + Gh[174],
        Xg = Gh[33] + Gh[681] + Gh[212] + Gh[423],
        Kg = Gh[173] + Gh[174] + Gh[212] + Gh[423],
        Zg = Gh[682] + Gh[446],
        Jg = Gh[140] + Gh[424],
        Qg = Gh[140] + Gh[126],
        ty = Gh[140] + Gh[130],
        iy = Gh[49] + Gh[212] + Gh[683] + Gh[120] + Gh[121],
        ny = Gh[684] + Gh[173] + Gh[586],
        ey = Gh[173] + Gh[585] + Gh[33] + Gh[679],
        sy = Gh[685],
        hy = Gh[686],
        ry = Gh[687],
        ay = Gh[251] + Gh[72] + Gh[688],
        oy = Gh[251],
        fy = Gh[416],
        uy = Gh[689],
        cy = Gh[689] + Gh[72] + Gh[430],
        _y = Gh[689] + Gh[72] + Gh[53],
        dy = Gh[690],
        ly = Gh[690] + Gh[72] + Gh[430],
        vy = Gh[690] + Gh[72] + Gh[53],
        by = Gh[690] + Gh[72] + Gh[430] + Gh[72] + Gh[53],
        gy = Gh[690] + Gh[72] + Gh[53] + Gh[72] + Gh[430],
        yy = Gh[691] + Gh[72] + Gh[277],
        my = Gh[691] + Gh[72] + Gh[217],
        Ey = Gh[691] + Gh[72] + Gh[79],
        xy = Gh[691] + Gh[72] + Gh[80],
        py = Gh[692],
        wy = Gh[693],
        Ty = Gh[688],
        ky = Gh[694],
        Oy = Gh[695],
        jy = Gh[696],
        My = Gh[697],
        Sy = Gh[698],
        Iy = Gh[699],
        Ay = Gh[700],
        Ly = Gh[701],
        Cy = Gh[702],
        Dy = Gh[703],
        Ry = Gh[704],
        Py = Gh[705],
        Ny = Gh[706],
        By = Gh[707],
        $y = Gh[708] + Gh[72] + Gh[709],
        Fy = Gh[708] + Gh[72] + Gh[199],
        qy = Gh[708] + Gh[72] + Gh[77],
        Gy = Gh[708] + Gh[72] + Gh[237],
        zy = Gh[708] + Gh[72] + Gh[178],
        Hy = Gh[708] + Gh[72] + Gh[336],
        Yy = Gh[708] + Gh[72] + Gh[260],
        Uy = Gh[708] + Gh[72] + Gh[504],
        Wy = Gh[708] + Gh[72] + Gh[632],
        Vy = Gh[708] + Gh[72] + Gh[710],
        Xy = Gh[162] + Gh[20] + Gh[711] + Gh[20] + Gh[345] + Gh[20] + Gh[712],
        Ky = Gh[713],
        Zy = Gh[162] + Gh[20] + Gh[711] + Gh[20] + Gh[345] + Gh[20] + Gh[366],
        Jy = Gh[162] + Gh[20] + Gh[711] + Gh[20] + Gh[345] + Gh[20] + Gh[714],
        Qy = Gh[180],
        tm = Gh[162] + Gh[20] + Gh[715] + Gh[20] + Gh[345] + Gh[20] + Gh[716],
        im = Gh[162] + Gh[20] + Gh[715] + Gh[20] + Gh[345] + Gh[20] + Gh[366],
        nm = Gh[162] + Gh[20] + Gh[715] + Gh[20] + Gh[345] + Gh[20] + Gh[717],
        em = Gh[718],
        sm = Gh[719] + Gh[20] + Gh[345],
        hm = Gh[719] + Gh[20] + Gh[720],
        rm = Gh[719] + Gh[20] + Gh[721],
        am = Gh[719] + Gh[20] + Gh[722] + Gh[20] + Gh[723],
        om = Gh[719] + Gh[20] + Gh[345] + Gh[20] + Gh[722],
        fm = Gh[721] + Gh[20] + Gh[365],
        um = Gh[724] + Gh[20] + Gh[210],
        cm = Gh[209] + Gh[20] + Gh[471] + Gh[20] + Gh[159],
        _m = Gh[152] + Gh[1] + Gh[299],
        dm = Gh[244],
        lm = Gh[725] + Gh[104] + Gh[318],
        vm = Gh[726] + Gh[1] + Gh[727] + Gh[104] + Gh[318],
        bm = Gh[728] + Gh[1] + Gh[727] + Gh[104] + Gh[318],
        gm = Gh[21] + Gh[202] + Gh[26] + Gh[144] + Gh[199],
        ym = Gh[21] + Gh[202] + Gh[26] + Gh[144] + Gh[77],
        mm = Gh[471] + Gh[20] + Gh[729] + Gh[20] + Gh[730],
        Em = Gh[156] + Gh[58] + Gh[197] + Gh[87] + Gh[108],
        xm = Gh[20] + Gh[731],
        pm = Gh[305] + Gh[303] + Gh[213],
        wm = Gh[171] + Gh[172] + Gh[44] + Gh[140] + Gh[44] + Gh[75] + Gh[221],
        Tm = Gh[20] + Gh[278],
        km = Gh[20] + Gh[732],
        Om = Gh[20] + Gh[142],
        jm = Gh[168] + Gh[33] + Gh[349],
        Mm = Gh[66] + Gh[733],
        Sm = Gh[168],
        Im = Gh[734],
        Am = Gh[460] + Gh[44],
        Lm = Gh[188] + Gh[104] + Gh[129],
        Cm = Gh[66] + Gh[735],
        Dm = Gh[416] + Gh[31] + Gh[114] + Gh[110],
        Rm = Gh[416] + Gh[31] + Gh[114] + Gh[111],
        Pm = Gh[41] + Gh[736] + Gh[737],
        Nm = Gh[301] + Gh[1] + Gh[417],
        Bm = Gh[23] + Gh[738] + Gh[737],
        $m = Gh[301] + Gh[1] + Gh[417] + Gh[58] + Gh[739] + Gh[212] + Gh[213],
        Fm = Gh[740],
        qm = Gh[741] + Gh[33] + Gh[569],
        Gm = Gh[220],
        zm = Gh[140],
        Hm = Gh[0] + Gh[37] + Gh[742] + Gh[171] + Gh[743],
        Ym = Gh[744],
        Um = Gh[745] + Gh[20] + Gh[345] + Gh[20] + Gh[746],
        Wm = Gh[745] + Gh[20] + Gh[345] + Gh[20] + Gh[747],
        Vm = Gh[748],
        Xm = Gh[152] + Gh[41] + Gh[749] + Gh[254] + Gh[255],
        Km = Gh[152] + Gh[87] + Gh[750] + Gh[254] + Gh[255],
        Zm = Gh[747] + Gh[20] + Gh[745] + Gh[20] + Gh[356],
        Jm = Gh[747] + Gh[20] + Gh[745] + Gh[20] + Gh[355],
        Qm = Gh[746] + Gh[20] + Gh[745],
        tE = Gh[751] + Gh[20] + Gh[747] + Gh[20] + Gh[745],
        iE = Gh[751] + Gh[20] + Gh[747] + Gh[20] + Gh[745] + Gh[20] + Gh[356],
        nE = Gh[751] + Gh[20] + Gh[746] + Gh[20] + Gh[745],
        eE = Gh[658],
        sE = Gh[752],
        hE = Gh[655],
        rE = Gh[753] + Gh[20] + Gh[162] + Gh[20] + Gh[381],
        aE = Gh[753] + Gh[20] + Gh[754] + Gh[20] + Gh[381],
        oE = Gh[26] + Gh[240] + Gh[33] + Gh[755],
        fE = Gh[68] + Gh[130],
        uE = Gh[422] + Gh[33] + Gh[756] + Gh[58] + Gh[419],
        cE = Gh[422] + Gh[1] + Gh[417],
        _E = Gh[422] + Gh[33] + Gh[756] + Gh[31] + Gh[114] + Gh[110],
        dE = Gh[422] + Gh[33] + Gh[756] + Gh[31] + Gh[114] + Gh[111],
        lE = Gh[719] + Gh[20] + Gh[345] + Gh[20] + Gh[721],
        vE = Gh[422] + Gh[58] + Gh[757],
        bE = Gh[758],
        gE = Gh[758] + Gh[33] + Gh[349],
        yE = Gh[62] + Gh[63] + Gh[297] + Gh[64] + Gh[297] + Gh[64] + Gh[297] + Gh[64] + Gh[297] + Gh[65],
        mE = Gh[168] + Gh[1] + Gh[417],
        EE = Gh[168] + Gh[254] + Gh[255],
        xE = Gh[20] + Gh[168] + Gh[254] + Gh[255],
        pE = Gh[190] + Gh[39] + Gh[759] + Gh[1] + Gh[417],
        wE = Gh[190] + Gh[173] + Gh[268] + Gh[413] + Gh[414],
        TE = Gh[760] + Gh[26] + Gh[240],
        kE = Gh[20] + Gh[141],
        OE = Gh[20] + Gh[761] + Gh[26] + Gh[762] + Gh[191] + Gh[192],
        jE = Gh[20] + Gh[761] + Gh[26] + Gh[762],
        ME = Gh[763],
        SE = Gh[764],
        IE = Gh[231] + Gh[72] + Gh[765],
        AE = Gh[224] + Gh[20] + Gh[225] + Gh[20] + Gh[747] + Gh[20] + Gh[229],
        LE = Gh[766] + Gh[72] + Gh[765],
        CE = Gh[224] + Gh[20] + Gh[225] + Gh[20] + Gh[767],
        DE = Gh[768],
        RE = Gh[584],
        PE = Gh[769],
        NE = Gh[102] + Gh[1] + Gh[417] + Gh[33] + Gh[277],
        BE = Gh[66] + Gh[199] + Gh[1] + Gh[260] + Gh[58] + Gh[633] + Gh[173],
        $E = Gh[66] + Gh[770],
        FE = Gh[66] + Gh[771] + Gh[23] + Gh[632] + Gh[58],
        qE = Gh[66] + Gh[772] + Gh[58] + Gh[773],
        GE = Gh[66] + Gh[772] + Gh[37] + Gh[774],
        zE = Gh[66] + Gh[775] + Gh[1] + Gh[776],
        HE = Gh[66] + Gh[777],
        YE = Gh[66] + Gh[199] + Gh[173] + Gh[260] + Gh[1] + Gh[633] + Gh[39],
        UE = Gh[66] + Gh[778] + Gh[58] + Gh[297],
        WE = Gh[66] + Gh[779] + Gh[780],
        VE = Gh[66] + Gh[199] + Gh[39] + Gh[260] + Gh[781] + Gh[77],
        XE = Gh[66] + Gh[772] + Gh[37] + Gh[782],
        KE = Gh[66] + Gh[783],
        ZE = Gh[66] + Gh[77] + Gh[643] + Gh[632] + Gh[784],
        JE = Gh[66] + Gh[785],
        QE = Gh[718] + Gh[41] + Gh[786],
        tx = Gh[66] + Gh[787] + Gh[173] + Gh[776],
        ix = Gh[66] + Gh[788],
        nx = Gh[66] + Gh[199] + Gh[39] + Gh[789] + Gh[37] + Gh[178],
        ex = Gh[66] + Gh[790] + Gh[791] + Gh[77],
        sx = Gh[66] + Gh[792] + Gh[793],
        hx = Gh[66] + Gh[199] + Gh[23] + Gh[260] + Gh[794] + Gh[297],
        rx = Gh[66] + Gh[795],
        ax = Gh[66] + Gh[205] + Gh[504] + Gh[205] + Gh[632] + Gh[205] + Gh[632],
        ox = Gh[66] + Gh[260] + Gh[37] + Gh[796],
        fx = Gh[66] + Gh[178] + Gh[39] + Gh[178] + Gh[1] + Gh[178] + Gh[58],
        ux = Gh[66] + Gh[797],
        cx = Gh[66] + Gh[798],
        _x = Gh[66] + Gh[260] + Gh[39] + Gh[260] + Gh[23] + Gh[260] + Gh[39],
        dx = Gh[66] + Gh[178] + Gh[1] + Gh[799],
        lx = Gh[66] + Gh[800],
        vx = Gh[66] + Gh[504] + Gh[173] + Gh[504] + Gh[173] + Gh[504] + Gh[173],
        bx = Gh[66] + Gh[801],
        gx = Gh[66] + Gh[802],
        yx = Gh[66] + Gh[803],
        mx = Gh[66] + Gh[633] + Gh[23] + Gh[633] + Gh[173] + Gh[633] + Gh[173],
        Ex = Gh[66] + Gh[37] + Gh[504] + Gh[37] + Gh[336] + Gh[37] + Gh[178],
        xx = Gh[66] + Gh[37] + Gh[633] + Gh[37] + Gh[260] + Gh[37] + Gh[336],
        px = Gh[66] + Gh[37] + Gh[504] + Gh[37] + Gh[178] + Gh[37] + Gh[237],
        wx = Gh[66] + Gh[804],
        Tx = Gh[66] + Gh[23] + Gh[633] + Gh[805],
        kx = Gh[66] + Gh[633] + Gh[806] + Gh[297] + Gh[752] + Gh[297],
        Ox = Gh[66] + Gh[475] + Gh[633] + Gh[807],
        jx = Gh[66] + Gh[237] + Gh[643] + Gh[237] + Gh[752] + Gh[808],
        Mx = Gh[66] + Gh[58] + Gh[77] + Gh[809],
        Sx = Gh[66] + Gh[77] + Gh[23] + Gh[632] + Gh[810],
        Ix = Gh[811] + Gh[37] + Gh[812],
        Ax = Gh[66] + Gh[813],
        Lx = Gh[66] + Gh[477] + Gh[336] + Gh[477] + Gh[336] + Gh[477] + Gh[260],
        Cx = Gh[393] + Gh[91],
        Dx = Gh[21] + Gh[249] + Gh[87] + Gh[248],
        Rx = Gh[814],
        Px = Gh[20] + Gh[815],
        Nx = Gh[20] + Gh[816],
        Bx = Gh[20] + Gh[817],
        $x = Gh[20] + Gh[818] + Gh[171] + Gh[172],
        Fx = Gh[819],
        qx = Gh[30] + Gh[58] + Gh[820],
        Gx = Gh[30],
        zx = Gh[30] + Gh[92] + Gh[821],
        Hx = Gh[30] + Gh[58] + Gh[822],
        Yx = Gh[171] + Gh[311] + Gh[44] + Gh[823] + Gh[44] + Gh[824] + Gh[72],
        Ux = Gh[825],
        Wx = Gh[303] + Gh[826] + Gh[44] + Gh[752] + Gh[44] + Gh[825] + Gh[44] + Gh[827] + Gh[72],
        Vx = Gh[828] + Gh[39] + Gh[257],
        Xx = Gh[829],
        Kx = Gh[231] + Gh[87] + Gh[830],
        Zx = Gh[831],
        Jx = Gh[832] + Gh[1] + Gh[417],
        Qx = Gh[833],
        tp = Gh[834] + Gh[212] + Gh[835],
        ip = Gh[456] + Gh[171] + Gh[836],
        np = Gh[814] + Gh[104] + Gh[282],
        ep = Gh[837],
        sp = Gh[838] + Gh[430] + Gh[839],
        hp = Gh[838] + Gh[173] + Gh[174],
        rp = Gh[840],
        ap = Gh[841],
        op = Gh[842],
        fp = Gh[843] + Gh[173] + Gh[174],
        up = Gh[844],
        cp = Gh[845],
        _p = Gh[129] + Gh[104] + Gh[283],
        dp = Gh[846],
        lp = Gh[847],
        vp = Gh[848],
        bp = Gh[843],
        gp = Gh[275] + Gh[33] + Gh[157],
        yp = Gh[849] + Gh[212] + Gh[613] + Gh[1] + Gh[213] + Gh[33] + Gh[157],
        mp = Gh[850],
        Ep = Gh[851],
        xp = Gh[129],
        pp = Gh[852],
        wp = Gh[92] + Gh[853] + Gh[44] + Gh[854] + Gh[482] + Gh[297] + Gh[200],
        Tp = Gh[540],
        kp = Gh[829] + Gh[120] + Gh[121],
        Op = Gh[855] + Gh[1] + Gh[213],
        jp = Gh[167] + Gh[53] + Gh[856] + Gh[221],
        Mp = Gh[857],
        Sp = Gh[167] + Gh[26] + Gh[858] + Gh[44] + Gh[173] + Gh[859] + Gh[221],
        Ip = Gh[860],
        Ap = Gh[205] + Gh[861] + Gh[477] + Gh[178] + Gh[643] + Gh[862] + Gh[863] + Gh[504] + Gh[752] + Gh[864] + Gh[643] + Gh[865] + Gh[475] + Gh[237] + Gh[866] + Gh[633] + Gh[867] + Gh[868] + Gh[869] + Gh[870] + Gh[871] + Gh[297] + Gh[477] + Gh[336] + Gh[205] + Gh[872] + Gh[752] + Gh[873] + Gh[874] + Gh[875] + Gh[475] + Gh[876] + Gh[877] + Gh[237] + Gh[878] + Gh[297] + Gh[879] + Gh[880] + Gh[477] + Gh[881] + Gh[882] + Gh[883] + Gh[884] + Gh[237] + Gh[643] + Gh[885] + Gh[205] + Gh[886] + Gh[887] + Gh[260] + Gh[477] + Gh[888] + Gh[205] + Gh[889] + Gh[890] + Gh[633] + Gh[891] + Gh[892] + Gh[205] + Gh[633] + Gh[475] + Gh[336] + Gh[155] + Gh[199] + Gh[643] + Gh[178] + Gh[643] + Gh[893] + Gh[477] + Gh[77] + Gh[155] + Gh[875] + Gh[894] + Gh[632] + Gh[155] + Gh[237] + Gh[643] + Gh[64] + Gh[895] + Gh[199] + Gh[752] + Gh[896] + Gh[155] + Gh[897] + Gh[752] + Gh[898] + Gh[205] + Gh[899] + Gh[874] + Gh[900] + Gh[901] + Gh[902] + Gh[903] + Gh[178] + Gh[904] + Gh[905] + Gh[906] + Gh[907] + Gh[752] + Gh[504] + Gh[908] + Gh[237] + Gh[475] + Gh[297] + Gh[909] + Gh[910] + Gh[911] + Gh[912] + Gh[643] + Gh[913] + Gh[914] + Gh[297] + Gh[475] + Gh[915] + Gh[916] + Gh[917] + Gh[895] + Gh[918] + Gh[919] + Gh[920] + Gh[475] + Gh[297] + Gh[921] + Gh[922] + Gh[923] + Gh[924] + Gh[475] + Gh[925] + Gh[926] + Gh[633] + Gh[477] + Gh[927] + Gh[475] + Gh[928] + Gh[929] + Gh[260] + Gh[930] + Gh[931] + Gh[752] + Gh[932] + Gh[643] + Gh[199] + Gh[933] + Gh[934] + Gh[935] + Gh[632] + Gh[475] + Gh[936] + Gh[643] + Gh[937] + Gh[929] + Gh[64] + Gh[632] + Gh[938] + Gh[633] + Gh[205] + Gh[77] + Gh[205] + Gh[939] + Gh[643] + Gh[940] + Gh[884] + Gh[941] + Gh[882] + Gh[199] + Gh[475] + Gh[942] + Gh[643] + Gh[178] + Gh[943] + Gh[944] + Gh[155] + Gh[945] + Gh[946] + Gh[947] + Gh[948] + Gh[949] + Gh[950] + Gh[951] + Gh[477] + Gh[633] + Gh[894] + Gh[808] + Gh[475] + Gh[952] + Gh[205] + Gh[953] + Gh[477] + Gh[336] + Gh[501] + Gh[954] + Gh[643] + Gh[955] + Gh[205] + Gh[956] + Gh[752] + Gh[237] + Gh[475] + Gh[633] + Gh[752] + Gh[957] + Gh[958] + Gh[959] + Gh[752] + Gh[960] + Gh[752] + Gh[961] + Gh[962] + Gh[963] + Gh[964] + Gh[965] + Gh[475] + Gh[966] + Gh[475] + Gh[967] + Gh[477] + Gh[632] + Gh[477] + Gh[968] + Gh[877] + Gh[969] + Gh[475] + Gh[237] + Gh[155] + Gh[970] + Gh[891] + Gh[934] + Gh[205],
        Lp = Gh[167] + Gh[41] + Gh[971] + Gh[44] + Gh[68] + Gh[482],
        Cp = Gh[410] + Gh[972] + Gh[973] + Gh[410] + Gh[237] + Gh[37] + Gh[410] + Gh[972] + Gh[974] + Gh[72] + Gh[293] + Gh[72] + Gh[847] + Gh[410] + Gh[77] + Gh[1] + Gh[975] + Gh[72] + Gh[293] + Gh[72] + Gh[847] + Gh[410] + Gh[77] + Gh[1] + Gh[976] + Gh[72] + Gh[293] + Gh[72] + Gh[847],
        Dp = Gh[977],
        Rp = Gh[978],
        Pp = Gh[979],
        Np = Gh[156] + Gh[104],
        Bp = Gh[980],
        $p = Gh[504] + Gh[72],
        Fp = Gh[935],
        qp = Gh[276],
        Gp = Gh[981],
        zp = Gh[1] + Gh[299],
        Hp = Gh[87] + Gh[982],
        Yp = Gh[1] + Gh[983],
        Up = Gh[984],
        Wp = Gh[985],
        Vp = Gh[152],
        Xp = Gh[986],
        Kp = Gh[987],
        Zp = Gh[988],
        Jp = Gh[989],
        Qp = Gh[990],
        tw = Gh[991],
        iw = Gh[458],
        nw = Gh[992],
        ew = Gh[993],
        sw = Gh[393] + Gh[994],
        hw = Gh[44] + Gh[279] + Gh[44] + Gh[995] + Gh[336],
        rw = Gh[996],
        aw = Gh[997],
        ow = Gh[998] + Gh[191] + Gh[549],
        fw = Gh[999],
        uw = Gh[1e3] + Gh[72] + Gh[875] + Gh[72] + Gh[199],
        cw = Gh[33] + Gh[82],
        _w = Gh[1001],
        dw = Gh[23] + Gh[153],
        lw = Gh[304],
        vw = Gh[1002],
        bw = Gh[1003] + Gh[191] + Gh[549],
        gw = Gh[1004],
        yw = Gh[129] + Gh[77] + Gh[173],
        mw = Gh[259] + Gh[104] + Gh[129],
        Ew = Gh[1005],
        xw = Gh[297] + Gh[44] + Gh[297],
        pw = Gh[393] + Gh[91] + Gh[1] + Gh[299],
        ww = Gh[393] + Gh[91] + Gh[1] + Gh[299] + Gh[26] + Gh[595],
        Tw = Gh[393] + Gh[91] + Gh[254] + Gh[394],
        kw = Gh[20] + Gh[1006],
        Ow = Gh[20] + Gh[1007],
        jw = Gh[20] + Gh[259] + Gh[336] + Gh[1008] + Gh[41] + Gh[42],
        Mw = Gh[20] + Gh[336] + Gh[1009],
        Sw = Gh[249],
        Iw = Gh[20] + Gh[508],
        Aw = Gh[1010],
        Lw = Gh[1011],
        Cw = Gh[20] + Gh[1012] + Gh[677],
        Dw = Gh[20] + Gh[1013],
        Rw = Gh[20] + Gh[1014],
        Pw = Gh[214] + Gh[87] + Gh[1015],
        Nw = Gh[1016],
        Bw = Gh[20] + Gh[1017],
        $w = Gh[20] + Gh[1011],
        Fw = Gh[132] + Gh[628] + Gh[633],
        qw = Gh[20] + Gh[1018],
        Gw = Gh[20] + Gh[1019],
        zw = Gh[474],
        Hw = Gh[655] + Gh[171] + Gh[204],
        Yw = Gh[20] + Gh[1020],
        Uw = Gh[20] + Gh[1021],
        Ww = Gh[20] + Gh[508] + Gh[1] + Gh[299] + Gh[33] + Gh[157] + Gh[39] + Gh[257],
        Vw = Gh[1022] + Gh[104] + Gh[185],
        Xw = Gh[20] + Gh[547] + Gh[212] + Gh[1023],
        Kw = Gh[1024] + Gh[212] + Gh[1023],
        Zw = Gh[1025],
        Jw = Gh[1016] + Gh[63],
        Qw = Gh[132] + Gh[1026] + Gh[212] + Gh[1023],
        tT = Gh[1027],
        iT = Gh[0] + Gh[23] + Gh[153] + Gh[58] + Gh[197] + Gh[171] + Gh[155],
        nT = Gh[20] + Gh[1028],
        eT = Gh[1029] + Gh[171] + Gh[204],
        sT = Gh[1030] + Gh[171] + Gh[155],
        hT = Gh[132] + Gh[1031],
        rT = Gh[20] + Gh[1032] + Gh[633],
        aT = Gh[20] + Gh[508] + Gh[58] + Gh[182] + Gh[39] + Gh[257],
        oT = Gh[279] + Gh[23] + Gh[280] + Gh[87] + Gh[1033],
        fT = Gh[68] + Gh[41] + Gh[1034],
        uT = Gh[218] + Gh[104] + Gh[318],
        cT = Gh[20] + Gh[1035] + Gh[1] + Gh[299],
        _T = Gh[20] + Gh[1011] + Gh[1] + Gh[1036],
        dT = Gh[0] + Gh[254] + Gh[1037] + Gh[58] + Gh[182],
        lT = Gh[154] + Gh[58] + Gh[182],
        vT = Gh[20] + Gh[68] + Gh[171] + Gh[1038] + Gh[87] + Gh[108],
        bT = Gh[20] + Gh[1032] + Gh[504],
        gT = Gh[411] + Gh[20] + Gh[1039],
        yT = Gh[1040] + Gh[72] + Gh[562],
        mT = Gh[411] + Gh[20] + Gh[1041],
        ET = Gh[1040] + Gh[72] + Gh[1042],
        xT = Gh[411] + Gh[20] + Gh[345],
        pT = Gh[20] + Gh[1043],
        wT = Gh[20] + Gh[277] + Gh[1] + Gh[299],
        TT = Gh[20] + Gh[1044],
        kT = Gh[20] + Gh[178] + Gh[200],
        OT = Gh[20] + Gh[628] + Gh[633] + Gh[39] + Gh[1045],
        jT = Gh[1030] + Gh[58] + Gh[182],
        MT = Gh[20] + Gh[260] + Gh[624],
        ST = Gh[20] + Gh[1046],
        IT = Gh[20] + Gh[1011] + Gh[1] + Gh[461],
        AT = Gh[20] + Gh[1047] + Gh[37] + Gh[1048],
        LT = Gh[1047] + Gh[37] + Gh[289] + Gh[1] + Gh[1049],
        CT = Gh[249] + Gh[53] + Gh[1050],
        DT = Gh[20] + Gh[205] + Gh[260],
        RT = Gh[20] + Gh[504] + Gh[630] + Gh[1] + Gh[461],
        PT = Gh[1051] + Gh[72] + Gh[183],
        NT = Gh[301] + Gh[33] + Gh[569],
        BT = Gh[68] + Gh[1] + Gh[299],
        $T = Gh[249] + Gh[87] + Gh[1052],
        FT = Gh[1053],
        qT = Gh[1030] + Gh[1] + Gh[1054],
        GT = Gh[1055],
        zT = Gh[22] + Gh[26] + Gh[27] + Gh[1] + Gh[506],
        HT = Gh[20] + Gh[340] + Gh[58] + Gh[307] + Gh[171] + Gh[256] + Gh[39] + Gh[257],
        YT = Gh[181] + Gh[23] + Gh[306] + Gh[58] + Gh[307],
        UT = Gh[279] + Gh[23] + Gh[280] + Gh[58] + Gh[197] + Gh[173] + Gh[1056] + Gh[39] + Gh[1057],
        WT = Gh[132] + Gh[1058],
        VT = Gh[5] + Gh[23] + Gh[306],
        XT = Gh[132] + Gh[178] + Gh[628],
        KT = Gh[0] + Gh[171] + Gh[204] + Gh[58] + Gh[197] + Gh[171] + Gh[155],
        ZT = Gh[377] + Gh[512],
        JT = Gh[125] + Gh[462] + Gh[44] + Gh[1059],
        QT = Gh[156] + Gh[171] + Gh[204] + Gh[37] + Gh[1060],
        tk = Gh[20] + Gh[1035] + Gh[212],
        ik = Gh[20] + Gh[811] + Gh[58] + Gh[182],
        nk = Gh[1030],
        ek = Gh[20] + Gh[1061],
        sk = Gh[393] + Gh[72] + Gh[23] + Gh[153],
        hk = Gh[1062],
        rk = Gh[235] + Gh[33] + Gh[1063],
        ak = Gh[21] + Gh[28],
        ok = Gh[1064],
        fk = Gh[393] + Gh[72] + Gh[23] + Gh[306],
        uk = Gh[20] + Gh[1065],
        ck = Gh[1066],
        _k = Gh[1067] + Gh[23] + Gh[1068],
        dk = Gh[1069] + Gh[72] + Gh[1070],
        lk = Gh[1071] + Gh[26] + Gh[240] + Gh[1] + Gh[506],
        vk = Gh[102] + Gh[26] + Gh[240] + Gh[33] + Gh[755],
        bk = Gh[49] + Gh[303] + Gh[1072],
        gk = Gh[68],
        yk = Gh[393] + Gh[91] + Gh[468],
        mk = Gh[5] + Gh[41] + Gh[1073],
        Ek = Gh[20] + Gh[1074] + Gh[303] + Gh[1075],
        xk = Gh[1076] + Gh[33] + Gh[1077],
        pk = Gh[20] + Gh[1078],
        wk = Gh[21] + Gh[1079],
        Tk = Gh[1079],
        kk = Gh[247],
        Ok = Gh[5] + Gh[39] + Gh[1080],
        jk = Gh[68] + Gh[39] + Gh[1080],
        Mk = Gh[20] + Gh[247],
        Sk = Gh[393] + Gh[72] + Gh[303] + Gh[213],
        Ik = Gh[1081] + Gh[72] + Gh[102],
        Ak = Gh[1081] + Gh[72] + Gh[11],
        Lk = Gh[252] + Gh[26] + Gh[84],
        Ck = Gh[1069],
        Dk = Gh[1082] + Gh[20] + Gh[1083],
        Rk = Gh[335] + Gh[20] + Gh[381],
        Pk = Gh[393] + Gh[72] + Gh[33] + Gh[261] + Gh[303] + Gh[213],
        Nk = Gh[1084],
        Bk = Gh[33] + Gh[261] + Gh[303] + Gh[213],
        $k = Gh[1085],
        Fk = Gh[393] + Gh[72] + Gh[58] + Gh[1086],
        qk = Gh[447] + Gh[33] + Gh[390] + Gh[303] + Gh[391],
        Gk = Gh[1087] + Gh[20] + Gh[1088],
        zk = Gh[1087] + Gh[20] + Gh[1089],
        Hk = Gh[249] + Gh[39] + Gh[257],
        Yk = Gh[1087] + Gh[20] + Gh[345],
        Uk = Gh[21] + Gh[450] + Gh[33] + Gh[157],
        Wk = Gh[1087] + Gh[20] + Gh[1090] + Gh[20] + Gh[159],
        Vk = Gh[393] + Gh[72] + Gh[254] + Gh[1091],
        Xk = Gh[450] + Gh[33] + Gh[157],
        Kk = Gh[1092] + Gh[104] + Gh[283],
        Zk = Gh[1092] + Gh[171] + Gh[172],
        Jk = Gh[254] + Gh[1091],
        Qk = Gh[393] + Gh[72] + Gh[104] + Gh[129],
        tO = Gh[104] + Gh[129],
        iO = Gh[249] + Gh[173] + Gh[174],
        nO = Gh[66] + Gh[1093],
        eO = Gh[21] + Gh[252] + Gh[26] + Gh[144],
        sO = Gh[422] + Gh[58] + Gh[1094] + Gh[1] + Gh[417],
        hO = Gh[168] + Gh[87] + Gh[108],
        rO = Gh[188] + Gh[87] + Gh[108],
        aO = Gh[1095] + Gh[33] + Gh[681],
        oO = Gh[1095] + Gh[33] + Gh[681] + Gh[33] + Gh[1063],
        fO = Gh[21] + Gh[1096],
        uO = Gh[244] + Gh[110],
        cO = Gh[244] + Gh[111],
        _O = Gh[719] + Gh[20] + Gh[345] + Gh[20] + Gh[721] + Gh[20] + Gh[324],
        dO = Gh[20] + Gh[1097],
        lO = Gh[251] + Gh[41] + Gh[269] + Gh[173] + Gh[268] + Gh[31] + Gh[114],
        vO = Gh[21] + Gh[258] + Gh[1] + Gh[417],
        bO = Gh[21] + Gh[249] + Gh[173] + Gh[174],
        gO = Gh[21] + Gh[258] + Gh[254] + Gh[255],
        yO = Gh[22] + Gh[58] + Gh[182] + Gh[1] + Gh[461],
        mO = Gh[21] + Gh[249] + Gh[41] + Gh[222],
        EO = Gh[21] + Gh[249] + Gh[53] + Gh[1050],
        xO = Gh[21] + Gh[1098] + Gh[23] + Gh[267],
        pO = Gh[1099],
        wO = Gh[1076] + Gh[53] + Gh[1100],
        TO = Gh[1076] + Gh[430] + Gh[1101] + Gh[104] + Gh[387],
        kO = Gh[22] + Gh[173] + Gh[174] + Gh[1] + Gh[461],
        OO = Gh[53] + Gh[1050],
        jO = Gh[41] + Gh[222],
        MO = Gh[37] + Gh[243] + Gh[26] + Gh[144],
        SO = Gh[58] + Gh[1094] + Gh[254] + Gh[255],
        IO = Gh[87] + Gh[248],
        AO = Gh[484],
        LO = Gh[719] + Gh[20] + Gh[228],
        CO = Gh[422] + Gh[72] + Gh[231],
        DO = Gh[422] + Gh[72] + Gh[251],
        RO = Gh[719] + Gh[20] + Gh[722] + Gh[20] + Gh[380] + Gh[20] + Gh[110],
        PO = Gh[719] + Gh[20] + Gh[722] + Gh[20] + Gh[380] + Gh[20] + Gh[111],
        NO = Gh[422] + Gh[72] + Gh[194],
        BO = Gh[1102] + Gh[20] + Gh[228],
        $O = Gh[301] + Gh[72] + Gh[231],
        FO = Gh[1102] + Gh[20] + Gh[228] + Gh[20] + Gh[224] + Gh[20] + Gh[225],
        qO = Gh[1096],
        GO = Gh[416] + Gh[72] + Gh[1103],
        zO = Gh[722] + Gh[20] + Gh[228],
        HO = Gh[416] + Gh[72] + Gh[231],
        YO = Gh[722] + Gh[20] + Gh[380] + Gh[20] + Gh[110],
        UO = Gh[416] + Gh[72] + Gh[244] + Gh[72] + Gh[200],
        WO = Gh[416] + Gh[72] + Gh[244] + Gh[72] + Gh[197],
        VO = Gh[322] + Gh[20] + Gh[1104],
        XO = Gh[1105] + Gh[72] + Gh[188],
        KO = Gh[322] + Gh[20] + Gh[1104] + Gh[20] + Gh[348],
        ZO = Gh[1105] + Gh[72] + Gh[188] + Gh[72] + Gh[95],
        JO = Gh[322] + Gh[20] + Gh[162] + Gh[20] + Gh[1106] + Gh[20] + Gh[228],
        QO = Gh[322] + Gh[20] + Gh[162] + Gh[20] + Gh[1107],
        tj = Gh[1105] + Gh[72] + Gh[190] + Gh[72] + Gh[1108],
        ij = Gh[322] + Gh[20] + Gh[1106] + Gh[20] + Gh[228],
        nj = Gh[1105] + Gh[72] + Gh[168] + Gh[72] + Gh[231],
        ej = Gh[1105] + Gh[72] + Gh[168] + Gh[72] + Gh[1109],
        sj = Gh[322] + Gh[20] + Gh[1110],
        hj = Gh[1105] + Gh[72] + Gh[758],
        rj = Gh[322] + Gh[20] + Gh[1110] + Gh[20] + Gh[348],
        aj = Gh[1105] + Gh[72] + Gh[758] + Gh[72] + Gh[95],
        oj = Gh[1111] + Gh[20] + Gh[1112] + Gh[20] + Gh[1113],
        fj = Gh[242] + Gh[72] + Gh[1114] + Gh[72] + Gh[1069],
        uj = Gh[1115] + Gh[20] + Gh[228],
        cj = Gh[258] + Gh[72] + Gh[231],
        _j = Gh[1115] + Gh[20] + Gh[745],
        dj = Gh[258] + Gh[72] + Gh[1109],
        lj = Gh[721],
        vj = Gh[251] + Gh[72] + Gh[141],
        bj = Gh[251] + Gh[72] + Gh[231],
        gj = Gh[251] + Gh[72] + Gh[190] + Gh[72] + Gh[1108],
        yj = Gh[721] + Gh[20] + Gh[162] + Gh[20] + Gh[1107] + Gh[20] + Gh[380],
        mj = Gh[251] + Gh[72] + Gh[480],
        Ej = Gh[1088],
        xj = Gh[162] + Gh[20] + Gh[711],
        pj = Gh[190] + Gh[72] + Gh[1116],
        wj = Gh[162] + Gh[20] + Gh[715],
        Tj = Gh[190] + Gh[72] + Gh[287],
        kj = Gh[162] + Gh[20] + Gh[1107] + Gh[20] + Gh[711],
        Oj = Gh[190] + Gh[72] + Gh[1108] + Gh[72] + Gh[1116],
        jj = Gh[162] + Gh[20] + Gh[1107] + Gh[20] + Gh[715],
        Mj = Gh[190] + Gh[72] + Gh[1108] + Gh[72] + Gh[287],
        Sj = Gh[209] + Gh[20] + Gh[721],
        Ij = Gh[220] + Gh[72] + Gh[251] + Gh[72] + Gh[141],
        Aj = Gh[209] + Gh[20] + Gh[721] + Gh[20] + Gh[348],
        Lj = Gh[209] + Gh[20] + Gh[721] + Gh[20] + Gh[228],
        Cj = Gh[220] + Gh[72] + Gh[251] + Gh[72] + Gh[95],
        Dj = Gh[209] + Gh[20] + Gh[365],
        Rj = Gh[209] + Gh[20] + Gh[721] + Gh[20] + Gh[365],
        Pj = Gh[220] + Gh[72] + Gh[480],
        Nj = Gh[209] + Gh[20] + Gh[1088],
        Bj = Gh[220] + Gh[72] + Gh[216],
        $j = Gh[209] + Gh[20] + Gh[635] + Gh[20] + Gh[496],
        Fj = Gh[220] + Gh[72] + Gh[655] + Gh[72] + Gh[223],
        qj = Gh[209] + Gh[20] + Gh[1117],
        Gj = Gh[220] + Gh[72] + Gh[1118],
        zj = Gh[209] + Gh[20] + Gh[1119],
        Hj = Gh[220] + Gh[72] + Gh[1096],
        Yj = Gh[1120] + Gh[20] + Gh[1121],
        Uj = Gh[1122] + Gh[72] + Gh[195],
        Wj = Gh[1120] + Gh[20] + Gh[1123],
        Vj = Gh[1122] + Gh[72] + Gh[266],
        Xj = Gh[1122] + Gh[72] + Gh[1027],
        Kj = Gh[1120] + Gh[20] + Gh[1124] + Gh[20] + Gh[1123],
        Zj = Gh[1120] + Gh[20] + Gh[228],
        Jj = Gh[1122] + Gh[72] + Gh[231],
        Qj = Gh[1120] + Gh[20] + Gh[158] + Gh[20] + Gh[159],
        tM = Gh[1122] + Gh[72] + Gh[432] + Gh[72] + Gh[1010],
        iM = Gh[1120] + Gh[20] + Gh[158] + Gh[20] + Gh[160],
        nM = Gh[1122] + Gh[72] + Gh[432] + Gh[72] + Gh[1125],
        eM = Gh[1120] + Gh[20] + Gh[158] + Gh[20] + Gh[348],
        sM = Gh[1122] + Gh[72] + Gh[432] + Gh[72] + Gh[95],
        hM = Gh[1122] + Gh[72] + Gh[216],
        rM = Gh[1120] + Gh[20] + Gh[724] + Gh[20] + Gh[210],
        aM = Gh[1122] + Gh[72] + Gh[263] + Gh[72] + Gh[141],
        oM = Gh[1122] + Gh[72] + Gh[263],
        fM = Gh[1122] + Gh[72] + Gh[480],
        uM = Gh[1122] + Gh[72] + Gh[244] + Gh[72] + Gh[200],
        cM = Gh[1122] + Gh[72] + Gh[244] + Gh[72] + Gh[197],
        _M = Gh[1120] + Gh[20] + Gh[159],
        dM = Gh[1122] + Gh[72] + Gh[1010],
        lM = Gh[1120] + Gh[20] + Gh[1126] + Gh[20] + Gh[1123],
        vM = Gh[1122] + Gh[72] + Gh[1127] + Gh[72] + Gh[266],
        bM = Gh[1122] + Gh[72] + Gh[251],
        gM = Gh[1122] + Gh[72] + Gh[251] + Gh[72] + Gh[95],
        yM = Gh[1120] + Gh[20] + Gh[1115] + Gh[20] + Gh[228],
        mM = Gh[1120] + Gh[20] + Gh[1128],
        EM = Gh[1122] + Gh[72] + Gh[245],
        xM = Gh[1122] + Gh[72] + Gh[416] + Gh[72] + Gh[1103],
        pM = Gh[1122] + Gh[72] + Gh[416] + Gh[72] + Gh[231],
        wM = Gh[1120] + Gh[20] + Gh[722] + Gh[20] + Gh[380] + Gh[20] + Gh[110],
        TM = Gh[1120] + Gh[20] + Gh[722] + Gh[20] + Gh[380] + Gh[20] + Gh[111],
        kM = Gh[1122] + Gh[72] + Gh[655] + Gh[72] + Gh[223],
        OM = Gh[1122] + Gh[72] + Gh[22] + Gh[72] + Gh[277],
        jM = Gh[1087] + Gh[20] + Gh[1104],
        MM = Gh[1092] + Gh[72] + Gh[188],
        SM = Gh[1087] + Gh[20] + Gh[1104] + Gh[20] + Gh[348],
        IM = Gh[1092] + Gh[72] + Gh[188] + Gh[72] + Gh[231],
        AM = Gh[350] + Gh[20] + Gh[1129] + Gh[20] + Gh[1120] + Gh[20] + Gh[1121],
        LM = Gh[350] + Gh[20] + Gh[1129] + Gh[20] + Gh[1120] + Gh[20] + Gh[1124] + Gh[20] + Gh[1123],
        CM = Gh[350] + Gh[20] + Gh[1129] + Gh[20] + Gh[1120] + Gh[20] + Gh[228],
        DM = Gh[350] + Gh[20] + Gh[1129] + Gh[20] + Gh[1120] + Gh[20] + Gh[158] + Gh[20] + Gh[160],
        RM = Gh[350] + Gh[20] + Gh[1129] + Gh[20] + Gh[1120] + Gh[20] + Gh[1088],
        PM = Gh[350] + Gh[20] + Gh[1129] + Gh[20] + Gh[1120] + Gh[20] + Gh[724] + Gh[20] + Gh[210],
        NM = Gh[350] + Gh[20] + Gh[1129] + Gh[20] + Gh[1120] + Gh[20] + Gh[380] + Gh[20] + Gh[110],
        BM = Gh[350] + Gh[20] + Gh[1129] + Gh[20] + Gh[1120] + Gh[20] + Gh[721] + Gh[20] + Gh[348],
        $M = Gh[350] + Gh[20] + Gh[1129] + Gh[20] + Gh[1120] + Gh[20] + Gh[1115] + Gh[20] + Gh[228],
        FM = Gh[350] + Gh[20] + Gh[1129] + Gh[20] + Gh[1120] + Gh[20] + Gh[1115] + Gh[20] + Gh[745],
        qM = Gh[350] + Gh[20] + Gh[1129] + Gh[20] + Gh[1120] + Gh[20] + Gh[1128],
        GM = Gh[350] + Gh[20] + Gh[210],
        zM = Gh[340] + Gh[72] + Gh[141],
        HM = Gh[350] + Gh[20] + Gh[228],
        YM = Gh[340] + Gh[72] + Gh[231],
        UM = Gh[350] + Gh[20] + Gh[1110],
        WM = Gh[340] + Gh[72] + Gh[758],
        VM = Gh[350] + Gh[20] + Gh[1110] + Gh[20] + Gh[348],
        XM = Gh[340] + Gh[72] + Gh[758] + Gh[72] + Gh[95],
        KM = Gh[350] + Gh[20] + Gh[162] + Gh[20] + Gh[1107],
        ZM = Gh[340] + Gh[72] + Gh[190] + Gh[72] + Gh[1108],
        JM = Gh[340] + Gh[72] + Gh[81] + Gh[72] + Gh[244],
        QM = Gh[340] + Gh[72] + Gh[68] + Gh[72] + Gh[244],
        tS = Gh[350] + Gh[20] + Gh[1106] + Gh[20] + Gh[228],
        iS = Gh[340] + Gh[72] + Gh[168] + Gh[72] + Gh[231],
        nS = Gh[350] + Gh[20] + Gh[1129] + Gh[20] + Gh[1130],
        eS = Gh[340] + Gh[72] + Gh[1067] + Gh[72] + Gh[1131],
        sS = Gh[350] + Gh[20] + Gh[1132] + Gh[20] + Gh[1133],
        hS = Gh[340] + Gh[72] + Gh[1134] + Gh[72] + Gh[1135],
        rS = Gh[340] + Gh[72] + Gh[691],
        aS = Gh[340] + Gh[72] + Gh[1136] + Gh[72] + Gh[1137],
        oS = Gh[350] + Gh[20] + Gh[351] + Gh[20] + Gh[1112] + Gh[20] + Gh[352],
        fS = Gh[340] + Gh[72] + Gh[45] + Gh[72] + Gh[1138],
        uS = Gh[340] + Gh[72] + Gh[45] + Gh[72] + Gh[29],
        cS = Gh[340] + Gh[72] + Gh[1139],
        _S = Gh[340] + Gh[72] + Gh[1139] + Gh[72] + Gh[480],
        dS = Gh[340] + Gh[72] + Gh[81] + Gh[72] + Gh[1140] + Gh[72] + Gh[340],
        lS = Gh[350] + Gh[20] + Gh[381] + Gh[20] + Gh[1141] + Gh[20] + Gh[350],
        vS = Gh[340] + Gh[72] + Gh[68] + Gh[72] + Gh[1140] + Gh[72] + Gh[340],
        bS = Gh[335] + Gh[20] + Gh[379],
        gS = Gh[708] + Gh[72] + Gh[81],
        yS = Gh[335] + Gh[20] + Gh[379] + Gh[20] + Gh[159],
        mS = Gh[708] + Gh[72] + Gh[81] + Gh[72] + Gh[1010],
        ES = Gh[335] + Gh[20] + Gh[379] + Gh[20] + Gh[380],
        xS = Gh[708] + Gh[72] + Gh[81] + Gh[72] + Gh[244],
        pS = Gh[335] + Gh[20] + Gh[379] + Gh[20] + Gh[1104],
        wS = Gh[708] + Gh[72] + Gh[81] + Gh[72] + Gh[188],
        TS = Gh[335] + Gh[20] + Gh[379] + Gh[20] + Gh[1104] + Gh[20] + Gh[348],
        kS = Gh[335] + Gh[20] + Gh[379] + Gh[20] + Gh[1110],
        OS = Gh[708] + Gh[72] + Gh[81] + Gh[72] + Gh[758],
        jS = Gh[708] + Gh[72] + Gh[81] + Gh[72] + Gh[190] + Gh[72] + Gh[1108],
        MS = Gh[335] + Gh[20] + Gh[379] + Gh[20] + Gh[1106] + Gh[20] + Gh[228],
        SS = Gh[335] + Gh[20] + Gh[379] + Gh[20] + Gh[1106] + Gh[20] + Gh[745],
        IS = Gh[335] + Gh[20] + Gh[379] + Gh[20] + Gh[162] + Gh[20] + Gh[711],
        AS = Gh[708] + Gh[72] + Gh[81] + Gh[72] + Gh[190] + Gh[72] + Gh[1116],
        LS = Gh[708] + Gh[72] + Gh[81] + Gh[72] + Gh[190] + Gh[72] + Gh[287],
        CS = Gh[708] + Gh[72] + Gh[68],
        DS = Gh[335] + Gh[20] + Gh[381] + Gh[20] + Gh[159],
        RS = Gh[708] + Gh[72] + Gh[68] + Gh[72] + Gh[1010],
        PS = Gh[708] + Gh[72] + Gh[68] + Gh[72] + Gh[244],
        NS = Gh[335] + Gh[20] + Gh[381] + Gh[20] + Gh[1104],
        BS = Gh[708] + Gh[72] + Gh[68] + Gh[72] + Gh[188],
        $S = Gh[708] + Gh[72] + Gh[68] + Gh[72] + Gh[758],
        FS = Gh[335] + Gh[20] + Gh[381] + Gh[20] + Gh[1110] + Gh[20] + Gh[348],
        qS = Gh[335] + Gh[20] + Gh[381] + Gh[20] + Gh[162] + Gh[20] + Gh[1107],
        GS = Gh[708] + Gh[72] + Gh[68] + Gh[72] + Gh[190] + Gh[72] + Gh[1108],
        zS = Gh[335] + Gh[20] + Gh[381] + Gh[20] + Gh[162] + Gh[20] + Gh[1107] + Gh[20] + Gh[380],
        HS = Gh[335] + Gh[20] + Gh[381] + Gh[20] + Gh[1106] + Gh[20] + Gh[228],
        YS = Gh[708] + Gh[72] + Gh[68] + Gh[72] + Gh[168] + Gh[72] + Gh[231],
        US = Gh[335] + Gh[20] + Gh[381] + Gh[20] + Gh[1106] + Gh[20] + Gh[745],
        WS = Gh[335] + Gh[20] + Gh[381] + Gh[20] + Gh[162] + Gh[20] + Gh[711],
        VS = Gh[708] + Gh[72] + Gh[68] + Gh[72] + Gh[190] + Gh[72] + Gh[1116],
        XS = Gh[335] + Gh[20] + Gh[381] + Gh[20] + Gh[162] + Gh[20] + Gh[715],
        KS = Gh[708] + Gh[72] + Gh[68] + Gh[72] + Gh[190] + Gh[72] + Gh[287],
        ZS = Gh[422] + Gh[104] + Gh[283],
        JS = Gh[1122],
        QS = Gh[1120] + Gh[20] + Gh[1142],
        tI = Gh[231],
        iI = Gh[1120] + Gh[20] + Gh[721],
        nI = Gh[1120] + Gh[20] + Gh[721] + Gh[20] + Gh[348],
        eI = Gh[251] + Gh[1] + Gh[417],
        sI = Gh[258] + Gh[1] + Gh[417],
        hI = Gh[1120] + Gh[20] + Gh[1143] + Gh[20] + Gh[372],
        rI = Gh[1098] + Gh[31] + Gh[186] + Gh[104] + Gh[1144],
        aI = Gh[722] + Gh[20] + Gh[723],
        oI = Gh[722] + Gh[20] + Gh[380] + Gh[20] + Gh[111],
        fI = Gh[432] + Gh[33] + Gh[349],
        uI = Gh[1127] + Gh[26] + Gh[84],
        cI = Gh[1120] + Gh[20] + Gh[1088],
        _I = Gh[263] + Gh[191] + Gh[192],
        dI = Gh[1120] + Gh[20] + Gh[724],
        lI = Gh[1098] + Gh[26] + Gh[265],
        vI = Gh[1120] + Gh[20] + Gh[365],
        bI = Gh[251] + Gh[87] + Gh[262],
        gI = Gh[1120] + Gh[20] + Gh[380] + Gh[20] + Gh[110],
        yI = Gh[1120] + Gh[20] + Gh[380] + Gh[20] + Gh[111],
        mI = Gh[245],
        EI = Gh[1120] + Gh[20] + Gh[1115] + Gh[20] + Gh[745],
        xI = Gh[1120] + Gh[20] + Gh[722] + Gh[20] + Gh[723],
        pI = Gh[1120] + Gh[20] + Gh[635] + Gh[20] + Gh[496],
        wI = Gh[1119],
        TI = Gh[20] + Gh[77] + Gh[628],
        kI = Gh[721] + Gh[20] + Gh[228],
        OI = Gh[721] + Gh[20] + Gh[162] + Gh[20] + Gh[1107],
        jI = Gh[251] + Gh[41] + Gh[269] + Gh[173] + Gh[268],
        MI = Gh[20] + Gh[1145],
        SI = Gh[1118] + Gh[104] + Gh[283],
        II = Gh[322] + Gh[20] + Gh[1106] + Gh[20] + Gh[745],
        AI = Gh[322] + Gh[20] + Gh[162] + Gh[20] + Gh[1107] + Gh[20] + Gh[380],
        LI = Gh[209] + Gh[20] + Gh[1115] + Gh[20] + Gh[228],
        CI = Gh[209] + Gh[20] + Gh[721] + Gh[20] + Gh[162] + Gh[20] + Gh[1107] + Gh[20] + Gh[380],
        DI = Gh[1146] + Gh[58] + Gh[1147],
        RI = Gh[20] + Gh[336] + Gh[177],
        PI = Gh[1087] + Gh[20] + Gh[1115] + Gh[20] + Gh[228],
        NI = Gh[1105],
        BI = Gh[1087] + Gh[20] + Gh[1104] + Gh[20] + Gh[162] + Gh[20] + Gh[1107],
        $I = Gh[1087] + Gh[20] + Gh[1104] + Gh[20] + Gh[162] + Gh[20] + Gh[1107] + Gh[20] + Gh[380],
        FI = Gh[20] + Gh[178] + Gh[628],
        qI = Gh[350] + Gh[20] + Gh[357],
        GI = Gh[81] + Gh[37] + Gh[1148],
        zI = Gh[68] + Gh[37] + Gh[1148],
        HI = Gh[190] + Gh[173] + Gh[268] + Gh[1] + Gh[412],
        YI = Gh[350] + Gh[20] + Gh[379] + Gh[20] + Gh[1141] + Gh[20] + Gh[350],
        UI = Gh[81] + Gh[37] + Gh[289] + Gh[23] + Gh[306],
        WI = Gh[81] + Gh[37] + Gh[1148] + Gh[33] + Gh[157],
        VI = Gh[81] + Gh[37] + Gh[1148] + Gh[31] + Gh[114],
        XI = Gh[81] + Gh[37] + Gh[1148] + Gh[33] + Gh[187],
        KI = Gh[81] + Gh[37] + Gh[1148] + Gh[33] + Gh[187] + Gh[33] + Gh[349],
        ZI = Gh[81] + Gh[37] + Gh[1148] + Gh[31] + Gh[1149],
        JI = Gh[335] + Gh[20] + Gh[379] + Gh[20] + Gh[1110] + Gh[20] + Gh[348],
        QI = Gh[81] + Gh[37] + Gh[1148] + Gh[39] + Gh[759] + Gh[1] + Gh[417],
        tA = Gh[335] + Gh[20] + Gh[379] + Gh[20] + Gh[162] + Gh[20] + Gh[1107],
        iA = Gh[81] + Gh[37] + Gh[1148] + Gh[41] + Gh[269] + Gh[173] + Gh[268],
        nA = Gh[335] + Gh[20] + Gh[379] + Gh[20] + Gh[162] + Gh[20] + Gh[1107] + Gh[20] + Gh[380],
        eA = Gh[335] + Gh[20] + Gh[379] + Gh[20] + Gh[162] + Gh[20] + Gh[715],
        sA = Gh[81] + Gh[37] + Gh[1148] + Gh[41] + Gh[269] + Gh[413] + Gh[414],
        hA = Gh[81] + Gh[37] + Gh[1148] + Gh[41] + Gh[269] + Gh[1] + Gh[412],
        rA = Gh[68] + Gh[37] + Gh[1148] + Gh[33] + Gh[157],
        aA = Gh[68] + Gh[37] + Gh[1148] + Gh[31] + Gh[114],
        oA = Gh[68] + Gh[37] + Gh[1148] + Gh[33] + Gh[187],
        fA = Gh[335] + Gh[20] + Gh[381] + Gh[20] + Gh[1104] + Gh[20] + Gh[348],
        uA = Gh[68] + Gh[37] + Gh[1148] + Gh[33] + Gh[187] + Gh[33] + Gh[349],
        cA = Gh[68] + Gh[37] + Gh[1148] + Gh[31] + Gh[1149],
        _A = Gh[68] + Gh[37] + Gh[1148] + Gh[31] + Gh[1149] + Gh[33] + Gh[349],
        dA = Gh[68] + Gh[37] + Gh[1148] + Gh[39] + Gh[759] + Gh[1] + Gh[417],
        lA = Gh[68] + Gh[37] + Gh[1148] + Gh[39] + Gh[759] + Gh[254] + Gh[255],
        vA = Gh[68] + Gh[37] + Gh[1148] + Gh[41] + Gh[269] + Gh[173] + Gh[268],
        bA = Gh[68] + Gh[37] + Gh[1148] + Gh[41] + Gh[269] + Gh[413] + Gh[414],
        gA = Gh[68] + Gh[37] + Gh[1148] + Gh[41] + Gh[269] + Gh[1] + Gh[412],
        yA = Gh[1067] + Gh[41] + Gh[1150],
        mA = Gh[350] + Gh[20] + Gh[1129] + Gh[20] + Gh[1120] + Gh[20] + Gh[1123],
        EA = Gh[350] + Gh[20] + Gh[1129] + Gh[20] + Gh[1120] + Gh[20] + Gh[158] + Gh[20] + Gh[159],
        xA = Gh[350] + Gh[20] + Gh[1129] + Gh[20] + Gh[1120] + Gh[20] + Gh[380] + Gh[20] + Gh[111],
        pA = Gh[350] + Gh[20] + Gh[1129] + Gh[20] + Gh[1120] + Gh[20] + Gh[721],
        wA = Gh[249] + Gh[33] + Gh[261],
        TA = Gh[209] + Gh[20] + Gh[1115] + Gh[20] + Gh[745],
        kA = Gh[209] + Gh[20] + Gh[721] + Gh[20] + Gh[162] + Gh[20] + Gh[1107],
        OA = Gh[335] + Gh[20] + Gh[381] + Gh[20] + Gh[380],
        jA = Gh[249] + Gh[1] + Gh[2] + Gh[171] + Gh[204],
        MA = Gh[20] + Gh[1032] + Gh[297],
        SA = Gh[249] + Gh[33] + Gh[157],
        IA = Gh[21] + Gh[249] + Gh[1] + Gh[86],
        AA = Gh[22] + Gh[58] + Gh[1151] + Gh[26] + Gh[27] + Gh[1] + Gh[506],
        LA = Gh[102] + Gh[1] + Gh[86],
        CA = Gh[1152] + Gh[58] + Gh[1151] + Gh[26] + Gh[71],
        DA = Gh[342] + Gh[26] + Gh[71],
        RA = Gh[102] + Gh[58] + Gh[1151],
        PA = Gh[181] + Gh[1] + Gh[2],
        NA = Gh[170],
        BA = Gh[21] + Gh[422] + Gh[58] + Gh[757],
        $A = Gh[21] + Gh[422] + Gh[33] + Gh[756] + Gh[31] + Gh[114] + Gh[110],
        FA = Gh[21] + Gh[416] + Gh[31] + Gh[114] + Gh[111],
        qA = Gh[21] + Gh[422] + Gh[33] + Gh[756] + Gh[31] + Gh[114] + Gh[111],
        GA = Gh[21] + Gh[422] + Gh[33] + Gh[756] + Gh[58] + Gh[419],
        zA = Gh[20] + Gh[1153],
        HA = Gh[378] + Gh[1] + Gh[461],
        YA = Gh[21] + Gh[301] + Gh[1] + Gh[417],
        UA = Gh[21] + Gh[301] + Gh[1] + Gh[417] + Gh[58] + Gh[739] + Gh[212] + Gh[213],
        WA = Gh[21] + Gh[416] + Gh[1] + Gh[417],
        VA = Gh[21] + Gh[416] + Gh[58] + Gh[419],
        XA = Gh[21] + Gh[416] + Gh[31] + Gh[114] + Gh[110],
        KA = Gh[386] + Gh[104] + Gh[387] + Gh[1] + Gh[2],
        ZA = Gh[378],
        JA = Gh[21] + Gh[249] + Gh[58] + Gh[182],
        QA = Gh[21] + Gh[1010],
        tL = Gh[21] + Gh[249] + Gh[33] + Gh[569],
        iL = Gh[156] + Gh[212] + Gh[1154] + Gh[58] + Gh[182],
        nL = Gh[20] + Gh[761] + Gh[58] + Gh[182],
        eL = Gh[21] + Gh[249] + Gh[39] + Gh[759] + Gh[254] + Gh[255],
        sL = Gh[1085] + Gh[254] + Gh[255],
        hL = Gh[21] + Gh[168] + Gh[254] + Gh[255],
        rL = Gh[21] + Gh[1118] + Gh[104] + Gh[283],
        aL = Gh[21] + Gh[190] + Gh[191] + Gh[192],
        oL = Gh[21] + Gh[168] + Gh[1] + Gh[417],
        fL = Gh[33] + Gh[569],
        uL = Gh[39] + Gh[759] + Gh[254] + Gh[255],
        cL = Gh[1126] + Gh[20] + Gh[1123],
        _L = Gh[21] + Gh[432] + Gh[33] + Gh[157],
        dL = Gh[21] + Gh[432] + Gh[39] + Gh[433],
        lL = Gh[21] + Gh[432] + Gh[33] + Gh[349],
        vL = Gh[21] + Gh[432],
        bL = Gh[39] + Gh[983],
        gL = Gh[21] + Gh[249] + Gh[39] + Gh[983],
        yL = Gh[1069] + Gh[58] + Gh[182],
        mL = Gh[21] + Gh[249] + Gh[39] + Gh[1155] + Gh[37] + Gh[1148],
        EL = Gh[21] + Gh[249] + Gh[104] + Gh[318] + Gh[37] + Gh[1148],
        xL = Gh[21] + Gh[758],
        pL = Gh[181] + Gh[39] + Gh[1155] + Gh[37] + Gh[1148],
        wL = Gh[181] + Gh[104] + Gh[318] + Gh[37] + Gh[1148],
        TL = Gh[21] + Gh[81] + Gh[37] + Gh[1148] + Gh[33] + Gh[261],
        kL = Gh[21] + Gh[81] + Gh[37] + Gh[1148] + Gh[31] + Gh[114],
        OL = Gh[81] + Gh[37] + Gh[1148] + Gh[41] + Gh[222],
        jL = Gh[21] + Gh[81] + Gh[37] + Gh[1148],
        ML = Gh[81] + Gh[37] + Gh[1148] + Gh[33] + Gh[1063],
        SL = Gh[254] + Gh[255],
        IL = Gh[21] + Gh[68] + Gh[37] + Gh[1148] + Gh[33] + Gh[261],
        AL = Gh[21] + Gh[68] + Gh[37] + Gh[1148] + Gh[31] + Gh[114],
        LL = Gh[68] + Gh[37] + Gh[1148] + Gh[41] + Gh[222],
        CL = Gh[21] + Gh[68] + Gh[37] + Gh[1148] + Gh[33] + Gh[157],
        DL = Gh[68] + Gh[37] + Gh[1148] + Gh[33] + Gh[1063],
        RL = Gh[37] + Gh[1148] + Gh[33] + Gh[187],
        PL = Gh[37] + Gh[1148] + Gh[33] + Gh[187] + Gh[33] + Gh[349],
        NL = Gh[37] + Gh[1148] + Gh[33] + Gh[1063],
        BL = Gh[37] + Gh[1148] + Gh[41] + Gh[269] + Gh[173] + Gh[268],
        $L = Gh[37] + Gh[1148] + Gh[41] + Gh[269] + Gh[173] + Gh[268] + Gh[31] + Gh[114],
        FL = Gh[37] + Gh[1148] + Gh[39] + Gh[759] + Gh[1] + Gh[417],
        qL = Gh[37] + Gh[1148] + Gh[39] + Gh[759] + Gh[254] + Gh[255],
        GL = Gh[37] + Gh[1148] + Gh[41] + Gh[269] + Gh[1] + Gh[412],
        zL = Gh[37] + Gh[1148] + Gh[41] + Gh[269] + Gh[413] + Gh[414],
        HL = Gh[37] + Gh[1148] + Gh[31] + Gh[1149],
        YL = Gh[37] + Gh[1148] + Gh[31] + Gh[1149] + Gh[33] + Gh[349],
        UL = Gh[21] + Gh[68] + Gh[37] + Gh[1148],
        WL = Gh[207] + Gh[37] + Gh[1148],
        VL = Gh[39] + Gh[1155] + Gh[37] + Gh[1148],
        XL = Gh[104] + Gh[318] + Gh[37] + Gh[1148],
        KL = Gh[350] + Gh[20] + Gh[345] + Gh[20] + Gh[1156],
        ZL = Gh[49] + Gh[58] + Gh[307] + Gh[23] + Gh[1068],
        JL = Gh[0] + Gh[115] + Gh[114],
        QL = Gh[0] + Gh[58] + Gh[307] + Gh[41] + Gh[1150],
        tC = Gh[152] + Gh[58] + Gh[307] + Gh[41] + Gh[1150],
        iC = Gh[1157],
        nC = Gh[181] + Gh[26] + Gh[118],
        eC = Gh[1146] + Gh[58] + Gh[307] + Gh[41] + Gh[1150],
        sC = Gh[350] + Gh[20] + Gh[345] + Gh[20] + Gh[1158],
        hC = Gh[350] + Gh[20] + Gh[345] + Gh[20] + Gh[371] + Gh[20] + Gh[356],
        rC = Gh[691] + Gh[72],
        aC = Gh[1069] + Gh[33] + Gh[375],
        oC = Gh[20] + Gh[201] + Gh[260],
        fC = Gh[66] + Gh[1159],
        uC = Gh[66] + Gh[1160] + Gh[1161] + Gh[336],
        cC = Gh[66] + Gh[1162] + Gh[23] + Gh[297],
        _C = Gh[20] + Gh[1163],
        dC = Gh[442],
        lC = Gh[1164],
        vC = Gh[1165],
        bC = Gh[55] + Gh[23] + Gh[24],
        gC = Gh[189] + Gh[104] + Gh[1166],
        yC = Gh[164],
        mC = Gh[0] + Gh[173] + Gh[1167] + Gh[171] + Gh[449],
        EC = Gh[811] + Gh[104] + Gh[318] + Gh[41] + Gh[1168],
        xC = Gh[303] + Gh[213],
        pC = Gh[152] + Gh[104] + Gh[129],
        wC = Gh[152] + Gh[33] + Gh[261] + Gh[303] + Gh[213],
        TC = Gh[152] + Gh[254] + Gh[1091],
        kC = Gh[20] + Gh[502] + Gh[504] + Gh[212] + Gh[423],
        OC = Gh[152] + Gh[303] + Gh[213],
        jC = Gh[0] + Gh[23] + Gh[153] + Gh[58] + Gh[197] + Gh[212] + Gh[532] + Gh[23] + Gh[24],
        MC = Gh[20] + Gh[1169],
        SC = Gh[1170],
        IC = Gh[109] + Gh[26] + Gh[71],
        AC = Gh[518],
        LC = Gh[22] + Gh[23] + Gh[153] + Gh[1] + Gh[1171],
        CC = Gh[1172] + Gh[20] + Gh[1173],
        DC = Gh[1174],
        RC = Gh[1175],
        PC = Gh[1176],
        NC = Gh[1177],
        BC = Gh[1178],
        $C = Gh[1179] + Gh[41] + Gh[1180] + Gh[39] + Gh[1155],
        FC = Gh[1179] + Gh[41] + Gh[1180] + Gh[104] + Gh[318],
        qC = Gh[1011] + Gh[58] + Gh[182],
        GC = Gh[55] + Gh[37] + Gh[38],
        zC = Gh[1181] + Gh[58] + Gh[182],
        HC = Gh[4] + Gh[41] + Gh[1182],
        YC = Gh[20] + Gh[1183] + Gh[58] + Gh[182],
        UC = Gh[612] + Gh[37] + Gh[289],
        WC = Gh[211] + Gh[33] + Gh[678],
        VC = Gh[450] + Gh[33] + Gh[569],
        XC = Gh[20] + Gh[1184],
        KC = Gh[49] + Gh[171] + Gh[256],
        ZC = Gh[383] + Gh[104] + Gh[318],
        JC = Gh[612] + Gh[104] + Gh[318] + Gh[31] + Gh[1185],
        QC = Gh[612] + Gh[37] + Gh[38],
        tD = Gh[20] + Gh[1186] + Gh[37] + Gh[38],
        iD = Gh[1187],
        nD = Gh[438] + Gh[104] + Gh[318],
        eD = Gh[1188],
        sD = Gh[1189] + Gh[33] + Gh[1190] + Gh[37] + Gh[742],
        hD = Gh[156] + Gh[41] + Gh[222],
        rD = Gh[49] + Gh[212] + Gh[1191],
        aD = Gh[20] + Gh[1175] + Gh[39] + Gh[1045],
        oD = Gh[1022] + Gh[23] + Gh[1192],
        fD = Gh[102] + Gh[1] + Gh[1193] + Gh[171] + Gh[605],
        uD = Gh[11] + Gh[1] + Gh[1193] + Gh[171] + Gh[605],
        cD = Gh[1194],
        _D = Gh[1195] + Gh[995],
        dD = Gh[1196],
        lD = Gh[173] + Gh[1197] + Gh[44] + Gh[23] + Gh[1198] + Gh[221],
        vD = Gh[11] + Gh[33] + Gh[681],
        bD = Gh[1172] + Gh[20] + Gh[1199],
        gD = Gh[22] + Gh[171] + Gh[605] + Gh[23] + Gh[24],
        yD = Gh[33] + Gh[261],
        mD = Gh[41] + Gh[269],
        ED = Gh[33] + Gh[1063],
        xD = Gh[23] + Gh[306],
        pD = Gh[340] + Gh[1200] + Gh[1054],
        wD = Gh[444] + Gh[26] + Gh[71],
        TD = Gh[22] + Gh[41] + Gh[1150] + Gh[23] + Gh[1201],
        kD = Gh[1202] + Gh[23] + Gh[267] + Gh[41] + Gh[1150],
        OD = Gh[249] + Gh[23] + Gh[153],
        jD = Gh[41] + Gh[1150] + Gh[44] + Gh[1] + Gh[1203] + Gh[124] + Gh[289] + Gh[44] + Gh[23] + Gh[267],
        MD = Gh[1204],
        SD = Gh[1205] + Gh[23] + Gh[510],
        ID = Gh[1206],
        AD = Gh[444] + Gh[212] + Gh[213],
        LD = Gh[484] + Gh[1] + Gh[506] + Gh[173] + Gh[507],
        CD = Gh[20] + Gh[1011] + Gh[58] + Gh[182],
        DD = Gh[20] + Gh[1181] + Gh[58] + Gh[182],
        RD = Gh[1207] + Gh[20] + Gh[1208],
        PD = Gh[1209] + Gh[87] + Gh[1210],
        ND = Gh[1087] + Gh[20] + Gh[1090] + Gh[20] + Gh[163],
        BD = Gh[20] + Gh[1211],
        $D = Gh[1087] + Gh[20] + Gh[345] + Gh[20] + Gh[323],
        FD = Gh[1087] + Gh[20] + Gh[345] + Gh[20] + Gh[695],
        qD = Gh[1212] + Gh[624],
        GD = Gh[254] + Gh[1091] + Gh[377],
        zD = Gh[1213] + Gh[146],
        HD = Gh[1214] + Gh[44] + Gh[297] + Gh[72] + Gh[77] + Gh[511] + Gh[44] + Gh[1215] + Gh[91] + Gh[613],
        YD = Gh[854],
        UD = Gh[72] + Gh[393] + Gh[91] + Gh[254] + Gh[394] + Gh[91] + Gh[303] + Gh[395] + Gh[44] + Gh[397],
        WD = Gh[1214] + Gh[96] + Gh[199] + Gh[97] + Gh[258] + Gh[91] + Gh[231] + Gh[482] + Gh[62] + Gh[63] + Gh[297] + Gh[405] + Gh[297] + Gh[405] + Gh[297] + Gh[405] + Gh[297] + Gh[72] + Gh[336] + Gh[65],
        VD = Gh[72] + Gh[393] + Gh[91] + Gh[254] + Gh[394] + Gh[91] + Gh[303] + Gh[395],
        XD = Gh[1214] + Gh[96] + Gh[297] + Gh[97],
        KD = Gh[1216],
        ZD = Gh[96] + Gh[1214] + Gh[44] + Gh[237] + Gh[511] + Gh[44] + Gh[1217] + Gh[91] + Gh[728] + Gh[63] + Gh[297] + Gh[72] + Gh[632] + Gh[405] + Gh[297] + Gh[405] + Gh[297] + Gh[72] + Gh[632] + Gh[405] + Gh[199] + Gh[65],
        JD = Gh[72] + Gh[393] + Gh[91] + Gh[254] + Gh[394] + Gh[91] + Gh[303] + Gh[395] + Gh[96] + Gh[421],
        QD = Gh[1214] + Gh[96] + Gh[199] + Gh[97],
        tR = Gh[96] + Gh[1214] + Gh[44] + Gh[297] + Gh[72] + Gh[237] + Gh[511] + Gh[44] + Gh[766],
        iR = Gh[20] + Gh[80],
        nR = Gh[20] + Gh[277],
        eR = Gh[20] + Gh[1218],
        sR = Gh[72] + Gh[393] + Gh[91] + Gh[254] + Gh[394] + Gh[91] + Gh[33] + Gh[533] + Gh[58] + Gh[1219],
        hR = Gh[1220] + Gh[482] + Gh[77] + Gh[146] + Gh[1221] + Gh[266] + Gh[482] + Gh[1005] + Gh[97] + Gh[1222] + Gh[91] + Gh[1223] + Gh[482] + Gh[251] + Gh[91] + Gh[1222] + Gh[97] + Gh[1222] + Gh[91] + Gh[416] + Gh[1224] + Gh[415] + Gh[44] + Gh[297] + Gh[146] + Gh[44] + Gh[297] + Gh[146] + Gh[44] + Gh[199] + Gh[146] + Gh[1221] + Gh[258] + Gh[91] + Gh[231] + Gh[482] + Gh[62] + Gh[63] + Gh[1225] + Gh[64] + Gh[1225] + Gh[64] + Gh[1225] + Gh[64] + Gh[297] + Gh[72] + Gh[237] + Gh[1226] + Gh[251] + Gh[91] + Gh[480] + Gh[482] + Gh[178] + Gh[146] + Gh[97] + Gh[1220] + Gh[482] + Gh[199] + Gh[146] + Gh[97],
        rR = Gh[72] + Gh[393] + Gh[91] + Gh[254] + Gh[394] + Gh[91] + Gh[33] + Gh[533] + Gh[58] + Gh[1219] + Gh[72] + Gh[421] + Gh[1227] + Gh[393] + Gh[91] + Gh[254] + Gh[394] + Gh[91] + Gh[33] + Gh[533] + Gh[58] + Gh[1219] + Gh[96] + Gh[421],
        aR = Gh[1220] + Gh[91] + Gh[79] + Gh[482] + Gh[632] + Gh[146] + Gh[97],
        oR = Gh[1220] + Gh[91] + Gh[80] + Gh[482] + Gh[632] + Gh[146] + Gh[97],
        fR = Gh[72] + Gh[393] + Gh[91] + Gh[254] + Gh[394] + Gh[91] + Gh[33] + Gh[533] + Gh[26] + Gh[404],
        uR = Gh[96] + Gh[1214] + Gh[44] + Gh[237] + Gh[511] + Gh[44] + Gh[1217] + Gh[91] + Gh[728] + Gh[63] + Gh[297] + Gh[72] + Gh[632] + Gh[405] + Gh[297] + Gh[405] + Gh[297] + Gh[72] + Gh[632] + Gh[405] + Gh[199] + Gh[1226],
        cR = Gh[20] + Gh[83] + Gh[173] + Gh[585] + Gh[33] + Gh[679],
        _R = Gh[20] + Gh[85] + Gh[173] + Gh[585] + Gh[33] + Gh[679],
        dR = Gh[393] + Gh[91] + Gh[254] + Gh[394] + Gh[91] + Gh[33] + Gh[533] + Gh[26] + Gh[404],
        lR = Gh[389] + Gh[171] + Gh[1228],
        vR = Gh[20] + Gh[1229],
        bR = Gh[58] + Gh[1230],
        gR = Gh[1231],
        yR = Gh[1232],
        mR = Gh[1233],
        ER = Gh[1234] + Gh[20] + Gh[1235],
        xR = Gh[1234] + Gh[20] + Gh[345],
        pR = Gh[1215] + Gh[31] + Gh[454],
        wR = Gh[132] + Gh[814] + Gh[87] + Gh[1052],
        TR = Gh[1236] + Gh[87] + Gh[1210],
        kR = Gh[22] + Gh[37] + Gh[38] + Gh[23] + Gh[435],
        OR = Gh[20] + Gh[81] + Gh[1237],
        jR = Gh[20] + Gh[68] + Gh[1237],
        MR = Gh[20] + Gh[81] + Gh[1238],
        SR = Gh[20] + Gh[81] + Gh[33] + Gh[569],
        IR = Gh[20] + Gh[68] + Gh[33] + Gh[569],
        AR = Gh[1239],
        LR = Gh[741] + Gh[104] + Gh[282],
        CR = Gh[1240] + Gh[104] + Gh[283],
        DR = Gh[20] + Gh[68] + Gh[1238],
        RR = Gh[1241] + Gh[20] + Gh[1121] + Gh[20] + Gh[1242] + Gh[20] + Gh[159] + Gh[20] + Gh[1243],
        PR = Gh[1051],
        NR = Gh[11] + Gh[173] + Gh[1244],
        BR = Gh[20] + Gh[1245] + Gh[171] + Gh[155],
        $R = Gh[1076] + Gh[173] + Gh[1246],
        FR = Gh[1247],
        qR = Gh[173] + Gh[1244] + Gh[171] + Gh[605],
        GR = Gh[207] + Gh[26] + Gh[144],
        zR = Gh[610],
        HR = Gh[753] + Gh[20] + Gh[1248] + Gh[20] + Gh[381],
        YR = Gh[1249],
        UR = Gh[1250],
        WR = Gh[447] + Gh[26] + Gh[144],
        VR = Gh[68] + Gh[41] + Gh[1034] + Gh[26] + Gh[144],
        XR = Gh[20] + Gh[1032] + Gh[178],
        KR = Gh[0] + Gh[173] + Gh[546] + Gh[33] + Gh[349],
        ZR = Gh[1] + Gh[1251] + Gh[23] + Gh[306] + Gh[171] + Gh[605],
        JR = Gh[753] + Gh[20] + Gh[1252] + Gh[20] + Gh[381],
        QR = Gh[1] + Gh[1251] + Gh[33] + Gh[261] + Gh[171] + Gh[605],
        tP = Gh[1] + Gh[1251] + Gh[41] + Gh[269] + Gh[171] + Gh[605],
        iP = Gh[23] + Gh[306] + Gh[377],
        nP = Gh[207] + Gh[87] + Gh[1253] + Gh[41] + Gh[269],
        eP = Gh[95] + Gh[173] + Gh[1246],
        sP = Gh[102] + Gh[173] + Gh[1244],
        hP = Gh[152] + Gh[23] + Gh[306] + Gh[58] + Gh[197] + Gh[171] + Gh[605],
        rP = Gh[1254],
        aP = Gh[393] + Gh[91] + Gh[41] + Gh[1150] + Gh[23] + Gh[1255],
        oP = Gh[1256] + Gh[1257] + Gh[1258] + Gh[23] + Gh[44] + Gh[199] + Gh[146],
        fP = Gh[336] + Gh[146],
        uP = Gh[1222] + Gh[33] + Gh[756],
        cP = Gh[1259],
        _P = Gh[1260],
        dP = Gh[57] + Gh[23] + Gh[1201],
        lP = Gh[1261],
        vP = Gh[1262] + Gh[120] + Gh[121],
        bP = Gh[22] + Gh[53] + Gh[54] + Gh[1] + Gh[506],
        gP = Gh[55] + Gh[23] + Gh[1201],
        yP = Gh[1120] + Gh[20] + Gh[1263] + Gh[20] + Gh[1264] + Gh[20] + Gh[1265] + Gh[20] + Gh[1266] + Gh[20] + Gh[1267],
        mP = Gh[55] + Gh[23] + Gh[1201] + Gh[191] + Gh[1268] + Gh[1] + Gh[560] + Gh[31] + Gh[186] + Gh[191] + Gh[549],
        EP = Gh[156] + Gh[104] + Gh[129],
        xP = Gh[49] + Gh[23] + Gh[1269],
        pP = Gh[41] + Gh[1150] + Gh[23] + Gh[1255],
        wP = Gh[1122] + Gh[23] + Gh[1255],
        TP = Gh[1270],
        kP = Gh[49] + Gh[23] + Gh[1271],
        OP = Gh[610] + Gh[41] + Gh[1150] + Gh[23] + Gh[1201],
        jP = Gh[5] + Gh[23] + Gh[306] + Gh[58] + Gh[307],
        MP = Gh[350] + Gh[20] + Gh[1129],
        SP = Gh[1272] + Gh[33] + Gh[390] + Gh[303] + Gh[391],
        IP = Gh[1273] + Gh[33] + Gh[569],
        AP = Gh[317] + Gh[104] + Gh[318] + Gh[1] + Gh[1049],
        LP = Gh[11] + Gh[33] + Gh[681] + Gh[58] + Gh[197] + Gh[171] + Gh[605],
        CP = Gh[1274] + Gh[171] + Gh[172],
        DP = Gh[710],
        RP = Gh[443],
        PP = Gh[1274] + Gh[44] + Gh[220] + Gh[221],
        NP = Gh[44] + Gh[200] + Gh[44],
        BP = Gh[49] + Gh[53] + Gh[1275],
        $P = Gh[447] + Gh[173] + Gh[1276] + Gh[23] + Gh[153],
        FP = Gh[1172] + Gh[20] + Gh[1252] + Gh[20] + Gh[1277],
        qP = Gh[489] + Gh[171] + Gh[605] + Gh[23] + Gh[24],
        GP = Gh[551] + Gh[23] + Gh[1198],
        zP = Gh[1172] + Gh[20] + Gh[1252] + Gh[20] + Gh[1278],
        HP = Gh[1279],
        YP = Gh[1280] + Gh[20] + Gh[1277],
        UP = Gh[1280] + Gh[20] + Gh[1278],
        WP = Gh[155] + Gh[33] + Gh[569],
        VP = Gh[20] + Gh[1281] + Gh[26] + Gh[27] + Gh[1] + Gh[506] + Gh[41] + Gh[1282],
        XP = Gh[189] + Gh[26] + Gh[27] + Gh[1] + Gh[506] + Gh[173] + Gh[507],
        KP = Gh[20] + Gh[1283],
        ZP = Gh[20] + Gh[1284] + Gh[26] + Gh[1285],
        JP = Gh[20] + Gh[1286] + Gh[23] + Gh[1201],
        QP = Gh[207] + Gh[41] + Gh[269] + Gh[171] + Gh[155],
        tN = Gh[207] + Gh[41] + Gh[269],
        iN = Gh[49] + Gh[1] + Gh[1287] + Gh[26] + Gh[144],
        nN = Gh[753] + Gh[20] + Gh[1288],
        eN = Gh[66] + Gh[1289],
        sN = Gh[20] + Gh[1290],
        hN = Gh[11] + Gh[26] + Gh[240] + Gh[33] + Gh[755] + Gh[58] + Gh[197] + Gh[171] + Gh[204],
        rN = Gh[49] + Gh[87] + Gh[1291],
        aN = Gh[49] + Gh[23] + Gh[435] + Gh[26] + Gh[144],
        oN = Gh[49] + Gh[39] + Gh[1155],
        fN = Gh[483] + Gh[26] + Gh[118],
        uN = Gh[466] + Gh[33] + Gh[755],
        cN = Gh[368] + Gh[20] + Gh[1252] + Gh[20] + Gh[1277],
        _N = Gh[1137],
        dN = Gh[20] + Gh[1292],
        lN = Gh[1070],
        vN = Gh[568] + Gh[33] + Gh[755],
        bN = Gh[1137] + Gh[171] + Gh[204],
        gN = Gh[368] + Gh[20] + Gh[1293],
        yN = Gh[368] + Gh[20] + Gh[1252] + Gh[20] + Gh[1278],
        mN = Gh[719] + Gh[20] + Gh[1294] + Gh[20] + Gh[1104],
        EN = Gh[719] + Gh[20] + Gh[1294] + Gh[20] + Gh[1104] + Gh[20] + Gh[228],
        xN = Gh[719] + Gh[20] + Gh[1294] + Gh[20] + Gh[1106] + Gh[20] + Gh[228],
        pN = Gh[20] + Gh[296] + Gh[171] + Gh[155],
        wN = Gh[20] + Gh[982],
        TN = Gh[1295] + Gh[20] + Gh[1277],
        kN = Gh[20] + Gh[205] + Gh[260] + Gh[104] + Gh[557],
        ON = Gh[1295] + Gh[20] + Gh[1278],
        jN = Gh[279] + Gh[23] + Gh[280] + Gh[53] + Gh[1275] + Gh[377],
        MN = Gh[49] + Gh[33] + Gh[1296],
        SN = Gh[1295] + Gh[20] + Gh[1297],
        IN = Gh[1298],
        AN = Gh[1299],
        LN = Gh[20] + Gh[195] + Gh[26] + Gh[144],
        CN = Gh[1300] + Gh[91] + Gh[442],
        DN = Gh[1301] + Gh[91] + Gh[442],
        RN = Gh[1302] + Gh[91] + Gh[442],
        PN = Gh[62] + Gh[63] + Gh[297] + Gh[405] + Gh[1303] + Gh[405] + Gh[297] + Gh[405] + Gh[199] + Gh[65],
        NN = Gh[66] + Gh[1304] + Gh[297],
        BN = Gh[20] + Gh[195] + Gh[430] + Gh[1305] + Gh[41] + Gh[1306],
        $N = Gh[20] + Gh[1307],
        FN = Gh[49] + Gh[87] + Gh[1308],
        qN = Gh[20] + Gh[1309],
        GN = Gh[20] + Gh[1310],
        zN = Gh[1311] + Gh[20] + Gh[1277],
        HN = Gh[761] + Gh[58] + Gh[182],
        YN = Gh[1312],
        UN = Gh[1311] + Gh[20] + Gh[1278],
        WN = Gh[87] + Gh[1313] + Gh[171] + Gh[605],
        VN = Gh[49] + Gh[33] + Gh[1314],
        XN = Gh[156] + Gh[33] + Gh[681],
        KN = Gh[286] + Gh[104] + Gh[318] + Gh[104] + Gh[1144],
        ZN = Gh[439] + Gh[37] + Gh[742],
        JN = Gh[1315] + Gh[20] + Gh[1316],
        QN = Gh[393] + Gh[91] + Gh[104] + Gh[1317],
        tB = Gh[66] + Gh[1318],
        iB = Gh[199] + Gh[146] + Gh[44] + Gh[1256] + Gh[1257] + Gh[173] + Gh[633] + Gh[173] + Gh[633] + Gh[173] + Gh[633],
        nB = Gh[77] + Gh[146] + Gh[44] + Gh[178] + Gh[146],
        eB = Gh[20] + Gh[90] + Gh[297],
        sB = Gh[20] + Gh[1152] + Gh[104] + Gh[557],
        hB = Gh[20] + Gh[90] + Gh[178],
        rB = Gh[20] + Gh[1319],
        aB = Gh[0] + Gh[104] + Gh[1317],
        oB = Gh[1062] + Gh[104] + Gh[283],
        fB = Gh[1320] + Gh[1321] + Gh[1322],
        uB = Gh[20] + Gh[1323] + Gh[104] + Gh[557],
        cB = Gh[1062] + Gh[173] + Gh[1324],
        _B = Gh[1315] + Gh[20] + Gh[1325],
        dB = Gh[20] + Gh[90] + Gh[260],
        lB = Gh[389] + Gh[104] + Gh[1317],
        vB = Gh[612] + Gh[58] + Gh[197] + Gh[212] + Gh[532] + Gh[23] + Gh[24],
        bB = Gh[1051] + Gh[72] + Gh[317] + Gh[72] + Gh[610],
        gB = Gh[1051] + Gh[72] + Gh[1326],
        yB = Gh[1051] + Gh[72] + Gh[317] + Gh[72] + Gh[982],
        mB = Gh[1051] + Gh[72] + Gh[1327],
        EB = Gh[1051] + Gh[72] + Gh[1328],
        xB = Gh[1137] + Gh[72] + Gh[317] + Gh[72] + Gh[610],
        pB = Gh[1137] + Gh[72] + Gh[1326],
        wB = Gh[1137] + Gh[72] + Gh[317] + Gh[72] + Gh[982],
        TB = Gh[442] + Gh[72] + Gh[610],
        kB = Gh[1329],
        OB = Gh[442] + Gh[72] + Gh[982],
        jB = Gh[1330],
        MB = Gh[1331],
        SB = Gh[195] + Gh[72] + Gh[982],
        IB = Gh[1186] + Gh[72] + Gh[610],
        AB = Gh[1186] + Gh[72] + Gh[982],
        LB = Gh[1092] + Gh[72] + Gh[1055],
        CB = Gh[340] + Gh[72] + Gh[1067],
        DB = Gh[1295],
        RB = Gh[439] + Gh[72] + Gh[610],
        PB = Gh[439] + Gh[72] + Gh[1332],
        NB = Gh[439] + Gh[72] + Gh[982],
        BB = Gh[543] + Gh[20] + Gh[523],
        $B = Gh[555] + Gh[72] + Gh[537],
        FB = Gh[20] + Gh[444] + Gh[33] + Gh[679],
        qB = Gh[20] + Gh[22] + Gh[23] + Gh[153] + Gh[87] + Gh[599],
        GB = Gh[20] + Gh[22] + Gh[23] + Gh[153] + Gh[1] + Gh[600],
        zB = Gh[20] + Gh[1333] + Gh[171] + Gh[605],
        HB = Gh[20] + Gh[1334] + Gh[1] + Gh[1193] + Gh[171] + Gh[605] + Gh[41] + Gh[446],
        YB = Gh[447] + Gh[171] + Gh[605] + Gh[212] + Gh[213],
        UB = Gh[20] + Gh[547] + Gh[212] + Gh[213],
        WB = Gh[0] + Gh[171] + Gh[605] + Gh[171] + Gh[1335],
        VB = Gh[1336],
        XB = Gh[1241] + Gh[20] + Gh[225] + Gh[20] + Gh[1158],
        KB = Gh[1241] + Gh[20] + Gh[225] + Gh[20] + Gh[719],
        ZB = Gh[1241] + Gh[20] + Gh[225] + Gh[20] + Gh[1337],
        JB = Gh[1338],
        QB = Gh[1241] + Gh[20] + Gh[225] + Gh[20] + Gh[1339],
        t$ = Gh[1340],
        i$ = Gh[1241] + Gh[20] + Gh[225] + Gh[20] + Gh[1341] + Gh[20] + Gh[1342] + Gh[20] + Gh[350],
        n$ = Gh[152] + Gh[72] + Gh[1343] + Gh[72] + Gh[340],
        e$ = Gh[1241] + Gh[20] + Gh[225] + Gh[20] + Gh[1341] + Gh[20] + Gh[350],
        s$ = Gh[152] + Gh[72] + Gh[340],
        h$ = Gh[1241] + Gh[20] + Gh[225] + Gh[20] + Gh[1341] + Gh[20] + Gh[322],
        r$ = Gh[152] + Gh[72] + Gh[1105],
        a$ = Gh[1241] + Gh[20] + Gh[225] + Gh[20] + Gh[1341] + Gh[20] + Gh[162],
        o$ = Gh[152] + Gh[72] + Gh[190],
        f$ = Gh[321] + Gh[171] + Gh[603],
        u$ = Gh[1241] + Gh[20] + Gh[225] + Gh[20] + Gh[1344],
        c$ = Gh[26] + Gh[1203] + Gh[171] + Gh[605],
        _$ = Gh[33] + Gh[681] + Gh[171] + Gh[605],
        d$ = Gh[212] + Gh[550] + Gh[171] + Gh[605],
        l$ = Gh[191] + Gh[1345] + Gh[635] + Gh[1346] + Gh[171] + Gh[605],
        v$ = Gh[23] + Gh[1347] + Gh[171] + Gh[605],
        b$ = Gh[87] + Gh[1348] + Gh[33] + Gh[681] + Gh[171] + Gh[605] + Gh[58] + Gh[197] + Gh[87] + Gh[1349] + Gh[58] + Gh[396],
        g$ = Gh[26] + Gh[118] + Gh[171] + Gh[605],
        y$ = Gh[41] + Gh[1350],
        m$ = Gh[1351],
        E$ = Gh[1352],
        x$ = Gh[0] + Gh[41] + Gh[339] + Gh[87] + Gh[1353],
        p$ = Gh[1354] + Gh[20] + Gh[358],
        w$ = Gh[1354] + Gh[20] + Gh[479],
        T$ = Gh[1354] + Gh[20] + Gh[359],
        k$ = Gh[1354] + Gh[20] + Gh[372],
        O$ = Gh[1354] + Gh[20] + Gh[358] + Gh[20] + Gh[372],
        j$ = Gh[1354] + Gh[20] + Gh[358] + Gh[20] + Gh[359],
        M$ = Gh[1354] + Gh[20] + Gh[360] + Gh[20] + Gh[372],
        S$ = Gh[1354] + Gh[20] + Gh[360] + Gh[20] + Gh[359],
        I$ = Gh[1354] + Gh[20] + Gh[359] + Gh[20] + Gh[360],
        A$ = Gh[1354] + Gh[20] + Gh[359] + Gh[20] + Gh[358],
        L$ = Gh[1354] + Gh[20] + Gh[372] + Gh[20] + Gh[360],
        C$ = Gh[1355],
        D$ = Gh[1356] + Gh[72] + Gh[1357],
        R$ = Gh[1355] + Gh[72] + Gh[628],
        P$ = Gh[1355] + Gh[72] + Gh[1358],
        N$ = Gh[1111] + Gh[20] + Gh[345] + Gh[20] + Gh[1359],
        B$ = Gh[1111] + Gh[20] + Gh[345] + Gh[20] + Gh[1359] + Gh[20] + Gh[355],
        $$ = Gh[1111] + Gh[20] + Gh[345] + Gh[20] + Gh[1359] + Gh[20] + Gh[356],
        F$ = Gh[1111] + Gh[20] + Gh[345] + Gh[20] + Gh[1360] + Gh[20] + Gh[1361],
        q$ = Gh[428] + Gh[33] + Gh[157],
        G$ = Gh[0] + Gh[303] + Gh[213] + Gh[33] + Gh[157],
        z$ = Gh[305] + Gh[1] + Gh[2] + Gh[173] + Gh[1362],
        H$ = Gh[20] + Gh[1363],
        Y$ = Gh[0] + Gh[1364] + Gh[412],
        U$ = Gh[628] + Gh[254] + Gh[412],
        W$ = Gh[1358] + Gh[254] + Gh[412],
        V$ = Gh[242] + Gh[104] + Gh[283],
        X$ = Gh[1365],
        K$ = Gh[1076] + Gh[41] + Gh[339],
        Z$ = Gh[305] + Gh[58] + Gh[182],
        J$ = Gh[468],
        Q$ = Gh[20] + Gh[1366],
        tF = Gh[20] + Gh[1367] + Gh[41] + Gh[222],
        iF = Gh[20] + Gh[643] + Gh[199],
        nF = Gh[247] + Gh[1368],
        eF = Gh[247] + Gh[1369],
        sF = Gh[468] + Gh[110],
        hF = Gh[468] + Gh[111],
        rF = Gh[20] + Gh[1370],
        aF = Gh[20] + Gh[1371],
        oF = Gh[242] + Gh[87] + Gh[1033],
        fF = Gh[20] + Gh[1372],
        uF = Gh[447] + Gh[212] + Gh[1373] + Gh[303] + Gh[1075],
        cF = Gh[242] + Gh[173] + Gh[505],
        _F = Gh[249] + Gh[41] + Gh[339] + Gh[173] + Gh[505],
        dF = Gh[55],
        lF = Gh[1273] + Gh[41] + Gh[339] + Gh[173] + Gh[505],
        vF = Gh[0] + Gh[212] + Gh[1374] + Gh[171] + Gh[1375],
        bF = Gh[468] + Gh[1] + Gh[315],
        gF = Gh[340] + Gh[1] + Gh[315],
        yF = Gh[1376],
        mF = Gh[1377],
        EF = Gh[133] + Gh[33] + Gh[678],
        xF = Gh[49] + Gh[87] + Gh[1378],
        pF = Gh[20] + Gh[1379],
        wF = Gh[1380],
        TF = Gh[1381],
        kF = Gh[1382],
        OF = Gh[215],
        jF = Gh[1383],
        MF = Gh[1384] + Gh[20] + Gh[1385] + Gh[20] + Gh[1386],
        SF = Gh[365] + Gh[20] + Gh[225] + Gh[20] + Gh[1387],
        IF = Gh[365] + Gh[20] + Gh[225] + Gh[20] + Gh[1388],
        AF = Gh[1389],
        LF = Gh[20] + Gh[1032] + Gh[260],
        CF = Gh[610] + Gh[37] + Gh[1390],
        DF = Gh[0] + Gh[254] + Gh[412],
        RF = Gh[1391],
        PF = Gh[382] + Gh[33] + Gh[1392],
        NF = Gh[0] + Gh[87] + Gh[262],
        BF = Gh[480] + Gh[212] + Gh[213],
        $F = Gh[20] + Gh[1393],
        FF = Gh[468] + Gh[199],
        qF = Gh[468] + Gh[77],
        GF = Gh[350] + Gh[20] + Gh[1129] + Gh[20] + Gh[1089],
        zF = Gh[20] + Gh[508] + Gh[58] + Gh[1394] + Gh[39] + Gh[257],
        HF = Gh[1179] + Gh[58] + Gh[1395],
        YF = Gh[49] + Gh[26] + Gh[1396] + Gh[31] + Gh[1397],
        UF = Gh[1398] + Gh[23] + Gh[306],
        WF = Gh[20] + Gh[628] + Gh[633] + Gh[171] + Gh[186] + Gh[58] + Gh[307],
        VF = Gh[1399],
        XF = Gh[1400] + Gh[171] + Gh[1401],
        KF = Gh[1402],
        ZF = Gh[1403],
        JF = Gh[1403] + Gh[110],
        QF = Gh[49] + Gh[171] + Gh[1404],
        tq = Gh[1273],
        iq = Gh[1403] + Gh[111],
        nq = Gh[1090] + Gh[20] + Gh[353],
        eq = Gh[1405],
        sq = Gh[1406],
        hq = Gh[242] + Gh[212] + Gh[1407],
        rq = Gh[242] + Gh[23] + Gh[1408],
        aq = Gh[1409],
        oq = Gh[20] + Gh[1410] + Gh[39] + Gh[1411],
        fq = Gh[1152],
        uq = Gh[1412],
        cq = Gh[1413],
        _q = Gh[447] + Gh[23] + Gh[451],
        dq = Gh[1215] + Gh[31] + Gh[454] + Gh[33] + Gh[1414],
        lq = Gh[483] + Gh[41] + Gh[1415],
        vq = Gh[20] + Gh[1416],
        bq = Gh[20] + Gh[1417] + Gh[104] + Gh[283],
        gq = Gh[20] + Gh[1418],
        yq = Gh[5] + Gh[171] + Gh[186] + Gh[23] + Gh[306],
        mq = Gh[279] + Gh[23] + Gh[280] + Gh[58] + Gh[197] + Gh[104] + Gh[1419] + Gh[58] + Gh[1420] + Gh[39] + Gh[1057] + Gh[33] + Gh[1421],
        Eq = Gh[20] + Gh[81],
        xq = Gh[68] + Gh[1] + Gh[417],
        pq = Gh[1422],
        wq = Gh[49] + Gh[1423],
        Tq = Gh[49] + Gh[191] + Gh[1424],
        kq = Gh[49] + Gh[254] + Gh[1425],
        Oq = Gh[49] + Gh[39] + Gh[1426],
        jq = Gh[49] + Gh[33] + Gh[1427],
        Mq = Gh[49] + Gh[1] + Gh[1428],
        Sq = Gh[173] + Gh[546] + Gh[33] + Gh[1063],
        Iq = Gh[173] + Gh[1429],
        Aq = Gh[254] + Gh[1430],
        Lq = Gh[58] + Gh[94] + Gh[377],
        Cq = Gh[23] + Gh[153] + Gh[377],
        Dq = Gh[303] + Gh[213] + Gh[377],
        Rq = Gh[41] + Gh[1150] + Gh[377],
        Pq = Gh[26] + Gh[240],
        Nq = Gh[171] + Gh[605] + Gh[23] + Gh[24],
        Bq = Gh[254] + Gh[394] + Gh[212] + Gh[423],
        $q = Gh[23] + Gh[306] + Gh[58] + Gh[307],
        Fq = Gh[104] + Gh[1431] + Gh[41] + Gh[1350],
        qq = Gh[393] + Gh[1432] + Gh[44] + Gh[279] + Gh[44] + Gh[995] + Gh[336],
        Gq = Gh[77] + Gh[72] + Gh[297],
        zq = Gh[77] + Gh[72] + Gh[260] + Gh[72] + Gh[199] + Gh[72] + Gh[178],
        Hq = Gh[393] + Gh[1432] + Gh[221] + Gh[173] + Gh[1433] + Gh[44] + Gh[1] + Gh[1434] + Gh[44] + Gh[279] + Gh[44] + Gh[995] + Gh[336] + Gh[425] + Gh[1] + Gh[299],
        Yq = Gh[1435],
        Uq = Gh[677] + Gh[1244],
        Wq = Gh[1436] + Gh[173] + Gh[859],
        Vq = Gh[296] + Gh[425] + Gh[504] + Gh[425] + Gh[1437],
        Xq = 0;
    if (t[xd]) {
            var Kq = navigator[pd],
                Zq = /opera/i [Lo](Kq),
                Jq = !Zq && /msie/i.test(Kq),
                Qq = /rv:11.0/i [Lo](Kq),
                tG = /MSIE 10./i.test(Kq);
            if (Qq && (Jq = !0), /msie\s[6,7,8]/i.test(Kq)) throw new Error("your browser is not supported");
            var iG = /webkit|khtml/i [Lo](Kq),
                nG = !iG && /gecko/i [Lo](Kq),
                eG = /firefox\//i [Lo](Kq),
                sG = /Chrome\//i [Lo](Kq),
                hG = !sG && /Safari\//i.test(Kq),
                rG = /Macintosh;/i [Lo](Kq),
                aG = /(iPad|iPhone|iPod)/g.test(Kq),
                oG = /Android/g [Lo](Kq),
                fG = /Windows Phone/g.test(Kq),
                uG = (aG || oG || fG) && u_ in t,
                cG = Kq.match(/AppleWebKit\/([0-9\.]*)/);
            if (cG && cG[Hh] > 1) {
                    parseFloat(cG[1])
                }
            oG && parseFloat(Kq[h_](/Android\s([0-9\.]*)/)[1])
        }
    t.requestAnimationFrame || (t[vr] = t[wd] || t.mozRequestAnimationFrame || t[Td] || t[kd] ||
        function (i) {
            return t[Od](function () {
                i()
            }, 1e3 / 60)
        }),
    t[jd] || (t[jd] = t.webkitCancelAnimationFrame || t.mozCancelAnimationFrame || t.oCancelAnimationFrame || t.msCancelAnimationFrame ||
        function (i) {
            return t[Md](i)
        });
    var _G = {
            SELECTION_TOLERANCE: uG ? 5 : 2,
            LABEL_COLOR: Sd
        };
    Z(_G, {
            FONT_STYLE: {
                get: function () {
                    return this[Id] || (this._fontStyle = Ad)
                },
                set: function (t) {
                    this[Id] != t && (this._fontStyle = t, this._fontChanged = !0)
                }
            },
            FONT_SIZE: {
                get: function () {
                    return this[Ld] || (this._fontSize = 12)
                },
                set: function (t) {
                    this[Ld] != t && (this[Ld] = t, this._fontChanged = !0)
                }
            },
            FONT_FAMILY: {
                get: function () {
                    return this[Cd] || (this[Cd] = "Verdana,helvetica,arial,sans-serif")
                },
                set: function (t) {
                    this[Cd] != t && (this[Cd] = t, this[Dd] = !0)
                }
            },
            FONT: {
                get: function () {
                    return (this[Dd] || this[Dd] === n) && (this[Dd] = !1, this[Rd] = this[Pd] + yr + this[Ya] + Ua + this[Wa]),
                    this[Rd]
                }
            }
        });
    var dG = function (t) {
            this._j8 = [],
            this._la = {},
            t && this.add(t)
        };
    dG[er] = {
            _j8: null,
            _la: null,
            get: function (t) {
                return this[Nd](t)
            },
            getById: function (t) {
                return this._la[t]
            },
            getByIndex: function (t) {
                return this._j8[t]
            },
            forEach: function (t, i, n) {
                return l(this._j8, t, i, n)
            },
            forEachReverse: function (t, i, n) {
                return b(this._j8, t, i, n)
            },
            size: function () {
                return this._j8[Hh]
            },
            contains: function (t) {
                return this[lu](t.id)
            },
            containsById: function (t) {
                return this._la.hasOwnProperty(t)
            },
            setIndex: function (t, i) {
                var n = this._j8[zo](t);
                if (0 > n) throw new Error(pa + t.id + Bd);
                return n == i ? !1 : (this._j8[Xh](n, 1), this._j8[Xh](i, 0, t), !0)
            },
            setIndexAfter: function (t, i) {
                var n = this._j8[zo](t);
                if (0 > n) throw new Error(pa + t.id + Bd);
                return n == i ? i : n == i + 1 ? i + 1 : (n > i && (i += 1), this._j8[Xh](n, 1), this._j8.splice(i, 0, t), i)
            },
            setIndexBefore: function (t, i) {
                var n = this._j8[zo](t);
                if (0 > n) throw new Error(pa + t.id + Bd);
                return n == i ? i : n == i - 1 ? i - 1 : (i > n && (i -= 1), this._j8.splice(n, 1), this._j8.splice(i, 0, t), i)
            },
            indexOf: function (t) {
                return this._j8[zo](t)
            },
            getIndexById: function (t) {
                var i = this[$d](t);
                return i ? this._j8[zo](i) : -1
            },
            add: function (t, i) {
                return $(t) ? this._fo(t, i) : this._k3(t, i)
            },
            addFirst: function (t) {
                return this.add(t, 0)
            },
            _fo: function (t, i) {
                if (0 == t[Hh]) return !1;
                var e = !1,
                    s = i >= 0;
                t = t._j8 || t;
                for (var h = 0, r = t[Hh]; r > h; h++) {
                        var a = t[h];
                        null !== a && a !== n && this._k3(a, i, !0) && (e = !0, s && i++)
                    }
                return e
            },
            _k3: function (t, i) {
                var e = t.id;
                return e === n || this[lu](e) ? !1 : (y(this._j8, t, i), this._la[e] = t, t)
            },
            remove: function (t) {
                return $(t) ? this[Fd](t) : t.id ? this._f7(t.id, t) : this[qd](t)
            },
            _mqr: function (t) {
                if (0 == t[Hh]) return !1;
                var i = !1;
                t = t._j8 || t;
                for (var e = 0, s = t[Hh]; s > e; e++) {
                    var h = t[e];
                    if (null !== h && h !== n) {
                        h.id === n && (h = this._la[h]);
                        var r = h.id;
                        this._f7(r, h, !0) && (i = !0)
                    }
                }
                return i
            },
            _f7: function (t, i) {
                return t !== n && this[lu](t) ? ((null === i || i === n) && (i = this[$d](t)), delete this._la[t], m(this._j8, i), !0) : !1
            },
            removeById: function (t) {
                var i = this._la[t];
                return i ? this._f7(t, i) : !1
            },
            set: function (t) {
                if (!t || 0 == t) return void this[Ra]();
                if (this[Lf]() || !$(t)) return this[Ra](),
                this.add(t);
                var i = [],
                    n = {},
                    e = 0;
                if (l(t, function (t) {
                        this._la[t.id] ? (n[t.id] = t, e++) : i[tr](t)
                    }, this), e != this[Hh]) {
                        var s = [];
                        this[Vf](function (t) {
                            n[t.id] || s[tr](t)
                        }, this),
                        s[Hh] && this[Fd](s)
                    }
                return i.length && this._fo(i),
                !0
            },
            clear: function () {
                return this[Lf]() ? !1 : (this._j8[Hh] = 0, this._la = {}, !0)
            },
            toDatas: function () {
                return this._j8[Vh](0)
            },
            isEmpty: function () {
                return 0 == this._j8[Hh]
            },
            valueOf: function () {
                return this._j8.length
            },
            clone: function (t) {
                var i = new dG;
                return i.add(t ? g(this._j8) : this.toDatas()),
                i
            }
        },
    Z(dG[er], {
            datas: {
                get: function () {
                    return this._j8
                }
            },
            random: {
                get: function () {
                    return this._j8 && this._j8.length ? this._j8[z(this._j8[Hh])] : null
                }
            },
            length: {
                get: function () {
                    return this._j8 ? this._j8[Hh] : 0
                }
            }
        });
    var lG = (2 * Math.PI, .5 * Math.PI),
        vG = function (t, i) {
            i = i.toUpperCase();
            for (var n = Jq ? t[Gd] : t.firstElementChild; n && (1 != n.nodeType || n.tagName && n[zd][Qr]() != i);) n = Jq ? n[Hd] : n[Yd];
            return n && 1 == n[Ud] && n[zd] && n[zd][Qr]() == i ? n : null
        },
        bG = function (t, i, n) {
            t instanceof bG && (i = t.y, t = t.x, n = t[mo]),
            this.set(t, i, n)
        },
        gG = function (t, i, n, e) {
            var s = t - n,
                h = i - e;
            return Math.sqrt(s * s + h * h)
        };
    bG.prototype = {
            x: 0,
            y: 0,
            rotate: n,
            set: function (t, i, n) {
                this.x = t || 0,
                this.y = i || 0,
                this[mo] = n || 0
            },
            negate: function () {
                this.x = -this.x,
                this.y = -this.y
            },
            offset: function (t, i) {
                this.x += t,
                this.y += i
            },
            equals: function (t) {
                return this.x == t.x && this.y == t.y
            },
            distanceTo: function (t) {
                return gG(this.x, this.y, t.x, t.y)
            },
            toString: function () {
                return Wd + this.x + Vd + this.y + Lr
            },
            clone: function () {
                return new bG(this.x, this.y)
            }
        },
    Object.defineProperty(bG[er], Xd, {
            get: function () {
                return Math[eo](this.x * this.x + this.y * this.y)
            }
        });
    var yG = function (t, i, e, s) {
            t !== n && this._ma(t, i, e, s)
        };
    yG.prototype = {
            _mm: null,
            _mk: null,
            _mi: null,
            _mo: null,
            _mq: null,
            _ms: null,
            _mu: 1,
            _ma: function (t, i, n, e) {
                this._mm = t,
                this._mk = i,
                this._mi = n,
                this._mo = e,
                t == n ? (this._mq = -1, this._mu = 0, this._ms = t) : (this._mq = (i - e) / (t - n), this._ms = i - this._mq * t, this._mu = 1),
                this._kd = Math.atan2(this._mo - this._mk, this._mi - this._mm),
                this[Kd] = Math.cos(this._kd),
                this[Hr] = Math.sin(this._kd)
            },
            _mun: function (t) {
                return 0 == this._mu ? Number.NaN : this._mq * t + this._ms
            },
            _mup: function (t) {
                return 0 == this._mq ? Number.NaN : (t - this._ms) / this._mq
            },
            _$e: function (t) {
                var i, n, e, s, h, r = t[0],
                    a = t[2],
                    o = t[4],
                    f = t[1],
                    u = t[3],
                    c = t[5],
                    _ = this._mq,
                    d = this._ms,
                    l = this._mu;
                if (0 == l ? (e = Math.sqrt((-_ * _ * r - _ * d) * o + _ * _ * a * a + 2 * _ * d * a - _ * d * r), s = -_ * a + _ * r, h = _ * o - 2 * _ * a + _ * r) : (e = Math[eo]((-f + _ * r + d) * c + u * u + (-2 * _ * a - 2 * d) * u + (_ * o + d) * f + (-_ * _ * r - _ * d) * o + _ * _ * a * a + 2 * _ * d * a - _ * d * r), s = -u + f + _ * a - _ * r, h = c - 2 * u + f - _ * o + 2 * _ * a - _ * r), 0 != h) {
                        i = (e + s) / h,
                        n = (-e + s) / h;
                        var v, b;
                        return i >= 0 && 1 >= i && (v = Fi(i, t)),
                        n >= 0 && 1 >= n && (b = Fi(n, t)),
                        v && b ? [v, b] : v ? v : b ? b : void 0
                    }
            },
            _3f: function (t, i, n) {
                if (this._mq == t._mq || 0 == this._mu && 0 == t._mu) return null;
                var e, s;
                if (e = 0 == this._mu ? this._ms : 0 == t._mu ? t._ms : (t._ms - this._ms) / (this._mq - t._mq), s = 0 == this._mq ? this._ms : 0 == t._mq ? t._ms : this._mu ? this._mq * e + this._ms : t._mq * e + t._ms, !i) return {
                    x: e,
                    y: s
                };
                var h, r, a;
                if (n) h = -i / 2,
                r = -h;
                else {
                    h = -gG(this._mm, this._mk, e, s),
                    r = gG(this._mi, this._mo, e, s);
                    var o = -h + r;
                    if (o > i) {
                        var f = i / o;
                        h *= f,
                        r *= f
                    } else a = (i - o) / 2
                }
                var u = this._79(e, s, h),
                    c = this._79(e, s, r);
                return a && (u[hf] = a, c[hf] = a),
                [u, c]
            },
            _79: function (t, i, n) {
                return 0 == this._mu ? {
                    x: t,
                    y: i + n
                } : {
                    x: t + n * this[Kd],
                    y: i + n * this._sin
                }
            }
        };
    var mG = function (t, i) {
            this.width = t,
            this[Da] = i
        };
    mG[er] = {
            width: 0,
            height: 0,
            isEmpty: function () {
                return this[Ca] <= 0 || this[Da] <= 0
            },
            clone: function () {
                return new mG(this[Ca], this[Da])
            },
            toString: function () {
                return Zd + this.width + Vd + this[Da] + Lr
            }
        };
    var EG = function (t, i, e, s) {
            t instanceof Object && !P(t) && (i = t.y, e = t.width, s = t[Da], t = t.x),
            e === n && (e = -1),
            s === n && (s = -1),
            this.x = t || 0,
            this.y = i || 0,
            this[Ca] = e,
            this.height = s
        };
    EG[er] = {
            x: 0,
            y: 0,
            width: -1,
            height: -1,
            setByRect: function (t) {
                this.x = t.x || 0,
                this.y = t.y || 0,
                this[Ca] = t.width || 0,
                this[Da] = t[Da] || 0
            },
            set: function (t, i, n, e) {
                this.x = t || 0,
                this.y = i || 0,
                this.width = n || 0,
                this[Da] = e || 0
            },
            offset: function (t, i) {
                return this.x += t,
                this.y += i,
                this
            },
            contains: function (t, i) {
                return t instanceof EG ? ai(this.x, this.y, this.width, this[Da], t.x, t.y, t.width, t[Da], i) : t >= this.x && t <= this.x + this[Ca] && i >= this.y && i <= this.y + this.height
            },
            intersectsPoint: function (t, i, n) {
                return this[Ca] <= 0 && this[Da] <= 0 ? !1 : n ? this[Jd](t - n, i - n, 2 * n, 2 * n) : t >= this.x && t <= this.x + this.width && i >= this.y && i <= this.y + this[Da]
            },
            intersectsRect: function (t, i, n, e) {
                return hi(this.x, this.y, this[Ca], this[Da], t, i, n, e)
            },
            intersects: function (t, i) {
                return P(t[Ca]) ? this[Jd](t.x, t.y, t.width, t[Da]) : this[Qd](t, i)
            },
            intersection: function (t, i, n, e) {
                var s = this.x,
                    h = this.y,
                    r = s;
                r += this.width;
                var a = h;
                a += this.height;
                var o = t;
                o += n;
                var f = i;
                return f += e,
                t > s && (s = t),
                i > h && (h = i),
                r > o && (r = o),
                a > f && (a = f),
                r -= s,
                a -= h,
                0 > r || 0 > a ? null : new EG(s, h, r, a)
            },
            addPoint: function (t) {
                this.add(t.x, t.y)
            },
            add: function (t, i) {
                if (P(t[Ca])) return this[tl](t.x, t.y, t[Ca], t[Da]);
                if (P(t.x) && (i = t.y, t = t.x), this[Ca] < 0 || this.height < 0) return this.x = t,
                this.y = i,
                void(this[Ca] = this[Da] = 0);
                var n = this.x,
                    e = this.y,
                    s = this[Ca],
                    h = this[Da];
                s += n,
                h += e,
                n > t && (n = t),
                e > i && (e = i),
                t > s && (s = t),
                i > h && (h = i),
                s -= n,
                h -= e,
                s > Number.MAX_VALUE && (s = Number[il]),
                h > Number.MAX_VALUE && (h = Number[il]),
                this.set(n, e, s, h)
            },
            addRect: function (t, i, n, e) {
                var s = this[Ca],
                    h = this[Da];
                    (0 > s || 0 > h) && this.set(t, i, n, e);
                var r = n,
                    a = e;
                if (!(0 > r || 0 > a)) {
                        var o = this.x,
                            f = this.y;
                        s += o,
                        h += f;
                        var u = t,
                            c = i;
                        r += u,
                        a += c,
                        o > u && (o = u),
                        f > c && (f = c),
                        r > s && (s = r),
                        a > h && (h = a),
                        s -= o,
                        h -= f,
                        s > Number[il] && (s = Number[il]),
                        h > Number[il] && (h = Number[il]),
                        this.set(o, f, s, h)
                    }
            },
            shrink: function (t, i, n, e) {
                return P(t) ? 1 == arguments[Hh] ? e = i = n = t || 0 : 2 == arguments[Hh] ? (n = t || 0, e = i || 0) : (t = t || 0, i = i || 0, n = n || 0, e = e || 0) : (i = t[Bo] || 0, n = t[Yr] || 0, e = t[Ur] || 0, t = t.top || 0),
                this.x += i,
                this.y += t,
                this[Ca] -= i + e,
                this[Da] -= t + n,
                this
            },
            grow: function (t, i, n, e) {
                return P(t) ? 1 == arguments[Hh] ? e = i = n = t || 0 : 2 == arguments[Hh] ? (n = t || 0, e = i || 0) : (t = t || 0, i = i || 0, n = n || 0, e = e || 0) : (i = t[Bo] || 0, n = t[Yr] || 0, e = t[Ur] || 0, t = t.top || 0),
                this.x -= i,
                this.y -= t,
                this[Ca] += i + e,
                this[Da] += t + n,
                this
            },
            scale: function (t) {
                return this.x *= t,
                this.y *= t,
                this[Ca] *= t,
                this[Da] *= t,
                this
            },
            isEmpty: function () {
                return this[Ca] <= 0 && this[Da] <= 0
            },
            toString: function () {
                return this.x + nl + this.y + nl + this[Ca] + nl + this[Da]
            },
            union: function (t) {
                var i = this.width,
                    n = this[Da];
                if (0 > i || 0 > n) return new EG(t.x, t.y, t.width, t[Da]);
                var e = t[Ca],
                    s = t.height;
                if (0 > e || 0 > s) return new EG(this.x, this.y, this.width, this[Da]);
                var h = this.x,
                    r = this.y;
                i += h,
                n += r;
                var a = t.x,
                    o = t.y;
                return e += a,
                s += o,
                h > a && (h = a),
                r > o && (r = o),
                e > i && (i = e),
                s > n && (n = s),
                i -= h,
                n -= r,
                i > Number.MAX_VALUE && (i = Number[il]),
                n > Number[il] && (n = Number.MAX_VALUE),
                new EG(h, r, i, n)
            },
            clear: function () {
                this.set(0, 0, -1, -1)
            },
            equals: function (t) {
                return t && this.x == t.x && this.y == t.y && this[Ca] == t[Ca] && this[Da] == t.height
            },
            clone: function (t, i) {
                return new EG(this.x + (t || 0), this.y + (i || 0), this.width, this.height)
            },
            toArray: function () {
                return [this.x, this.y, this[Ca], this[Da]]
            },
            getIntersectionPoint: function (t, i, n, e) {
                return si(this, t, i, n, e)
            }
        },
    p(EG, mG),
    EG.equals = function (t, i) {
            return t == i || t && i && t.x == i.x && t.y == i.y && t.width == i.width && t[Da] == i.height
        },
    Z(EG[er], {
            left: {
                get: function () {
                    return this.x
                }
            },
            top: {
                get: function () {
                    return this.y
                }
            },
            bottom: {
                get: function () {
                    return this.y + this.height
                }
            },
            right: {
                get: function () {
                    return this.x + this[Ca]
                }
            },
            cx: {
                get: function () {
                    return this.x + this.width / 2
                }
            },
            cy: {
                get: function () {
                    return this.y + this.height / 2
                }
            },
            center: {
                get: function () {
                    return new bG(this.cx, this.cy)
                }
            }
        }),
    EG[Dc] = hi,
    EG[el] = oi,
    EG.intersectsPoint = ri;
    var xG = function (t, i, n, e) {
            1 == arguments[Hh] ? i = n = e = t : 2 == arguments.length && (n = t, e = i),
            this.set(t, i, n, e)
        };
    xG.prototype = {
            top: 0,
            bottom: 0,
            left: 0,
            right: 0,
            set: function (t, i, n, e) {
                this.top = t || 0,
                this[Bo] = i || 0,
                this[Yr] = n || 0,
                this[Ur] = e || 0
            },
            clone: function () {
                return new xG(this.top, this[Bo], this[Yr], this[Ur])
            },
            equals: function (t) {
                return t && this.top == t.top && this.bottom == t[Yr] && this[Bo] == t[Bo] && this.right == t[Ur]
            }
        };
    var pG = function (t, i) {
            this[Vr] = t,
            this[Xr] = i
        };
    pG[er] = {
            verticalPosition: !1,
            horizontalPosition: !1,
            toString: function () {
                return (this.horizontalPosition || "") + (this.verticalPosition || "")
            }
        },
    K(pG.prototype, sl, {
            get: function () {
                return (this[Vr] || "") + (this[Xr] || "")
            }
        });
    var wG = hl,
        TG = rl,
        kG = al,
        OG = ou,
        jG = ol,
        MG = fl;
    pG[ul] = new pG(wG, OG),
    pG[cl] = new pG(wG, jG),
    pG[_l] = new pG(wG, MG),
    pG[dl] = new pG(TG, OG),
    pG[ll] = new pG(TG, jG),
    pG[vl] = new pG(TG, MG),
    pG.RIGHT_TOP = new pG(kG, OG),
    pG.RIGHT_MIDDLE = new pG(kG, jG),
    pG[bl] = new pG(kG, MG);
    var SG = [pG[ul], pG[cl], pG[_l], pG[dl], pG[ll], pG.CENTER_BOTTOM, pG[gl], pG[yl], pG[bl]];
    K(pG, Sr, {
            get: function () {
                return SG[z(SG[Hh])]
            }
        }),
    pG[Wr] = function (t) {
            for (var i in pG) {
                var n = pG[i];
                if (n && Sr != i && n instanceof pG && n.toString() == t) return n
            }
        };
    var IG = function (t, i, n, e, s) {
            this.set(t, i, n, e),
            this[ml] = s
        };
    IG[er] = {
            radius: 0,
            classify: function (t, i, n, e) {
                return i > t ? 0 : i + e > t ? 1 : n - e > t ? 2 : n > t ? 3 : 4
            },
            intersectsRect: function (t, i, n, e) {
                if (T(this, IG, Jd, arguments) === !1) return !1;
                var s = this.x,
                    h = this.y,
                    r = s + this[Ca],
                    a = h + this[Da],
                    o = 2 * radius,
                    f = 2 * radius,
                    u = Math.min(this[Ca], Math.abs(o)) / 2,
                    c = Math.min(this[Da], Math.abs(f)) / 2,
                    _ = this[El](t, s, r, u),
                    d = this.classify(t + n, s, r, u),
                    l = this.classify(i, h, a, c),
                    v = this[El](i + e, h, a, c);
                return 2 == _ || 2 == d || 2 == l || 2 == v ? !0 : 2 > _ && d > 2 || 2 > l && v > 2 ? !0 : (t = 1 == d ? t = t + n - (s + u) : t -= r - u, i = 1 == v ? i = i + e - (h + c) : i -= a - c, t /= u, i /= c, 1 >= t * t + i * i)
            },
            intersectsPoint: function (t, i) {
                if (T(this, IG, Qd, arguments) === !1) return !1;
                var n = this.x,
                    e = this.y,
                    s = n + this[Ca],
                    h = e + this[Da];
                if (n > t || e > i || t >= s || i >= h) return !1;
                var r = 2 * radius,
                    a = 2 * radius,
                    o = Math.min(this.width, Math.abs(r)) / 2,
                    f = Math.min(this[Da], Math.abs(a)) / 2;
                return t >= (n += o) && t < (n = s - o) ? !0 : i >= (e += f) && i < (e = h - f) ? !0 : (t = (t - n) / o, i = (i - e) / f, 1 >= t * t + i * i)
            },
            clone: function () {
                return new IG(this.x, this.y, this[Ca], this.height, this[ml])
            }
        },
    p(IG, EG);
    var AG = function (t, i, n, e) {
            this[Fo] = t,
            this[yo] = i,
            this[cd] = n,
            this.value = e
        };
    AG[er] = {
            source: null,
            type: null,
            kind: null,
            value: null,
            toString: function () {
                return xl + this.source + pl + this[yo] + wl + this[cd]
            }
        };
    var LG = function (t, i, n, e, s) {
            this[Fo] = t,
            this[cd] = i,
            this[Tl] = e,
            this[cr] = n,
            this[kl] = s
        };
    LG[er] = {
            type: Ol,
            propertyType: null,
            toString: function () {
                return xl + this[Fo] + pl + this[yo] + jl + this[cd] + Ml + this[Tl] + Sl + this[cr]
            }
        },
    p(LG, AG),
    K(LG[er], Il, {
            get: function () {
                return this[cd]
            },
            set: function (t) {
                this[cd] = t
            }
        });
    var CG = function (t, i, n) {
            this[Fo] = t,
            this[Tl] = t[yu],
            this[cr] = i,
            this[Al] = n,
            this.oldValue && (this[Ll] = this[Tl][Cl](t))
        };
    CG[er] = {
            kind: yu
        },
    p(CG, LG);
    var DG = function (t, i) {
            this[Fo] = t,
            this.value = i
        };
    DG[er][cd] = Dl,
    p(DG, LG);
    var RG = function (t, i) {
            this[Fo] = t,
            this.value = i
        };
    RG[er][cd] = Rl,
    p(RG, LG);
    var PG = function (t, i, n, e) {
            this[Fo] = i,
            this[Tl] = n,
            this.value = e,
            this.parent = t,
            this[Pl] = i,
            this[Ll] = n,
            this[Al] = e
        };
    PG[er].kind = Nl,
    p(PG, LG);
    var NG = function () {};
    NG[er] = {
            listener: null,
            beforeEvent: function (t) {
                return null != this[Bl] && this[Bl].beforeEvent ? this.listener[$l](t) : !0
            },
            onEvent: function (t) {
                null != this.listener && this[Bl].onEvent && this[Bl][or](t)
            }
        };
    var BG = function () {
            w(this, BG, arguments),
            this.events = {},
            this[Fl] = []
        },
        $G = function (t, i) {
            this.listener = t,
            this.scope = i,
            t instanceof Function ? this.onEvent = t : (this.onEvent = t[or], this[$l] = t[$l]),
            this[ql] = function (t) {
                return t && this[Bl] == t.listener && this.scope == t[Gl]
            }
        };
    $G[er] = {
            equals: function (t) {
                return t && this.listener == t[Bl] && this[Gl] == t[Gl]
            },
            destroy: function () {
                delete this.scope,
                delete this[Bl]
            }
        },
    BG[er] = {
            listeners: null,
            _ms8: function () {
                return this[Fl] && this.listeners[Hh] > 0
            },
            _5u: function (t, i) {
                return t instanceof BG ? t : new $G(t, i)
            },
            _8x: function (t, i) {
                if (t instanceof BG) return this[Fl].indexOf(t);
                for (var n = this[Fl], e = 0, s = n.length; s > e; e++) {
                    var h = n[e];
                    if (h.listener == t && h[Gl] == i) return e
                }
                return -1
            },
            contains: function (t, i) {
                return this._8x(t, i) >= 0
            },
            addListener: function (t, i) {
                return this.contains(t, i) ? !1 : void this[Fl][tr](this._5u(t, i))
            },
            removeListener: function (t, i) {
                var n = this._8x(t, i);
                n >= 0 && this[Fl][Xh](n, 1)
            },
            on: function (t, i) {
                this[_d](t, i)
            },
            un: function (t, i, n) {
                this[zl](t, i, n)
            },
            onEvent: function (t) {
                return this.listeners ? void l(this.listeners, function (i) {
                    i[or] && (i[Gl] ? i[or][Yh](i[Gl], t) : i[or](t))
                }, this) : !1
            },
            beforeEvent: function (t) {
                return this[Fl] ? l(this[Fl], function (i) {
                    return i.beforeEvent ? i[Gl] ? i.beforeEvent[Yh](i.scope, t) : i[$l](t) : !0
                }, this) : !0
            },
            _dc: function (t) {
                return this[$l](t) === !1 ? !1 : (this[or](t), !0)
            },
            clear: function () {
                this[Fl] = []
            },
            destroy: function () {
                this[Ra]()
            }
        },
    p(BG, NG);
    var FG = {
            onEvent: function () {},
            beforeEvent: function () {}
        },
        qG = function (t, i, n, e, s) {
            this[Fo] = t,
            this.type = Hl,
            this[cd] = i,
            this.data = n,
            this[Yl] = e,
            this.oldIndex = s
        };
    qG[er] = {
            index: -1,
            oldIndex: -1,
            toString: function () {
                return xl + this[Fo] + pl + this.type + wl + this[cd] + Ul + this.data + Wl + this[Yl] + Vl + this.oldIndex
            }
        },
    p(qG, AG),
    qG[Xl] = Kl,
    qG.KIND_REMOVE = Jh,
    qG[Zl] = Ra,
    qG[Jl] = Ql;
    var GG = function () {
            this.id = ++Xq,
            this[tv] = {}
        };
    GG.prototype = {
            _msy: null,
            id: null,
            get: function (t) {
                return this._msy[t]
            },
            set: function (t, i) {
                var n = this.get(t);
                if (n === i) return !1;
                var e = new LG(this, t, i, n);
                return e[kl] = Mz[lc],
                this[iv](t, i, e, this[tv])
            },
            _mqd: function (t, i, e, s) {
                return this.beforeEvent(e) === !1 ? !1 : (s || (s = this[tv]), i === n ? delete s[t] : s[t] = i, this.onEvent(e), !0)
            },
            remove: function (t) {
                this.set(t, null)
            },
            valueOf: function () {
                return this.id
            },
            toString: function () {
                return this.id
            },
            _dg: function (t, i) {
                if (i === n && (i = -1), this == t || t == this._je) return !1;
                if (t && this == t._je && !t._dg(null)) return !1;
                var e = new CG(this, t, i);
                if (!this[$l](e)) return !1;
                var s, h, r = this._je;
                return t && (s = new DG(t, this), !t[$l](s)) ? !1 : null == r || (h = new RG(r, this), r[$l](h)) ? (this._je = t, null != t && ui(t, this, i), null != r && ci(r, this), this[or](e), null != t && t[or](s), null != r && r.onEvent(h), this[nv](r, t), !0) : !1
            },
            addChild: function (t, i) {
                var n = t._dg(this, i);
                return n && this[ev](t, i),
                n
            },
            onChildAdd: function () {},
            removeChild: function (t) {
                if (!this._f5 || !this._f5[i_](t)) return !1;
                var i = t._dg(null);
                return this[Kr](t),
                i
            },
            onChildRemove: function () {},
            toChildren: function () {
                return this._f5 ? this._f5.toDatas() : null
            },
            clearChildren: function () {
                if (this._f5 && this._f5[Hh]) {
                    var t = this[sv]();
                    l(t, function (t) {
                        t._dg(null)
                    }, this),
                    this.onChildrenClear(t)
                }
            },
            forEachChild: function (t, i) {
                return this.hasChildren() ? this._f5[Vf](t, i) : !1
            },
            onChildrenClear: function () {},
            getChildIndex: function (t) {
                return this._f5 && this._f5[Hh] ? this._f5[zo](t) : -1
            },
            setChildIndex: function (t, i) {
                if (!this._f5 || !this._f5.length) return !1;
                var n = this._f5[zo](t);
                if (0 > n || n == i) return !1;
                var e = new PG(this, t, n, i);
                return this.beforeEvent(e) === !1 ? !1 : (this._f5.remove(t) && this._f5.add(t, i), this[or](e), !0)
            },
            hasChildren: function () {
                return this._f5 && this._f5[Hh] > 0
            },
            getChildAt: function (t) {
                return null == this._f5 ? null : this._f5._j8[t]
            },
            isDescendantOf: function (t) {
                if (!t[Uh]()) return !1;
                for (var i = this.parent; null != i;) {
                    if (t == i) return !0;
                    i = i.parent
                }
                return !1
            },
            onParentChanged: function () {},
            firePropertyChangeEvent: function (t, i, n, e) {
                this.onEvent(new LG(this, t, i, n, e))
            }
        },
    p(GG, NG),
    Z(GG[er], {
            childrenCount: {
                get: function () {
                    return this._f5 ? this._f5[Hh] : 0
                }
            },
            children: {
                get: function () {
                    return this._f5 || (this._f5 = new dG),
                    this._f5
                }
            },
            parent: {
                get: function () {
                    return this._je
                },
                set: function (t) {
                    this._dg(t, -1)
                }
            },
            properties: {
                get: function () {
                    return this[tv]
                },
                set: function (t) {
                    this[tv] != t && (this._msy = t)
                }
            }
        });
    var zG = function () {
            this._j8 = [],
            this._la = {},
            this._1g = new BG
        };
    zG[er] = {
            beforeEvent: function (t) {
                return null != this._1g && this._1g.beforeEvent ? this._1g.beforeEvent(t) : !0
            },
            onEvent: function (t) {
                return this._1g instanceof Function ? void this._1g(t) : void(null != this._1g && this._1g.onEvent && this._1g.onEvent(t))
            },
            _1g: null,
            setIndex: function (t, i) {
                if (!this[i_](t)) throw new Error(pa + t.getId() + Bd);
                var n = this.indexOf(t);
                if (n == i) return !1;
                var e = new qG(this, qG[Jl], t, i, n);
                return this[$l](e) === !1 ? !1 : (this._j8[Jh](t) >= 0 && this._j8.add(i, t), this.onEvent(e), !0)
            },
            _fo: function (t, i) {
                if (0 == t.length) return !1;
                var e = !1,
                    s = i >= 0,
                    h = new qG(this, qG[Xl], t, i);
                if (this[$l](h) === !1) return !1;
                var r = [];
                t = t._j8 || t;
                for (var a = 0, o = t.length; o > a; a++) {
                        var f = t[a];
                        null !== f && f !== n && this._k3(f, i, !0) && (r[tr](f), e = !0, s && i++)
                    }
                return h.data = r,
                this[or](h),
                e
            },
            _k3: function (t, i, n) {
                if (this.accept(t) === !1) return !1;
                if (n) return T(this, zG, hv, arguments);
                var e = new qG(this, qG[Xl], t, i);
                return this[$l](e) === !1 ? !1 : T(this, zG, hv, arguments) ? (this._k5(t, e), t) : !1
            },
            _k5: function (t, i) {
                this[or](i)
            },
            _mqr: function (t) {
                if (0 == t[Hh]) return !1;
                var i = new qG(this, qG[rv], t);
                if (this[$l](i) === !1) return !1;
                var e = [],
                    s = !1;
                t = t._j8 || t;
                for (var h = 0, r = t.length; r > h; h++) {
                        var a = t[h];
                        if (null !== a && a !== n) {
                            var o = a.id || a;
                            a.id === n && (a = null),
                            this._f7(o, a, !0) && (e[tr](a), s = !0)
                        }
                    }
                return i.data = e,
                this.onEvent(i),
                s
            },
            _f7: function (t, i, n) {
                if (n) return T(this, zG, av, arguments);
                var e = new qG(this, qG.KIND_REMOVE, i);
                return this.beforeEvent(e) === !1 ? !1 : T(this, zG, av, arguments) ? (this[or](e), !0) : !1
            },
            clear: function () {
                if (this.isEmpty()) return !1;
                var t = new qG(this, qG[Zl], this[ov]());
                return this[$l](t) === !1 ? !1 : T(this, zG, Ra) ? (this.onEvent(t), !0) : !1
            },
            accept: function (t) {
                return this.filter && this.filter(t) === !1 ? !1 : !0
            }
        },
    p(zG, dG),
    K(zG[er], fv, {
            get: function () {
                return this._1g
            }
        });
    var HG = function () {
            w(this, HG, arguments),
            this[uv] = new BG,
            this[cv] = new YG(this),
            this[cv]._1g = this.selectionChangeDispatcher,
            this[_v] = new BG,
            this.dataChangeDispatcher.addListener({
                beforeEvent: this[dv],
                onEvent: this.onDataPropertyChanged
            }, this),
            this[lv] = new BG,
            this[vv] = new BG,
            this[bv] = new dG;
            var t = this;
            this[bv][Au] = function (i, n) {
                if (!t[bv][i_](i)) throw new Error(pa + i.id + Bd);
                var e = t[bv]._j8[zo](i);
                if (e == n) return !1;
                t[bv]._j8[Xh](e, 1),
                t[bv]._j8[Xh](n, 0, i),
                t[gv] = !0;
                var s = new PG(t, i, e, n);
                return t._1z(s),
                !0
            }
        };
    HG[er] = {
            selectionModel: null,
            selectionChangeDispatcher: null,
            dataChangeDispatcher: null,
            parentChangeDispatcher: null,
            roots: null,
            _k5: function (t, i) {
                t.listener = this[_v],
                t[yu] || this[bv].add(t),
                this[or](i)
            },
            _f7: function (t, i) {
                if (T(this, HG, av, arguments)) {
                    if (i instanceof DY) i[yv]();
                    else if (i instanceof RY) {
                        var n = i[mv]();
                        this.remove(n)
                    }
                    var e = i[yu];
                    return null == e ? this[bv][Jh](i) : (e[Ev](i), e[xv] = !0),
                    i[Uh]() && this[Jh](i.toChildren()),
                    i[Bl] = null,
                    !0
                }
                return !1
            },
            _4v: function (t) {
                var i = t.source;
                this.contains(i) && (null == i[yu] ? this[bv].add(i) : null == t[Tl] && this[bv].remove(i), this[lv][or](t))
            },
            _1z: function (t) {
                this[vv][or](t)
            },
            beforeDataPropertyChange: function (t) {
                return t instanceof CG ? this[lv].beforeEvent(t) : !0
            },
            onDataPropertyChanged: function (t) {
                return t instanceof CG ? (this[gv] = !0, t.source[gv] = !0, void this._4v(t)) : void(t instanceof PG && (this[gv] = !0, t.source[gv] = !0, this._1z(t)))
            },
            toRoots: function () {
                return this[bv][ov]()
            },
            _gi: function (t) {
                var i, n = t._je;
                i = n ? n._f5 : this[bv];
                var e = i[zo](t);
                if (0 > e) throw new Error(pv + t + "' not exist in the box");
                return 0 == e ? n : i[Nd](e - 1)
            },
            _g5: function (t) {
                var i, n = t._je;
                i = n ? n._f5 : this.$roots;
                var e = i[zo](t);
                if (0 > e) throw new Error(pv + t + "' not exist in the box");
                return e == i[Hh] - 1 ? n ? this._g5(n) : null : i.getByIndex(e + 1)
            },
            forEachByDepthFirst: function (t, i, n) {
                return this[bv][Hh] ? h(this.$roots, t, i, n) : !1
            },
            forEachByDepthFirstReverse: function (t, i, n) {
                return this[bv].length ? o(this.$roots, t, i, n) : !1
            },
            forEachByBreadthFirst: function (t, i) {
                return this[bv][Hh] ? c(this[bv], t, i) : !1
            },
            forEachByBreadthFirstReverse: function (t, i) {
                return this[bv][Hh] ? _(this.$roots, t, i) : !1
            },
            clear: function () {
                return T(this, HG, Ra) ? (this[bv][Ra](), this[B_][Ra](), !0) : !1
            }
        },
    p(HG, zG),
    Z(HG[er], {
            selectionModel: {
                get: function () {
                    return this._selectionModel
                }
            },
            roots: {
                get: function () {
                    return this[bv]
                }
            }
        });
    var YG = function (t) {
            w(this, YG),
            this.box = t,
            this._msoxChangeListener = {
                onEvent: function (t) {
                    qG.KIND_REMOVE == t[cd] ? null != t[vo] ? this.remove(t.data) : null != t[wv] && this.remove(t[wv]) : qG[Zl] == t[cd] && this.clear()
                }
            },
            this.box[fv][_d](this[Tv], this)
        };
    YG[er] = {
            box: null,
            isSelected: function (t) {
                return this.containsById(t.id || t)
            },
            select: function (t) {
                return this.add(t)
            },
            unselect: function (t) {
                return this.remove(t)
            },
            reverseSelect: function (t) {
                return this.contains(t) ? this[Jh](t) : this.add(t)
            },
            accept: function (t) {
                return this.box.contains(t)
            }
        },
    p(YG, zG);
    var UG = null,
        WG = null,
        VG = function () {
            if (!i.createElement) return function (t) {
                return t
            };
            var t = i[qa](a_),
                e = t.style,
                s = {};
            return function (t) {
                    if (s[t]) return s[t];
                    var i = _i(t);
                    return e[i] !== n || WG && e[i = _i(WG + i)] !== n ? (s[t] = i, i) : t
                }
        }(),
        XG = {};
    !
    function () {
            if (!i[kv]) return !1;
            for (var e = i[kv], s = "Webkit Moz O ms Khtml".split(yr), h = 0; h < s[Hh]; h++) if (e[na][s[h] + Ov] !== n) {
                WG = ta + s[h][jv]() + ta;
                break
            }
            var r = i[qa](na);
            t.createPopup || r[bu](i.createTextNode("")),
            r[yo] = Mv,
            r.id = Sv,
            e[bu](r),
            UG = r[Iv];
            var a, o;
            for (var f in XG) {
                var u = XG[f];
                a = f,
                o = "";
                for (var c in u) o += VG(c) + ea + u[c] + Av;
                gi(a, o)
            }
        }();
    var KG = function (t, i, n, e, s) {
            if (s) {
                var h = function (t) {
                    h[Lv][Yh](h.scope, t)
                };
                return h.scope = s,
                h.handle = n,
                t[Cv](i, h, e),
                h
            }
            return t[Cv](i, n, e),
            n
        },
        ZG = function (t, i, n) {
            t[Dv](i, n)
        };
    if (_G[Rv] = 200, _G.LONG_PRESS_INTERVAL = 800, _G.DELAY_CLICK = !0, t.navigator && navigator[pd]) {
            var JG, QG = /mobile|tablet|ip(ad|hone|od)|android/i,
                tz = u_ in t,
                iz = tz && QG[Lo](navigator[pd]);
            if (iz) JG = Pv;
            else {
                    var nz = Nv in t ? "mousewheel" : Bv;
                    JG = $v + nz,
                    tz && (JG += Fv)
                }
            JG = JG[mr](/[\s,]+/);
            var ez = function (i) {
                    return t[qv] && i instanceof t[qv]
                },
                sz = function () {
                    return _G[Rv]
                },
                hz = function () {
                    return _G[Gv]
                },
                F = function (t) {
                    t[zv] ? t[zv]() : t.returnValue = !1
                },
                q = function (t) {
                    t.stopPropagation && t[Or](),
                    t.cancelBubble = !0
                },
                G = function (t) {
                    F(t),
                    q(t)
                },
                rz = function (t) {
                    return t.defaultPrevented || t[kr] === !1
                },
                az = function (t) {
                    dz[Hv] && dz[Hv][Yv](t)
                },
                oz = function (t) {
                    if (dz[Hv]) {
                        var i = dz[Hv];
                        i[Uv](t),
                        fz(null)
                    }
                },
                fz = function (t) {
                    dz._muurrentItem != t && (dz[Hv] && (dz[Hv][Wv] = !1), dz[Hv] = t)
                },
                uz = function (i, n) {
                    JG[Vf](function (t) {
                        i[Cv](t, n, !1)
                    }),
                    uG || dz._mq0 || (dz._mq0 = !0, t[Cv](Vv, az, !0), t[Cv](Xv, oz, !0))
                },
                cz = function (t, i) {
                    JG.forEach(function (n) {
                        t.removeEventListener(n, i, !1)
                    })
                },
                _z = function (t) {
                    return t.touches ? {
                        timeStamp: t[Kv],
                        x: t.cx,
                        y: t.cy
                    } : {
                        timeStamp: t.timeStamp,
                        x: t.clientX,
                        y: t[_a]
                    }
                };
            mi.prototype = {
                    _install: function () {
                        this.__mqction || (this[Zv] = function (t) {
                            this._mqction(t)
                        }[lr](this), uz(this._lq, this[Zv]))
                    },
                    _uninstall: function () {
                        this[Zv] && cz(this._lq, this[Zv])
                    },
                    _mqction: function (t) {
                        t = this[Jv](t);
                        var i = t.type;
                        this[Qv](t, i) === !1 && this._onEvent(t, tb + i)
                    },
                    _muancelLongPressTimer: function () {
                        this[ib] && (clearTimeout(this.__longPressTimer), this.__longPressTimer = null)
                    },
                    __kjLongPress: function (t) {
                        this[nb] || (this[nb] = function () {
                            this._kjEvent && (this[eb] = !0, this[sb][hb] ? this._onEvent(this[sb], rb) : this._onEvent(this[sb], ab))
                        }[lr](this)),
                        this[ob](),
                        this[ib] = setTimeout(this.__onLongPressFunction, hz(t))
                    },
                    __fixTouchEvent: function (t) {
                        for (var i, n, e = 0, s = 0, h = t.touches.length, r = 0; h > r;) {
                            var a = t[Fr][r++],
                                o = a[ca],
                                f = a[_a];
                            if (2 == r) {
                                    var u = n[0] - o,
                                        c = n[1] - f;
                                    i = Math.sqrt(u * u + c * c)
                                }
                            n = [o, f],
                            e += o,
                            s += f
                        }
                        t.cx = e / h,
                        t.cy = s / h,
                        t[Jc] = {
                            x: t.cx,
                            y: t.cy,
                            clientX: t.cx,
                            clientY: t.cy
                        },
                        t[Xd] = i
                    },
                    __touchCountChange: function (t) {
                        this._dragPoints[Ra](),
                        this._8q = _z(t)
                    },
                    _handleTouchEvent: function (t, i) {
                        switch (i) {
                        case "touchstart":
                            q(t),
                            this[fb](t),
                            this[ub](t);
                            var n = t[Fr][Hh];
                            this[sb] || (this[sb] = t, this[cb](t), this.__muancelClick = !1, this.__kjLongPress(t)),
                            1 == n && (this[_b] = null),
                            n >= 2 && !this[_b] && (this[_b] = {
                                cx: t.cx,
                                cy: t.cy,
                                distance: t[Xd]
                            });
                            break;
                        case "touchmove":
                            G(t),
                            this[fb](t);
                            var n = t[Fr][Hh];
                            if (n >= 2 && this[_b]) {
                                var e = this[_b].distance;
                                t[db] = t[Xd] / e,
                                t.dScale = this[_b].prevScale ? t._scale / this.__kjMulTouchEvent[lb] : t[db],
                                this[_b][lb] = t._scale,
                                this[vb] || (this.__pinching = !0, this[bb](t, gb))
                            }
                            this.__dragging || (this[Wv] = !0, this._kjdrag(t)),
                            this[yb](t),
                            this.__pinching && this[bb](t, mb);
                            break;
                        case "touchend":
                            G(t);
                            var n = t.touches[Hh];
                            n && (this[fb](t), this.__touchCountChange(t)),
                            1 >= n && (this[vb] && (this[vb] = !1, this[bb](t, Eb)), this.__kjMulTouchEvent = null),
                            0 == n && (this[Wv] ? (this[xb](t), this.__dragging = !1) : t[Kv] - this[sb][Kv] < .8 * sz(t) && this.__onclick(this._kjEvent), this[pb](t));
                            break;
                        case "touchcancel":
                            this[Wv] = !1,
                            this.__pinching = !1,
                            this[_b] = null
                        }
                        return !1
                    },
                    _handleEvent: function (t, i) {
                        if (ez(t)) return this[wb](t, i);
                        if (Tb == i) q(t),
                        fz(this),
                        this._8q = _z(t),
                        this._kjEvent || (this[sb] = t, this[cb](t)),
                        this[eb] = !1,
                        this.__kjLongPress(t);
                        else if (Xv == i) fz(),
                        this[pb](t);
                        else if (kb == i) {
                            if (this[eb]) return !0;
                            if (this[Ob]()) return this[jb](t),
                            !0
                        } else if (Mb == i) {
                            if (this._isDelayClick()) return !0
                        } else {
                            if (Sb == i) return this[bb](t, Ib),
                            this[sb] && rz(t) && fz(this),
                            !0;
                            if (i == nz) {
                                var e = t[Ab];
                                if (e !== n ? e /= 120 : e = -t.detail, !e) return;
                                return t[Lb] = e,
                                this[bb](t, Nv)
                            }
                        }
                        return !1
                    },
                    _onEvent: function (t, i) {
                        if (this[Cb]) {
                            var n = this[Cb];
                            if (i = i || t[yo], n instanceof Function) return n(t, i);
                            if (!(n[Mu] instanceof Function && n[Mu](i, t) === !1)) return n[Db] instanceof Function && n[Db](i, t, this._scope || this._lq),
                            n[i] instanceof Function ? n[i].call(n, t, this[ga] || this._lq) : void 0
                        }
                    },
                    _toQEvent: function (t) {
                        return t
                    },
                    _onWindowMouseUp: function (t) {
                        this.__dragging && (G(t), this.__dragging = !1, t = this[Jv](t), this._enddrag(t), this._onrelease(t), this[bb](t, Rb))
                    },
                    _kjDragDistance: 4,
                    _onWindowMouseMove: function (t) {
                        if (this[sb]) {
                            if (G(t), !this.__dragging) {
                                var i = this[sb].screenX - t[Pb],
                                    n = this[sb][Nb] - t[Nb];
                                if (i * i + n * n < this[Bb]) return;
                                this[Wv] = !0,
                                this[$b](t)
                            }
                            this[yb](this[Jv](t))
                        }
                    },
                    _isDelayClick: function () {
                        return _G.DELAY_CLICK
                    },
                    __onclick: function (t) {
                        if (!this.__muancelClick) {
                            var i = sz(t);
                            this[Fb] ? this.__dblclicked || (clearTimeout(this[Fb]), this[Fb] = null, this._onEvent(t, qb), this.__dblclicked = !0) : (this[Gb] = !1, this.__mulickTimer = setTimeout(function (t) {
                                this[Fb] = null,
                                this[Gb] || this[bb](t, c_)
                            }.bind(this, t, i), i))
                        }
                    },
                    _onstart: function (t) {
                        t[hb] ? this[bb](t, zb) : this._onEvent(t, Hb)
                    },
                    _onrelease: function (t) {
                        this[sb] && (this[ob](), t.button ? this[bb](t, Yb) : this[bb](t, Ub), this._kjEvent = null, this._8q = null)
                    },
                    _mqppendDragInfo: function (t) {
                        var i = this._8q;
                        this._8q = _z(t),
                        this._dragPoints.add(i, this._8q, t)
                    },
                    _kjdrag: function () {
                        this.__muancelClick = !0,
                        this._muancelLongPressTimer(),
                        this[sb][hb] ? this[bb](this._kjEvent, Wb) : this[bb](this[sb], Vb)
                    },
                    _ondrag: function (t) {
                        this[Xb](t),
                        this._kjEvent[hb] ? this[bb](t, Kb) : this[bb](t, Zb)
                    },
                    _enddrag: function (t) {
                        if (t.timeStamp - this._8q[Kv] < 100) {
                            var i = this._dragPoints[Jb]();
                            i && (t.vx = i.x, t.vy = i.y)
                        }
                        this._kjEvent[hb] ? this[bb](t, Qb) : this[bb](t, tg),
                        this._dragPoints[Ra]()
                    },
                    _hg: function () {
                        this._khStatus()
                    },
                    _khStatus: function () {
                        dz[Hv] == this && delete dz[Hv],
                        this[ob](),
                        delete this._8q,
                        this._kjEvent && (delete this[sb][ig], delete this[sb][Uc], delete this[sb])
                    }
                };
            var dz = M(function (t) {
                    this._k7 = t,
                    mi.apply(this, [t[ng], null, t])
                }, {
                    "super": mi,
                    _mfData: function (t) {
                        return this._k7.getElementByMouseEvent(t)
                    },
                    _l4: function (t) {
                        return this._k7.getUIByMouseEvent(t)
                    },
                    _toQEvent: function (i) {
                        return (i instanceof MouseEvent || t[qv] && i instanceof t[qv]) && (i[ig] = this[eg][lr](this, i), i.getUI = this._l4[lr](this, i)),
                        i
                    },
                    _onElementRemoved: function (t) {
                        this[sg](function (i) {
                            i.onElementRemoved instanceof Function && i[hg](t, this._k7)
                        })
                    },
                    _onElementClear: function () {
                        this[sg](function (t) {
                            t[rg] instanceof Function && t[rg](this._k7)
                        })
                    },
                    _hg: function (t) {
                        this[ag] && this[og](this[ag], t),
                        this[fg] && this[og](this[fg], t),
                        this[ug]()
                    },
                    _k7: null,
                    _23s: null,
                    _muustomInteractionListeners: null,
                    _maInteraction: function (t) {
                        return this._23s == t ? !1 : (this._23s && this._23s[Hh] && this[og](this._23s), void(this._23s = t))
                    },
                    _mbCustomInteractionListener: function (t) {
                        this[fg] || (this[fg] = []),
                        this._muustomInteractionListeners.push(t)
                    },
                    _jyCustomInteractionListener: function (t) {
                        this[fg] && 0 != this._muustomInteractionListeners[Hh] && m(this[fg], t)
                    },
                    _onEvent: function (t, i, n) {
                        this._k7[i] instanceof Function && this._k7[i][Yh](this._k7, t, n),
                        this[ag] && this[cg](t, i, this[ag], n),
                        this[fg] && this.__onEvent(t, i, this[fg], n)
                    },
                    _igListeners: function (t) {
                        this._23s && l(this[ag], t, this),
                        this[fg] && l(this[fg], t, this)
                    },
                    __onEvent: function (t, i, n, e) {
                        if (!$(n)) return void this.__handleEvent(t, i, n, e);
                        for (var s = 0; s < n[Hh]; s++) {
                            var h = n[s];
                            this[_g](t, i, h, e)
                        }
                    },
                    __handleEvent: function (t, i, n, e) {
                        if (!(n[Mu] instanceof Function && n[Mu](i, t, this._k7, e) === !1)) {
                            n[Db] instanceof Function && n.onevent(i, t, this._k7, e);
                            var s = n[i];
                            s instanceof Function && s[Yh](n, t, this._k7, e)
                        }
                    },
                    _hgInteraction: function (t) {
                        t[dg] instanceof Function && t[dg][Yh](t, this._k7)
                    },
                    _hgInteractions: function (t, i) {
                        if (!$(t)) return void this._hgInteraction(t, i);
                        for (var n = 0; n < t[Hh]; n++) {
                            var e = t[n];
                            e && this[lg](e, i)
                        }
                    }
                })
        }
    xi[er] = {
            limitCount: 10,
            points: null,
            add: function (t, i, n) {
                0 == this.points[Hh] && (this[vg] = t.x, this[bg] = t.y);
                var e = i.timeStamp - t.timeStamp || 1;
                n[gg] = e;
                var s = i.x - t.x,
                    h = i.y - t.y;
                n.dx = s,
                n.dy = h,
                n[yg] = this[vg],
                n[mg] = this[bg],
                n[Eg] = i.x - this[vg],
                n[xg] = i.y - this[bg],
                this[xa].splice(0, 0, {
                        interval: e,
                        dx: s,
                        dy: h
                    }),
                this[xa][Hh] > this.limitCount && this[xa].pop()
            },
            getCurrentSpeed: function () {
                if (!this[xa][Hh]) return null;
                for (var t = 0, i = 0, n = 0, e = 0, s = this[xa][Hh]; s > e; e++) {
                    var h = this[xa][e],
                        r = h[gg];
                    if (r > 150) {
                            t = 0;
                            break
                        }
                    if (t += r, i += h.dx, n += h.dy, t > 300) break
                }
                return 0 == t || 0 == i && 0 == n ? null : {
                    x: i / t,
                    y: n / t
                }
            },
            clear: function () {
                this[xa] = []
            }
        };
    var lz, vz, bz, gz;
    iG ? (lz = pg, vz = wg, bz = Tg, gz = kg) : nG ? (lz = Og, vz = jg, bz = Mg, gz = Sg) : (lz = Ig, vz = Ig, bz = Y_, gz = Ag);
    var yz = Lg,
        mz = Math.PI,
        Ez = Math.pow,
        xz = Math.sin,
        pz = 1.70158,
        wz = {
            swing: function (t) {
                return -Math.cos(t * mz) / 2 + .5
            },
            easeNone: function (t) {
                return t
            },
            easeIn: function (t) {
                return t * t
            },
            easeOut: function (t) {
                return (2 - t) * t
            },
            easeBoth: function (t) {
                return (t *= 2) < 1 ? .5 * t * t : .5 * (1 - --t * (t - 2))
            },
            easeInStrong: function (t) {
                return t * t * t * t
            },
            easeOutStrong: function (t) {
                return 1 - --t * t * t * t
            },
            easeBothStrong: function (t) {
                return (t *= 2) < 1 ? .5 * t * t * t * t : .5 * (2 - (t -= 2) * t * t * t)
            },
            elasticIn: function (t) {
                var i = .3,
                    n = i / 4;
                return 0 === t || 1 === t ? t : -(Ez(2, 10 * (t -= 1)) * xz(2 * (t - n) * mz / i))
            },
            elasticOut: function (t) {
                var i = .3,
                    n = i / 4;
                return 0 === t || 1 === t ? t : Ez(2, -10 * t) * xz(2 * (t - n) * mz / i) + 1
            },
            elasticBoth: function (t) {
                var i = .45,
                    n = i / 4;
                return 0 === t || 2 === (t *= 2) ? t : 1 > t ? -.5 * Ez(2, 10 * (t -= 1)) * xz(2 * (t - n) * mz / i) : Ez(2, -10 * (t -= 1)) * xz(2 * (t - n) * mz / i) * .5 + 1
            },
            backIn: function (t) {
                return 1 === t && (t -= .001),
                t * t * ((pz + 1) * t - pz)
            },
            backOut: function (t) {
                return (t -= 1) * t * ((pz + 1) * t + pz) + 1
            },
            backBoth: function (t) {
                return (t *= 2) < 1 ? .5 * t * t * (((pz *= 1.525) + 1) * t - pz) : .5 * ((t -= 2) * t * (((pz *= 1.525) + 1) * t + pz) + 2)
            },
            bounceIn: function (t) {
                return 1 - wz.bounceOut(1 - t)
            },
            bounceOut: function (t) {
                var i, n = 7.5625;
                return i = 1 / 2.75 > t ? n * t * t : 2 / 2.75 > t ? n * (t -= 1.5 / 2.75) * t + .75 : 2.5 / 2.75 > t ? n * (t -= 2.25 / 2.75) * t + .9375 : n * (t -= 2.625 / 2.75) * t + .984375
            },
            bounceBoth: function (t) {
                return.5 > t ? .5 * wz[Cg](2 * t) : .5 * wz.bounceOut(2 * t - 1) + .5
            }
        },
        Tz = function (t) {
            this._jf = t
        };
    Tz[er] = {
            _jf: null,
            _8a: function () {
                this[Dg] instanceof Function && (this[Dg](), this._muallback = null)
            },
            _kj: function (t) {
                var i = Date.now();
                this._lo(),
                this[Dg] = t,
                this._requestID = requestAnimationFrame(function n() {
                    var t = Date.now(),
                        e = t - i;
                    return !e || this._jf && this._jf(e) !== !1 ? (i = t, void(this[Rg] = requestAnimationFrame(n.bind(this)))) : void this._lo()
                }[lr](this))
            },
            _6g: function () {},
            _lo: function () {
                return this[Rg] ? (this._6g(), this._8a(), t[jd](this[Rg]), void delete this._requestID) : !1
            },
            _dn: function () {
                return null != this._requestID
            }
        };
    var kz = function (t, i, n, e) {
            this[Pg] = t,
            this[ga] = i || this,
            this._3b = e,
            n && n > 0 && (this._i5 = n)
        };
    kz[er] = {
            _i5: 1e3,
            _3b: null,
            _d8: 0,
            _lo: function () {
                return this._d8 = 0,
                this._d1 = 0,
                T(this, kz, Ng)
            },
            _d1: 0,
            _jf: function (t) {
                if (this._d8 += t, this._d8 >= this._i5) return this._onStep[Yh](this[ga], 1, (1 - this._d1) * this._i5, t, this._i5),
                !1;
                var i = this._d8 / this._i5;
                return this._3b && (i = this._3b(i)),
                this[Pg][Yh](this[ga], i, (i - this._d1) * this._i5, t, this._i5) === !1 ? !1 : void(this._d1 = i)
            }
        },
    p(kz, Tz);
    var Oz = function (t) {
            ni(t)
        },
        jz = {
            version: Bg,
            extend: p,
            doSuperConstructor: w,
            doSuper: T,
            createFunction: function (t, i) {
                return i.bind(t)
            },
            setClass: C,
            appendClass: D,
            removeClass: R,
            forEach: l,
            forEachReverse: b,
            isNumber: P,
            isString: N,
            isBoolean: B,
            isArray: $,
            eventPreventDefault: F,
            eventStopPropagation: q,
            stopEvent: G,
            callLater: A,
            nextFrame: L,
            forEachChild: e,
            forEachByDepthFirst: h,
            forEachByDepthFirstReverse: o,
            forEachByBreadthFirst: c,
            randomInt: z,
            randomBool: H,
            randomColor: U,
            addEventListener: KG,
            getFirstElementChildByTagName: vG
        };
    jz[$g] = uG,
    jz.isIOS = aG,
    jz[Qd] = ri,
    jz[Fg] = ai,
    jz[od] = EG,
    jz[qg] = mG,
    jz[Gg] = bG,
    jz[zg] = xG,
    jz.Event = AG,
    jz[Hg] = LG,
    jz[Yg] = qG,
    jz.Handler = NG,
    jz[Ug] = BG,
    jz[Wg] = pG,
    jz[Vg] = GG,
    jz[Xg] = YG,
    jz[Kg] = HG,
    jz[Zg] = FG,
    jz[Jg] = Ti,
    jz[Qg] = pi,
    jz[ty] = wi,
    jz[iy] = Ei,
    jz[ny] = gG,
    jz.HashList = dG,
    jz[ey] = mi,
    jz[sy] = function (t) {
            alert(t)
        },
    jz.prompt = function (t, i, n, e) {
            var s = prompt(t, i);
            return s != i && n ? n[Yh](e, s) : s
        },
    jz.confirm = function (t, i, n) {
            var e = confirm(t);
            return e && i ? i[Yh](n) : e
        },
    jz.addCSSRule = gi;
    var Mz = {
            IMAGE_ADJUST_FLIP: hy,
            IMAGE_ADJUST_MIRROR: ry,
            SELECTION_TYPE_BORDER_RECT: ay,
            SELECTION_TYPE_BORDER: oy,
            SELECTION_TYPE_SHADOW: fy,
            NS_SVG: "http://www.w3.org/2000/svg",
            PROPERTY_TYPE_ACCESSOR: 0,
            PROPERTY_TYPE_STYLE: 1,
            PROPERTY_TYPE_CLIENT: 2,
            EDGE_TYPE_DEFAULT: null,
            EDGE_TYPE_ELBOW: uy,
            EDGE_TYPE_ELBOW_HORIZONTAL: cy,
            EDGE_TYPE_ELBOW_VERTICAL: _y,
            EDGE_TYPE_ORTHOGONAL: dy,
            EDGE_TYPE_ORTHOGONAL_HORIZONTAL: ly,
            EDGE_TYPE_ORTHOGONAL_VERTICAL: vy,
            EDGE_TYPE_HORIZONTAL_VERTICAL: by,
            EDGE_TYPE_VERTICAL_HORIZONTAL: gy,
            EDGE_TYPE_EXTEND_TOP: yy,
            EDGE_TYPE_EXTEND_LEFT: my,
            EDGE_TYPE_EXTEND_BOTTOM: Ey,
            EDGE_TYPE_EXTEND_RIGHT: xy,
            EDGE_TYPE_ZIGZAG: py,
            EDGE_CORNER_NONE: m_,
            EDGE_CORNER_ROUND: uo,
            EDGE_CORNER_BEVEL: wy,
            GROUP_TYPE_RECT: Ty,
            GROUP_TYPE_CIRCLE: ky,
            GROUP_TYPE_ELLIPSE: Oy,
            SHAPE_CIRCLE: jy,
            SHAPE_RECT: Ty,
            SHAPE_ROUNDRECT: My,
            SHAPE_STAR: Sy,
            SHAPE_TRIANGLE: Iy,
            SHAPE_HEXAGON: Ay,
            SHAPE_PENTAGON: Ly,
            SHAPE_TRAPEZIUM: Cy,
            SHAPE_RHOMBUS: Dy,
            SHAPE_PARALLELOGRAM: Ry,
            SHAPE_HEART: Py,
            SHAPE_DIAMOND: Ny,
            SHAPE_CROSS: By,
            SHAPE_ARROW_STANDARD: $y,
            SHAPE_ARROW_1: Fy,
            SHAPE_ARROW_2: qy,
            SHAPE_ARROW_3: Gy,
            SHAPE_ARROW_4: zy,
            SHAPE_ARROW_5: Hy,
            SHAPE_ARROW_6: Yy,
            SHAPE_ARROW_7: Uy,
            SHAPE_ARROW_8: Wy,
            SHAPE_ARROW_OPEN: Vy
        };
    Mz[Xy] = Ky,
    Mz[Zy] = uo,
    Mz[Jy] = Qy,
    Mz[tm] = wy,
    Mz[im] = uo,
    Mz[nm] = em,
    _G[sm] = Mz.SELECTION_TYPE_SHADOW,
    _G[hm] = iz ? 8 : 3,
    _G[rm] = 2,
    _G[am] = 7,
    _G.SELECTION_COLOR = X(3422561023),
    _G.SELECTION_TYPE = Mz[om],
    _G[fm] = 10,
    _G[um] = 10,
    _G[$c] = 10,
    _G[cm] = 200,
    _G.LINE_HEIGHT = 1.2;
    var Sz = t.devicePixelRatio || 1;
    1 > Sz && (Sz = 1);
    var Iz;
    jz[_m] = Li;
    var Az = sG && !uG,
        Lz = 9,
        Cz = function (t, i, n, e) {
            var s = t - n,
                h = i - e;
            return s * s + h * h
        };
    Qi[er] = {
            equals: function (t) {
                return this.cx == t.cx && this.cy == t.cy && this.r == t.r
            }
        },
    Qi._j4Circle = function (t, i, n) {
            if (!n) return Zi(t, i);
            var e = Cz(t.x, t.y, i.x, i.y),
                s = Cz(t.x, t.y, n.x, n.y),
                h = Cz(n.x, n.y, i.x, i.y);
            if (e + Dz >= s + h) return Zi(t, i, 0, n);
            if (s + Dz >= e + h) return Zi(t, n, 0, i);
            if (h + Dz >= e + s) return Zi(i, n, 0, t);
            var r;
            Math.abs(n.y - i.y) < 1e-4 && (r = t, t = i, i = r),
            r = n.x * (t.y - i.y) + t.x * (i.y - n.y) + i.x * (-t.y + n.y);
            var a = (n.x * n.x * (t.y - i.y) + (t.x * t.x + (t.y - i.y) * (t.y - n.y)) * (i.y - n.y) + i.x * i.x * (-t.y + n.y)) / (2 * r),
                o = (i.y + n.y) / 2 - (n.x - i.x) / (n.y - i.y) * (a - (i.x + n.x) / 2);
            return new Qi(a, o, gG(a, o, t.x, t.y), t, i, n)
        };
    var Dz = .01,
        Rz = {
            _mq4: function (t, i, e, s, h) {
                if (N(t) && (t = pG[Wr](t)), !t) return {
                    x: 0,
                    y: 0
                };
                var r = 0,
                    a = 0,
                    o = i._il;
                if (e = e || 0, t.x === n) {
                        var f = t[Vr],
                            u = t[Xr],
                            c = !0;
                        switch (f) {
                            case kG:
                                c = !1;
                                break;
                            case TG:
                                r += o / 2
                            }
                        switch (u) {
                            case OG:
                                a -= e / 2;
                                break;
                            case MG:
                                a += e / 2
                            }
                    } else r = t.x,
                a = t.y,
                Math.abs(r) > 0 && Math.abs(r) < 1 && (r *= o);
                h && null != s && (a += s.y, r += Math.abs(s.x) < 1 ? s.x * o : s.x);
                var _ = on[Yh](i, r, a, c);
                return _ ? (h || null == s || _[dm](s), _) : {
                        x: 0,
                        y: 0
                    }
            },
            _l5: function (t, i) {
                var n = i[yo],
                    e = i[xa];
                switch (n) {
                    case aH:
                        t[lm](e[0], e[1], e[2], e[3], i._r);
                        break;
                    case eH:
                        t.moveTo(e[0], e[1]);
                        break;
                    case sH:
                        t.lineTo(e[0], e[1]);
                        break;
                    case hH:
                        t[vm](e[0], e[1], e[2], e[3]);
                        break;
                    case rH:
                        t[bm](e[0], e[1], e[2], e[3], e[4], e[5]);
                        break;
                    case oH:
                        t.closePath()
                    }
            },
            _5n: function (t, i, n, e) {
                var s = i[yo];
                if (s != eH && s != oH) {
                    var h = n.lastPoint,
                        r = i[xa];
                    switch (n[yo] == eH && t.add(h.x, h.y), s) {
                        case aH:
                            cn(i, h.x, h.y, r[0], r[1], r[2], r[3], r[4]),
                            t.add(r[0], r[1]),
                            t.add(i[po], i._p1y),
                            t.add(i._p2x, i[Eo]),
                            i[gm] && t.add(i.$boundaryPoint1.x, i[gm].y),
                            i.$boundaryPoint2 && t.add(i.$boundaryPoint2.x, i[ym].y);
                            break;
                        case sH:
                            t.add(r[0], r[1]);
                            break;
                        case hH:
                            Gi([h.x, h.y].concat(r), t);
                            break;
                        case rH:
                            Wi([h.x, h.y][Kh](r), t);
                            break;
                        case oH:
                            e && t.add(e[xa][0], e[xa][1])
                        }
                }
            },
            _5o: function (t, i, n) {
                var e = t[yo];
                if (e == eH) return 0;
                var s = i[go],
                    h = t.points;
                switch (e == rH && 4 == h[Hh] && (e = hH), e) {
                    case sH:
                        return gG(h[0], h[1], s.x, s.y);
                    case aH:
                        return t._il;
                    case hH:
                        var r = Hi([s.x, s.y][Kh](h));
                        return t._lf = r,
                        r(1);
                    case rH:
                        var r = Xi([s.x, s.y].concat(h));
                        return t._lf = r,
                        r(1) || Vi([s.x, s.y][Kh](h));
                    case oH:
                        if (s && n) return t[xa] = n[xa],
                        gG(n[xa][0], n[xa][1], s.x, s.y)
                    }
                return 0
            }
        },
        Pz = /^data:image\/(\w+);base64,/i,
        Nz = /^gif/i,
        Bz = /^svg/i,
        $z = 10,
        Fz = 11,
        qz = 12,
        Gz = 20,
        zz = 30;
    _G[Co] = 50,
    _G[Do] = 30,
    _G[mm] = 1e6;
    var Hz = 1,
        Yz = 2,
        Uz = 3;
    bn.prototype = {
            _ix: 0,
            _5s: !0,
            _km: null,
            _ik: null,
            _lr: null,
            _ls: null,
            _9w: n,
            _9j: n,
            _5y: function () {
                return this._ix == Hz
            },
            getBounds: function (t) {
                return this._ls == zz ? this._lr[oo](t) : (this._5s && this._f6(), this)
            },
            validate: function () {
                this._5s && this._f6()
            },
            _f6: function () {
                if (this._5s = !1, this._ls == zz) return this._lr[ao](),
                void this[Em](this._lr[fo]);
                if (this._ls == Gz) return void this._9o();
                if (this._ix != Hz) try {
                    this._ef()
                } catch (t) {
                    this._ix = Uz,
                    jz[Gr](t)
                }
            },
            _4r: function () {
                this._dc(),
                this[xm][Ra](),
                delete this._dispatcher
            },
            _hd: function (t) {
                this._km && this._km[pm] && this._km[pm][Ev](this._km),
                this._ix = Uz,
                jz[Gr](wm + this._lr),
                this[Tm] = null,
                this._ik = null,
                this._km = null,
                t !== !1 && this._4r()
            },
            _ef: function () {
                var t = this._lr;
                if (this._ix = Hz, this[xm] = new BG, this._ls == qz) {
                    for (var n in yH) this[n] = yH[n];
                    return void Vn(this._lr, this, this[km], this._hd, this._f3)
                }
                this._km || (this._km = i.createElement(f_), Jq && (this._km[na].visibility = y_, i.body[bu](this._km))),
                this._km.src = t,
                this._km[Ca] && (this[Ca] = this._km[Ca], this[Da] = this._km.height),
                this._km.onload = Jq ?
                function (t) {
                    setTimeout(this._8i[lr](this, t), 100)
                }.bind(this) : this._8i[lr](this),
                this._km.onerror = this._hd[lr](this)
            },
            _8i: function () {
                this._ix = Yz;
                var t = this._km[Ca],
                    i = this._km[Da];
                if (this._km[pm] && this._km[pm].removeChild(this._km), !t || !i) return void this._hd();
                this.width = t,
                this[Da] = i;
                var n = this._en();
                n[Ca] = t,
                n.height = i,
                n.g.drawImage(this._km, 0, 0, t, i),
                this[Tm] = Jq && this._ls == Fz ? null : wn(n),
                this._4r()
            },
            _9o: function () {
                var t = this._lr;
                if (!(t[Ao] instanceof Function)) return void this._hd(!1);
                if (t.cacheable === !1 && t[Ca] && t[Da]) return this[Ca] = t[Ca],
                void(this[Da] = t[Da]);
                var i = t.width || _G.IMAGE_MAX_SIZE,
                    n = t[Da] || _G[cm],
                    e = this._en();
                e[Ca] = i,
                e.height = n;
                var s = e.g;
                t[Ao](s);
                var h = Ni(s, 0, 0, i, n);
                if (h) {
                        var r = kn(h[vo], i, n);
                        this.x = r._x,
                        this.y = r._y,
                        this.width = r._width,
                        this.height = r[Om],
                        e[Ca] = this[Ca],
                        e[Da] = this.height,
                        s[Qo](h, -this.x, -this.y),
                        this[Tm] = r
                    }
            },
            _en: function () {
                return this._ik || (this._ik = Li())
            },
            _67: function (t, i, n, e, s, h) {
                i[Va](),
                i[Ty](0, 0, e, s),
                i[jm] = h || Mm,
                i[Sm](),
                i[Im](),
                i[Ka] = Jc,
                i.textBaseline = Za,
                i.fillStyle = L_;
                var r = 6 * (i[Ga][Na] || 1);
                i.font = Am + r + "px Verdana,helvetica,arial,sans-serif",
                i[S_] = I_,
                i[bo] = 1,
                i[Lm](t, e / 2 + .5, s / 2 + .5),
                i[S_] = Cm,
                i[Lm](t, e / 2 - .5, s / 2 - .5),
                i[Qa](t, e / 2, s / 2),
                i[to]()
            },
            draw: function (t, i, n, e, s, h) {
                if (this[Ca] && this.height) {
                    i = i || 1,
                    e = e || 1,
                    s = s || 1;
                    var r = this[Ca] * e,
                        a = this[Da] * s;
                    if (h && n[A_] && (t.shadowColor = n.shadowColor, t.shadowBlur = (n[C_] || 0) * i, t.shadowOffsetX = (n[Dm] || 0) * i, t[Rm] = (n.shadowOffsetY || 0) * i), this._ix == Hz) return this._67(Pm, t, i, r, a, n[Nm]);
                    if (this._ix == Uz) return this._67(Bm, t, i, r, a, n[Nm]);
                    if (this._ls == zz) return t[nf](e, s),
                    void this._lr.draw(t, i, n);
                    var o = this._g9(i, e, s);
                    return o ? ((this.x || this.y) && t[$o](this.x * e, this.y * s), t[nf](e / o.scale, s / o.scale), void o._l5(t, n[Nm], n[$m])) : void this._ij(t, i, e, s, this.width * e, this[Da] * s, n)
                }
            },
            _ij: function (t, i, n, e, s, h, r) {
                if (this._ls == Gz) return 1 != n && 1 != e && t.scale(n, e),
                void this._lr[Ao](t, r);
                if (this._km) {
                    if (!eG) return void t.drawImage(this._km, 0, 0, s, h);
                    var n = i * s / this.width,
                        e = i * h / this[Da];
                    t[nf](1 / n, 1 / e),
                    t[D_](this._km, 0, 0, s * n, h * e)
                }
            },
            _iz: null,
            _g9: function (t, i, n) {
                if (this._ls == Gz && this._lr[Fm] === !1) return null;
                if (this._ls == $z || (t *= Math.max(i, n)) <= 1) return this._defaultCache || (this._defaultCache = this._gb(this._ik || this._km, 1)),
                this._defaultCache;
                var e = this._iz.maxScale || 0;
                if (t = Math.ceil(t), e >= t) {
                    for (var s = t, h = this._iz[s]; !h && ++s <= e;) h = this._iz[s];
                    if (h) return h
                }
                t % 2 && t++;
                var r = this[Ca] * t,
                    a = this.height * t;
                if (r * a > _G[mm]) return null;
                var o = Li(r, a);
                return (this.x || this.y) && o.g[$o](-this.x * t, -this.y * t),
                this._ij(o.g, 1, t, t, r, a),
                this._gb(o, t)
            },
            _gb: function (t, i) {
                var n = new _H(t, i);
                return this._iz[i] = n,
                this._iz[qm] = i,
                n
            },
            hitTest: function (t, i, n) {
                if (this._ls == zz) return this._lr[s_][nr](this._lr, arguments);
                if (!(this[Tm] || this._km && this._km[Tm])) return !0;
                var e = this[Tm] || this._km[Tm];
                return e._hv(t, i, n)
            },
            _dc: function () {
                this[xm] && this._dispatcher[or](new AG(this, Gm, zm, this._km))
            },
            _9x: function (t, i) {
                this[xm] && this._dispatcher.addListener(t, i)
            },
            _63: function (t, i) {
                this[xm] && this[xm][zl](t, i)
            },
            _mu2: function (t) {
                this._iz = {},
                (t || this.width * this[Da] > 1e5) && (this._km = null, this._ik = null)
            }
        },
    p(bn, EG);
    var Wz = {};
    jz[D_] = xn,
    jz.registerImage = gn,
    jz.hasImage = mn,
    jz[Hm] = function () {
            var t = [];
            for (var i in Wz) t.push(i);
            return t
        };
    var Vz = function (t, i, n, e, s, h) {
            this[yo] = t,
            this[Ym] = i,
            this.positions = n,
            this.angle = e || 0,
            this.tx = s || 0,
            this.ty = h || 0
        };
    Mz[Um] = al,
    Mz[Wm] = hl,
    Vz[er] = {
            type: null,
            colors: null,
            positions: null,
            angle: null,
            tx: 0,
            ty: 0,
            position: pG[ll],
            isEmpty: function () {
                return null == this[Ym] || 0 == this.colors.length
            },
            _75: function () {
                var t = this[Ym].length;
                if (1 == t) return [0];
                for (var i = [], n = 1 / (t - 1), e = 0; t > e; e++) i[tr](n * e);
                return this.positions || (this[Vm] = i),
                i
            },
            generatorGradient: function (t) {
                if (null == this.colors || 0 == this[Ym][Hh]) return null;
                var i, n = Ci();
                if (this.type == Mz.GRADIENT_TYPE_LINEAR) {
                    var e = this[Zc];
                    e > Math.PI && (e -= Math.PI);
                    var s;
                    if (e <= Math.PI / 2) {
                        var h = Math.atan2(t.height, t.width),
                            r = Math[eo](t[Ca] * t[Ca] + t[Da] * t.height),
                            a = h - e;
                        s = Math.cos(a) * r
                    } else {
                        var h = Math[zr](t[Ca], t[Da]),
                            r = Math.sqrt(t.width * t.width + t[Da] * t.height),
                            a = h - (e - Math.PI / 2);
                        s = Math.cos(a) * r
                    }
                    var o = s / 2,
                        f = o * Math.cos(e),
                        u = o * Math.sin(e),
                        c = t.x + t[Ca] / 2 - f,
                        _ = t.y + t.height / 2 - u,
                        d = t.x + t[Ca] / 2 + f,
                        l = t.y + t[Da] / 2 + u;
                    i = n[Xm](c, _, d, l)
                } else {
                    if (!(this[yo] = Mz[Um])) return null;
                    var v = fi(this.position, t[Ca], t[Da]);
                    v.x += t.x,
                    v.y += t.y,
                    this.tx && (v.x += Math.abs(this.tx) < 1 ? t.width * this.tx : this.tx),
                    this.ty && (v.y += Math.abs(this.ty) < 1 ? t[Da] * this.ty : this.ty);
                    var b = gG(v.x, v.y, t.x, t.y);
                    b = Math.max(b, gG(v.x, v.y, t.x, t.y + t.height)),
                    b = Math.max(b, gG(v.x, v.y, t.x + t.width, t.y + t[Da])),
                    b = Math.max(b, gG(v.x, v.y, t.x + t[Ca], t.y)),
                    i = n[Km](v.x, v.y, 0, v.x, v.y, b)
                }
                var g = this[Ym],
                    y = this[Vm];
                y && y[Hh] == g.length || (y = this._75());
                for (var m = 0, E = g[Hh]; E > m; m++) i.addColorStop(y[m], g[m]);
                return i
            }
        };
    var Xz = new Vz(Mz[Wm], [X(2332033023), X(1154272460), X(1154272460), X(1442840575)], [.1, .3, .7, .9], Math.PI / 2),
        Kz = new Vz(Mz.GRADIENT_TYPE_LINEAR, [X(2332033023), X(1154272460), X(1154272460), X(1442840575)], [.1, .3, .7, .9], 0),
        Zz = (new Vz(Mz.GRADIENT_TYPE_LINEAR, [X(1154272460), X(1442840575)], [.1, .9], 0), new Vz(Mz[Um], [X(2298478591), X(1156509422), X(1720223880), X(1147561574)], [.1, .3, .7, .9], 0, -.3, -.3)),
        Jz = [X(0), X(4294901760), X(4294967040), X(4278255360), X(4278250239), X(4278190992), X(4294901958), X(0)],
        Qz = [0, .12, .28, .45, .6, .75, .8, 1],
        tH = new Vz(Mz[Wm], Jz, Qz),
        iH = new Vz(Mz[Wm], Jz, Qz, Math.PI / 2),
        nH = new Vz(Mz.GRADIENT_TYPE_RADIAL, Jz, Qz);
    Vz[Zm] = Xz,
    Vz[Jm] = Kz,
    Vz[Qm] = Zz,
    Vz[tE] = tH,
    Vz[iE] = iH,
    Vz[nE] = nH;
    var eH = ol,
        sH = hl,
        hH = eE,
        rH = rl,
        aH = sE,
        oH = hE;
    Mz.SEGMENT_MOVE_TO = eH,
    Mz[rE] = sH,
    Mz.SEGMENT_QUAD_TO = hH,
    Mz.SEGMENT_CURVE_TO = rH,
    Mz[aE] = aH,
    Mz.SEGMENT_CLOSE = oH;
    var fH = function (t, i) {
            this.id = ++Xq,
            $(t) ? this[xa] = t : (this[yo] = t, this.points = i)
        };
    fH.prototype = {
            toJSON: function () {
                var t = {
                    type: this.type,
                    points: this[xa]
                };
                return this[qc] && (t[qc] = !0),
                t
            },
            parseJSON: function (t) {
                this[yo] = t[yo],
                this[xa] = t[xa],
                this.invalidTerminal = t.invalidTerminal
            },
            points: null,
            type: sH,
            clone: function () {
                return new fH(this[yo], this[xa] ? g(this[xa]) : null)
            },
            move: function (t, i) {
                if (this.points) for (var n = 0, e = this.points[Hh]; e > n; n++) {
                    var s = this.points[n];
                    jz.isNumber(s) && (this[xa][n] += n % 2 == 0 ? t : i)
                }
            }
        },
    Z(fH[er], {
            lastPoint: {
                get: function () {
                    return this[yo] == aH ? {
                        x: this._p2x,
                        y: this._p2y
                    } : {
                        x: this.points[this.points.length - 2],
                        y: this.points[this[xa][Hh] - 1]
                    }
                }
            },
            firstPoint: {
                get: function () {
                    return {
                        x: this[xa][0],
                        y: this[xa][1]
                    }
                }
            }
        }),
    jz[oE] = fH;
    var uH = 0,
        cH = function (t) {
            this[fo] = new EG,
            this._f8 = t || []
        };
    cH[er] = {
            toJSON: function () {
                var t = [];
                return this._f8.forEach(function (i) {
                    t[tr](i[fE]())
                }),
                t
            },
            parseJSON: function (t) {
                var i = this._f8;
                t[Vf](function (t) {
                    i[tr](new fH(t[yo], t[xa]))
                })
            },
            clear: function () {
                this._f8[Hh] = 0,
                this[fo][Ra](),
                this._il = 0,
                this._5s = !0
            },
            _dl: !0,
            _5x: function (t, i) {
                this._dl && 0 === this._f8[Hh] && t != eH && this._f8.push(new fH(eH, [0, 0])),
                this._f8[tr](new fH(t, i)),
                this._5s = !0
            },
            add: function (t) {
                this._f8[tr](t),
                this._5s = !0
            },
            removePathSegment: function (t) {
                return t >= this._f8.length ? !1 : (this._f8[Xh](t, 1), void(this._5s = !0))
            },
            moveTo: function (t, i) {
                this._5x(eH, [t, i])
            },
            lineTo: function (t, i) {
                this._5x(sH, [t, i])
            },
            quadTo: function (t, i, n, e) {
                this._5x(hH, [t, i, n, e])
            },
            curveTo: function (t, i, n, e, s, h) {
                this._5x(rH, [t, i, n, e, s, h])
            },
            arcTo: function (t, i, n, e, s) {
                this._5x(aH, [t, i, n, e, s])
            },
            closePath: function () {
                this._5x(oH)
            },
            _7h: function (t, i, n, e, s) {
                if (e.selectionColor) {
                    if (n == Mz[om]) {
                        if (!e[uE]) return;
                        return t[A_] = e[cE],
                        t[C_] = e.selectionShadowBlur * i,
                        t[Dm] = (e[_E] || 0) * i,
                        void(t[Rm] = (e[dE] || 0) * i)
                    }
                    if (n == Mz[lE]) {
                        if (!e[vE]) return;
                        t.strokeStyle = e.selectionColor;
                        var h = s[bo] || 0;
                        s[bE] && (h += 2 * s[bE]),
                        t.lineWidth = e.selectionBorder + h,
                        this._l5(t),
                        t[lo]()
                    }
                }
            },
            _5s: !0,
            _f8: null,
            _il: 0,
            lineCap: Ky,
            lineJoin: uo,
            draw: function (t, i, n, e, s) {
                t[j_] = n[j_] || this[j_],
                t[M_] = n[M_] || this.lineJoin,
                e && (s || (s = n), this._7h(t, i, s.selectionType, s, n));
                var h = e && s.selectionType == Mz[om];
                n[gE] && (this._l5(t), t[bo] = n.lineWidth + 2 * (n.outline || 0), t.strokeStyle = n.outlineStyle, t[lo](), h && (h = !1, t.shadowColor = yE)),
                t[bo] = 0,
                this._l5(t),
                n[mE] && (t[jm] = n[Nm] || n[mE], t[Sm]()),
                n[EE] && (t[jm] = n[xE] || n[EE], t[Sm]()),
                n[bo] && (t[bo] = n[bo], n[Df] && (n[pE] && (t[S_] = n[pE], t[lo](), h && (t[A_] = yE)), t.lineCap = n.lineDashCap || t.lineCap, t[M_] = n[wE] || t[M_], t[Df] = n.lineDash, t[Ff] = n[Ff]), t.strokeStyle = n[Nm] || n.strokeStyle, t.stroke(), t[Df] = [])
            },
            _l5: function (t) {
                t[TE]();
                for (var i, n, e = 0, s = this._f8[Hh]; s > e; e++) i = this._f8[e],
                Rz._l5(t, i, n),
                n = i
            },
            invalidate: function () {
                this._5s = !0
            },
            validate: function () {
                if (this._5s = !1, this[fo][Ra](), this._il = 0, 0 != this._f8.length) for (var t, i, n = this._f8, e = 1, s = n[0], h = s, r = n.length; r > e; e++) t = n[e],
                t.type == eH ? h = t : (Rz._5n(this[fo], t, s, h), i = Rz._5o(t, s, h), t._il = i, this._il += i),
                s = t
            },
            getBounds: function (t, i) {
                if (this._5s && this[ao](), i = i || new EG, t) {
                    var n = t / 2;
                    i.set(this[fo].x - n, this[fo].y - n, this.bounds.width + t, this.bounds[Da] + t)
                } else i.set(this[fo].x, this[fo].y, this[fo][Ca], this[fo].height);
                return i
            },
            hitTest: function (t, i, n, e, s, h) {
                return an.call(this, t, i, n, e, s, h)
            },
            toSegments: function () {
                return [].concat(this._f8)
            },
            generator: function (t, i, n, e, s) {
                return rn.call(this, t, i, n, e, s)
            },
            getLocation: function (t, i) {
                return on[Yh](this, t, i || 0)
            }
        },
    Z(cH[er], {
            segments: {
                get: function () {
                    return this._f8
                },
                set: function (t) {
                    this.clear(),
                    this._f8 = t
                }
            },
            length: {
                get: function () {
                    return this._5s && this.validate(),
                    this._il
                }
            },
            _empty: {
                get: function () {
                    return 0 == this._f8[Hh]
                }
            }
        }),
    Tn.prototype = {
            _15: function (t, i) {
                var n, e, s, h, r, a = t[Hh],
                    o = 0,
                    f = 0;
                for (r = 0; a > r; r += 4) if (t[r + 3] > 0) {
                        n = (r + 4) / i / 4 | 0;
                        break
                    }
                for (r = a - 4; r >= 0; r -= 4) if (t[r + 3] > 0) {
                        e = (r + 4) / i / 4 | 0;
                        break
                    }
                for (o = 0; i > o; o++) {
                        for (f = n; e > f; f++) if (t[f * i * 4 + 4 * o + 3] > 0) {
                            s = o;
                            break
                        }
                        if (s >= 0) break
                    }
                for (o = i - 1; o >= 0; o--) {
                        for (f = n; e > f; f++) if (t[f * i * 4 + 4 * o + 3] > 0) {
                            h = o;
                            break
                        }
                        if (h >= 0) break
                    }
                this._x = s,
                this._y = n,
                this._width = h - s + 1,
                this[Om] = e - n + 1,
                this._iv = new EG(s, n, this[kE], this[Om]),
                this._pixelSize = this[kE] * this[Om],
                this[OE] = i,
                this[jE] = t
            },
            _er: function (t, i) {
                return this._originalPixels[4 * (t + this._x + (this._y + i) * this._originalPixelsWidth) + 3]
            },
            _hv: function (t, i, n) {
                (!n || 1 >= n) && (n = 1),
                n = 0 | n,
                t = Math.round(t - this._x) - n,
                i = Math.round(i - this._y) - n,
                n += n;
                for (var e = t, s = i; i + n > s;) {
                    for (var e = t; t + n > e;) {
                        if (this._er(e, s)) return !0;
                        ++e
                    }++s
                }
                return !1
            }
        },
    Mz[Wo] = ME,
    Mz[Uo] = SE,
    Mz.BLEND_MODE_COLOR_BURN = IE,
    Mz[AE] = LE,
    Mz[CE] = DE,
    Mz[Xo] = RE,
    Mz[Jo] = PE,
    _G[Yo] = Mz.BLEND_MODE_LINEAR_BURN;
    var _H = function (t, i, n) {
            this._ik = t,
            this.scale = i || 1,
            t instanceof Image && (n = !1),
            this._hf = n
        };
    _H[er] = {
            scale: 1,
            _ik: null,
            _iz: null,
            _hf: !0,
            _l5: function (t, i, n) {
                if (!i || this._hf === !1) return void t[D_](this._ik, 0, 0);
                this._iz || (this._iz = {});
                var e = i + n,
                    s = this._iz[e];
                if (s || (s = Mn(this._ik, i, n), s || (this._hf = !1), this._iz[e] = s || this._ik), s) if (Jq) try {
                        t.drawImage(s, 0, 0)
                    } catch (h) {} else t.drawImage(s, 0, 0)
            }
        };
    var dH = function (t, i, n, e, s, h, r, a, o) {
            this._m3 = Ln(t, i, n, e, s, h, r, a, o)
        },
        lH = {
            server: {
                draw: function (t) {
                    t[Va](),
                    t[$o](0, 0),
                    t[TE](),
                    t.moveTo(0, 0),
                    t[Nu](40, 0),
                    t[Nu](40, 40),
                    t.lineTo(0, 40),
                    t.closePath(),
                    t[Im](),
                    t.translate(0, 0),
                    t[$o](0, 0),
                    t[nf](1, 1),
                    t.translate(0, 0),
                    t.strokeStyle = yE,
                    t[j_] = Ky,
                    t[M_] = em,
                    t.miterLimit = 4,
                    t[Va](),
                    t.save(),
                    t[to](),
                    t[Va](),
                    t[to](),
                    t[Va](),
                    t.restore(),
                    t[Va](),
                    t[to](),
                    t.save(),
                    t.restore(),
                    t.save(),
                    t[to](),
                    t[Va](),
                    t[to](),
                    t[Va](),
                    t[to](),
                    t.save(),
                    t[to](),
                    t.save(),
                    t[to](),
                    t[Va](),
                    t[to](),
                    t[Va](),
                    t.restore(),
                    t[Va](),
                    t[to](),
                    t[to](),
                    t[Va]();
                    var i = t[Xm](6.75, 3.9033, 30.5914, 27.7447);
                    i[NE](.0493, BE),
                    i.addColorStop(.0689, $E),
                    i[NE](.0939, FE),
                    i.addColorStop(.129, qE),
                    i.addColorStop(.2266, GE),
                    i.addColorStop(.2556, zE),
                    i[NE](.2869, HE),
                    i[NE](.3194, YE),
                    i[NE](.3525, UE),
                    i[NE](.3695, WE),
                    i[NE](.5025, VE),
                    i[NE](.9212, XE),
                    i[NE](1, KE),
                    t[jm] = i,
                    t[TE](),
                    t[Pu](25.677, 4.113),
                    t[bm](25.361, 2.4410000000000007, 23.364, 2.7940000000000005, 22.14, 2.7990000000000004),
                    t.bezierCurveTo(19.261, 2.813, 16.381, 2.8260000000000005, 13.502, 2.8400000000000003),
                    t[bm](12.185, 2.846, 10.699000000000002, 2.652, 9.393, 2.8790000000000004),
                    t.bezierCurveTo(9.19, 2.897, 8.977, 2.989, 8.805, 3.094),
                    t.bezierCurveTo(8.084999999999999, 3.5109999999999997, 7.436999999999999, 4.1259999999999994, 6.776, 4.63),
                    t[bm](5.718999999999999, 5.436, 4.641, 6.22, 3.6029999999999998, 7.05),
                    t[bm](4.207, 6.5889999999999995, 21.601999999999997, 36.579, 21.028, 37.307),
                    t[bm](22.019, 36.063, 23.009999999999998, 34.819, 24.000999999999998, 33.575),
                    t[bm](24.587999999999997, 32.84, 25.589999999999996, 31.995000000000005, 25.593999999999998, 30.983000000000004),
                    t[bm](25.595999999999997, 30.489000000000004, 25.598, 29.994000000000003, 25.601, 29.500000000000004),
                    t[bm](25.612, 26.950000000000003, 25.622, 24.400000000000006, 25.633, 21.85),
                    t[bm](25.657, 16.318, 25.680999999999997, 10.786000000000001, 25.704, 5.253),
                    t.bezierCurveTo(25.706, 4.885, 25.749, 4.478, 25.677, 4.113),
                    t[bm](25.67, 4.077, 25.697, 4.217, 25.677, 4.113),
                    t.closePath(),
                    t[Sm](),
                    t[lo](),
                    t[to](),
                    t[Va](),
                    t.save(),
                    t[jm] = ZE,
                    t[TE](),
                    t.moveTo(19.763, 6.645),
                    t.bezierCurveTo(20.002000000000002, 6.643999999999999, 20.23, 6.691999999999999, 20.437, 6.778),
                    t[bm](20.644000000000002, 6.864999999999999, 20.830000000000002, 6.991, 20.985, 7.146999999999999),
                    t[bm](21.14, 7.302999999999999, 21.266, 7.488999999999999, 21.352999999999998, 7.696999999999999),
                    t[bm](21.438999999999997, 7.903999999999999, 21.487, 8.133, 21.487, 8.372),
                    t[Nu](21.398, 36.253),
                    t[bm](21.397, 36.489, 21.349, 36.713, 21.262, 36.917),
                    t[bm](21.174, 37.121, 21.048000000000002, 37.305, 20.893, 37.458),
                    t[bm](20.738, 37.611, 20.553, 37.734, 20.348, 37.818999999999996),
                    t.bezierCurveTo(20.141, 37.903999999999996, 19.916, 37.95099999999999, 19.679, 37.949),
                    t[Nu](4.675, 37.877),
                    t[bm](4.4399999999999995, 37.876000000000005, 4.216, 37.827000000000005, 4.012, 37.741),
                    t[bm](3.8089999999999997, 37.653999999999996, 3.6249999999999996, 37.528999999999996, 3.4719999999999995, 37.376),
                    t[bm](3.3179999999999996, 37.221, 3.1939999999999995, 37.037, 3.1079999999999997, 36.833999999999996),
                    t[bm](3.022, 36.629999999999995, 2.9739999999999998, 36.406, 2.9739999999999998, 36.172),
                    t.lineTo(2.924, 8.431),
                    t.bezierCurveTo(2.923, 8.192, 2.971, 7.964, 3.057, 7.758),
                    t[bm](3.143, 7.552, 3.267, 7.365, 3.4219999999999997, 7.209),
                    t[bm](3.5769999999999995, 7.052999999999999, 3.76, 6.925, 3.965, 6.837),
                    t.bezierCurveTo(4.17, 6.749, 4.396, 6.701, 4.633, 6.7),
                    t[Nu](19.763, 6.645),
                    t[sf](),
                    t.fill(),
                    t[lo](),
                    t.restore(),
                    t[to](),
                    t[Va](),
                    t.fillStyle = JE,
                    t[TE](),
                    t.arc(12.208, 26.543, 2.208, 0, 6.283185307179586, !0),
                    t[sf](),
                    t[Sm](),
                    t[lo](),
                    t[to](),
                    t[Va](),
                    t.fillStyle = ZE,
                    t[TE](),
                    t.arc(12.208, 26.543, 1.876, 0, 6.283185307179586, !0),
                    t.closePath(),
                    t[Sm](),
                    t[lo](),
                    t[to](),
                    t.save(),
                    t[jm] = JE,
                    t.beginPath(),
                    t[Pu](19.377, 17.247),
                    t.bezierCurveTo(19.377, 17.724, 18.991999999999997, 18.108999999999998, 18.516, 18.108999999999998),
                    t[Nu](5.882, 18.108999999999998),
                    t[bm](5.404999999999999, 18.108999999999998, 5.02, 17.723, 5.02, 17.247),
                    t[Nu](5.02, 11.144),
                    t.bezierCurveTo(5.02, 10.666, 5.406, 10.281, 5.882, 10.281),
                    t[Nu](18.516, 10.281),
                    t[bm](18.993, 10.281, 19.377, 10.666, 19.377, 11.144),
                    t.lineTo(19.377, 17.247),
                    t[sf](),
                    t[Sm](),
                    t[lo](),
                    t[to](),
                    t.save(),
                    t[Va](),
                    t.fillStyle = ZE,
                    t[TE](),
                    t[Pu](18.536, 13.176),
                    t[bm](18.536, 13.518, 18.261000000000003, 13.794, 17.919, 13.794),
                    t[Nu](6.479, 13.794),
                    t.bezierCurveTo(6.1370000000000005, 13.794, 5.861, 13.518, 5.861, 13.176),
                    t[Nu](5.861, 11.84),
                    t[bm](5.861, 11.498, 6.137, 11.221, 6.479, 11.221),
                    t[Nu](17.918, 11.221),
                    t[bm](18.259999999999998, 11.221, 18.535, 11.497, 18.535, 11.84),
                    t.lineTo(18.535, 13.176),
                    t[sf](),
                    t[Sm](),
                    t[lo](),
                    t.restore(),
                    t.save(),
                    t[jm] = ZE,
                    t[TE](),
                    t[Pu](18.536, 16.551),
                    t[bm](18.536, 16.892999999999997, 18.261000000000003, 17.168999999999997, 17.919, 17.168999999999997),
                    t[Nu](6.479, 17.168999999999997),
                    t[bm](6.1370000000000005, 17.168999999999997, 5.861, 16.892999999999997, 5.861, 16.551),
                    t[Nu](5.861, 15.215999999999998),
                    t[bm](5.861, 14.872999999999998, 6.137, 14.596999999999998, 6.479, 14.596999999999998),
                    t[Nu](17.918, 14.596999999999998),
                    t.bezierCurveTo(18.259999999999998, 14.596999999999998, 18.535, 14.872999999999998, 18.535, 15.215999999999998),
                    t[Nu](18.535, 16.551),
                    t[sf](),
                    t[Sm](),
                    t[lo](),
                    t.restore(),
                    t[to](),
                    t.restore()
                }
            },
            exchanger2: {
                draw: function (t) {
                    t.save(),
                    t[$o](0, 0),
                    t.beginPath(),
                    t.moveTo(0, 0),
                    t[Nu](40, 0),
                    t[Nu](40, 40),
                    t[Nu](0, 40),
                    t[sf](),
                    t[Im](),
                    t[$o](0, 0),
                    t.translate(0, 0),
                    t.scale(1, 1),
                    t.translate(0, 0),
                    t[S_] = yE,
                    t[j_] = Ky,
                    t[M_] = em,
                    t[QE] = 4,
                    t[Va](),
                    t[Va](),
                    t[to](),
                    t.save(),
                    t[to](),
                    t[Va](),
                    t[to](),
                    t[Va](),
                    t[to](),
                    t[Va](),
                    t[to](),
                    t.save(),
                    t.restore(),
                    t[Va](),
                    t[to](),
                    t.save(),
                    t.restore(),
                    t[Va](),
                    t[to](),
                    t.save(),
                    t.restore(),
                    t.restore(),
                    t[Va]();
                    var i = t[Xm](.4102, 24.3613, 39.5898, 24.3613);
                    i[NE](0, BE),
                    i[NE](.0788, GE),
                    i[NE](.2046, tx),
                    i[NE](.3649, ix),
                    i[NE](.5432, nx),
                    i.addColorStop(.6798, ex),
                    i[NE](.7462, sx),
                    i.addColorStop(.8508, hx),
                    i[NE](.98, zE),
                    i[NE](1, rx),
                    t[jm] = i,
                    t[TE](),
                    t[Pu](.41, 16.649),
                    t[bm](.633, 19.767, .871, 20.689, 1.094, 23.807000000000002),
                    t[bm](1.29, 26.548000000000002, 3.324, 28.415000000000003, 5.807, 29.711000000000002),
                    t[bm](10.582, 32.202000000000005, 16.477, 32.806000000000004, 21.875999999999998, 32.523),
                    t[bm](26.929, 32.258, 32.806, 31.197000000000003, 36.709999999999994, 27.992000000000004),
                    t.bezierCurveTo(38.30499999999999, 26.728000000000005, 38.83599999999999, 25.103000000000005, 38.998999999999995, 23.161000000000005),
                    t[bm](39.589, 16.135000000000005, 39.589, 16.135000000000005, 39.589, 16.135000000000005),
                    t.bezierCurveTo(39.589, 16.135000000000005, 3.26, 16.647, .41, 16.649),
                    t.closePath(),
                    t[Sm](),
                    t[lo](),
                    t[to](),
                    t.save(),
                    t[Va](),
                    t[jm] = ZE,
                    t[TE](),
                    t[Pu](16.4, 25.185),
                    t.bezierCurveTo(12.807999999999998, 24.924999999999997, 9.139, 24.238, 5.857999999999999, 22.705),
                    t[bm](3.175999999999999, 21.450999999999997, -.32200000000000095, 18.971999999999998, .544999999999999, 15.533999999999999),
                    t[bm](1.3499999999999992, 12.335999999999999, 4.987999999999999, 10.495999999999999, 7.807999999999999, 9.428999999999998),
                    t[bm](11.230999999999998, 8.133999999999999, 14.911999999999999, 7.519999999999999, 18.558, 7.345999999999998),
                    t[bm](22.233, 7.169999999999998, 25.966, 7.437999999999998, 29.548000000000002, 8.300999999999998),
                    t.bezierCurveTo(32.673, 9.052999999999999, 36.192, 10.296, 38.343, 12.814999999999998),
                    t[bm](40.86600000000001, 15.768999999999998, 39.208000000000006, 19.066999999999997, 36.406000000000006, 21.043999999999997),
                    t.bezierCurveTo(33.566, 23.046999999999997, 30.055000000000007, 24.071999999999996, 26.670000000000005, 24.676999999999996),
                    t[bm](23.289, 25.28, 19.824, 25.436, 16.4, 25.185),
                    t[bm](13.529, 24.977, 19.286, 25.396, 16.4, 25.185),
                    t[sf](),
                    t[Sm](),
                    t.stroke(),
                    t.restore(),
                    t.restore(),
                    t[Va](),
                    t[Va](),
                    t.save(),
                    t.save(),
                    t[Va](),
                    t[jm] = ax,
                    t.beginPath(),
                    t[Pu](5.21, 21.754),
                    t.lineTo(8.188, 17.922),
                    t[Nu](9.53, 18.75),
                    t[Nu](15.956, 16.004),
                    t[Nu](18.547, 17.523),
                    t[Nu](12.074, 20.334),
                    t.lineTo(13.464, 21.204),
                    t[Nu](5.21, 21.754),
                    t.closePath(),
                    t.fill(),
                    t[lo](),
                    t[to](),
                    t.restore(),
                    t[to](),
                    t[Va](),
                    t.save(),
                    t[Va](),
                    t[jm] = ax,
                    t.beginPath(),
                    t[Pu](17.88, 14.61),
                    t[Nu](9.85, 13.522),
                    t.lineTo(11.703, 12.757),
                    t.lineTo(7.436, 10.285),
                    t.lineTo(10.783, 8.942),
                    t.lineTo(15.091, 11.357),
                    t[Nu](16.88, 10.614),
                    t[Nu](17.88, 14.61),
                    t.closePath(),
                    t[Sm](),
                    t[lo](),
                    t[to](),
                    t[to](),
                    t[Va](),
                    t.save(),
                    t[jm] = ax,
                    t[TE](),
                    t.moveTo(17.88, 14.61),
                    t[Nu](9.85, 13.522),
                    t[Nu](11.703, 12.757),
                    t[Nu](7.436, 10.285),
                    t[Nu](10.783, 8.942),
                    t[Nu](15.091, 11.357),
                    t[Nu](16.88, 10.614),
                    t[Nu](17.88, 14.61),
                    t.closePath(),
                    t.fill(),
                    t[lo](),
                    t[to](),
                    t[to](),
                    t.restore(),
                    t[Va](),
                    t[Va](),
                    t.save(),
                    t[jm] = ax,
                    t[TE](),
                    t[Pu](23.556, 15.339),
                    t[Nu](20.93, 13.879),
                    t[Nu](26.953, 11.304),
                    t[Nu](25.559, 10.567),
                    t.lineTo(33.251, 9.909),
                    t.lineTo(31.087, 13.467),
                    t.lineTo(29.619, 12.703),
                    t[Nu](23.556, 15.339),
                    t.closePath(),
                    t[Sm](),
                    t.stroke(),
                    t[to](),
                    t[to](),
                    t.restore(),
                    t[Va](),
                    t[Va](),
                    t.save(),
                    t.fillStyle = ax,
                    t.beginPath(),
                    t[Pu](30.028, 23.383),
                    t[Nu](24.821, 20.366),
                    t.lineTo(22.915, 21.227),
                    t[Nu](21.669, 16.762),
                    t[Nu](30.189, 17.942),
                    t[Nu](28.33, 18.782),
                    t.lineTo(33.579, 21.725),
                    t[Nu](30.028, 23.383),
                    t[sf](),
                    t[Sm](),
                    t[lo](),
                    t[to](),
                    t[to](),
                    t.save(),
                    t[Va](),
                    t.fillStyle = ax,
                    t[TE](),
                    t[Pu](30.028, 23.383),
                    t.lineTo(24.821, 20.366),
                    t[Nu](22.915, 21.227),
                    t[Nu](21.669, 16.762),
                    t[Nu](30.189, 17.942),
                    t[Nu](28.33, 18.782),
                    t[Nu](33.579, 21.725),
                    t[Nu](30.028, 23.383),
                    t[sf](),
                    t[Sm](),
                    t[lo](),
                    t[to](),
                    t.restore(),
                    t[to](),
                    t.restore(),
                    t.restore(),
                    t[to]()
                }
            },
            exchanger: {
                draw: function (t) {
                    t[Va](),
                    t[$o](0, 0),
                    t.beginPath(),
                    t[Pu](0, 0),
                    t[Nu](40, 0),
                    t[Nu](40, 40),
                    t.lineTo(0, 40),
                    t[sf](),
                    t[Im](),
                    t[$o](0, 0),
                    t[$o](0, 0),
                    t.scale(1, 1),
                    t.translate(0, 0),
                    t[S_] = yE,
                    t[j_] = Ky,
                    t[M_] = em,
                    t[QE] = 4,
                    t[Va](),
                    t.save(),
                    t[to](),
                    t[Va](),
                    t.restore(),
                    t[Va](),
                    t[to](),
                    t[Va](),
                    t[to](),
                    t[Va](),
                    t[to](),
                    t[Va](),
                    t[to](),
                    t.save(),
                    t[to](),
                    t[to](),
                    t[Va]();
                    var i = t[Xm](.2095, 20.7588, 39.4941, 20.7588);
                    i.addColorStop(0, ox),
                    i[NE](.0788, fx),
                    i[NE](.352, ux),
                    i[NE](.6967, cx),
                    i[NE](.8916, _x),
                    i[NE](.9557, dx),
                    i.addColorStop(1, lx),
                    t[jm] = i,
                    t[TE](),
                    t[Pu](39.449, 12.417),
                    t[Nu](39.384, 9.424),
                    t[bm](39.384, 9.424, .7980000000000018, 22.264, .3710000000000022, 23.024),
                    t[bm](-.026999999999997804, 23.733, .4240000000000022, 24.903000000000002, .5190000000000022, 25.647000000000002),
                    t[bm](.7240000000000022, 27.244000000000003, .9240000000000023, 28.841, 1.1350000000000022, 30.437),
                    t[bm](1.3220000000000023, 31.843, 2.7530000000000023, 32.094, 3.9620000000000024, 32.094),
                    t.bezierCurveTo(8.799000000000003, 32.092, 13.636000000000003, 32.091, 18.473000000000003, 32.089),
                    t[bm](23.515, 32.086999999999996, 28.556000000000004, 32.086, 33.598, 32.083999999999996),
                    t[bm](34.859, 32.083999999999996, 36.286, 31.979999999999997, 37.266, 31.081999999999997),
                    t[bm](37.537, 30.820999999999998, 37.655, 30.535999999999998, 37.699999999999996, 30.229999999999997),
                    t[Nu](37.711, 30.316999999999997),
                    t.lineTo(39.281, 16.498999999999995),
                    t[bm](39.281, 16.498999999999995, 39.467999999999996, 15.126999999999995, 39.489, 14.666999999999994),
                    t[bm](39.515, 14.105, 39.449, 12.417, 39.449, 12.417),
                    t[sf](),
                    t[Sm](),
                    t[lo](),
                    t[to](),
                    t[Va](),
                    t.save(),
                    t[Va](),
                    t[Va](),
                    t[to](),
                    t[Va](),
                    t[to](),
                    t[Va](),
                    t[to](),
                    t[Va](),
                    t.restore(),
                    t[Va](),
                    t[to](),
                    t[Va](),
                    t[to](),
                    t[Va](),
                    t[to](),
                    t.save(),
                    t[to](),
                    t[Va](),
                    t[to](),
                    t[to](),
                    t.save();
                    var i = t[Xm](19.8052, 7.7949, 19.8052, 24.7632);
                    i.addColorStop(0, vx),
                    i[NE](.1455, bx),
                    i.addColorStop(.2975, gx),
                    i[NE](.4527, yx),
                    i[NE](.6099, mx),
                    i.addColorStop(.7687, Ex),
                    i[NE](.9268, xx),
                    i[NE](.9754, px),
                    i[NE](1, wx),
                    t[jm] = i,
                    t.beginPath(),
                    t.moveTo(33.591, 24.763),
                    t[bm](23.868000000000002, 24.754, 14.145, 24.746000000000002, 4.423000000000002, 24.738000000000003),
                    t.bezierCurveTo(3.140000000000002, 24.737000000000002, -.48799999999999777, 24.838000000000005, .3520000000000021, 22.837000000000003),
                    t.bezierCurveTo(1.292000000000002, 20.594000000000005, 2.2330000000000023, 18.351000000000003, 3.1730000000000023, 16.108000000000004),
                    t[bm](4.113000000000002, 13.865000000000006, 5.054000000000002, 11.623000000000005, 5.994000000000002, 9.380000000000004),
                    t[bm](6.728000000000002, 7.629000000000005, 9.521000000000003, 7.885000000000004, 11.156000000000002, 7.880000000000004),
                    t.bezierCurveTo(16.974000000000004, 7.861000000000004, 22.793000000000003, 7.843000000000004, 28.612000000000002, 7.825000000000005),
                    t[bm](30.976000000000003, 7.818000000000005, 33.341, 7.810000000000005, 35.707, 7.803000000000004),
                    t[bm](36.157000000000004, 7.802000000000004, 36.609, 7.787000000000004, 37.06, 7.804000000000005),
                    t[bm](37.793, 7.833000000000005, 39.389, 7.875000000000004, 39.385000000000005, 9.424000000000005),
                    t[bm](39.38400000000001, 9.647000000000006, 39.31, 10.138000000000005, 39.27700000000001, 10.359000000000005),
                    t.bezierCurveTo(38.81900000000001, 13.361000000000004, 38.452000000000005, 15.764000000000006, 37.99400000000001, 18.766000000000005),
                    t[bm](37.806000000000004, 19.998000000000005, 37.61800000000001, 21.230000000000004, 37.43000000000001, 22.462000000000007),
                    t[bm](37.151, 24.271, 35.264, 24.77, 33.591, 24.763),
                    t[sf](),
                    t[Sm](),
                    t[lo](),
                    t[to](),
                    t.restore(),
                    t[to](),
                    t[Va](),
                    t.save(),
                    t[Va](),
                    t.fillStyle = ax,
                    t[TE](),
                    t[Pu](10.427, 19.292),
                    t[Nu](5.735, 16.452),
                    t[Nu](12.58, 13.8),
                    t[Nu](12.045, 15.07),
                    t.lineTo(20.482, 15.072),
                    t.lineTo(19.667, 17.887),
                    t.lineTo(11.029, 17.851),
                    t[Nu](10.427, 19.292),
                    t[sf](),
                    t[Sm](),
                    t.stroke(),
                    t[to](),
                    t.restore(),
                    t.save(),
                    t.save(),
                    t[jm] = ax,
                    t[TE](),
                    t.moveTo(13.041, 13.042),
                    t[Nu](8.641, 10.73),
                    t[Nu](14.82, 8.474),
                    t[Nu](14.373, 9.537),
                    t[Nu](22.102, 9.479),
                    t[Nu](21.425, 11.816),
                    t[Nu](13.54, 11.85),
                    t.lineTo(13.041, 13.042),
                    t[sf](),
                    t.fill(),
                    t[lo](),
                    t.restore(),
                    t[to](),
                    t[Va](),
                    t[Va](),
                    t[jm] = ax,
                    t[TE](),
                    t.moveTo(29.787, 16.049),
                    t[Nu](29.979, 14.704),
                    t.lineTo(21.51, 14.706),
                    t[Nu](22.214, 12.147),
                    t[Nu](30.486, 12.116),
                    t[Nu](30.653, 10.926),
                    t[Nu](36.141, 13.4),
                    t[Nu](29.787, 16.049),
                    t[sf](),
                    t[Sm](),
                    t.stroke(),
                    t.restore(),
                    t[to](),
                    t[Va](),
                    t[Va](),
                    t[jm] = ax,
                    t[TE](),
                    t[Pu](28.775, 23.14),
                    t[Nu](29.011, 21.49),
                    t.lineTo(19.668, 21.405),
                    t[Nu](20.523, 18.295),
                    t[Nu](29.613, 18.338),
                    t.lineTo(29.815, 16.898),
                    t.lineTo(35.832, 19.964),
                    t[Nu](28.775, 23.14),
                    t.closePath(),
                    t.fill(),
                    t[lo](),
                    t[to](),
                    t[to](),
                    t[to](),
                    t[to]()
                }
            },
            cloud: {
                draw: function (t) {
                    t.save(),
                    t.beginPath(),
                    t[Pu](0, 0),
                    t.lineTo(90.75, 0),
                    t[Nu](90.75, 62.125),
                    t.lineTo(0, 62.125),
                    t[sf](),
                    t[Im](),
                    t[S_] = yE,
                    t.lineCap = Ky,
                    t[M_] = em,
                    t[QE] = 4,
                    t[Va]();
                    var i = t[Xm](44.0054, 6.4116, 44.0054, 51.3674);
                    i[NE](0, "rgba(159, 160, 160, 0.7)"),
                    i[NE](.9726, Tx),
                    t[jm] = i,
                    t[TE](),
                    t[Pu](57.07, 20.354),
                    t[bm](57.037, 20.354, 57.006, 20.358, 56.974000000000004, 20.358),
                    t.bezierCurveTo(54.461000000000006, 14.308, 48.499, 10.049000000000001, 41.538000000000004, 10.049000000000001),
                    t[bm](33.801, 10.049000000000001, 27.309000000000005, 15.316000000000003, 25.408000000000005, 22.456000000000003),
                    t[bm](18.988000000000007, 23.289, 14.025000000000006, 28.765000000000004, 14.025000000000006, 35.413000000000004),
                    t[bm](14.025000000000006, 42.635000000000005, 19.880000000000006, 48.49, 27.102000000000004, 48.49),
                    t[bm](29.321000000000005, 48.49, 31.407000000000004, 47.933, 33.237, 46.961),
                    t[bm](34.980000000000004, 49.327, 37.78, 50.867999999999995, 40.945, 50.867999999999995),
                    t[bm](43.197, 50.867999999999995, 45.261, 50.086, 46.896, 48.785999999999994),
                    t[bm](49.729, 50.78699999999999, 53.244, 51.98799999999999, 57.07, 51.98799999999999),
                    t[bm](66.412, 51.98799999999999, 73.986, 44.90699999999999, 73.986, 36.17099999999999),
                    t[bm](73.986, 27.436, 66.413, 20.354, 57.07, 20.354),
                    t[sf](),
                    t[Sm](),
                    t.stroke(),
                    t.restore(),
                    t.restore()
                }
            },
            node: {
                width: 60,
                height: 100,
                draw: function (t) {
                    t[Va](),
                    t.translate(0, 0),
                    t[TE](),
                    t[Pu](0, 0),
                    t.lineTo(40, 0),
                    t[Nu](40, 40),
                    t[Nu](0, 40),
                    t.closePath(),
                    t[Im](),
                    t[$o](0, 0),
                    t[$o](0, 0),
                    t[nf](1, 1),
                    t.translate(0, 0),
                    t[S_] = yE,
                    t[j_] = Ky,
                    t[M_] = em,
                    t[QE] = 4,
                    t[Va](),
                    t[jm] = kx,
                    t[TE](),
                    t[Pu](13.948, 31.075),
                    t.lineTo(25.914, 31.075),
                    t[vm](25.914, 31.075, 25.914, 31.075),
                    t[Nu](25.914, 34.862),
                    t[vm](25.914, 34.862, 25.914, 34.862),
                    t[Nu](13.948, 34.862),
                    t.quadraticCurveTo(13.948, 34.862, 13.948, 34.862),
                    t.lineTo(13.948, 31.075),
                    t[vm](13.948, 31.075, 13.948, 31.075),
                    t.closePath(),
                    t.fill(),
                    t[lo](),
                    t[to](),
                    t[Va](),
                    t.fillStyle = Ox,
                    t.beginPath(),
                    t[Pu](29.679, 35.972),
                    t[bm](29.679, 36.675000000000004, 29.110999999999997, 37.244, 28.407999999999998, 37.244),
                    t.lineTo(11.456, 37.244),
                    t[bm](10.751999999999999, 37.244, 10.183, 36.675, 10.183, 35.972),
                    t[Nu](10.183, 36.136),
                    t[bm](10.183, 35.431000000000004, 10.751999999999999, 34.863, 11.456, 34.863),
                    t.lineTo(28.407, 34.863),
                    t[bm](29.11, 34.863, 29.678, 35.431, 29.678, 36.136),
                    t.lineTo(29.678, 35.972),
                    t[sf](),
                    t[Sm](),
                    t[lo](),
                    t.restore(),
                    t[Va](),
                    t[jm] = Ox,
                    t.beginPath(),
                    t.moveTo(.196, 29.346),
                    t[bm](.196, 30.301, .9690000000000001, 31.075, 1.925, 31.075),
                    t.lineTo(37.936, 31.075),
                    t[bm](38.891, 31.075, 39.665, 30.301, 39.665, 29.346),
                    t.lineTo(39.665, 27.174),
                    t[Nu](.196, 27.174),
                    t.lineTo(.196, 29.346),
                    t[sf](),
                    t[Sm](),
                    t[lo](),
                    t[to](),
                    t[Va](),
                    t[jm] = jx,
                    t[TE](),
                    t[Pu](37.937, 3.884),
                    t[Nu](1.926, 3.884),
                    t.bezierCurveTo(.97, 3.884, .19699999999999984, 4.657, .19699999999999984, 5.614),
                    t[Nu](.19699999999999984, 27.12),
                    t[Nu](39.666000000000004, 27.12),
                    t[Nu](39.666000000000004, 5.615),
                    t[bm](39.665, 4.657, 38.892, 3.884, 37.937, 3.884),
                    t.closePath(),
                    t[Sm](),
                    t.stroke(),
                    t[to](),
                    t[Va](),
                    t[Va](),
                    t[to](),
                    t[Va](),
                    t[to](),
                    t[to](),
                    t[Va]();
                    var i = t[Xm](6.9609, 2.9341, 32.9008, 28.874);
                    i.addColorStop(0, Mx),
                    i[NE](1, Sx),
                    t[jm] = i,
                    t.beginPath(),
                    t[Pu](35.788, 6.39),
                    t[Nu](4.074, 6.39),
                    t[bm](3.315, 6.39, 2.702, 7.003, 2.702, 7.763),
                    t[Nu](2.702, 24.616),
                    t[Nu](37.159, 24.616),
                    t[Nu](37.159, 7.763),
                    t[bm](37.159, 7.003, 36.546, 6.39, 35.788, 6.39),
                    t[sf](),
                    t.fill(),
                    t[lo](),
                    t[to](),
                    t.restore()
                }
            },
            group: {
                draw: function (t) {
                    t[Va](),
                    t[$o](0, 0),
                    t.beginPath(),
                    t[Pu](0, 0),
                    t[Nu](47.75, 0),
                    t[Nu](47.75, 40),
                    t.lineTo(0, 40),
                    t[sf](),
                    t[Im](),
                    t[$o](0, 0),
                    t.translate(0, 0),
                    t.scale(1, 1),
                    t.translate(0, 0),
                    t[S_] = yE,
                    t[j_] = Ky,
                    t[M_] = em,
                    t[QE] = 4,
                    t.save(),
                    t[Va](),
                    t.fillStyle = kx,
                    t[TE](),
                    t[Pu](10.447, 26.005),
                    t.lineTo(18.847, 26.005),
                    t[vm](18.847, 26.005, 18.847, 26.005),
                    t.lineTo(18.847, 28.663),
                    t[vm](18.847, 28.663, 18.847, 28.663),
                    t[Nu](10.447, 28.663),
                    t[vm](10.447, 28.663, 10.447, 28.663),
                    t[Nu](10.447, 26.005),
                    t.quadraticCurveTo(10.447, 26.005, 10.447, 26.005),
                    t[sf](),
                    t[Sm](),
                    t[lo](),
                    t[to](),
                    t[Va](),
                    t[jm] = Ox,
                    t[TE](),
                    t.moveTo(21.491, 29.443),
                    t[bm](21.491, 29.935000000000002, 21.094, 30.338, 20.597, 30.338),
                    t[Nu](8.698, 30.338),
                    t[bm](8.201, 30.338, 7.8020000000000005, 29.936, 7.8020000000000005, 29.443),
                    t[Nu](7.8020000000000005, 29.557000000000002),
                    t[bm](7.8020000000000005, 29.063000000000002, 8.201, 28.662000000000003, 8.698, 28.662000000000003),
                    t[Nu](20.597, 28.662000000000003),
                    t[bm](21.093, 28.662000000000003, 21.491, 29.062, 21.491, 29.557000000000002),
                    t[Nu](21.491, 29.443),
                    t[sf](),
                    t.fill(),
                    t[lo](),
                    t.restore(),
                    t[Va](),
                    t.fillStyle = Ox,
                    t.beginPath(),
                    t[Pu](.789, 24.79),
                    t.bezierCurveTo(.789, 25.461, 1.334, 26.005, 2.0060000000000002, 26.005),
                    t.lineTo(27.289, 26.005),
                    t[bm](27.961000000000002, 26.005, 28.504, 25.461, 28.504, 24.79),
                    t[Nu](28.504, 23.267),
                    t[Nu](.789, 23.267),
                    t[Nu](.789, 24.79),
                    t.closePath(),
                    t[Sm](),
                    t[lo](),
                    t.restore(),
                    t[Va](),
                    t[jm] = jx,
                    t.beginPath(),
                    t[Pu](27.289, 6.912),
                    t[Nu](2.006, 6.912),
                    t[bm](1.3339999999999996, 6.912, .7889999999999997, 7.455, .7889999999999997, 8.126),
                    t[Nu](.7889999999999997, 23.227),
                    t.lineTo(28.503999999999998, 23.227),
                    t[Nu](28.503999999999998, 8.126),
                    t[bm](28.504, 7.455, 27.961, 6.912, 27.289, 6.912),
                    t[sf](),
                    t[Sm](),
                    t[lo](),
                    t[to](),
                    t.save(),
                    t[Va](),
                    t[to](),
                    t[Va](),
                    t[to](),
                    t[to](),
                    t[Va]();
                    var i = t.createLinearGradient(5.54, 6.2451, 23.7529, 24.458);
                    i[NE](0, Mx),
                    i[NE](1, Sx),
                    t.fillStyle = i,
                    t[TE](),
                    t[Pu](25.78, 8.671),
                    t[Nu](3.514, 8.671),
                    t.bezierCurveTo(2.9819999999999998, 8.671, 2.549, 9.101999999999999, 2.549, 9.635),
                    t[Nu](2.549, 21.466),
                    t.lineTo(26.743, 21.466),
                    t[Nu](26.743, 9.636),
                    t.bezierCurveTo(26.743, 9.102, 26.312, 8.671, 25.78, 8.671),
                    t[sf](),
                    t[Sm](),
                    t[lo](),
                    t.restore(),
                    t.restore(),
                    t.save(),
                    t.save(),
                    t[jm] = kx,
                    t.beginPath(),
                    t.moveTo(27.053, 33.602),
                    t.lineTo(36.22, 33.602),
                    t.quadraticCurveTo(36.22, 33.602, 36.22, 33.602),
                    t[Nu](36.22, 36.501),
                    t[vm](36.22, 36.501, 36.22, 36.501),
                    t[Nu](27.053, 36.501),
                    t.quadraticCurveTo(27.053, 36.501, 27.053, 36.501),
                    t[Nu](27.053, 33.602),
                    t[vm](27.053, 33.602, 27.053, 33.602),
                    t[sf](),
                    t[Sm](),
                    t.stroke(),
                    t[to](),
                    t.save(),
                    t.fillStyle = Ox,
                    t[TE](),
                    t[Pu](39.104, 37.352),
                    t[bm](39.104, 37.891, 38.67, 38.327, 38.13, 38.327),
                    t.lineTo(25.143, 38.327),
                    t[bm](24.602, 38.327, 24.166, 37.891, 24.166, 37.352),
                    t[Nu](24.166, 37.477999999999994),
                    t.bezierCurveTo(24.166, 36.937, 24.602, 36.501, 25.143, 36.501),
                    t.lineTo(38.131, 36.501),
                    t[bm](38.671, 36.501, 39.105, 36.937, 39.105, 37.477999999999994),
                    t.lineTo(39.105, 37.352),
                    t[sf](),
                    t[Sm](),
                    t[lo](),
                    t[to](),
                    t[Va](),
                    t[jm] = Ox,
                    t[TE](),
                    t[Pu](16.514, 32.275),
                    t[bm](16.514, 33.004999999999995, 17.107, 33.601, 17.839, 33.601),
                    t[Nu](45.433, 33.601),
                    t[bm](46.166, 33.601, 46.758, 33.005, 46.758, 32.275),
                    t[Nu](46.758, 30.607999999999997),
                    t[Nu](16.514, 30.607999999999997),
                    t[Nu](16.514, 32.275),
                    t[sf](),
                    t.fill(),
                    t.stroke(),
                    t[to](),
                    t[Va](),
                    t[jm] = jx,
                    t.beginPath(),
                    t.moveTo(45.433, 12.763),
                    t[Nu](17.839, 12.763),
                    t[bm](17.107, 12.763, 16.514, 13.356, 16.514, 14.089),
                    t.lineTo(16.514, 30.57),
                    t[Nu](46.757999999999996, 30.57),
                    t[Nu](46.757999999999996, 14.088),
                    t.bezierCurveTo(46.758, 13.356, 46.166, 12.763, 45.433, 12.763),
                    t[sf](),
                    t[Sm](),
                    t[lo](),
                    t[to](),
                    t.save(),
                    t[Va](),
                    t.restore(),
                    t[Va](),
                    t[to](),
                    t.restore(),
                    t[Va](),
                    i = t[Xm](21.6973, 12.0352, 41.5743, 31.9122),
                    i[NE](0, Mx),
                    i[NE](1, Sx),
                    t[jm] = i,
                    t[TE](),
                    t[Pu](43.785, 14.683),
                    t[Nu](19.486, 14.683),
                    t[bm](18.903000000000002, 14.683, 18.433, 15.153, 18.433, 15.735),
                    t[Nu](18.433, 28.649),
                    t.lineTo(44.837, 28.649),
                    t[Nu](44.837, 15.734),
                    t[bm](44.838, 15.153, 44.367, 14.683, 43.785, 14.683),
                    t[sf](),
                    t[Sm](),
                    t.stroke(),
                    t[to](),
                    t[to](),
                    t[Va](),
                    t[Ix] = .5,
                    t[TE](),
                    t.moveTo(23.709, 36.33),
                    t[Nu](4.232, 36.33),
                    t[Nu](4.232, 27.199),
                    t.lineTo(5.304, 27.199),
                    t.lineTo(5.304, 35.259),
                    t.lineTo(23.709, 35.259),
                    t[Nu](23.709, 36.33),
                    t[sf](),
                    t[Sm](),
                    t.stroke(),
                    t[to](),
                    t[to]()
                }
            },
            subnetwork: {
                draw: function (t) {
                    t[Va](),
                    t[$o](0, 0),
                    t[TE](),
                    t[Pu](0, 0),
                    t[Nu](60.75, 0),
                    t.lineTo(60.75, 42.125),
                    t[Nu](0, 42.125),
                    t[sf](),
                    t[Im](),
                    t[$o](0, .26859504132231393),
                    t[nf](.6694214876033058, .6694214876033058),
                    t.translate(0, 0),
                    t[S_] = yE,
                    t[j_] = Ky,
                    t[M_] = em,
                    t.miterLimit = 4,
                    t[Va](),
                    t[Va](),
                    t[to](),
                    t[Va](),
                    t[to](),
                    t[to](),
                    t.save();
                    var i = t[Xm](43.6724, -2.7627, 43.6724, 59.3806);
                    i[NE](0, "rgba(159, 160, 160, 0.7)"),
                    i[NE](.9726, Tx),
                    t[jm] = i,
                    t[TE](),
                    t[Pu](61.732, 16.509),
                    t[bm](61.686, 16.509, 61.644, 16.515, 61.599, 16.515),
                    t[bm](58.126, 8.152000000000001, 49.884, 2.2650000000000006, 40.262, 2.2650000000000006),
                    t[bm](29.567, 2.2650000000000006, 20.594, 9.545000000000002, 17.966, 19.415),
                    t[bm](9.09, 20.566, 2.229, 28.136, 2.229, 37.326),
                    t[bm](2.229, 47.309, 10.322, 55.403000000000006, 20.306, 55.403000000000006),
                    t.bezierCurveTo(23.374000000000002, 55.403000000000006, 26.257, 54.633, 28.787, 53.28900000000001),
                    t[bm](31.197, 56.56000000000001, 35.067, 58.69000000000001, 39.442, 58.69000000000001),
                    t[bm](42.555, 58.69000000000001, 45.408, 57.60900000000001, 47.669, 55.81200000000001),
                    t.bezierCurveTo(51.586, 58.57800000000001, 56.443999999999996, 60.238000000000014, 61.732, 60.238000000000014),
                    t.bezierCurveTo(74.64699999999999, 60.238000000000014, 85.116, 50.45000000000002, 85.116, 38.37400000000001),
                    t.bezierCurveTo(85.116, 26.298, 74.646, 16.509, 61.732, 16.509),
                    t[sf](),
                    t[Sm](),
                    t.stroke(),
                    t[to](),
                    t[Va](),
                    t.save(),
                    t[jm] = kx,
                    t[TE](),
                    t[Pu](34.966, 44.287),
                    t.lineTo(45.112, 44.287),
                    t.quadraticCurveTo(45.112, 44.287, 45.112, 44.287),
                    t[Nu](45.112, 47.497),
                    t.quadraticCurveTo(45.112, 47.497, 45.112, 47.497),
                    t[Nu](34.966, 47.497),
                    t.quadraticCurveTo(34.966, 47.497, 34.966, 47.497),
                    t[Nu](34.966, 44.287),
                    t[vm](34.966, 44.287, 34.966, 44.287),
                    t[sf](),
                    t[Sm](),
                    t[lo](),
                    t[to](),
                    t[Va](),
                    t[jm] = Ax,
                    t[TE](),
                    t[Pu](48.306, 48.439),
                    t.bezierCurveTo(48.306, 49.034, 47.824999999999996, 49.52, 47.226, 49.52),
                    t[Nu](32.854, 49.52),
                    t.bezierCurveTo(32.253, 49.52, 31.771, 49.034000000000006, 31.771, 48.439),
                    t[Nu](31.771, 48.578),
                    t[bm](31.771, 47.981, 32.253, 47.497, 32.854, 47.497),
                    t[Nu](47.226, 47.497),
                    t[bm](47.824999999999996, 47.497, 48.306, 47.98, 48.306, 48.578),
                    t.lineTo(48.306, 48.439),
                    t.closePath(),
                    t[Sm](),
                    t.stroke(),
                    t[to](),
                    t.save(),
                    t.fillStyle = Lx,
                    t.beginPath(),
                    t.moveTo(23.302, 42.82),
                    t[bm](23.302, 43.63, 23.96, 44.287, 24.772, 44.287),
                    t.lineTo(55.308, 44.287),
                    t[bm](56.12, 44.287, 56.775, 43.629999999999995, 56.775, 42.82),
                    t.lineTo(56.775, 40.98),
                    t.lineTo(23.302, 40.98),
                    t[Nu](23.302, 42.82),
                    t[sf](),
                    t[Sm](),
                    t[lo](),
                    t[to](),
                    t[Va](),
                    t.fillStyle = jx,
                    t[TE](),
                    t.moveTo(55.307, 21.229),
                    t[Nu](24.771, 21.229),
                    t[bm](23.959, 21.229, 23.301000000000002, 21.884, 23.301000000000002, 22.695),
                    t[Nu](23.301000000000002, 40.933),
                    t.lineTo(56.774, 40.933),
                    t[Nu](56.774, 22.695),
                    t[bm](56.774, 21.884, 56.119, 21.229, 55.307, 21.229),
                    t[sf](),
                    t.fill(),
                    t[lo](),
                    t[to](),
                    t.save(),
                    t[Va](),
                    t[to](),
                    t[Va](),
                    t[to](),
                    t[to](),
                    t[Va](),
                    i = t.createLinearGradient(29.04, 20.4219, 51.0363, 42.4181),
                    i[NE](0, Mx),
                    i.addColorStop(1, Sx),
                    t.fillStyle = i,
                    t.beginPath(),
                    t.moveTo(53.485, 23.353),
                    t.lineTo(26.592, 23.353),
                    t[bm](25.948999999999998, 23.353, 25.427, 23.873, 25.427, 24.517000000000003),
                    t[Nu](25.427, 38.807),
                    t[Nu](54.647, 38.807),
                    t[Nu](54.647, 24.517000000000003),
                    t.bezierCurveTo(54.648, 23.873, 54.127, 23.353, 53.485, 23.353),
                    t.closePath(),
                    t.fill(),
                    t.stroke(),
                    t[to](),
                    t.restore(),
                    t.restore()
                }
            }
        };
    for (var vH in lH) gn(Cx + vH, lH[vH]);
    var bH = function () {
            this[Dx] = !1;
            var t = this._fa;
            t[Ra]();
            var i = this.$border || 0,
                n = this._7i.x + i / 2,
                e = this._7i.y + i / 2,
                s = this._7i[Ca] - i,
                h = this._7i[Da] - i,
                r = Fn[Yh](this, {
                    x: n,
                    y: e
                });
            Hn(t, r.x, r.y, !0),
            r = Fn[Yh](this, {
                    x: n + s,
                    y: e
                }),
            Hn(t, r.x, r.y),
            r = Fn.call(this, {
                    x: n + s,
                    y: e + h
                }),
            Hn(t, r.x, r.y),
            r = Fn.call(this, {
                    x: n,
                    y: e + h
                }),
            Hn(t, r.x, r.y),
            this.__lyPointer && (r = Fn[Yh](this, {
                    x: this[wf],
                    y: this._pointerY
                }), Hn(t, r.x, r.y)),
            i && t[vf](i / 2)
        },
        gH = 20,
        yH = {
            _h3: !1,
            _jw: null,
            _mul: 0,
            _kq: -1,
            _ks: null,
            _f3: function (t) {
                this._jw || (this._jw = [], this._ix = Yz),
                this._jw.push(t),
                this._ej(),
                this._kj()
            },
            _kj: function () {
                if (!this._ks) {
                    var t = this;
                    this._ks = setTimeout(function i() {
                        return t._ej() !== !1 ? void(t._ks = setTimeout(i, t._h2())) : void delete t._ks
                    }, this._h2())
                }
            },
            _h2: function () {
                return Math.max(gH, this._jw[this._kq][Rx])
            },
            _ej: function () {
                return this._fc(this._kq + 1)
            },
            _fc: function (t) {
                if (this._h3) t %= this[Px];
                else if (t >= this._jw[Hh]) return !1;
                if (this._kq == t) return !1;
                this._kq = t;
                var i = this._jw[this._kq],
                    n = i[Nx];
                return n || (i[Nx] = n = Li(this[Ca], this[Da]), n.g[Qo](i[vo], 0, 0), n[Tm] = i[Tm]),
                this._km = n,
                this.$invalidateSize = !0,
                this._dc()
            },
            _mut: function () {
                return this._jw ? (this._h3 = !0, this[Px] = this._jw.length, 1 == this[Px] ? this._dc() : void this._kj()) : void this._hd()
            },
            _lo: function () {
                this._ks && (clearTimeout(this._ks), delete this._ks)
            },
            _dc: function () {
                var t = this._dispatcher[Fl];
                if (!t || !t[Hh]) return !1;
                for (var i = new AG(this, Gm, zm, this._km), n = 0, e = t[Hh]; e > n; n++) {
                    var s = t[n];
                    s.scope._je && s[Gl]._je[Bx] ? (t[Xh](n, 1), n--, e--) : s[or].call(s[Gl], i)
                }
                return t[Hh] > 0
            },
            _9x: function (t, i) {
                this[xm].addListener(t, i),
                this._h3 && !this._ks && this._kj()
            },
            _63: function (t, i) {
                this[xm][zl](t, i),
                this._dispatcher._ms8() || this._lo()
            },
            _hg: function () {
                this._lo(),
                this[xm].clear()
            },
            _g9: function () {
                var t = this._km[$x];
                return t || (this._km._msufferedImage = t = new _H(this._km, 1)),
                t
            }
        },
        mH = function (t) {
            return t[Fx](function (t, i) {
                return 2 * t + i
            }, 0)
        },
        EH = function (t) {
            for (var i = [], n = 7; n >= 0; n--) i[tr]( !! (t & 1 << n));
            return i
        },
        xH = function (t) {
            this[vo] = t,
            this.len = this.data.length,
            this.pos = 0,
            this[qx] = function () {
                if (this.pos >= this.data[Hh]) throw new Error("Attempted to read past end of stream.");
                return 255 & t[tu](this.pos++)
            },
            this.readBytes = function (t) {
                for (var i = [], n = 0; t > n; n++) i[tr](this[qx]());
                return i
            },
            this[Gx] = function (t) {
                for (var i = "", n = 0; t > n; n++) i += String.fromCharCode(this.readByte());
                return i
            },
            this[zx] = function () {
                var t = this[Hx](2);
                return (t[1] << 8) + t[0]
            }
        },
        pH = function (t, i, n) {
            for (var e, s, h = 0, r = function (t) {
                for (var n = 0, e = 0; t > e; e++) i.charCodeAt(h >> 3) & 1 << (7 & h) && (n |= 1 << e),
                h++;
                return n
            }, a = [], o = 1 << t, f = o + 1, u = t + 1, c = [], _ = function () {
                c = [],
                u = t + 1;
                for (var i = 0; o > i; i++) c[i] = [i];
                c[o] = [],
                c[f] = null
            }, d = 0; s = e, e = r(u), !(d++ > n);) if (e !== o) {
                if (e === f) break;
                if (e < c[Hh]) s !== o && c[tr](c[s][Kh](c[e][0]));
                else {
                    if (e !== c[Hh]) throw new Error(Yx);
                    c.push(c[s].concat(c[s][0]))
                }
                a.push[nr](a, c[e]),
                c[Hh] === 1 << u && 12 > u && u++
            } else _();
            return a
        },
        wH = function (t, i) {
            i || (i = {});
            var n = function (i) {
                for (var n = [], e = 0; i > e; e++) n.push(t[Hx](3));
                return n
            },
                e = function () {
                    var i, n;
                    n = "";
                    do i = t.readByte(),
                    n += t.read(i);
                    while (0 !== i);
                    return n
                },
                s = function () {
                    var e = {};
                    if (e.sig = t[Gx](3), e.ver = t.read(3), Ux !== e.sig) throw new Error(Wx);
                    e[Ca] = t[zx](),
                    e[Da] = t[zx]();
                    var s = EH(t[qx]());
                    e[Vx] = s[Xx](),
                    e[Kx] = mH(s[Xh](0, 3)),
                    e[Zx] = s.shift(),
                    e.gctSize = mH(s[Xh](0, 3)),
                    e[Jx] = t.readByte(),
                    e.pixelAspectRatio = t[qx](),
                    e[Vx] && (e.gct = n(1 << e.gctSize + 1)),
                    i.hdr && i.hdr(e)
                },
                h = function (n) {
                    var s = function (n) {
                        var e = (t[qx](), EH(t.readByte()));
                        n[Qx] = e.splice(0, 3),
                        n[tp] = mH(e[Xh](0, 3)),
                        n[ip] = e.shift(),
                        n[qf] = e.shift(),
                        n[np] = t[zx](),
                        n.transparencyIndex = t[qx](),
                        n.terminator = t[qx](),
                        i.gce && i.gce(n)
                    },
                        h = function (t) {
                            t[ep] = e(),
                            i.com && i.com(t)
                        },
                        r = function (n) {
                            t.readByte(),
                            n[sp] = t.readBytes(12),
                            n[hp] = e(),
                            i.pte && i.pte(n)
                        },
                        a = function (n) {
                            var s = function (n) {
                                t.readByte(),
                                n.unknown = t[qx](),
                                n[rp] = t[zx](),
                                n[ap] = t[qx](),
                                i.app && i.app[op] && i.app[op](n)
                            },
                                h = function (t) {
                                    t[fp] = e(),
                                    i.app && i.app[t.identifier] && i.app[t[up]](t)
                                };
                            switch (t.readByte(), n.identifier = t[Gx](8), n.authCode = t.read(3), n.identifier) {
                                case "NETSCAPE":
                                    s(n);
                                    break;
                                default:
                                    h(n)
                                }
                        },
                        o = function (t) {
                            t[vo] = e(),
                            i.unknown && i[cp](t)
                        };
                    switch (n.label = t.readByte(), n.label) {
                        case 249:
                            n[_p] = dp,
                            s(n);
                            break;
                        case 254:
                            n[_p] = lp,
                            h(n);
                            break;
                        case 1:
                            n[_p] = vp,
                            r(n);
                            break;
                        case 255:
                            n[_p] = bp,
                            a(n);
                            break;
                        default:
                            n[_p] = cp,
                            o(n)
                        }
                },
                r = function (s) {
                    var h = function (t, i) {
                        for (var n = new Array(t.length), e = t.length / i, s = function (e, s) {
                            var h = t.slice(s * i, (s + 1) * i);
                            n.splice[nr](n, [e * i, i].concat(h))
                        }, h = [0, 4, 2, 1], r = [8, 8, 4, 2], a = 0, o = 0; 4 > o; o++) for (var f = h[o]; e > f; f += r[o]) s(f, a),
                        a++;
                        return n
                    };
                    s[Yf] = t[zx](),
                    s.topPos = t[zx](),
                    s[Ca] = t[zx](),
                    s[Da] = t[zx]();
                    var r = s.width * s[Da],
                        a = EH(t[qx]());
                    s[Hf] = a.shift(),
                    s.interlaced = a[Xx](),
                    s[Zx] = a[Xx](),
                    s[Qx] = a.splice(0, 2),
                    s.lctSize = mH(a[Xh](0, 3)),
                    s.lctFlag && (s.lct = n(1 << s[gp] + 1)),
                    s[yp] = t[qx]();
                    var o = e();
                    s[Wf] = pH(s[yp], o, r),
                    s[mp] && (s[Wf] = h(s[Wf], s.width)),
                    i.img && i.img(s)
                },
                a = function () {
                    var n = {};
                    switch (n[Ep] = t[qx](), String.fromCharCode(n[Ep])) {
                    case "!":
                        n.type = xp,
                        h(n);
                        break;
                    case ",":
                        n[yo] = f_,
                        r(n);
                        break;
                    case ";":
                        n.type = pp,
                        i.eof && i.eof(n);
                        break;
                    default:
                        throw new Error(wp + n[Ep].toString(16))
                    }
                    pp !== n[yo] && setTimeout(a, 0)
                },
                o = function () {
                    s(),
                    setTimeout(a, 0)
                };
            o()
        },
        TH = "";
    i.addEventListener && i[Cv](Tp, function (t) {
            if (t.ctrlKey && t[kp] && t.altKey && 73 == t[Op]) {
                var i = jz.name + jp + jz[Mp] + Sp + jz.publishDate + Ja + jz[Ip] + Ja + jz.copyright + TH;
                jz[sy](i)
            }
        }, !1);
    var kH = Ap;
    TH = Lp + decodeURIComponent(Cp);
    var OH, jH, MH, SH = t,
        IH = Dp,
        AH = Rp,
        LH = Pp,
        CH = Np,
        DH = Bp,
        RH = $p,
        PH = Fp,
        NH = qp,
        BH = Gp,
        $H = zp,
        FH = Hp,
        qH = Yp,
        GH = Up,
        zH = Wp,
        HH = Vp,
        YH = Xp,
        UH = Kp,
        WH = Zp,
        VH = Jp,
        XH = Qp,
        KH = tw,
        ZH = SH[CH + iw];
    ZH && (jH = SH[zH + nw][DH + ew], ZH[Yh](SH, Zn, YH), ZH.call(SH, Jn, VH), ZH.call(SH, function () {
            QH && QH == kH && (fY = !1)
        }, UH));
    var JH, QH, tY, iY = 111,
        nY = function (t, i) {
            i || (i = sw + AH + hw);
            try {
                MH.call(t, i, 6 * iY, 1 * iY)
            } catch (n) {}
        },
        eY = !0,
        sY = !0,
        hY = !0,
        rY = !0,
        aY = !0,
        oY = !0,
        fY = !0,
        uY = uG ? 200 : 1024,
        cY = function (t, i) {
            return Kn ? Kn(t, i) || "" : void 0
        };
    if (i[qa]) {
            var _Y = i.createElement(rw);
            _Y[na].display = m_,
            _Y[aw] = function (t) {
                var i = t[R_][ow],
                    n = jH;
                if ("" === n || fw == n || uw == n) return void this[pm][pm][Ev](this[pm]);
                var e = i[cw][nu];
                i[CH + iw](function () {
                        Xn(e) != JH && ($Y[er]._ir = null)
                    }, VH),
                this.parentNode[pm][Ev](this[pm])
            };
            var dY = i.createElement(a_);
            dY.style.width = O_,
            dY[na][Da] = O_,
            dY[na][_w] = y_,
            dY[bu](_Y),
            i.documentElement.appendChild(dY)
        }
    if (i[HH + dw]) {
            var lY = i[HH + dw](BH + lw);
            lY[na].display = m_,
            lY.onload = function (t) {
                var i = vw,
                    n = t[R_][i + bw];
                OH = n.Date.now();
                var e = n[$H + FH + gw + qH + yw][GH + yo];
                MH = e[IH + mw],
                sG && (n = SH);
                var s = n[CH + iw];
                s[Yh](SH, ne, VH),
                s[Yh](SH, ee, XH),
                s.call(SH, he, KH),
                s[Yh](SH, re, UH),
                s[Yh](SH, Qn, WH),
                s[Yh](SH, ie, KH),
                s[Yh](SH, se, VH),
                s.call(SH, te, VH),
                this[pm].parentNode[Ev](this[pm])
            };
            var dY = i[qa](a_);
            dY.style[Ca] = O_,
            dY[na][Da] = O_,
            dY[na][_w] = y_,
            dY[bu](lY),
            i[ad].appendChild(dY)
        }
    var vY = {
            position: Ew,
            userSelect: m_,
            outline: m_,
            transformOrigin: xw,
            "-webkit-tap-highlight-color": yE
        },
        bY = pw;
    gi($r + bY, vY);
    var gY = {
            width: x_,
            height: x_,
            position: E_,
            overflow: y_,
            textAlign: Bo,
            outline: m_,
            tapHighlightColor: yE,
            userSelect: m_
        },
        yY = ww;
    gi($r + yY, gY);
    var mY = Tw,
        EY = {
            overflow: y_,
            "text-align": Bo,
            "-webkit-tap-highlight-color": yE,
            outline: m_
        };
    gi($r + mY, EY);
    var xY = M(function (t) {
            this[kw] = new wY,
            this._m9 = new dG,
            this._82 = [],
            this[Ow] = [],
            this[jw] = [],
            this._83 = {},
            this[fo] = new EG,
            this._ip = new SY,
            this._viewport = new IY,
            this._ip[Bl] = function (t) {
                this._6o(t)
            }[lr](this),
            this._mu3(),
            this.setParent(t)
        }, {
            _mqe: null,
            _ik: null,
            _m9: null,
            _muk: null,
            _ip: null,
            _muj: function (t) {
                return t ? (this[Mw] || (this[Mw] = {}), this[Mw][t] ? !1 : (this[Mw][t] = !0, void this[Sw]())) : this[Sw]()
            },
            _93: function (t) {
                return this[Mw] && this[Mw][t]
            },
            isInvalidate: function () {
                return this._5s
            },
            clear: function () {
                this._m9[Ra](),
                this[Ow][Hh] = 0,
                this._83 = {},
                this[ac] = !1,
                this.invalidate()
            },
            _6e: function () {
                this[Iw](Aw),
                this._2k()
            },
            _2k: function () {
                this._muj(Lw)
            },
            invalidate: function (t) {
                (t || !this._5s) && (this._5s = !0, this._lo || (this[Cw] = requestAnimationFrame(this._f6.bind(this))))
            },
            _6k: function (t) {
                return this._lo = t,
                t ? void(this[Cw] && (cancelAnimationFrame(this[Cw]), this[Cw] = null)) : void(this._5s && this[Sw](!0))
            },
            _f6: function () {
                this[Cw] = null,
                this._5s = !1;
                var t = this[ac];
                this[ac] || (this[Dw](), this[ac] = !0),
                this._muu(!t),
                this._3h(),
                this._ir(),
                this._24()
            },
            _muu: function (t) {
                this[Rw] = t || this[Pw],
                (t || this._5ss[Aw]) && this._8v(),
                (t || this._5ss[Nw]) && this._6q(),
                this[Bw](t),
                this._4d(t),
                this[Mw] = {}
            },
            _3h: function () {
                this[Ow][Hh] = 0;
                var t = this[$w];
                if (this._m9[Vf](function (i) {
                    if (i[Fw] !== !1) {
                        var n = this[qw](i);
                        t[Dc](n.x, n.y, n[Ca], n[Da]) && this[Ow][tr](i)
                    }
                }, this), this._muk = this._hl(this[Ow]), !this._mqm) {
                    var i = this[kw];
                    this[jw][Hh] = 0,
                    i[Gw](this[$w]),
                    i._ht() || this._muk.forEach(function (t) {
                        var n = this[qw](t);
                        i._ez(n.x, n.y, n.width, n.height) && this._l5ingList[tr](t)
                    }, this)
                }
            },
            _hl: function (t) {
                return sG ? t = d(t, this._96) : t[zw](this._96),
                t
            },
            _96: function (t, i) {
                return t = t[Hw] || 0,
                i = i[Hw] || 0,
                t - i
            },
            _mqf: function (t) {
                return t.boundingBox
            },
            _ir: function () {
                if (this._mqm) return this._e8(),
                this._6w(!0),
                void this[vu](this[Yw], this[Ow]);
                this._6w(this[Uw]);
                var t = this[kw],
                    i = this[Yw];
                i[Va](),
                this[Uw] && (fe(i), i[D_](this._msuffer[Ga], this._msuffer.x, this[Uw].y)),
                t._jt(i, this._e7[lr](this)),
                this._e8(),
                this[vu](i, this[jw]),
                i[to]()
            },
            _6w: function (t) {
                this[Ww] ? (this[Ww] = !1, this._ik.setSize(this[kE], this._height)) : t && oe(this._ik)
            },
            _8v: function () {
                var t = this.width,
                    i = this[Da];
                return this[kE] == t && this[Om] == i ? !1 : (this[kE] = t, this._height = i, void(this[Ww] = !0))
            },
            _4d: function (t) {
                if (!t && !this._5ss.viewport) return !1;
                var i = this._ip[Vw]([0, 0]),
                    n = this[nf],
                    e = this[kE] / n,
                    s = this[Om] / n,
                    h = this[mo],
                    r = this._viewport;
                if (r.x == i[0] && r.y == i[1] && r[Ca] == e && r[Da] == s && r.rotate == h) return !1;
                var a = r[fE]();
                return this[$w].set(i[0], i[1], e, s, h, n),
                this._36(this._viewport, a, t),
                !0
            },
            _36: function (t, i, n) {
                this[Rw] || n || (this._msuffer = this._fn(i, t))
            },
            _6o: function () {
                if (this._mq0) {
                    if (this._lo) {
                        var t;
                        this[Xw] ? this[Xw][Kw] = t = OY.mul([], this._ip.m, OY[Zw]([], this[Xw].m)) : t = this._ip.m,
                        this._4x(t)
                    }
                    this[Iw](Nw),
                    this._2k()
                }
            },
            _4x: function (t) {
                this.__mussMatrix = t,
                AY[bc](this._ik, __, t ? Jw + t.join(Ar) + ")" : "")
            },
            _6q: function () {
                var t = this[Xw];
                if (this[Xw] = {
                    tx: this._ip.m[4],
                    ty: this._ip.m[5],
                    m: this._ip.m[Vh](),
                    scale: this._ip._fi(),
                    rotate: this._ip._e5()
                }, this[Qw] && this._4x(null), !this[Rw]) {
                    if (this._2m(this._muurrentMatrix, t), !t || t.scale != this[Xw][nf]) return this._6l(this[Xw][nf], t ? t[nf] : null),
                    void(this[Rw] = !0);
                    if (!t || t.rotate != this[Xw][mo]) return this._5a(this[Xw][mo], t ? t[mo] : null),
                    void(this._mqm = !0);
                    var i = t.m[4] - this[Xw].m[4],
                        n = t.m[5] - this[Xw].m[5],
                        e = this[Na];
                    i *= e,
                    n *= e;
                    var s = 1e-4;
                        (Math.abs(i - Math[uo](i)) > s || Math.abs(n - Math[uo](n)) > s) && (this._mqm = !0)
                }
            },
            _6s: function () {
                var t = this[fo],
                    i = t.clone();
                t.clear(),
                this._m9[Vf](function (i) {
                        i[Fw] !== !1 && t.add(this._mqf(i))
                    }, this),
                t[ql](i) || this._39(t, i)
            },
            _39: function () {},
            _mq0: !1,
            _mss: function () {},
            _8o: function (t) {
                var i = t[Na];
                t.scale(i, i),
                t[__][nr](t, this._ip.m)
            },
            render: function (t, i) {
                i && i[Hh] && (t[Va](), this._8o(t), i.forEach(function (i) {
                    if (t.save(), i[tT] !== !1) try {
                        i.render(t)
                    } catch (n) {
                        console.error(n)
                    }
                    t[to]()
                }, this), t[to]())
            },
            setParent: function (t) {
                N(t) && (t = i[iT](t)),
                this._m8 != t && (this._m8 && this[nT] && (R(this._m8, mY), this._m8[Ev](this[nT])), this._m8 = t, this._m8 && (D(this._m8, mY), this._m8.appendChild(this._6u()), this._6e()))
            },
            _6u: function () {
                return this[nT] || this._mu3(),
                this[nT]
            },
            _mu3: function () {
                var t = Li(!0);
                Wn(t.g),
                t[gu] = bY;
                var n = i[qa](a_);
                return n[gu] = yY,
                n[bu](t),
                n[eT] = 0,
                this._ik = t,
                this._mqe = n,
                this[Yw] = this._ik[zf](za),
                t
            },
            toLogical: function (t, i) {
                return t instanceof Object && (Q(t) && (t = this._7s(t)), Array.isArray(t) ? (i = t[1] || 0, t = t[0] || 0) : (i = t.y || 0, t = t.x || 0)),
                this._ip[Vw]([t, i])
            },
            toCanvas: function (t, i) {
                return this._ip[__]([t, i])
            },
            _7s: function (t) {
                return yi(t, this[nT])
            },
            _ex: function (t, i, n) {
                if (t[s_] instanceof Function) return t[s_](i, n);
                var e = this[qw](t);
                return e ? n ? EG[Dc](e.x, e.y, e[Ca], e.height, i[0] - n, i[1] - n, n + n, n + n) : EG[Dc](e.x, e.y, e.width, e[Da], i[0], i[1]) : t
            },
            hitTest: function (t, i) {
                return this._84(t, i)
            },
            _84: function (t, i) {
                i = this._8w(i),
                t = this.toLogical(t);
                for (var n, e = this[Ow].length; --e >= 0;) if (n = this[Ow][e], this._ex(n, t, i)) return n
            },
            _8w: function (t) {
                return (t === n || null === t) && (t = _G[hm]),
                t ? t / this[nf] : 0
            },
            getUIByMouseEvent: function (t, i) {
                if (t[sT]) return this._m9[$d](t[sT]);
                var n = this._84(t, i);
                return t.uiId = n ? n.id : -1,
                n
            },
            _83: null,
            invalidateUI: function (t) {
                this._83[t.id] = t,
                this[Sw]()
            },
            _8s: function (t) {
                t.validate instanceof Function && t[ao](this)
            },
            _mu9: function (t, i) {
                t[hT] && this._fl(t.__iv);
                var n = t[Fw];
                if (t.__h9 = this._es(t, i), !t.__h9) return n;
                var e = t[hT];
                this._8s(t);
                var s = this[qw](t);
                t.__iv = {
                    x: s.x,
                    y: s.y,
                    width: s[Ca],
                    height: s.height
                };
                var h = t.__h9 !== n || !EG[ql](e, s);
                return h && t[hT] && this._fl(t[hT]),
                h
            },
            _es: function (t) {
                return t.visible !== !1
            },
            _$v: function (t) {
                this._m9[Vf](function (i) {
                    this[rT](i, t)
                }, this),
                this._83 = {},
                this._6s()
            },
            _mqh: function (t) {
                var i = this[nf];
                if (t) return this._$v(i);
                var n = this[aT];
                this[aT] = !1;
                for (var e in this._83) {
                    var s = this._83[e];
                    n ? this[rT](s, i) : n = this[rT](s, i)
                }
                this._83 = {},
                n && this._6s()
            },
            _82: null,
            _24: function () {
                if (!this._82[Hh]) return !1;
                var t = this._82;
                this._82 = [],
                t[Vf](function (t) {
                    try {
                        var i = t[Yh],
                            n = t.scope,
                            e = t.delay;
                        n instanceof Object ? i = i.bind(n) : n && !e && (e = parseInt(n)),
                        e ? setTimeout(i, e) : i()
                    } catch (s) {}
                }, this),
                this._5s && this._f6()
            },
            _el: function (t, i, n) {
                this._82.push({
                    call: t,
                    scope: i,
                    delay: n
                }),
                this._5s || this._24()
            },
            _41: function (t, i) {
                for (var n = this[Ow], e = 0, s = n[Hh]; s > e; e++) if (t[Yh](i, n[e]) === !1) return !1
            },
            _ew: function (t, i) {
                this._m9.forEach(t, i)
            },
            _$x: function (t, i) {
                for (var n = this._muk, e = n.length - 1; e >= 0; e--) if (t[Yh](i, n[e]) === !1) return !1
            },
            _44: function (t, i) {
                this._m9[oT](t, i)
            },
            _46: function () {
                return this.bounds
            },
            _g4: function (t, i, n) {
                t /= this[nf] || 1,
                this._ji(t, i, n)
            },
            _ji: function (t, i, e) {
                if (this[ac] && (i === n || null === i)) {
                    var s = this[fT](this[Ca] / 2, this.height / 2);
                    i = s[0] || 0,
                    e = s[1] || 0
                }
                return this._ip.scale(t, [i || 0, e || 0])
            },
            _eg: function (t, i) {
                this._ip.translate([t, i], !0)
            },
            _mqj: function (t, i, n, e) {
                if (n == this[nf] && e !== !1) {
                    var s = this[Na];
                    s != (0 | s) && (t = Math[uo](t * s) / s, i = Math[uo](i * s) / s)
                }
                this._ip[uT]([t, i], n)
            },
            _jk: function (t, i) {
                return this._ji(this._ed, t, i)
            },
            _ho: function (t, i) {
                return this._ji(1 / this._ed, t, i)
            },
            _1l: function () {
                var t = this._46();
                if (!t.isEmpty()) {
                    var i = this[Ca] / t[Ca],
                        n = this[Da] / t[Da],
                        e = Math.min(i, n);
                    return e = Math.max(this._fq, Math.min(this._ft, e)),
                    {
                            scale: e,
                            cx: t.cx,
                            cy: t.cy
                        }
                }
            },
            _ed: 1.3,
            _ft: 10,
            _fq: .1,
            _mqm: !1,
            _6l: function () {},
            _5a: function () {},
            _2m: function () {},
            _e8: function () {
                this[Uw] = null,
                this[kw]._kh()
            },
            _e7: function (t) {
                var i = this._ip,
                    n = this._ik.ratio,
                    e = this.scale,
                    s = i._e5();
                if (!s) {
                        var h = i[__]([t[0], t[1]]);
                        return h[0] *= n,
                        h[1] *= n,
                        n *= e,
                        h[2] = t[2] * n,
                        h[3] = t[3] * n,
                        h
                    }
                var r = new EG,
                    a = i[__]([t[0], t[1]]);
                return r.add(a[0], a[1]),
                a = i[__]([t[0] + t[2], t[1]]),
                r.add(a[0], a[1]),
                a = i[__]([t[0], t[1] + t[3]]),
                r.add(a[0], a[1]),
                a = i[__]([t[0] + t[2], t[1] + t[3]]),
                r.add(a[0], a[1]),
                [r.x * n, r.y * n, r.width * n, r[Da] * n]
            },
            _fn: function (t, n) {
                var e = n._3a(t.x, t.y, t[Ca], t[Da]);
                if (e) {
                    var s = this._ik,
                        h = this[nf] * this[Na],
                        r = this[kw],
                        a = {},
                        o = 1e-6;
                    e.x > o && (a[Bo] = n._4p(0, 0, e.x, n[Da], h)),
                    n[Ca] - e[Ur] > o && (a[Ur] = n._4p(e[Ur], 0, n.width - e[Ur], n[Da], h)),
                    e.y > o && (a.top = n._4p(e.x, 0, e.width, e.y, h)),
                    n[Da] - e.bottom > o && (a[Yr] = n._4p(e.x, e[Yr], e[Ca], n[Da] - e.bottom, h)),
                    Y(a) || r._4c(a);
                    var f = n._hb(t.x, t.y),
                        u = (f[0] - e.x) * h,
                        c = (f[1] - e.y) * h,
                        _ = e.width * h,
                        d = e.height * h;
                    u = Math[uo](u),
                    c = Math[uo](c),
                    _ = Math[uo](_),
                    d = Math[uo](d);
                    var l = this[cT];
                    return l || (l = this[cT] = i[qa](Ga), l.g = l[zf](za)),
                    l[Ca] = _,
                    l[Da] = d,
                    fe(l.g),
                    l.g.drawImage(s, u, c),
                    u = f[0] * h - u,
                    c = f[1] * h - c,
                    {
                            x: u,
                            y: c,
                            canvas: l
                        }
                }
            },
            _l6: function (t, i, n, e) {
                this[kw]._mb(t, i, n, e)
            },
            _fl: function (t) {
                this[kw]._hu(t)
            }
        });
    Object[Br](xY[er], {
            width: {
                get: function () {
                    return this[nT].clientWidth
                }
            },
            height: {
                get: function () {
                    return this._mqe.clientHeight
                }
            },
            rotate: {
                get: function () {
                    return this._ip._e5()
                }
            },
            tx: {
                get: function () {
                    return this._ip._7x()[0]
                }
            },
            ty: {
                get: function () {
                    return this._ip._7x()[1]
                }
            },
            ratio: {
                get: function () {
                    return this._ik ? this._ik[Na] : void 0
                }
            },
            scale: {
                get: function () {
                    return this._ip._fi()
                },
                set: function (t) {
                    this._g4(t)
                }
            },
            renderScale: {
                get: function () {
                    return this[nf] * this.ratio
                }
            },
            uis: {
                get: function () {
                    return this._m9
                }
            },
            length: {
                get: function () {
                    return this._m9[Hh]
                }
            },
            viewportBounds: {
                get: function () {
                    return this[$w].getGlobalBounds()
                }
            }
        });
    var pY, wY = M({
            constructor: function () {
                this._gv = [],
                this._iv = new EG,
                this._gw = Jq ? 20 : 50
            },
            _gw: 20,
            _gv: null,
            _l8: !1,
            _iv: null,
            _kh: function () {
                this._l8 = !1,
                this._gv[Hh] = 0,
                this._viewportClips = null,
                this._iv[Ra]()
            },
            _ht: function () {
                return 0 == this._gv[Hh] && !this[_T]
            },
            _mb: function (t, i, n, e) {
                0 >= n || 0 >= e || this._gv.push([t, i, n, e])
            },
            _hu: function (t) {
                this._mb(t.x, t.y, t[Ca], t[Da])
            },
            _4c: function (t) {
                var i = this._iv;
                for (var n in t) {
                    var e = t[n],
                        s = e[dT]();
                    i.add(s)
                }
                this._viewportClips = t
            },
            _mqn: function (t, i) {
                for (var n = [], e = this._gv, s = 0, h = e[Hh]; h > s; s++) {
                    var r = e[s];
                    t[Dc](r[0], r[1], r[2], r[3]) && (n[tr](r), this._iv.addRect(r[0], r[1], r[2], r[3]))
                }
                this._gv = n,
                this._l8 = i || n.length >= this._gw
            },
            _ez: function (t, i, n, e) {
                if (!this._iv.intersectsRect(t, i, n, e)) return !1;
                if (this._l8) return !0;
                if (this[_T]) {
                    var s = this[_T];
                    for (var h in s) if (s[h].intersects(t, i, n, e)) return !0
                }
                for (var r, a = 0, o = this._gv[Hh]; o > a; a++) if (r = this._gv[a], EG.intersects(t, i, n, e, r[0], r[1], r[2], r[3])) return !0;
                return !1
            },
            _jt: function (t, i) {
                if (this._ht()) return !1;
                if (t[TE](), this._l8) {
                    var n = i([this._iv.x, this._iv.y, this._iv[Ca], this._iv[Da]]);
                    return ue(t, n[0], n[1], n[2], n[3]),
                    void t[Im]()
                }
                if (this._viewportClips) for (var e in this[_T]) {
                    var n = this._viewportClips[e][lT];
                    ue(t, n[0], n[1], n[2], n[3])
                }
                for (var s = this._gv, h = 0, r = s.length; r > h; h++) {
                    var n = i(s[h]);
                    ue(t, n[0], n[1], n[2], n[3])
                }
                t[Im]()
            }
        });
    wY[vT] = function (t, i, n, e) {
            return t instanceof Object && (i = t.y, n = t[Ca], e = t[Da], t = t.x),
            n = V(t + n) - (t = W(t)),
            e = V(i + e) - (i = W(i)),
            [t, i, n, e]
        },
    wY[bT] = W,
    wY._gc = V,
    Mz[gT] = yT,
    Mz[mT] = ET,
    _G[xT] = Mz[mT];
    var TY = M({
            _ir: function () {
                k(this, TY, pT, arguments),
                this[wT]._ir()
            },
            _96: function (t, i) {
                return t = t[Af][Hw] || 0,
                i = i[Af][Hw] || 0,
                t - i
            },
            "super": xY,
            constructor: function (t, n) {
                this._k7 = t,
                N(n) && (n = i[iT](n)),
                n && n.tagName || (n = i[qa](a_)),
                O(this, TY, [n]),
                this[wT] = new uh(this, this._mqe),
                this._gk = [],
                this._k7._7.addListener(this._1q, this),
                this._k7._1g[_d](this._99, this),
                this._k7._10.addListener(this._6a, this),
                this._k7._$o[_d](this._2x, this),
                this._k7._$p[_d](this._3n, this),
                this[TT] = {},
                this._3l(_G.NAVIGATION_TYPE, !0)
            },
            _4x: function (t) {
                k(this, TY, kT, arguments),
                this[wT]._4x(t)
            },
            _gs: function (t) {
                return t.id || (t = this._m9[$d](t)),
                t ? (this._m9[Jh](t), t[dg](), t[hT] && this._fl(t[hT]), void(this._mujBoundsFlag = !0)) : !1
            },
            _gq: function () {
                this._m9.forEach(function (t) {
                    t[dg]()
                }),
                this._m9.clear()
            },
            _es: function (t, i) {
                var n = t.data || t;
                return n._$s && (n._$s = !1, n._h9 = this._51(n, i)),
                n._h9 !== !1
            },
            _51: function (t, i) {
                return this._3i(t, i) ? !this._k7[OT] || this._k7._h9Filter(t, i) !== !1 : !1
            },
            _9q: function (t) {
                return this._k7._3m == eh(t)
            },
            _3i: function (t, i) {
                if (t.visible === !1) return !1;
                if (!(t instanceof DY)) return this._k7._3m != eh(t) ? !1 : !t._di;
                var n = t.fromAgent,
                    e = t.toAgent;
                if (!n || !e) return !1;
                if (n == e && !t.isLooped()) return !1;
                if (t.isBundleEnabled()) {
                        var s = t.getEdgeBundle(!0);
                        if (s && !s._es(t, i)) return !1
                    }
                var h = this._es(n, i),
                    r = this._es(e, i);
                return h && r ? !0 : !1
            },
            _mqf: function (t) {
                return t[ac] ? {
                    x: t.$x + t[jT].x,
                    y: t.$y + t[jT].y,
                    width: t.uiBounds[Ca],
                    height: t[jT].height
                } : void 0
            },
            _30: function (t) {
                var i = this._l4(t);
                if (i) {
                    var n = this[qw](i);
                    if (n) return new EG(n)
                }
            },
            _ex: function (t, i, n) {
                return t[s_](i[0], i[1], n)
            },
            hitTest: function (t, i) {
                var n = k(this, TY, s_, arguments);
                if (n) {
                    t = this.toLogical(t),
                    i = this._8w(i);
                    var e = n.hitTest(t[0], t[1], i, !0);
                    if (e instanceof $Y) return e
                }
                return n
            },
            _3k: function (t) {
                return this.getUIByMouseEvent(t)
            },
            _6w: function () {
                k(this, TY, MT, arguments),
                this[wT]._ib(this[Ca], this.height)
            },
            _l1: 1,
            _muk: null,
            _89: null,
            _7g: null,
            _m9: null,
            _m8: null,
            _ik: null,
            _mqp: null,
            _5s: !1,
            _mq0: !1,
            _ip: null,
            _41: function (t, i) {
                for (var n = this[Ow], e = 0, s = n.length; s > e; e++) if (t[Yh](i, n[e]) === !1) return !1
            },
            _ew: function (t, i) {
                this._m9.forEach(t, i)
            },
            _$x: function (t, i) {
                for (var n = this._muk, e = n.length - 1; e >= 0; e--) if (t[Yh](i, n[e]) === !1) return !1
            },
            _44: function (t, i) {
                this._m9[oT](t, i)
            },
            _36: function (t) {
                k(this, TY, ST, arguments),
                this[IT] = {
                    value: t
                }
            },
            _mss: function () {
                this._4d(!0),
                this[AT] || (this[AT] = !0, this._k7 && this._k7[LT] && this._ip.translateTo([this[Ca] / 2, this[Da] / 2]))
            },
            _f6: function () {
                if (!this._hged && this._5s) {
                    if (this._iringID = null, this._5s = !1, this[ac] && this._k7 && this._k7._$s && (this._k7._$s = !1, this._k7[Vf](function (t) {
                        t[CT](!0)
                    })), k(this, TY, DT, arguments), this._7gChanged) {
                        this._6i && this._6i._in();
                        var t = this[RT][cr],
                            i = this._7gChanged.old;
                        this[RT] = null,
                        this._k7._4a(new LG(this._k7, PT, t, i))
                    }
                    this[IT] && (this[IT] = !1, this._6i && this._6i._36 && this._6i._36(this._viewport[Ca] * this[$w].scale, this[$w][Da] * this._viewport[nf]), this._k7._4a(new LG(this._k7, Lw, this[$w])))
                }
            },
            _gk: null,
            _mu9: function (t) {
                var i = t[Af];
                if (!t._1e && !i._5s && !i._$s) return !1;
                var n = t[_f];
                return n = k(this, TY, rT, arguments) || n
            },
            _8s: function (t) {
                var i = t.$data;
                i.__4h && (i.__4h = !1, t._4h()),
                i[xv] && i._hh() && (t._5j(), i[xv] = !1),
                (t._1e || i._5s) && (i._5s = !1, t.validate())
            },
            _gh: function (t, i) {
                var n = t.ratio;
                t[nf](n, n),
                t[__].apply(t, this._ip.m);
                for (var e = this[NT], s = [], h = 0, r = i.length; r > h; h++) {
                    var a = i[h];
                    a._ir(t, e),
                    a._jg && a._jg[Hh] && s.push(a)
                }
                if (s.length) for (h = 0, r = s.length; r > h; h++) s[h]._8t(t, e)
            },
            render: function (t, i) {
                if (i[Hh]) {
                    if (t[Va](), Jq) try {
                        this._gh(t, i)
                    } catch (n) {} else this._gh(t, i);
                    t[to]()
                }
            },
            _gf: function (t, i, n) {
                t[Va](),
                t[$o](-n.x * i, -n.y * i),
                t.scale(i, i);
                var e, s, h = this._m9._j8[Vh]();
                h = this._hl(h);
                for (var r = [], a = 0, o = h[Hh]; o > a; a++) e = h[a],
                e[Fw] && (s = this._mqf(e), n[Jd](s.x, s.y, s[Ca], s[Da]) && (e._ir(t, i), e._jg && e._jg[Hh] && r[tr](e)));
                if (r[Hh]) for (a = 0, o = r[Hh]; o > a; a++) r[a]._8t(t, i);
                t[to]()
            },
            _16: function () {},
            _1i: function () {
                for (var t, i, n = this._m9._j8, e = new EG, s = n[Hh] - 1; s >= 0; s--) t = n[s],
                t._h9 && (i = t[jT], e[tl](t.$x + i.x, t.$y + i.y, i[Ca], i[Da]));
                var h = this._7g;
                this._7g = e,
                e[ql](h) || this._16(h, e)
            },
            _53: function () {
                this._muk[Hh] = 0,
                this._89 = {}
            },
            _l2: function () {
                this._kh()
            },
            _hg: function () {
                this._kh(),
                this[Bx] = !0,
                this._5s = !1,
                this[wT][Ra](),
                this._82[Hh] = 0,
                this._6i && (this._6i._hg(), delete this._6i)
            },
            _l4: function (t) {
                return this._m9[$d](t.id || t)
            },
            _$j: function (t) {
                return this._ep(t)
            },
            _gd: function (t, i) {
                var n = this[BT](t, i);
                return {
                    x: n[0],
                    y: n[1]
                }
            },
            _ep: function (t, i) {
                var n = this[fT](t, i);
                return {
                    x: n[0],
                    y: n[1]
                }
            },
            _$h: null,
            _3n: function (t) {
                var i = t.source,
                    n = t[vo];
                if (n) if (this._mq0) {
                        var e, s;
                        if ($(n)) for (var h = 0, r = n.length; r > h; h++) s = n[h].id,
                        e = this._m9[$d](s),
                        e && (e.selected = i[lu](s), e[$T]());
                        else {
                            if (s = n.id, e = this._m9[$d](s), !e) return;
                            e[FT] = i.containsById(s),
                            e[$T]()
                        }
                        this._muj()
                    } else {
                        this._$h || (this._$h = {});
                        var e, s;
                        if ($(n)) for (var h = 0, r = n.length; r > h; h++) s = n[h].id,
                        this._$h[s] = !0;
                        else s = n.id,
                        this._$h[s] = !0
                    }
            },
            _k7: null,
            _d7: function (t) {
                var i = t[qT];
                return i ? new i(t, this._k7) : void 0
            },
            _1q: function (t) {
                if (!this._mq0) return !1;
                var i = t[Fo],
                    n = t[cd];
                r_ == n && this._k7.invalidateVisibility(),
                qT == n ? (this._gs(i.id), this._k5(i)) : GT == n && i._hh() && t.value && this._4y(i);
                var e = this._m9.getById(i.id);
                e && e[ac] && e[zT](t) && this[Iw]()
            },
            _3o: function (t) {
                var i = this._l4(t);
                i && (i.invalidateData(), this[Iw]())
            },
            _99: function (t) {
                if (!this._mq0) return !1;
                switch (t[cd]) {
                case qG[Xl]:
                    this._k5(t[vo]);
                    break;
                case qG[rv]:
                    this._ga(t[vo]);
                    break;
                case qG[Zl]:
                    this._hy(t[vo])
                }
            },
            _kh: function () {
                this._mqu = {},
                this._gq(),
                this[Ra]()
            },
            _mqu: null,
            _k5: function (t) {
                var i = this._d7(t);
                i && (this._m9.add(i), this[ac] && (this[TT][t.id] = t), this[Iw]())
            },
            _ga: function (t) {
                if (Array.isArray(t)) {
                    for (var i, n = [], e = 0, s = t.length; s > e; e++) i = t[e].id,
                    n.push(i),
                    delete this[TT][i];
                    t = n
                } else t = t.id,
                delete this[TT][t],
                t = [t];
                t[Vf](function (t) {
                    this._gs(t)
                }, this),
                this._muj()
            },
            _hy: function () {
                this._kh()
            },
            _6a: function (t) {
                return this[ac] ? void(t.source instanceof RY && !this._mqu[t.source.id] && (t[Tl] && (this._3o(t.oldValue), t.oldValue[xv] = !0), t.value && (this._3o(t[cr]), t[cr][xv] = !0), this._4y(t[Fo]))) : !1
            },
            _2x: function (t) {
                return this._mq0 ? void(t[Fo] instanceof RY && !this[TT][t.source.id] && this._4y(t[Fo])) : !1
            },
            _2i: function (t) {
                if (t[HT]) {
                    var i = t[mu](!0);
                    if (!i) return t._edgeBundleInvalidateFlag = !1,
                    void t[YT]();
                    i._f6(this._k7),
                    i._ms2(function (t) {
                        t[YT]()
                    })
                }
            },
            _$v: function (t) {
                var i, n = (this._k7, this._k7.graphModel),
                    e = this._m9,
                    s = [],
                    h = 1;
                if (n[UT](function (t) {
                        return t instanceof DY ? (this._2i(t), void s[tr](t)) : (i = this._d7(t), void(i && (e.add(i), t[WT] = h++)))
                    }, this), e[Hh]) for (var r = e._j8, h = r[Hh] - 1; h >= 0; h--) i = r[h],
                this._3p(i, i[Af], t);
                for (var a, h = 0, o = s[Hh]; o > h; h++) if (a = s[h], i = this._d7(a)) {
                        this._3p(i, a, t),
                        e.add(i);
                        var f = a[pu],
                            u = a[Eu],
                            c = f[WT] || 0;
                        f != u && (c = Math.max(c, u[WT] || 0)),
                        a[WT] = c
                    }
                if (s[Hh] && e._j8[zw](function (t, i) {
                        return t.$data[WT] - i[Af][WT]
                    }), this._$h) {
                        var _ = n.selectionModel;
                        for (var d in this._$h) if (_.containsById(d)) {
                            var i = e[$d](d);
                            i && (i[FT] = !0)
                        }
                        this._$h = null
                    }
                this._6s()
            },
            _mqh: function (t, i) {
                if (t) return this._$v();
                var n = this[aT];
                this._mujBoundsFlag = !1;
                for (var e in this[TT]) {
                    var s = this[TT][e];
                    s instanceof RY ? this._4y(s) : this._50(s)
                }
                this._mqu = {};
                for (var h, r, a = this._m9._j8, o = [], f = a[Hh] - 1; f >= 0; f--) h = a[f],
                r = h[Af],
                r instanceof DY ? (this._2i(r), o.push(h)) : this._3p(h, r, i) && !n && (n = !0);
                if (o[Hh]) for (var f = 0, u = o[Hh]; u > f; f++) h = o[f],
                this._3p(h, h.$data, i) && !n && (n = !0);
                n && this._6s()
            },
            _3p: function (t, i, n) {
                if (i instanceof DY) return i.__4h && (i.__4h = !1, t._4h()),
                this[rT](t, n);
                if (i.__5s && i._hh() && (t._5j(), i[xv] = !1), this[rT](t, n)) {
                    var e = this._4j(i);
                    return e && (e[xv] = !0),
                    i[VT]() && i.forEachEdge(function (t) {
                        t[XT] = !0
                    }, this),
                    !0
                }
            },
            _2t: function (t, i) {
                var n = t[pu],
                    e = t[Eu],
                    s = i[KT](n.id);
                if (n == e) return s;
                var h = i[KT](e.id);
                return Math.max(s, h)
            },
            _2u: function (t, i) {
                var n = this[$_]._gu(t);
                return n ? i.getIndexById(n.id) : 0
            },
            _4y: function (t) {
                var i = this._m9,
                    n = i[$d](t.id);
                if (!n) throw new Error(ZT + t[ur] + JT);
                var s = this._2u(t, i),
                    h = [n];
                t[Uh]() && e(t, function (t) {
                        t instanceof RY && (n = i[$d](t.id), n && h[tr](n))
                    }, this),
                this._4m(i, s, h)
            },
            _50: function (t) {
                var i = this._m9.getById(t.id);
                if (i) {
                    var n = this._2t(t, this._m9);
                    this._m9.setIndexBefore(i, n)
                }
            },
            _4m: function (t, i, n) {
                function e(t) {
                    s.add(t)
                }
                var s = new dG;
                l(n, function (n) {
                    i = t[QT](n, i),
                    n[Af][Cu](e)
                }, this),
                0 != s.length && s.forEach(this._50, this)
            },
            _87: function (t) {
                return t[mu](!0)
            },
            _4j: function (t) {
                var i = we(t);
                return i && i[GT] ? i : null
            },
            _6h: null,
            _6i: null,
            _3l: function (t, i) {
                return i || t != this._6h ? (this._6h = t, this._6i && (this._6i._hg(), delete this._6i), t == Mz.NAVIGATION_SCROLLBAR ? void(this._6i = new oh(this, this[nT])) : t == Mz[gT] ? void(this._6i = new ah(this, this[nT])) : void 0) : !1
            },
            _2m: function (t, i) {
                this._6i && this._6i._in(),
                this._k7._4a(new LG(this._k7, __, t, i))
            },
            _6l: function (t, i) {
                this._k7._4a(new LG(this._k7, nf, t, i))
            },
            _39: function (t, i) {
                this[RT] = {
                    value: t,
                    old: i
                }
            },
            _6n: function () {
                this._6e()
            }
        });
    Object[Br](TY[er], {
            _viewportBounds: {
                get: function () {
                    return this.viewportBounds
                }
            },
            _scale: {
                get: function () {
                    return this[nf]
                },
                set: function (t) {
                    this._g4(t)
                }
            },
            _tx: {
                get: function () {
                    return this.tx
                }
            },
            _ty: {
                get: function () {
                    return this.ty
                }
            },
            graphModel: {
                get: function () {
                    return this._k7._k7Model
                }
            }
        });
    var kY = xY,
        OY = {};
    OY[Vp] = function () {
            return [1, 0, 0, 1, 0, 0]
        },
    OY[Zw] = function (t, i) {
            var n = i[0],
                e = i[1],
                s = i[2],
                h = i[3],
                r = i[4],
                a = i[5],
                o = n * h - e * s;
            return o ? (o = 1 / o, t[0] = h * o, t[1] = -e * o, t[2] = -s * o, t[3] = n * o, t[4] = (s * a - h * r) * o, t[5] = (e * r - n * a) * o, t) : null
        },
    OY[SE] = function (t, i, n) {
            var e = i[0],
                s = i[1],
                h = i[2],
                r = i[3],
                a = i[4],
                o = i[5],
                f = n[0],
                u = n[1],
                c = n[2],
                _ = n[3],
                d = n[4],
                l = n[5];
            return t[0] = e * f + h * u,
            t[1] = s * f + r * u,
            t[2] = e * c + h * _,
            t[3] = s * c + r * _,
            t[4] = e * d + h * l + a,
            t[5] = s * d + r * l + o,
            t
        },
    OY.mul = OY[SE],
    OY[mo] = function (t, i, n) {
            var e = i[0],
                s = i[1],
                h = i[2],
                r = i[3],
                a = i[4],
                o = i[5],
                f = Math.sin(n),
                u = Math.cos(n);
            return t[0] = e * u + h * f,
            t[1] = s * u + r * f,
            t[2] = e * -f + h * u,
            t[3] = s * -f + r * u,
            t[4] = a,
            t[5] = o,
            t
        },
    OY.scale = function (t, i, n) {
            var e = i[0],
                s = i[1],
                h = i[2],
                r = i[3],
                a = i[4],
                o = i[5],
                f = n[0],
                u = n[1];
            return t[0] = e * f,
            t[1] = s * f,
            t[2] = h * u,
            t[3] = r * u,
            t[4] = a,
            t[5] = o,
            t
        },
    OY[$o] = function (t, i, n) {
            var e = i[0],
                s = i[1],
                h = i[2],
                r = i[3],
                a = i[4],
                o = i[5],
                f = n[0],
                u = n[1];
            return t[0] = e,
            t[1] = s,
            t[2] = h,
            t[3] = r,
            t[4] = e * f + h * u + a,
            t[5] = s * f + r * u + o,
            t
        },
    OY[__] = function (t, i) {
            var n = i[0],
                e = i[1];
            return [n * t[0] + e * t[2] + t[4], n * t[1] + e * t[3] + t[5]]
        },
    OY[Vw] = function (t, i) {
            return OY.transform(OY[Zw]([], t), i)
        };
    var jY = Math.PI + Math.PI,
        MY = P,
        SY = M({
            equals: function (t) {
                if (!t || !Array.isArray(t)) return !1;
                for (var i = this.m, n = 0; n < i[Hh];) {
                    if (i[n] != t[n]) return !1;
                    ++n
                }
                return !0
            },
            constructor: function (t) {
                this.m = t || OY[Vp](),
                this.im = []
            },
            listener: null,
            _5s: !0,
            invalidate: function () {
                return this._5s = !0,
                this[tk] && this[ql](this._msackM) ? !1 : (this[Bl] && this.listener({
                    target: this,
                    kind: Sw
                }), this[tk] = this.m[Vh](), this)
            },
            validate: function () {
                return this._5s = !1,
                OY.invert(this.im, this.m),
                this
            },
            translate: function (t, i) {
                return MY(t) && (t = [arguments[0], arguments[1]], i = arguments[2]),
                i !== !1 ? (this.m[4] += t[0], this.m[5] += t[1], this[Sw]()) : (OY[$o](this.m, this.m, t), this[Sw]())
            },
            translateTo: function (t, i) {
                return MY(t) && (t = [arguments[0], arguments[1]], i = arguments[2]),
                i && (i /= this._fi(), OY[nf](this.m, this.m, [i, i])),
                this.m[4] = t[0],
                this.m[5] = t[1],
                this.invalidate()
            },
            scale: function (t, i) {
                return Er == typeof t && (t = [t, t]),
                i ? (OY[$o](this.m, this.m, i), OY.scale(this.m, this.m, t), OY.translate(this.m, this.m, ce(i))) : OY[nf](this.m, this.m, t),
                this.invalidate()
            },
            rotate: function (t, i) {
                return i ? (OY[$o](this.m, this.m, i), OY[mo](this.m, this.m, t), OY[$o](this.m, this.m, ce(i))) : OY.rotate(this.m, this.m, t),
                this[Sw]()
            },
            transform: function (t) {
                return OY.transform(this.m, t)
            },
            reverseTransform: function (t) {
                return OY[__](this._49(), t)
            },
            toString: function () {
                return Jw + this.m[Qf](Ar) + Lr
            },
            _49: function () {
                return this._5s && this.validate(),
                this.im
            },
            _e6: function () {
                var t = this.m[0],
                    i = this.m[1],
                    n = this.m[2],
                    e = this.m[3];
                return [Math.sqrt(t * t + n * n), Math[eo](i * i + e * e)]
            },
            _fi: function () {
                var t = this.m[0],
                    i = this.m[2];
                return Math[eo](t * t + i * i)
            },
            _7x: function () {
                return [this.m[4], this.m[5]]
            },
            _muf: function () {
                var t = this.m[0],
                    i = this.m[1],
                    n = this.m[2],
                    e = this.m[3];
                return [_e(Math[zr](i, e)), _e(Math.atan2(-n, t))]
            },
            _e5: function () {
                return _e(Math[zr](this.m[1], this.m[3]))
            }
        }),
        IY = M({
            constructor: function () {},
            x: 0,
            y: 0,
            width: 0,
            height: 0,
            rotate: 0,
            set: function (t, i, n, e, s, h) {
                return this.x = t,
                this.y = i,
                this.width = n,
                this[Da] = e,
                this[mo] = s,
                this[Kd] = Math.cos(s),
                this[Hr] = Math.sin(s),
                this.scale = h,
                this[ik] = null,
                this
            },
            _hb: function (t, i) {
                return t -= this.x,
                i -= this.y,
                this[mo] ? ve(t, i, this[Hr], this[Kd]) : [t, i]
            },
            _7v: function (t) {
                var i = new EG;
                return i.add(this._hb(t.x, t.y)),
                i.add(this._hb(t.x + t.width, t.y)),
                i.add(this._hb(t.x, t.y + t.height)),
                i.add(this._hb(t.x + t[Ca], t.y + t[Da])),
                i
            },
            _ff: function (t, i) {
                if (this[mo]) {
                    var n = ve(t, i, Math.sin(-this[mo]), Math.cos(-this[mo]));
                    t = n[0],
                    i = n[1]
                }
                return [this.x + t, this.y + i]
            },
            _5i: function (t, i) {
                var n = this._hb(t, i);
                return t = n[0],
                i = n[1],
                t >= 0 && i >= 0 && t <= this[Ca] && i <= this.height
            },
            intersects: function (t, i, n, e) {
                if (!this.rotate) return EG.intersects(this.x, this.y, this[Ca], this[Da], t, i, n, e);
                if (!n || !e) return this._5i(t, i);
                var s = this.getGlobalBounds();
                if (!s[Dc](t, i, n, e)) return !1;
                for (var h = s[xa], r = 0; r < h.length;) {
                    var a = h[r];
                    if (EG[Qd](t, i, n, e, a[0], a[1])) return !0;
                    r++
                }
                var o = [
                    [t, i],
                    [t + n, i],
                    [t, i + e],
                    [t + n, i + e]
                ];
                for (r = 0; r < o[Hh];) {
                    var a = o[r];
                    if (this._5i(a[0], a[1])) return !0;
                    r++
                }
                return le(h, o)
            },
            getGlobalBounds: function () {
                return this[ik] || (this[ik] = this._70(0, 0, this[Ca], this[Da])),
                this[ik]
            },
            _70: function (t, i, n, e) {
                if (!this[mo]) return new EG(this.x + t, this.y + i, n, e);
                var s = [],
                    h = new EG,
                    r = this._ff(t, i);
                return s[tr](r),
                h.add(r[0], r[1]),
                r = this._ff(t + n, i),
                s[tr](r),
                h.add(r[0], r[1]),
                r = this._ff(t, i + e),
                s[tr](r),
                h.add(r[0], r[1]),
                r = this._ff(t + n, i + e),
                s[tr](r),
                h.add(r[0], r[1]),
                h[xa] = s,
                h
            },
            _4p: function (t, i, n, e, s) {
                var h;
                if (this[mo]) {
                    var r = this._ff(t, i);
                    h = (new IY).set(r[0], r[1], n, e, this[mo], this[nf])
                } else h = (new IY).set(this.x + t, this.y + i, n, e, 0, this[nf]);
                return h[lT] = [Math.round(s * t), Math[uo](s * i), Math[uo](s * n), Math[uo](s * e)],
                h
            },
            _3a: function (t, i, n, e) {
                if (!this.rotate) {
                    var s = EG[el](this.x, this.y, this[Ca], this.height, t, i, n, e);
                    return s && s.offset(-this.x, -this.y),
                    s
                }
                var h = this._hb(t, i);
                return t = h[0],
                i = h[1],
                EG[el](0, 0, this[Ca], this.height, h[0], h[1], n, e)
            },
            equals: function (t) {
                return this.x == t.x && this.y == t.y && this.width == t.width && this[Da] == t[Da] && this[mo] == t.rotate
            },
            toString: function () {
                return this.x + Ar + this.y + Ar + this.width + Ar + this[Da] + Ar + this[mo]
            },
            toJSON: function () {
                return {
                    x: this.x,
                    y: this.y,
                    width: this[Ca],
                    height: this[Da],
                    rotate: this.rotate,
                    scale: this[nf]
                }
            }
        }),
        AY = {
            setStyle: bi,
            setStyles: li,
            addRule: gi,
            pre: VG
        },
        LY = function (t, i, n, e) {
            this[Fo] = t,
            this[cd] = i,
            this.oldValue = e,
            this[cr] = n,
            this[kl] = Mz.PROPERTY_TYPE_STYLE
        };
    p(LY, LG);
    var CY = function (t) {
            this.id = ++Xq,
            this[tv] = {},
            this._it = {},
            t && (this.$name = t)
        };
    CY[er] = {
            _it: null,
            getStyle: function (t) {
                return this._it[t]
            },
            setStyle: function (t, i) {
                var n = this._it[t];
                return n === i || n && i && n[ql] && n[ql](i) ? !1 : this[iv](t, i, new LY(this, t, i, n), this._it)
            },
            putStyles: function (t, i) {
                for (var n in t) {
                    var e = t[n];
                    i ? this._it[n] = e : this.setStyle(n, e)
                }
            },
            _$s: !0,
            invalidateVisibility: function (t) {
                this._$s = !0,
                t || (this instanceof RY && this.hasEdge() && this.forEachEdge(function (t) {
                    t._$s = !0
                }), this._hh() && this[Uh]() && this[Ru](function (t) {
                    t[CT]()
                }))
            },
            onParentChanged: function () {
                this[CT]()
            },
            _hh: function () {
                return !this._3x && this instanceof BY
            },
            invalidate: function () {
                this[or](new AG(this, nk, Sw))
            },
            _msu: null,
            addUI: function (t, i) {
                if (this._msu || (this._msu = new dG), t.id || (t.id = ++Xq), this[ek][lu](t.id)) return !1;
                var n = {
                    id: t.id,
                    ui: t,
                    bindingProperties: i
                };
                this[ek].add(n);
                var e = new AG(this, nk, Kl, n);
                return this.onEvent(e)
            },
            removeUI: function (t) {
                if (!this[ek]) return !1;
                var i = this._msu[$d](t.id);
                return i ? (this[ek].remove(i), void this[or](new AG(this, nk, Jh, i))) : !1
            },
            toString: function () {
                return this.$name || this.id
            },
            type: sk,
            _3x: !1,
            _h9: !0,
            inGroup: function (t) {
                var i = we(this);
                return i == t || i && t instanceof BY && i.isDescendantOf(t)
            }
        },
    p(CY, GG),
    I(CY[er], [qT, ur, Hw, hk]),
    Z(CY[er], {
            enableSubNetwork: {
                get: function () {
                    return this._3x
                },
                set: function (t) {
                    if (this._3x != t) {
                        var i = this._3x;
                        this._3x = t,
                        this instanceof RY && this._$z(),
                        this[or](new LG(this, r_, t, i))
                    }
                }
            },
            bindingUIs: {
                get: function () {
                    return this[ek]
                }
            },
            styles: {
                get: function () {
                    return this._it
                },
                set: function (t) {
                    if (this._it != t) {
                        for (var i in this._it) i in t || (t[i] = n);
                        this[rk](t),
                        this._it = t
                    }
                }
            }
        }),
    jz.findGroup = we;
    var DY = function (t, i, n) {
            this.id = ++Xq,
            this[tv] = {},
            this._it = {},
            n && (this[ak] = n),
            this[Du] = t,
            this.$to = i,
            this[ok]()
        };
    DY[er] = {
            $uiClass: vs,
            _jp: null,
            _i2: null,
            _jr: null,
            _i4: null,
            _e3: !1,
            type: fk,
            otherNode: function (t) {
                return t == this.from ? this.to : t == this.to ? this[ku] : void 0
            },
            connect: function () {
                if (this._e3) return !1;
                if (!this[Du] || !this.$to) return !1;
                if (this._e3 = !0, this[Du] == this.$to) return void this[Du]._hm(this);
                Ce(this.$to, this),
                Ae(this[Du], this),
                ge(this[Du], this, this.$to);
                var t = this[pu],
                    i = this.toAgent;
                if (t != i) {
                        var n;
                        this.$from._di && (me(t, this, i), n = !0),
                        this.$to._di && (xe(i, this, t), n = !0),
                        n && ge(t, this, i)
                    }
            },
            disconnect: function () {
                if (!this._e3) return !1;
                if (this._e3 = !1, this.$from == this.$to) return void this[Du][uk](this);
                Le(this[Du], this),
                De(this.$to, this),
                ye(this[Du], this, this.$to);
                var t = this[pu],
                    i = this[Eu];
                if (t != i) {
                        var n;
                        this.$from._di && (Ee(t, this, i), n = !0),
                        this.$to._di && (pe(i, this, t), n = !0),
                        n && ye(t, this, i)
                    }
            },
            isConnected: function () {
                return this._e3
            },
            isInvalid: function () {
                return !this[Du] || !this.$to
            },
            isLooped: function () {
                return this[Du] == this.$to
            },
            getEdgeBundle: function (t) {
                return t ? this._2l() : this[Ou]() ? this.$from._3z : this[Du][mu](this.$to)
            },
            hasEdgeBundle: function () {
                var t = this[mu](!0);
                return t && t[ck][Hh] > 1
            },
            _2l: function () {
                var t = this[pu],
                    i = this.toAgent;
                return t == i ? this[Du]._di || this.$to._di ? null : this[Du]._3z : this.fromAgent[mu](this[Eu])
            },
            _9d: null,
            hasPathSegments: function () {
                return this._9d && !this._9d.isEmpty()
            },
            isBundleEnabled: function () {
                return this[_k] && !this.hasPathSegments()
            },
            firePathChange: function (t) {
                this[or](new LG(this, dk, t))
            },
            addPathSegment: function (t, i, n) {
                var e = new fH(i || sH, t);
                this._9d || (this._9d = new dG),
                this._9d.add(e, n),
                this[lk](e)
            },
            addPathSegement: function () {
                return jz.log('change "edge.addPathSegement(...)" to "edge.addPathSegment(...)"'),
                this[vk][nr](this, arguments)
            },
            removePathSegmentByIndex: function (t) {
                if (!this._9d) return !1;
                var i = this._9d[Nd](t);
                i && (this._9d[Jh](i), this[lk](i))
            },
            removePathSegment: function (t) {
                return this._9d ? (this._9d[Jh](t), void this.firePathChange(t)) : !1
            },
            movePathSegment: function (t, i, n) {
                if (!this._9d) return !1;
                if (t = t || 0, i = i || 0, jz[bk](n)) {
                    var e = this._9d[Nd](n);
                    return e ? (e[Ag](t, i), void this.firePathChange()) : !1
                }
                l(function (n) {
                    n.move(t, i)
                }),
                this[lk]()
            },
            move: function (t, i) {
                return this._9d ? (this._9d.forEach(function (n) {
                    n.move(t, i)
                }, this), void this[lk]()) : !1
            },
            validateEdgeBundle: function () {}
        },
    p(DY, CY),
    Z(DY.prototype, {
            pathSegments: {
                get: function () {
                    return this._9d
                },
                set: function (t) {
                    jz[wr](t) && (t = new dG(t)),
                    this._9d = t,
                    this[lk]()
                }
            },
            from: {
                get: function () {
                    return this[Du]
                },
                set: function (t) {
                    if (this[Du] != t) {
                        var i = new LG(this, ku, t, this[Du]);
                        this[$l](i) !== !1 && (this[yv](), this[Du] = t, this.connect(), this[or](i))
                    }
                }
            },
            to: {
                get: function () {
                    return this.$to
                },
                set: function (t) {
                    if (this.$to != t) {
                        var i = new LG(this, gk, t, this.$to);
                        this.beforeEvent(i) !== !1 && (this[yv](), this.$to = t, this.connect(), this[or](i))
                    }
                }
            },
            fromAgent: {
                get: function () {
                    return this[Du] ? this.$from._di || this[Du] : null
                }
            },
            toAgent: {
                get: function () {
                    return this.$to ? this.$to._di || this.$to : null
                }
            }
        }),
    I(DY[er], [Wc, {
            name: _k,
            value: !0
        },
        Zc]);
    var RY = function (t, i, n) {
            2 == arguments[Hh] && P(t) && (n = i, i = t, t = null),
            this.id = ++Xq,
            this[tv] = {},
            this._it = {},
            t && (this.$name = t),
            this[rc] = yk,
            this[gf] = pG[ll],
            this.$location = {
                x: i || 0,
                y: n || 0
            },
            this._linkedNodes = {}
        };
    RY.prototype = {
            $uiClass: bs,
            _di: null,
            forEachEdge: function (t, i, n) {
                return !n && this._k8 && this._k8[Vf](t, i) === !1 ? !1 : Pe(this, t, i)
            },
            forEachOutEdge: function (t, i) {
                return Ne(this, t, i)
            },
            forEachInEdge: function (t, i) {
                return Be(this, t, i)
            },
            getEdges: function () {
                var t = [];
                return this.forEachEdge(function (i) {
                    t[tr](i)
                }),
                t
            },
            _hr: null,
            _g2: null,
            _j2: null,
            _hq: null,
            _mqk: 0,
            _8n: 0,
            hasInEdge: function () {
                return null != this._hr
            },
            hasOutEdge: function () {
                return null != this._g2
            },
            hasEdge: function () {
                return null != this._hr || null != this._g2 || this[mk]()
            },
            linkedWith: function (t) {
                return t[ku] == this || t.to == this || t[pu] == this || t[Eu] == this
            },
            hasEdgeWith: function (t) {
                var i = this[mu](t);
                return i && i.edges.length > 0
            },
            _k8: null,
            _3z: null,
            hasLoops: function () {
                return this._k8 && this._k8.length > 0
            },
            _hm: function (t) {
                return this._k8 || (this._k8 = new dG, this._3z = new eW(this, this, this._k8)),
                this._3z._hw(t)
            },
            _mub: function (t) {
                return this._3z ? this._3z[xu](t) : void 0
            },
            getEdgeBundle: function (t) {
                return t == this ? this._3z : this._linkedNodes[t.id] || t[Ek][this.id]
            },
            _73: function () {
                return this._8m && this._8m[Hh]
            },
            _57: function () {
                return this._7l && this._7l.length
            },
            _8j: function () {
                return this._73() || this._57()
            },
            _7l: null,
            _8m: null,
            _msq: function () {
                var t = this._di,
                    i = be(this);
                if (t != i) {
                        var n = Re(this);
                        this._8p(i),
                        n[Vf](function (t) {
                            var i = t[pu],
                                n = t[Eu],
                                t = t[hc],
                                e = t[Du]._di,
                                s = t.$to._di;
                            i != n && (i && Ee(i, t, n || t.$to), n && pe(n, t, i || t[Du])),
                            e != s && (e && me(e, t, s || t.$to), s && xe(s, t, e || t[Du]), ge(e || t[Du], t, s || t.$to))
                        }, this)
                    }
            },
            onParentChanged: function () {
                jz[xk](this, RY, nv, arguments),
                this[pk]()
            },
            _7k: null,
            _$z: function () {
                var t;
                if (this._3x ? t = null : (t = this._di, t || this._g6 !== !1 || (t = this)), this._7k == t) return !1;
                if (this._7k = t, this._f5 && this._f5._j8.length) for (var i, n = this._f5._j8, e = 0, s = n[Hh]; s > e; e++) i = n[e],
                i instanceof RY && i._8p(t)
            },
            setLocation: function (t, i) {
                if (this.$location && this[wk].x == t && this[wk].y == i) return !1;
                var n;
                n = this[wk] ? {
                    x: this[wk].x,
                    y: this[wk].y
                } : this[wk];
                var e = new LG(this, Tk, n, {
                    x: t,
                    y: i
                });
                return this[$l](e) === !1 ? !1 : (this[wk] ? (this[wk].x = t, this[wk].y = i) : this.$location = new bG(t, i), this.onEvent(e), !0)
            },
            _dh: null,
            addFollower: function (t) {
                return null == t ? !1 : t[kk] = this
            },
            removeFollower: function (t) {
                return this._dh && this._dh.contains(t) ? t[kk] = null : !1
            },
            hasFollowers: function () {
                return this._dh && !this._dh.isEmpty()
            },
            toFollowers: function () {
                return this[Ok]() ? this._dh[ov]() : null
            },
            clearFollowers: function () {
                this[Ok]() && (this[jk](), l(this.toFollowers(), function (t) {
                    t[kk] = null
                }))
            },
            getFollowerIndex: function (t) {
                return this._dh && this._dh[i_](t) ? this._dh.indexOf(t) : -1
            },
            setFollowerIndex: function (t, i) {
                return this._dh && this._dh[i_](t) ? void this._dh[Au](t, i) : -1
            },
            getFollowerCount: function () {
                return this._dh ? this._dh.length : 0
            },
            _8l: function () {
                return this._dh ? this._dh : (this._dh = new dG, this._dh)
            },
            isFollow: function (t) {
                if (!t || !this[Mk]) return !1;
                for (var i = this[Mk]; i;) {
                    if (i == t) return !0;
                    i = i[Mk]
                }
                return !1
            },
            _8p: function (t) {
                return t == this._di ? !1 : (this._di = t, this[CT](), void this._$z())
            },
            type: Sk
        },
    p(RY, CY),
    Z(RY.prototype, {
            loops: {
                get: function () {
                    return this._k8
                }
            },
            edgeCount: {
                get: function () {
                    return this[wu] + this._8n
                }
            },
            agentNode: {
                get: function () {
                    return this._di || this
                }
            },
            host: {
                set: function (t) {
                    if (this == t || t == this[Mk]) return !1;
                    var i = new LG(this, kk, this[Mk], t);
                    if (!1 === this[$l](i)) return !1;
                    var n = null,
                        e = null,
                        s = this._host;
                    if (null != t && (n = new LG(t, Ik, null, this), !1 === t[$l](n))) return !1;
                    if (null != s && (e = new LG(s, Ak, null, this), !1 === s.beforeEvent(e))) return !1;
                    if (this._host = t, null != t) {
                            var h = t._8l();
                            h.add(this)
                        }
                    if (null != s) {
                            var h = s._8l();
                            h[Jh](this)
                        }
                    return this[or](i),
                    null != t && t[or](n),
                    null != s && s[or](e),
                    !0
                },
                get: function () {
                    return this[Mk]
                }
            }
        }),
    I(RY[er], [Tk, Aw, Gm, mo, Lk]),
    Z(RY[er], {
            x: {
                get: function () {
                    return this[Tk].x
                },
                set: function (t) {
                    t != this[Tk].x && (this[Tk] = new bG(t, this.location.y))
                }
            },
            y: {
                get: function () {
                    return this[Tk].y
                },
                set: function (t) {
                    t != this.location.y && (this[Tk] = new bG(this[Tk].x, t))
                }
            }
        });
    var PY = function (t, i) {
            t instanceof cH && (i = t, t = n),
            w(this, PY, [t]),
            this[Ck] = i || new cH,
            this.anchorPosition = null,
            this[qT] = fh,
            _G.SHAPENODE_STYLES || (_G[Dk] = {}, _G[Dk][qY[Rk]] = !1),
            this[rk](_G.SHAPENODE_STYLES)
        };
    PY[er] = {
            $uiClass: fh,
            type: Pk,
            moveTo: function (t, i) {
                this[Ck][Pu](t, i),
                this[lk]()
            },
            lineTo: function (t, i) {
                this[Ck].lineTo(t, i),
                this[lk]()
            },
            quadTo: function (t, i, n, e) {
                this.path[Bu](t, i, n, e),
                this.firePathChange()
            },
            curveTo: function (t, i, n, e, s, h) {
                this[Ck][$u](t, i, n, e, s, h),
                this.firePathChange()
            },
            arcTo: function (t, i, n, e, s) {
                this[Ck][lm](t, i, n, e, s),
                this[lk]()
            },
            closePath: function () {
                this[Ck][sf](),
                this[lk]()
            },
            clear: function () {
                this[Ck][Ra](),
                this[lk]()
            },
            removePathSegmentByIndex: function (t) {
                this[Ck].removePathSegment(t) !== !1 && this[lk]()
            },
            firePathChange: function () {
                this[Ck]._5s = !0,
                this[or](new LG(this, dk))
            }
        },
    p(PY, RY),
    Z(PY[er], {
            path: {
                get: function () {
                    return this.image
                },
                set: function (t) {
                    this.image = t
                }
            },
            pathSegments: {
                get: function () {
                    return this.path[Nk]
                },
                set: function (t) {
                    this[Ck].segments = t || [],
                    this[lk]()
                }
            },
            length: {
                get: function () {
                    return this[Ck][Hh]
                }
            }
        }),
    jz[Bk] = PY;
    var NY = {
            _jc: {},
            register: function (t, i) {
                NY._jc[t] = i
            },
            getShape: function (t, i, e, s, h, r) {
                s === n && (s = i, h = e, i = 0, e = 0),
                s || (s = 50),
                h || (h = 50);
                var a = NY._jc[t];
                return a ? a[$k] instanceof Function ? a.generator(i, e, s, h, r) : a : void 0
            },
            getRect: function (t, i, n, e, s, h, r) {
                return t instanceof Object && Ca in t && (i = t.y, n = t[Ca], e = t.height, s = t.rx, h = t.ry, r = t[Ck], t = t.x),
                $e(r || new cH, t, i, n, e, s, h)
            },
            getAllShapes: function (t, i, n, e, s) {
                var h = {};
                for (var r in NY._jc) {
                    var a = NY[Fc](r, t, i, n, e, s);
                    a && (h[r] = a)
                }
                return h
            },
            createRegularShape: function (t, i, n, e, s) {
                return Ue(t, i, n, e, s)
            }
        };
    hs(),
    rs.prototype = {
            type: Fk
        },
    p(rs, PY),
    jz.Bus = rs,
    as[er] = {
            _gu: function (t) {
                var i, n = t._je;
                i = n ? n._f5 : this[bv];
                var e = i[zo](t);
                if (0 > e) throw new Error(pv + t + "' not exist in the box");
                for (; e >= 0;) {
                    if (0 == e) return n instanceof RY ? n : null;
                    e -= 1;
                    var h = i[Nd](e);
                    if (h = s(h)) return h
                }
                return null
            },
            forEachNode: function (t, i) {
                this.forEach(function (n) {
                    return n instanceof RY && t[Yh](i, n) === !1 ? !1 : void 0
                })
            },
            _3m: null
        },
    p(as, HG),
    Z(as[er], {
            propertyChangeDispatcher: {
                get: function () {
                    return this._$u
                }
            },
            currentSubNetwork: {
                get: function () {
                    return this._3m
                },
                set: function (t) {
                    if (t && !t[r_] && (t = null), this._3m != t) {
                        var i = this._3m;
                        this._3m = t,
                        this._$u[or](new LG(this, qk, t, i))
                    }
                }
            }
        }),
    _G.GROUP_TYPE = Mz.GROUP_TYPE_RECT,
    _G[Gk] = 5,
    _G[zk] = !0,
    _G.GROUP_MIN_SIZE = {
            width: 60,
            height: 60
        };
    var BY = function (t, i, e) {
            w(this, BY, arguments),
            (i === n || e === n) && (this[wk][Hk] = !0),
            this.$groupType = _G[Yk],
            this.$padding = _G[Gk],
            this[rc] = lH.group,
            this[Uk] = _G[Wk],
            this[GT] = _G[zk]
        };
    BY[er] = {
            type: Vk,
            $uiClass: sh,
            _9s: function () {
                return !this._g6 && !this._di
            },
            forEachOutEdge: function (t, i, n) {
                return Ne(this, t, i) === !1 ? !1 : !n && this._9s() && this._7l ? this._7l[Vf](t, i) : void 0
            },
            forEachInEdge: function (t, i, n) {
                return Be(this, t, i) === !1 ? !1 : !n && this._9s() && this._8m ? this._8m.forEach(t, i) : void 0
            },
            forEachEdge: function (t, i, n) {
                return T(this, BY, Cu, arguments) === !1 ? !1 : n || n || !this._9s() ? void 0 : this._8m && this._8m[Vf](t, i) === !1 ? !1 : this._7l ? this._7l.forEach(t, i) : void 0
            },
            hasInEdge: function (t) {
                return t ? null != this._hr : null != this._hr || this._73()
            },
            hasOutEdge: function (t) {
                return t ? null != this._g2 : null != this._g2 || this._57()
            },
            hasEdge: function (t) {
                return t ? null != this._hr || null != this._g2 : null != this._hr || null != this._g2 || this._8j()
            }
        },
    p(BY, RY),
    Z(BY.prototype, {
            expanded: {
                get: function () {
                    return this._g6
                },
                set: function (t) {
                    if (this._g6 != t) {
                        var i = new LG(this, GT, t, this._g6);
                        this[$l](i) !== !1 && (this._g6 = t, this._$z(), this[or](i), this._di || os[Yh](this))
                    }
                }
            }
        }),
    I(BY[er], [Xk, Kk, No, Zk]),
    jz[Jk] = BY,
    fs.prototype[yo] = Qk,
    p(fs, RY),
    jz[tO] = fs;
    var $Y = function (t) {
            this._iv = new EG,
            this._7i = new EG,
            this._fa = new EG,
            this.id = ++Xq,
            t && (this.data = t)
        };
    $Y[er] = {
            invalidate: function () {
                this[iO]()
            },
            _1e: !0,
            _iv: null,
            _7i: null,
            _fa: null,
            _mq0: !1,
            _jd: 1,
            _j6: 1,
            _h9: !0,
            _7o: 0,
            _60: 0,
            _je: null,
            _mq2: null,
            borderColor: nO,
            borderLineDash: null,
            borderLineDashOffset: null,
            syncSelection: !0,
            syncSelectionStyles: !0,
            _17: function () {
                this[eO] = fi(this[Lk], this._7o, this._60)
            },
            setMeasuredBounds: function (t, i, n, e) {
                return t instanceof Object && (n = t.x, e = t.y, i = t[Da], t = t.width),
                this._iv[Ca] == t && this._iv[Da] == i && this._iv.x == n && this._iv.y == e ? !1 : void this._iv.set(n || 0, e || 0, t || 0, i || 0)
            },
            initialize: function () {},
            measure: function () {},
            draw: function () {},
            _7h: function (t, i, n) {
                n.selectionType == Mz[om] ? (t.shadowColor = n[cE], t.shadowBlur = n[uE] * i, t[Dm] = (n[_E] || 0) * i, t[Rm] = (n[dE] || 0) * i) : this._1x(t, i, n)
            },
            _1x: function (t, i, n) {
                var e = n[vE] || 0;
                n[sO] && (t.fillStyle = n.selectionBackgroundColor, t[hO](this._7i.x - e / 2, this._7i.y - e / 2, this._7i.width + e, this._7i.height + e)),
                t.strokeStyle = n.selectionColor,
                t[bo] = e,
                t[rO](this._7i.x - e / 2, this._7i.y - e / 2, this._7i[Ca] + e, this._7i.height + e)
            },
            _ir: function (t, i, n, e) {
                if (!this._h9) return !1;
                if (this[aO] || (n = this[FT]), (n && !this[oO] || !e) && (e = this), t[Va](), 1 != this[fO] && (t[Ix] = this[fO]), t.translate(this.$x, this.$y), this[uf] && this[cf] && t[mo](this.$_hostRotate), (this[uO] || this[cO]) && t[$o](this.offsetX, this[cO]), this[af] && t[mo](this[af]), this[rf] && this[Oo] && t.translate(-this[Oo].x, -this[Oo].y), this[A_] && (t[A_] = this[A_], t.shadowBlur = this[C_] * i, t[Dm] = this[Dm] * i, t.shadowOffsetY = this[Rm] * i), n && e.selectionType == Mz[_O] && (this._1x(t, i, e), n = !1), this._$q() && this[xf] && !this[xf][dO]) {
                    this[xf].validate();
                    var s = {
                        lineWidth: this.$border,
                        strokeStyle: this.borderColor,
                        lineDash: this.borderLineDash,
                        lineDashOffset: this[lO],
                        fillColor: this[vO],
                        fillGradient: this[mf],
                        lineCap: Ky,
                        lineJoin: uo
                    };
                    this[xf][Ao](t, i, s, n, e),
                    n = !1,
                    t.shadowColor = yE
                }
                t[TE](),
                this.draw(t, i, n, e),
                t[to]()
            },
            invalidateData: function () {
                this[bO] = !0,
                this[_f] = !0,
                this._1e = !0
            },
            invalidateSize: function () {
                this.$invalidateSize = !0,
                this._1e = !0
            },
            invalidateRender: function () {
                this._1e = !0
            },
            _51: function () {},
            _$q: function () {
                return this.$backgroundColor || this[gO] || this[bf]
            },
            _3w: function () {
                return this[vO] || this.$backgroundGradient
            },
            doValidate: function () {
                return this[bO] && (this[bO] = !1, this.measure() !== !1 && (this.$invalidateSize = !0)),
                this[_f] && this.validateSize && this.validateSize(),
                Gn[Yh](this) ? (this.$invalidateRotate = !0, this.onBoundsChanged && this[yO](), !0) : this[mO] ? (this[Dx] = !0, this[mO] = !1, !0) : void 0
            },
            validate: function () {
                var t = this._h9;
                return this.$invalidateVisibility && (this[EO] = !1, this._h9 = this.$visible, !this._h9 || (this[Af] || this[xO]) && this._51() !== !1 || (this._h9 = !1)),
                this._h9 ? (this._1e = !1, this[ac] || (this[pO](), this._mq0 = !0), this[wO]()) : t != this._h9
            },
            _hb: function (t, i) {
                return t -= this.$x,
                i -= this.$y,
                qn.call(this, {
                    x: t,
                    y: i
                })
            },
            hitTest: function (t, i, n, e) {
                if (t -= this.$x, i -= this.$y, !this._fa.intersectsPoint(t, i, n)) return !1;
                var s = qn[Yh](this, {
                    x: t,
                    y: i
                });
                return t = s.x,
                i = s.y,
                !e && this._$q() && this[xf] && this[xf][s_](t, i, n, !1, this.$border, this[vO] || this[gO]) ? !0 : this[TO](t, i, n)
            },
            doHitTest: function (t, i, n) {
                return this._iv.intersectsPoint(t, i, n)
            },
            hitTestByBounds: function (t, i, n, e) {
                var s = this._hb(t, i);
                return !e && this._$q() && this[xf] && this[xf][s_](t, i, n, !1, this.$border, this[vO] || this[gO]) ? !0 : this._iv[Qd](s.x, s.y, n)
            },
            onDataChanged: function () {
                this.$invalidateData = !0,
                this._1e = !0,
                this[EO] = !0
            },
            getBounds: function () {
                var t = this._fa.clone();
                return t.offset(this.x, this.y),
                this[yu] && (this[yu][mo] && Mi(t, this[yu].rotate, t), t.offset(this[yu].x || 0, this[yu].y || 0)),
                t
            },
            destroy: function () {
                this[Bx] = !0
            },
            _da: !1
        },
    Z($Y[er], {
            originalBounds: {
                get: function () {
                    return this._iv
                }
            },
            data: {
                get: function () {
                    return this[Af]
                },
                set: function (t) {
                    if (this[Af] != t) {
                        var i = this[Af];
                        this.$data = t,
                        this[kO](t, i)
                    }
                }
            },
            parent: {
                get: function () {
                    return this._je
                }
            },
            showOnTop: {
                get: function () {
                    return this._da
                },
                set: function (t) {
                    t != this._da && (this._da = t, this._1e = !0, this._je && this._je._9y && this._je._9y(this))
                }
            }
        }),
    cs($Y[er], {
            visible: {
                value: !0,
                validateFlags: [OO, jO]
            },
            showEmpty: {
                validateFlags: [OO]
            },
            anchorPosition: {
                value: pG[ll],
                validateFlags: [MO, jO]
            },
            position: {
                value: pG[ll],
                validateFlags: [jO]
            },
            offsetX: {
                value: 0,
                validateFlags: [jO]
            },
            offsetY: {
                value: 0,
                validateFlags: [jO]
            },
            layoutByAnchorPoint: {
                value: !0,
                validateFlags: [qg, MO, jO]
            },
            padding: {
                value: 0,
                validateFlags: [qg]
            },
            border: {
                value: 0,
                validateFlags: [qg]
            },
            borderRadius: {
                value: _G[fm]
            },
            showPointer: {
                value: !1,
                validateFlags: [qg]
            },
            pointerX: {
                value: 0,
                validateFlags: [qg]
            },
            pointerY: {
                value: 0,
                validateFlags: [qg]
            },
            pointerWidth: {
                value: _G[um]
            },
            backgroundColor: {
                validateFlags: [qg]
            },
            backgroundGradient: {
                validateFlags: [qg, SO]
            },
            selected: {
                value: !1,
                validateFlags: [qg]
            },
            selectionBorder: {
                value: _G.SELECTION_BORDER,
                validateFlags: [qg]
            },
            selectionShadowBlur: {
                value: _G.SELECTION_SHADOW_BLUR,
                validateFlags: [qg]
            },
            selectionColor: {
                value: _G.SELECTION_COLOR,
                validateFlags: [qg]
            },
            selectionType: {
                value: _G[sm],
                validateFlags: [qg]
            },
            selectionShadowOffsetX: {
                value: 0,
                validateFlags: [qg]
            },
            selectionShadowOffsetY: {
                value: 0,
                validateFlags: [qg]
            },
            shadowBlur: {
                value: 0,
                validateFlags: [qg]
            },
            shadowColor: {
                validateFlags: [qg]
            },
            shadowOffsetX: {
                value: 0,
                validateFlags: [qg]
            },
            shadowOffsetY: {
                value: 0,
                validateFlags: [qg]
            },
            renderColorBlendMode: {},
            renderColor: {},
            x: {
                value: 0,
                validateFlags: [jO]
            },
            y: {
                value: 0,
                validateFlags: [jO]
            },
            rotatable: {
                value: !0,
                validateFlags: [IO, qg]
            },
            rotate: {
                value: 0,
                validateFlags: [IO, qg]
            },
            _hostRotate: {
                validateFlags: [IO]
            },
            lineWidth: {
                value: 0,
                validateFlags: [Vg]
            },
            alpha: {
                value: 1
            }
        });
    var FY = [Mz[dc], Mz.PROPERTY_TYPE_STYLE, Mz[lc]];
    ds[er] = {
            removeBinding: function (t) {
                for (var i = FY[Hh]; --i >= 0;) {
                    var n = FY[i],
                        e = this[n];
                    for (var s in e) {
                            var h = e[s];
                            Array[wr](h) ? (v(h, function (i) {
                                return i[R_] == t
                            }, this), h[Hh] || delete e[s]) : h[R_] == t && delete e[s]
                        }
                }
            },
            _26: function (t, i, n) {
                if (!n && (n = this[i[kl] || Mz[dc]], !n)) return !1;
                var e = n[t];
                e ? (Array.isArray(e) || (n[t] = e = [e]), e.push(i)) : n[t] = i
            },
            _2j: function (t, i, n, e, s, h) {
                t = t || Mz.PROPERTY_TYPE_ACCESSOR;
                var r = this[t];
                if (!r) return !1;
                var a = {
                    property: i,
                    propertyType: t,
                    bindingProperty: e,
                    target: n,
                    callback: s,
                    invalidateSize: h
                };
                this._26(i, a, r)
            },
            onBindingPropertyChange: function (t, i, n, e) {
                var s = this[n || Mz[dc]];
                if (!s) return !1;
                var h = s[i];
                return h ? (t._1e = !0, _s(t, h, n, e), !0) : !1
            },
            initBindingProperties: function (t, i) {
                for (var e = FY[Hh]; --e >= 0;) {
                    var s = FY[e],
                        h = this[s];
                    for (var r in h) {
                            var a = h[r];
                            if (a[cc]) {
                                var o = a[R_];
                                if (o) {
                                    if (!(o instanceof $Y || (o = t[o]))) continue
                                } else o = t;
                                var f;
                                f = i === !1 ? t[uc](a[AO], s) : s == Mz[vc] ? t[Yc].getStyle(t[Af], a.property) : t[Af][a[AO]],
                                f !== n && (o[a[cc]] = f)
                            }
                        }
                }
            }
        };
    var qY = {};
    qY[LO] = CO,
    qY.SELECTION_BORDER = DO,
    qY[am] = "selection.shadow.blur",
    qY[RO] = "selection.shadow.offset.x",
    qY[PO] = "selection.shadow.offset.y",
    qY.SELECTION_TYPE = NO,
    qY[BO] = $O,
    qY[FO] = "render.color.blend.mode",
    qY.ALPHA = qO,
    qY.SHADOW_BLUR = GO,
    qY[zO] = HO,
    qY[YO] = UO,
    qY.SHADOW_OFFSET_Y = WO,
    qY[VO] = XO,
    qY[KO] = ZO,
    qY[JO] = "shape.stroke.fill.color",
    qY[QO] = tj,
    qY.SHAPE_LINE_DASH_OFFSET = "shape.line.dash.offset",
    qY[ij] = nj,
    qY.SHAPE_FILL_GRADIENT = ej,
    qY[sj] = hj,
    qY[rj] = aj,
    qY[oj] = fj,
    qY[uj] = cj,
    qY[_j] = dj,
    qY[lj] = vj,
    qY.BORDER_COLOR = bj,
    qY.BORDER_LINE_DASH = gj,
    qY[yj] = "border.line.dash.offset",
    qY[fm] = mj,
    qY[Ej] = No,
    qY[xj] = pj,
    qY[wj] = Tj,
    qY[kj] = Oj,
    qY[jj] = Mj,
    qY.IMAGE_BACKGROUND_COLOR = "image.background.color",
    qY.IMAGE_BACKGROUND_GRADIENT = "image.background.gradient",
    qY[Sj] = Ij,
    qY[Aj] = qY[Lj] = Cj,
    qY.IMAGE_BORDER_LINE_DASH = "image.border.line.dash",
    qY.IMAGE_BORDER_LINE_DASH_OFFSET = "image.border.line.dash.offset",
    qY[Dj] = qY[Rj] = Pj,
    qY[Nj] = Bj,
    qY[$j] = Fj,
    qY[qj] = Gj,
    qY[zj] = Hj,
    qY[Yj] = Uj,
    qY[Wj] = Vj,
    qY.LABEL_VISIBLE = Xj,
    qY[Kj] = "label.anchor.position",
    qY[Zj] = Jj,
    qY[Qj] = tM,
    qY[iM] = nM,
    qY[eM] = sM,
    qY.LABEL_PADDING = hM,
    qY[rM] = aM,
    qY.LABEL_POINTER = oM,
    qY.LABEL_RADIUS = fM,
    qY.LABEL_OFFSET_X = uM,
    qY.LABEL_OFFSET_Y = cM,
    qY[_M] = dM,
    qY[lM] = vM,
    qY.LABEL_BORDER = bM,
    qY.LABEL_BORDER_STYLE = gM,
    qY[yM] = "label.background.color",
    qY.LABEL_BACKGROUND_GRADIENT = "label.background.gradient",
    qY[mM] = EM,
    qY.LABEL_SHADOW_BLUR = xM,
    qY.LABEL_SHADOW_COLOR = pM,
    qY[wM] = "label.shadow.offset.x",
    qY[TM] = "label.shadow.offset.y",
    qY.LABEL_Z_INDEX = kM,
    qY.LABEL_ON_TOP = OM,
    qY.GROUP_BACKGROUND_COLOR = "group.background.color",
    qY.GROUP_BACKGROUND_GRADIENT = "group.background.gradient",
    qY[jM] = MM,
    qY[SM] = IM,
    qY.GROUP_STROKE_LINE_DASH = "group.stroke.line.dash",
    qY.GROUP_STROKE_LINE_DASH_OFFSET = "group.stroke.line.dash.offset",
    qY[AM] = "edge.bundle.label.rotate",
    qY.EDGE_BUNDLE_LABEL_POSITION = "edge.bundle.label.position",
    qY[LM] = "edge.bundle.label.anchor.position",
    qY[CM] = "edge.bundle.label.color",
    qY.EDGE_BUNDLE_LABEL_FONT_SIZE = "edge.bundle.label.font.size",
    qY[DM] = "edge.bundle.label.font.family",
    qY.EDGE_BUNDLE_LABEL_FONT_STYLE = "edge.bundle.label.font.style",
    qY[RM] = "edge.bundle.label.padding",
    qY[PM] = "edge.bundle.label.pointer.width",
    qY.EDGE_BUNDLE_LABEL_POINTER = "edge.bundle.label.pointer",
    qY.EDGE_BUNDLE_LABEL_RADIUS = "edge.bundle.label.radius",
    qY[NM] = "edge.bundle.label.offset.x",
    qY.EDGE_BUNDLE_LABEL_OFFSET_Y = "edge.bundle.label.offset.y",
    qY.EDGE_BUNDLE_LABEL_BORDER = "edge.bundle.label.border",
    qY[BM] = "edge.bundle.label.border.color",
    qY[$M] = "edge.bundle.label.background.color",
    qY[FM] = "edge.bundle.label.background.gradient",
    qY[qM] = "edge.bundle.label.rotatable",
    qY[GM] = zM,
    qY[HM] = YM,
    qY[UM] = WM,
    qY[VM] = XM,
    qY[KM] = ZM,
    qY.EDGE_LINE_DASH_OFFSET = "edge.line.dash.offset",
    qY[Xc] = JM,
    qY.EDGE_TO_OFFSET = QM,
    qY[tS] = iS,
    qY[nS] = eS,
    qY[sS] = hS,
    qY.EDGE_EXTEND = rS,
    qY[Lc] = aS,
    qY[oS] = "edge.split.by.percent",
    qY[yc] = fS,
    qY[mc] = uS,
    qY[Mc] = cS,
    qY.EDGE_CORNER_RADIUS = _S,
    qY.EDGE_FROM_AT_EDGE = dS,
    qY[lS] = vS,
    qY[bS] = gS,
    qY[yS] = mS,
    qY[ES] = xS,
    qY[pS] = wS,
    qY[TS] = "arrow.from.stroke.style",
    qY[kS] = OS,
    qY.ARROW_FROM_OUTLINE_STYLE = "arrow.from.outline.style",
    qY.ARROW_FROM_LINE_DASH = jS,
    qY.ARROW_FROM_LINE_DASH_OFFSET = "arrow.from.line.dash.offset",
    qY[MS] = "arrow.from.fill.color",
    qY[SS] = "arrow.from.fill.gradient",
    qY[IS] = AS,
    qY.ARROW_FROM_LINE_JOIN = LS,
    qY[Rk] = CS,
    qY[DS] = RS,
    qY.ARROW_TO_OFFSET = PS,
    qY[NS] = BS,
    qY.ARROW_TO_STROKE_STYLE = "arrow.to.stroke.style",
    qY.ARROW_TO_OUTLINE = $S,
    qY[FS] = "arrow.to.outline.style",
    qY[qS] = GS,
    qY[zS] = "arrow.to.line.dash.offset",
    qY[HS] = YS,
    qY[US] = "arrow.to.fill.gradient",
    qY[WS] = VS,
    qY[XS] = KS;
    var GY = new ds,
        zY = Mz.PROPERTY_TYPE_ACCESSOR,
        HY = Mz[vc],
        YY = !1;
    GY._2j(HY, qY[sm], null, ZS),
    GY._2j(HY, qY[rm], null, vE),
    GY._2j(HY, qY.SELECTION_SHADOW_BLUR, null, uE),
    GY._2j(HY, qY[LO], null, cE),
    GY._2j(HY, qY[RO], null, "selectionShadowOffsetX"),
    GY._2j(HY, qY[PO], null, "selectionShadowOffsetY"),
    GY._2j(zY, ur, JS, vo),
    GY._2j(HY, qY[QS], JS, tT),
    GY._2j(HY, qY[Wj], JS, Sf),
    GY._2j(HY, qY[Kj], JS, Lk),
    GY._2j(HY, qY[Zj], JS, tI),
    GY._2j(HY, qY[Qj], JS, K_),
    GY._2j(HY, qY[iI], JS, oy),
    GY._2j(HY, qY[nI], JS, eI),
    GY._2j(HY, qY[yM], JS, sI),
    GY._2j(HY, qY[hI], JS, rI),
    YY || (GY._2j(HY, qY[aI], null, C_), GY._2j(HY, qY[zO], null, A_), GY._2j(HY, qY[YO], null, Dm), GY._2j(HY, qY[oI], null, Rm), GY._2j(HY, qY[iM], JS, V_), GY._2j(HY, qY[eM], JS, fI), GY._2j(HY, qY[lM], JS, uI), GY._2j(HY, qY[Yj], JS, mo), GY._2j(HY, qY[cI], JS, No), GY._2j(HY, qY[rM], JS, _I), GY._2j(HY, qY[dI], JS, lI), GY._2j(HY, qY[vI], JS, bI), GY._2j(HY, qY[gI], JS, uO), GY._2j(HY, qY[yI], JS, cO), GY._2j(HY, qY[mM], JS, mI), GY._2j(HY, qY[EI], JS, Ef), GY._2j(HY, qY[_M], JS, Aw), GY._2j(HY, qY[xI], JS, C_), GY._2j(HY, qY.LABEL_SHADOW_COLOR, JS, A_), GY._2j(HY, qY.LABEL_SHADOW_OFFSET_X, JS, Dm), GY._2j(HY, qY.LABEL_SHADOW_OFFSET_Y, JS, Rm), GY._2j(HY, qY[pI], JS, Hw), GY._2j(HY, qY.RENDER_COLOR, null, Nm), GY._2j(HY, qY.RENDER_COLOR_BLEND_MODE, null, $m), GY._2j(HY, qY[wI], null, qO));
    var UY = new ds;
    UY._2j(zY, Tk),
    UY._2j(zY, Lk, null, TI),
    UY._2j(zY, mo, null, mo),
    YY || (UY._2j(HY, qY.BACKGROUND_COLOR, null, sI), UY._2j(HY, qY[_j], null, Ef), UY._2j(HY, qY[Ej], null, No), UY._2j(HY, qY[lj], null, oy), UY._2j(HY, qY[fm], null, bI), UY._2j(HY, qY[kI], null, eI), UY._2j(HY, qY[OI], null, jI), UY._2j(HY, qY[yj], null, lO)),
    UY._2j(zY, Gm, Gm, vo, MI),
    UY._2j(zY, Aw, Gm, Aw),
    UY._2j(HY, qY[VO], Gm, bo),
    UY._2j(HY, qY[KO], Gm, S_),
    UY._2j(HY, qY[ij], Gm, mE),
    UY._2j(HY, qY[oj], Gm, If),
    YY || (UY._2j(HY, qY[qj], Gm, SI), UY._2j(HY, qY[sj], Gm, bE), UY._2j(HY, qY[rj], Gm, gE), UY._2j(HY, qY[II], Gm, EE), UY._2j(HY, qY[QO], Gm, Df), UY._2j(HY, qY[AI], Gm, Ff), UY._2j(HY, qY[xj], Gm, j_), UY._2j(HY, qY.LINE_JOIN, Gm, M_), UY._2j(HY, qY[LI], Gm, sI), UY._2j(HY, qY.IMAGE_BACKGROUND_GRADIENT, Gm, Ef), UY._2j(HY, qY.IMAGE_PADDING, Gm, No), UY._2j(HY, qY[Sj], Gm, oy), UY._2j(HY, qY[Rj], Gm, bI), UY._2j(HY, qY[Lj], Gm, eI), UY._2j(HY, qY.IMAGE_BORDER_LINE_DASH, Gm, jI), UY._2j(HY, qY[CI], Gm, lO), UY._2j(HY, qY[$j], Gm, Hw), UY._2j(HY, qY[zj], Gm, qO)),
    UY._2j(zY, GT, null, null, DI),
    UY._2j(zY, r_, null, null, DI);
    var WY = new ds;
    WY._2j(zY, Kk, null, null, RI),
    WY._2j(zY, Zk, null, null, RI),
    WY._2j(zY, Xk, null, null, RI),
    WY._2j(zY, No, null, null, RI),
    WY._2j(HY, qY[PI], NI, mE),
    WY._2j(HY, qY.GROUP_BACKGROUND_GRADIENT, NI, EE),
    WY._2j(HY, qY[jM], NI, bo),
    WY._2j(HY, qY[SM], NI, S_),
    WY._2j(HY, qY[BI], NI, Df),
    WY._2j(HY, qY[$I], NI, Ff);
    var VY = new ds;
    VY._2j(zY, ku, NI, null, FI),
    VY._2j(zY, gk, NI, null, FI),
    VY._2j(zY, Wc, NI, null, FI),
    VY._2j(HY, qY[qI], NI, null, FI),
    VY._2j(HY, qY[GM], NI, bo),
    VY._2j(HY, qY[HM], NI, S_),
    VY._2j(HY, qY[bS], NI, GI),
    VY._2j(HY, qY[Rk], NI, zI),
    YY || (VY._2j(HY, qY[kj], NI, HI), VY._2j(HY, qY[jj], NI, wE), VY._2j(HY, qY.EDGE_FILL_COLOR, NI, pE), VY._2j(HY, qY[YI], null, UI, FI), VY._2j(HY, qY[lS], null, Qc, FI), VY._2j(HY, qY.EDGE_OUTLINE, NI, bE), VY._2j(HY, qY[VM], NI, gE), VY._2j(HY, qY.EDGE_LINE_DASH, NI, Df), VY._2j(HY, qY.EDGE_LINE_DASH_OFFSET, NI, Ff), VY._2j(HY, qY[Lc], NI, null, FI), VY._2j(HY, qY[Xc], NI, null, FI), VY._2j(HY, qY[Kc], NI, null, FI), VY._2j(HY, qY[xj], NI, j_), VY._2j(HY, qY[wj], NI, M_), VY._2j(zY, dk, null, null, FI, !0), VY._2j(zY, Zc, null, null, FI, !0), VY._2j(HY, qY[yS], NI, WI), VY._2j(HY, qY[ES], NI, VI), VY._2j(HY, qY.ARROW_FROM_STROKE, NI, XI), VY._2j(HY, qY[TS], NI, KI), VY._2j(HY, qY.ARROW_FROM_OUTLINE, NI, ZI), VY._2j(HY, qY[JI], NI, "fromArrowOutlineStyle"), VY._2j(HY, qY[MS], NI, QI), VY._2j(HY, qY[SS], NI, "fromArrowFillGradient"), VY._2j(HY, qY[tA], NI, iA), VY._2j(HY, qY[nA], NI, "fromArrowLineDashOffset"), VY._2j(HY, qY[eA], NI, sA), VY._2j(HY, qY[IS], NI, hA), VY._2j(HY, qY.ARROW_TO_SIZE, NI, rA), VY._2j(HY, qY.ARROW_TO_OFFSET, NI, aA), VY._2j(HY, qY[NS], NI, oA), VY._2j(HY, qY[fA], NI, uA), VY._2j(HY, qY.ARROW_TO_OUTLINE, NI, cA), VY._2j(HY, qY[FS], NI, _A), VY._2j(HY, qY[HS], NI, dA), VY._2j(HY, qY.ARROW_TO_FILL_GRADIENT, NI, lA), VY._2j(HY, qY.ARROW_TO_LINE_DASH, NI, vA), VY._2j(HY, qY[zS], NI, "toArrowLineDashOffset"), VY._2j(HY, qY.ARROW_TO_LINE_JOIN, NI, bA), VY._2j(HY, qY[WS], NI, gA));
    var XY = new ds;
    XY._2j(HY, qY.EDGE_BUNDLE_LABEL_COLOR, yA, tI),
    XY._2j(HY, qY[mA], yA, Sf),
    XY._2j(HY, qY[LM], yA, Lk),
    XY._2j(HY, qY[EA], yA, K_),
    XY._2j(HY, qY[qM], yA, mI),
    YY || (XY._2j(HY, qY.EDGE_BUNDLE_LABEL_ROTATE, yA, mo), XY._2j(HY, qY.EDGE_BUNDLE_LABEL_FONT_FAMILY, yA, V_), XY._2j(HY, qY.EDGE_BUNDLE_LABEL_FONT_STYLE, yA, fI), XY._2j(HY, qY.EDGE_BUNDLE_LABEL_PADDING, yA, No), XY._2j(HY, qY.EDGE_BUNDLE_LABEL_POINTER_WIDTH, yA, _I), XY._2j(HY, qY.EDGE_BUNDLE_LABEL_POINTER, yA, lI), XY._2j(HY, qY.EDGE_BUNDLE_LABEL_RADIUS, yA, bI), XY._2j(HY, qY[NM], yA, uO), XY._2j(HY, qY[xA], yA, cO), XY._2j(HY, qY[pA], yA, oy), XY._2j(HY, qY[BM], yA, eI), XY._2j(HY, qY.EDGE_BUNDLE_LABEL_BACKGROUND_COLOR, yA, sI), XY._2j(HY, qY.EDGE_BUNDLE_LABEL_BACKGROUND_GRADIENT, yA, Ef));
    var KY = new ds;
    KY._2j(zY, Tk),
    KY._2j(HY, qY[uj], null, sI),
    KY._2j(HY, qY[_j], null, Ef),
    KY._2j(HY, qY[Ej], null, No),
    KY._2j(HY, qY.BORDER, null, oy),
    KY._2j(HY, qY.BORDER_RADIUS, null, bI),
    KY._2j(HY, qY.BORDER_COLOR, null, eI),
    KY._2j(HY, qY[OI], null, jI),
    KY._2j(HY, qY[yj], null, lO),
    KY._2j(zY, mo, null, mo),
    KY._2j(zY, dk, null, null, wA),
    KY._2j(zY, Ck, Gm, vo),
    KY._2j(zY, Aw, Gm, Aw),
    KY._2j(HY, qY[VO], Gm, bo),
    KY._2j(HY, qY[KO], Gm, S_),
    KY._2j(HY, qY.SHAPE_FILL_COLOR, Gm, mE),
    KY._2j(HY, qY[II], Gm, EE),
    YY || (KY._2j(HY, qY.LINE_DASH_CAP, Gm, HI), KY._2j(HY, qY.LINE_DASH_JOIN, Gm, wE), KY._2j(HY, qY.SHAPE_LINE_FILL_COLOR, Gm, pE), KY._2j(HY, qY[sj], Gm, bE), KY._2j(HY, qY[rj], Gm, gE), KY._2j(HY, qY[QO], Gm, Df), KY._2j(HY, qY[AI], Gm, Ff), KY._2j(HY, qY[xj], Gm, j_), KY._2j(HY, qY[wj], Gm, M_), KY._2j(HY, qY.LAYOUT_BY_PATH, Gm, If), KY._2j(HY, qY[LI], Gm, sI), KY._2j(HY, qY[TA], Gm, Ef), KY._2j(HY, qY[Nj], Gm, No), KY._2j(HY, qY[Sj], Gm, oy), KY._2j(HY, qY[Rj], Gm, bI), KY._2j(HY, qY.IMAGE_BORDER_COLOR, Gm, eI), KY._2j(HY, qY[kA], Gm, jI), KY._2j(HY, qY.IMAGE_BORDER_LINE_DASH_OFFSET, Gm, lO), KY._2j(HY, qY[bS], Gm, GI), KY._2j(HY, qY[yS], Gm, WI), KY._2j(HY, qY[ES], Gm, VI), KY._2j(HY, qY[pS], Gm, XI), KY._2j(HY, qY[TS], Gm, KI), KY._2j(HY, qY.ARROW_FROM_FILL_COLOR, Gm, QI), KY._2j(HY, qY[SS], Gm, "fromArrowFillGradient"), KY._2j(HY, qY.ARROW_FROM_LINE_DASH, Gm, iA), KY._2j(HY, qY.ARROW_FROM_LINE_DASH_OFFSET, Gm, "fromArrowLineDashOffset"), KY._2j(HY, qY[eA], Gm, sA), KY._2j(HY, qY[IS], Gm, hA), KY._2j(HY, qY.ARROW_TO_SIZE, Gm, rA), KY._2j(HY, qY[OA], Gm, aA), KY._2j(HY, qY.ARROW_TO, Gm, zI), KY._2j(HY, qY.ARROW_TO_STROKE, Gm, oA), KY._2j(HY, qY[fA], Gm, uA), KY._2j(HY, qY.ARROW_TO_FILL_COLOR, Gm, dA), KY._2j(HY, qY.ARROW_TO_FILL_GRADIENT, Gm, lA), KY._2j(HY, qY[qS], Gm, vA), KY._2j(HY, qY[zS], Gm, "toArrowLineDashOffset"), KY._2j(HY, qY.ARROW_TO_LINE_JOIN, Gm, bA), KY._2j(HY, qY[WS], Gm, gA));
    var ZY = function (t, i) {
            return t = t[Hw],
            i = i[Hw],
            t == i ? 0 : (t = t || 0, i = i || 0, t > i ? 1 : i > t ? -1 : void 0)
        },
        JY = function (t, i) {
            this[jT] = new EG,
            w(this, JY, arguments),
            this.id = this[Af].id,
            this[Yc] = i,
            this._f5 = [],
            this._mu0 = new ds
        };
    JY[er] = {
            syncSelection: !1,
            graph: null,
            layoutByAnchorPoint: !1,
            _mu0: null,
            _f5: null,
            addChild: function (t, i) {
                t._je = this,
                i !== n ? y(this._f5, t, i) : this._f5[tr](t),
                t._da && this._9y(t),
                this[jA](),
                this.invalidateSize(),
                this.$invalidateChild = !0
            },
            removeChild: function (t) {
                this[MA].removeBinding(t),
                t._je = null,
                m(this._f5, t),
                this._jg && this._jg.remove(t),
                this[SA](),
                this[IA] = !0
            },
            getProperty: function (t, i) {
                return i == Mz[vc] ? this[Yc][gc](this.$data, t) : i == Mz[lc] ? this[Af].get(t) : this[Af][t]
            },
            getStyle: function (t) {
                return this[Yc][gc](this[Af], t)
            },
            _$w: function (t, i, n) {
                var e = this[MA].onBindingPropertyChange(this, t, i, n);
                return GY[AA](this, t, i, n) || e
            },
            onPropertyChange: function (t) {
                if (Hw == t[cd]) return this.invalidateRender(),
                !0;
                if (nk == t[yo]) {
                    if (Sw == t.kind) return this[Sw](),
                    !0;
                    var i = t[cr];
                    return i && i.ui ? (Kl == t.kind ? this._94(i) : Jh == t[cd] && this[Ev](i.ui), !0) : !1
                }
                return this._$w(t.kind, t[kl] || zY, t[cr])
            },
            label: null,
            initLabel: function () {
                var t = new tU;
                t[ur] = JS,
                this[LA](t),
                this[JS] = t
            },
            initialize: function () {
                this.initLabel(),
                this[Af][ek] && this.$data[ek].forEach(this._94, this),
                GY[CA](this),
                this[MA][CA](this, !1)
            },
            addBinding: function (t, i) {
                return i.property ? (i[R_] = t, void this[MA]._26(i[AO], i)) : !1
            },
            _fe: function (t, i) {
                var n = this[Af];
                if (!n._msu) return !1;
                var e = n._msu.getById(t.id);
                if (!e || !e.bindingProperties) return !1;
                var s = e[DA];
                if ($(s)) {
                    var h = !1;
                    return l(s, function (t) {
                        return vo == t[cc] ? (h = ls(n, i, t.property, t[kl]), !1) : void 0
                    }, this),
                    h
                }
                return vo == s[cc] ? ls(n, i, s.property, s[kl]) : !1
            },
            _94: function (t) {
                var i = t.ui;
                if (i) {
                    var n = t[DA];
                    n && (Array[wr](n) ? n[Vf](function (t) {
                        this.addBinding(i, t)
                    }, this) : this[RA](i, n)),
                    this.addChild(i)
                }
            },
            validate: function () {
                return this._mq0 || (this[pO](), this[ac] = !0),
                this[wO]()
            },
            _$c: !0,
            invalidateChildrenIndex: function () {
                this._$c = !0
            },
            doValidate: function () {
                if (this._1e && (this._1e = !1, this[PA]() && (this[NA](), this[_f] = !0), this._$c && (this._$c = !1, sG ? this._f5 = d(this._f5, ZY) : this._f5[zw](ZY))), Gn[Yh](this) && (this[Dx] = !0), this[Dx]) {
                    bH[Yh](this),
                    this.uiBounds.setByRect(this._fa);
                    var t = this[BA] || 0,
                        i = Math.max(this.$selectionBorder || 0, this.$shadowOffsetX || 0, this[$A] || 0),
                        n = Math.max(this[FA] || 0, this[qA] || 0),
                        e = Math.max(2 * t, this.$shadowBlur, this[GA]);
                    e += _G.UI_BOUNDS_GROW || 0;
                    var s = e - i,
                        h = e + i,
                        r = e - n,
                        a = e + n;
                    return 0 > s && (s = 0),
                    0 > h && (h = 0),
                    0 > r && (r = 0),
                    0 > a && (a = 0),
                    this.uiBounds[vf](r, s, a, h),
                    this.onBoundsChanged && this[yO](),
                    this.$invalidateBounds = !0,
                    !0
                }
            },
            validateChildren: function () {
                var t = this[IA];
                this[IA] = !1;
                var i = this[zA],
                    n = this[HA];
                i && (i[YA] = this.$renderColor, i[UA] = this[UA], i[WA] = this[WA], i[VA] = this[VA], i[XA] = this[XA], i[FA] = this[FA]),
                this[HA] = !1,
                i && i._1e && (n = i.validate() || n, i.$x = 0, i.$y = 0, i.$invalidateRotate && bH[Yh](i), t = !0);
                for (var e = 0, s = this._f5.length; s > e; e++) {
                        var h = this._f5[e];
                        if (h != i) {
                            var r = h._1e && h[ao]();
                            (r || n) && h._h9 && Un(h, i, this),
                            !t && r && (t = !0)
                        }
                    }
                return t
            },
            measure: function () {
                this._iv[Ra]();
                for (var t, i, n = 0, e = this._f5[Hh]; e > n; n++) t = this._f5[n],
                t._h9 && (i = t._fa, i[Ca] <= 0 || i.height <= 0 || this._iv[tl](t.$x + i.x, t.$y + i.y, i[Ca], i[Da]))
            },
            _jg: null,
            _9y: function (t) {
                if (!this._jg) {
                    if (!t[rI]) return;
                    return this._jg = new dG,
                    this._jg.add(t)
                }
                return t.showOnTop ? this._jg.add(t) : this._jg[Jh](t)
            },
            draw: function (t, i, n) {
                for (var e, s = 0, h = this._f5[Hh]; h > s; s++) e = this._f5[s],
                e._h9 && !e.showOnTop && e._ir(t, i, n, this)
            },
            _8t: function (t, i) {
                if (!this._h9 || !this._jg || !this._jg.length) return !1;
                t[Va](),
                t.translate(this.$x, this.$y),
                this.$rotatable && this.$_hostRotate && t[mo](this[cf]),
                (this[uO] || this.offsetY) && t[$o](this[uO], this.offsetY),
                this[af] && t[mo](this[af]),
                this[rf] && this[Oo] && t[$o](-this._mq2.x, -this[Oo].y),
                this[A_] && (t.shadowColor = this[A_], t[C_] = this[C_] * i, t.shadowOffsetX = this[Dm] * i, t.shadowOffsetY = this[Rm] * i),
                t[TE]();
                for (var n, e = 0, s = this._f5[Hh]; s > e; e++) n = this._f5[e],
                n._h9 && n[rI] && n._ir(t, i, this.selected, this);
                t.restore()
            },
            doHitTest: function (t, i, n) {
                if (n) {
                    if (!this._iv.intersectsRect(t - n, i - n, 2 * n, 2 * n)) return !1
                } else if (!this._iv.intersectsPoint(t, i)) return !1;
                return this[KA](t, i, n)
            },
            hitTestChildren: function (t, i, n) {
                for (var e, s = this._f5[Hh] - 1; s >= 0; s--) if (e = this._f5[s], e._h9 && e[s_](t, i, n)) return e;
                return !1
            },
            destroy: function () {
                this[Bx] = !0;
                for (var t, i = this._f5[Hh] - 1; i >= 0; i--) t = this._f5[i],
                t.destroy()
            }
        },
    p(JY, $Y),
    Z(JY[er], {
            renderColorBlendMode: {
                get: function () {
                    return this[UA]
                },
                set: function (t) {
                    this[UA] = t,
                    this._1e = !0,
                    this.body && (this.body[$m] = this[UA])
                }
            },
            renderColor: {
                get: function () {
                    return this.$renderColor
                },
                set: function (t) {
                    this[YA] = t,
                    this._1e = !0,
                    this[ZA] && (this[ZA].renderColor = this[YA])
                }
            },
            bodyBounds: {
                get: function () {
                    if (this[JA]) {
                        this[JA] = !1;
                        var t, i = this.body;
                        t = i && i._h9 && !this._$q() ? i._fa[Qh]() : this._fa[Qh](),
                        this.rotate && Mi(t, this.rotate, t),
                        t.x += this.$x,
                        t.y += this.$y,
                        this._d6 = t
                    }
                    return this._d6
                }
            },
            bounds: {
                get: function () {
                    return new EG((this.$x || 0) + this[jT].x, (this.$y || 0) + this[jT].y, this[jT].width, this[jT][Da])
                }
            },
            body: {
                get: function () {
                    return this[zA]
                },
                set: function (t) {
                    t && this[zA] != t && (this[zA] = t, this[HA] = !0, this.invalidateSize())
                }
            }
        }),
    _G.UI_BOUNDS_GROW = 1;
    var QY = function () {
            w(this, QY, arguments)
        };
    QY.prototype = {
            strokeStyle: Cm,
            lineWidth: 0,
            fillColor: null,
            fillGradient: null,
            _jd: 1,
            _j6: 1,
            outline: 0,
            onDataChanged: function (t) {
                T(this, QY, kO, arguments),
                this._km && this._8i && this._km._63(this._8i, this),
                t && this[MI](t)
            },
            _mua: function (t) {
                this._km = yn(t),
                this._km[ao](),
                (this._km._ls == qz || this._km._5y()) && (this._8i || (this._8i = function () {
                    this.invalidateData(),
                    this._je && this._je[Yc] && (this._je.invalidateSize(), this._je.graph.invalidate())
                }), this._km._9x(this._8i, this))
            },
            _km: null,
            initialize: function () {
                this._mua(this[Af])
            },
            _51: function () {
                return this._km && this._km[Ao]
            },
            _8v: function (t) {
                if (!t || t[Ca] <= 0 || t[Da] <= 0 || !this[QA] || !(this.size instanceof Object)) return this._jd = 1,
                void(this._j6 = 1);
                var i = this[Aw][Ca],
                    e = this[Aw][Da];
                if ((i === n || null === i) && (i = -1), (e === n || null === e) && (e = -1), 0 > i && 0 > e) return this._jd = 1,
                void(this._j6 = 1);
                var s, h, r = t.width,
                    a = t.height;
                i >= 0 && (s = i / r),
                e >= 0 && (h = e / a),
                0 > i ? s = h : 0 > e && (h = s),
                this._jd = s,
                this._j6 = h
            },
            validateSize: function () {
                if (this[tL]) {
                    this[tL] = !1;
                    var t = this._originalBounds;
                    this._jd,
                    this._j6,
                    this._8v(t),
                    this[iL](t[Ca] * this._jd, t[Da] * this._j6, t.x * this._jd, t.y * this._j6)
                }
            },
            measure: function () {
                var t = this._km[oo](this.lineWidth + this[bE]);
                return t ? (this[tL] = !0, void(this[nL] = t.clone())) : void this._iv.set(0, 0, 0, 0)
            },
            onBoundsChanged: function () {
                this[eL] = !0
            },
            _1o: function () {
                this[eL] = !1,
                this._fillGradient = this[EE] ? Vz[er][sL].call(this[hL], this._7i) : null
            },
            _jj: function (t) {
                var i, n;
                if (hy == this.$adjustType) i = 1,
                n = -1;
                else {
                    if (ry != this[rL]) return;
                    i = -1,
                    n = 1
                }
                var e = this._iv.cx,
                    s = this._iv.cy;
                t[$o](e, s),
                t.scale(i, n),
                t[$o](-e, -s)
            },
            draw: function (t, i, n, e) {
                if (this._jd && this._j6) {
                    if (this.$invalidateFillGradient && this._1o(), t[Va](), this[rL] && this._jj(t), this._km._ls == zz) return t[nf](this._jd, this._j6),
                    this._km._lr[Ao](t, i, this, n, e || this),
                    void t[to]();
                    n && this._7h(t, i, e),
                    this._km[Ao](t, i, this, this._jd, this._j6),
                    t[to]()
                }
            },
            doHitTest: function (t, i, n) {
                if (this._km[s_]) {
                    if (hy == this.$adjustType) {
                        var e = this._iv.cy;
                        i = 2 * e - i
                    } else if (ry == this[rL]) {
                        var s = this._iv.cx;
                        t = 2 * s - t
                    }
                    t /= this._jd,
                    i /= this._j6;
                    var h = (this._jd + this._j6) / 2;
                    return h > 1 && (n /= h, n = 0 | n),
                    this._km._lr instanceof cH ? this._km._lr[s_](t, i, n, !0, this[aL], this[oL] || this.$fillGradient) : this._km.hitTest(t, i, n)
                }
                return !0
            },
            $invalidateScale: !0,
            $invalidateFillGradient: !0
        },
    p(QY, $Y),
    cs(QY[er], {
            adjustType: {},
            fillColor: {},
            size: {
                validateFlags: [qg, fL]
            },
            fillGradient: {
                validateFlags: [uL]
            }
        }),
    Z(QY[er], {
            originalBounds: {
                get: function () {
                    return this[nL]
                }
            }
        }),
    _G[cL] = pG.CENTER_MIDDLE;
    var tU = function () {
            w(this, tU, arguments),
            this.color = _G[Zj]
        };
    tU[er] = {
            color: _G[Zj],
            showPointer: !0,
            fontSize: null,
            fontFamily: null,
            fontStyle: null,
            _gm: null,
            alignPosition: null,
            measure: function () {
                this[X_];
                var t = Pi(this.$data, this[_L], this[dL], this[lL], _G[Xa], this.$font);
                if (this._gm = t, this[QA]) {
                    var i = this[QA][Ca] || 0,
                        n = this[QA].height || 0;
                    return this.setMeasuredBounds(i > t.width ? i : t[Ca], n > t[Da] ? n : t[Da])
                }
                return this[iL](t.width, t[Da])
            },
            doHitTest: function (t, i, n) {
                return this[Af] ? Sn(t, i, n, this) : !1
            },
            draw: function (t, i, n, e) {
                if (n && this._7h(t, i, e), this[_L] || _G[Ya], this[uf] && this[cf]) {
                    var s = un(this[cf]);
                    s > lG && 3 * lG > s && (t[$o](this._iv[Ca] / 2, this._iv[Da] / 2), t[mo](Math.PI), t[$o](-this._iv.width / 2, -this._iv[Da] / 2))
                }
                var h = this[uI] || _G[cL],
                    r = h.horizontalPosition,
                    a = h[Xr],
                    o = 0;
                r == TG ? (r = Jc, o += this._iv[Ca] / 2) : r == kG ? (r = Ur, o += this._iv[Ca]) : r = Bo;
                var f = 0;
                a == jG ? f = (this._iv[Da] - this._gm[Da]) / 2 : a == MG && (f = this._iv.height - this._gm[Da]),
                t[jm] = this[tI],
                Ri(t, this[Af], o, f, r, this[dL], this[_L], this[lL], _G[Xa], this[vL])
            },
            _51: function () {
                return null != this.$data || this[QA]
            },
            $invalidateFont: !0
        },
    p(tU, $Y),
    cs(tU[er], {
            size: {
                validateFlags: [Vg]
            },
            fontStyle: {
                validateFlags: [Vg, bL]
            },
            fontSize: {
                validateFlags: [Vg, bL]
            },
            fontFamily: {
                validateFlags: [Vg, bL]
            }
        }),
    Z(tU[er], {
            font: {
                get: function () {
                    return this[gL] && (this[gL] = !1, this.$font = (this[lL] || _G[Pd]) + yr + (this[_L] || _G.FONT_SIZE) + Ua + (this[dL] || _G[Wa])),
                    this[vL]
                }
            }
        });
    var iU = function (t) {
            t = t || new cH,
            this[yL] = new EG,
            w(this, iU, [t])
        };
    iU[er] = {
            layoutByPath: !0,
            layoutByAnchorPoint: !1,
            measure: function () {
                this[mL] = !0,
                this[EL] = !0,
                this[Af].getBounds(this[aL] + this[xL], this[yL]),
                this.setMeasuredBounds(this[yL])
            },
            validateSize: function () {
                if (this.$invalidateFromArrow || this.$invalidateToArrow) {
                    var t = this.pathBounds[Qh]();
                    if (this[mL]) {
                        this[mL] = !1;
                        var i = this[pL]();
                        i && t.add(i)
                    }
                    if (this[EL]) {
                        this[EL] = !1;
                        var i = this[wL]();
                        i && t.add(i)
                    }
                    this[iL](t)
                }
            },
            validateFromArrow: function () {
                if (!this[Af]._il || !this.$fromArrow) return void(this[TL] = null);
                var t = this[Af],
                    i = 0,
                    n = 0,
                    e = this[kL];
                e && (isNaN(e) && (e.x || e.y) ? (i += e.x || 0, n += e.y || 0) : i += e || 0, i > 0 && 1 > i && (i *= t._il)),
                this[OL] = t[Go](i, n),
                this.fromArrowLocation[mo] = Math.PI + this[OL][mo] || 0,
                this.$fromArrowShape = Fs(this[jL], this.$fromArrowSize);
                var s = this[TL].getBounds(this[ML][bo] + this[ML][bE]);
                return this.fromArrowFillGradient instanceof jz[SL] ? this[ML][xE] = Vz[er].generatorGradient[Yh](this.fromArrowFillGradient, s) : this[ML] && (this[ML][xE] = null),
                s[dm](this.fromArrowLocation.x, this[OL].y),
                Si(s, this.fromArrowLocation[mo], s, this[OL].x, this[OL].y),
                s
            },
            validateToArrow: function () {
                if (!this.$data._il || !this.$toArrow) return void(this[IL] = null);
                var t = this[Af],
                    i = 0,
                    n = 0,
                    e = this[AL];
                e && (isNaN(e) && (e.x || e.y) ? (i += e.x || 0, n += e.y || 0) : i += e || 0),
                0 > i && i > -1 && (i *= t._il),
                i += t._il,
                this[LL] = t[Go](i, n),
                this.$toArrowShape = Fs(this.$toArrow, this[CL]);
                var s = this.$toArrowShape[oo](this[DL][bo] + this.toArrowStyles[bE]);
                return this[lA] instanceof jz.Gradient ? this.toArrowStyles[xE] = Vz[er][sL][Yh](this[lA], s) : this[DL] && (this[DL][xE] = null),
                s.offset(this[LL].x, this[LL].y),
                Si(s, this[LL].rotate, s, this[LL].x, this[LL].y),
                s
            },
            _21: function (t) {
                var i = t ? "from" : gk,
                    e = this[i + RL];
                e === n && (e = this[aL]);
                var s = this[i + PL];
                s === n && (s = this[S_]);
                var h = this[i + NL];
                h || (this[i + NL] = h = {}),
                h[bo] = e,
                h[S_] = s,
                h.lineDash = this[i + BL],
                h.lineDashOffset = this[i + $L],
                h.fillColor = this[i + FL],
                h.fillGradient = this[i + qL],
                h[j_] = this[i + GL],
                h[M_] = this[i + zL],
                h.outline = this[i + HL] || 0,
                h[gE] = this[i + YL]
            },
            doValidate: function () {
                return this[jL] && this._21(!0),
                this[UL] && this._21(!1),
                T(this, iU, wO)
            },
            drawArrow: function (t, i, n, e) {
                if (this[jL] && this[TL]) {
                    t[Va]();
                    var s = this[OL],
                        h = s.x,
                        r = s.y,
                        a = s[mo];
                    t[$o](h, r),
                    a && t[mo](a),
                    this[TL].draw(t, i, this[ML], n, e),
                    t[to]()
                }
                if (this[UL] && this[IL]) {
                    t[Va]();
                    var s = this[LL],
                        h = s.x,
                        r = s.y,
                        a = s[mo];
                    t[$o](h, r),
                    a && t.rotate(a),
                    this.$toArrowShape[Ao](t, i, this[DL], n, e),
                    t.restore()
                }
            },
            outlineStyle: null,
            outline: 0,
            onBoundsChanged: function () {
                this.$invalidateFillGradient = !0
            },
            _1o: function () {
                this.$invalidateFillGradient = !1,
                this[xE] = this[hL] ? Vz[er][sL][Yh](this[hL], this._7i) : null
            },
            draw: function (t, i, n, e) {
                this[eL] && this._1o(),
                this[Af][Ao](t, i, this, n, e),
                this[WL](t, i, n, e)
            },
            doHitTest: function (t, i, n) {
                if (this[Af][s_](t, i, n, !0, this.$lineWidth + this[xL], this.$fillColor || this[hL])) return !0;
                if (this[UL] && this.$toArrowShape) {
                    var e = t - this[LL].x,
                        s = i - this[LL].y;
                    if (this[LL][mo]) {
                            var h = ki(e, s, -this.toArrowLocation[mo]);
                            e = h.x,
                            s = h.y
                        }
                    var r = this[DL].fillColor || this[DL][EE];
                    if (this[IL].hitTest(e, s, n, !0, this[DL][bo], r)) return !0
                }
                if (this.$fromArrow && this[TL]) {
                    var e = t - this.fromArrowLocation.x,
                        s = i - this[OL].y;
                    if (this[OL][mo]) {
                            var h = ki(e, s, -this[OL][mo]);
                            e = h.x,
                            s = h.y
                        }
                    var r = this[ML].fillColor || this.fromArrowStyles[EE];
                    if (this[TL][s_](e, s, n, !0, this[ML][bo], r)) return !0
                }
                return !1
            },
            $fromArrowOutline: 0,
            $toArrowOutline: 0,
            $invalidateFillGradient: !0,
            $invalidateFromArrow: !0,
            $invalidateToArrow: !0
        },
    p(iU, $Y),
    cs(iU[er], {
            strokeStyle: {
                validateFlags: [VL, XL]
            },
            fillColor: {},
            fillGradient: {
                validateFlags: [uL]
            },
            fromArrowOffset: {
                validateFlags: [VL, qg]
            },
            fromArrowSize: {
                validateFlags: [VL, qg]
            },
            fromArrow: {
                validateFlags: [VL, qg]
            },
            fromArrowOutline: {
                validateFlags: [VL, qg]
            },
            fromArrowStroke: {
                validateFlags: [VL, qg]
            },
            fromArrowStrokeStyle: {
                validateFlags: [VL]
            },
            toArrowOffset: {
                validateFlags: [XL, qg]
            },
            toArrowSize: {
                validateFlags: [XL, qg]
            },
            toArrow: {
                validateFlags: [XL, qg]
            },
            toArrowOutline: {
                validateFlags: [XL, qg]
            },
            toArrowStroke: {
                validateFlags: [XL, qg]
            },
            toArrowStrokeStyle: {
                validateFlags: [XL]
            },
            outline: {
                value: 0,
                validateFlags: [Vg]
            }
        }),
    Z(iU[er], {
            length: {
                get: function () {
                    return this[vo].length
                }
            }
        }),
    vs[er] = {
            shape: null,
            path: null,
            initialize: function () {
                T(this, vs, pO),
                this[Ck] = new cH,
                this[Ck]._dl = !1,
                this[NI] = new iU(this[Ck]),
                this[LA](this.shape, 0),
                this[zA] = this[NI],
                VY[CA](this)
            },
            _1u: !0,
            _5q: null,
            _$q: function () {
                return !1
            },
            _3w: function () {
                return !1
            },
            validatePoints: function () {
                this[NI].invalidateData();
                var t = this[Af],
                    i = this[Ck];
                i.clear();
                var n = t[pu],
                    e = t[Eu];
                n && e && Us(this, t, i, n, e)
            },
            drawLoopedEdge: function (t, i, n, e) {
                Ks(this, e, t)
            },
            drawEdge: function (t, i, n, e, s, h) {
                var r = s[Jc],
                    a = h[Jc];
                if (e == Mz[KL]) {
                        var o = (r.x + a.x) / 2,
                            f = (r.y + a.y) / 2,
                            u = r.x - a.x,
                            c = r.y - a.y,
                            _ = Math.sqrt(u * u + c * c),
                            d = Math[zr](c, u);
                        d += Math.PI / 6,
                        _ *= .04,
                        _ > 30 && (_ = 30);
                        var l = Math.cos(d) * _,
                            v = Math.sin(d) * _;
                        return t[Nu](o - v, f + l),
                        void t.lineTo(o + v, f - l)
                    }
                var b = Xs(this, this[vo], s, h, i, n, r, a);
                b && (t._f8 = b)
            },
            _2d: function () {
                if (!this[Af][ZL]()) return null;
                var t = this[Yc]._88._87(this[Af]);
                if (!t || !t.canBind(this[Yc]) || !t._g6) return null;
                var i = t[JL](this);
                return t.isPositiveOrder(this[Af]) || (i = -i),
                i
            },
            checkBundleLabel: function () {
                var t = this[QL]();
                return t ? (this[yA] || this[tC](), this[yA]._h9 = !0, void(this.bundleLabel[vo] = t)) : void(this[yA] && (this[yA]._h9 = !1, this[yA][vo] = null))
            },
            createBundleLabel: function () {
                var t = new tU;
                t[iC] = !1,
                this[yA] = t,
                this[LA](this.bundleLabel),
                XY[CA](this)
            },
            getBundleLabel: function () {
                return this[Yc].getBundleLabel(this[vo])
            },
            doValidate: function () {
                return this._1u && (this._1u = !1, this[nC]()),
                this[eC](),
                T(this, vs, wO)
            },
            _4h: function () {
                this._1u = !0,
                this[SA]()
            },
            _$w: function (t, i, n) {
                var e = this[MA][AA](this, t, i, n);
                return e = GY.onBindingPropertyChange(this, t, i, n) || e,
                this.bundleLabel && this.bundleLabel[Af] && (e = XY[AA](this, t, i, n) || e),
                VY[AA](this, t, i, n) || e
            }
        },
    p(vs, JY),
    vs.drawReferenceLine = function (t, i, n, e) {
            if (t[Pu](i.x, i.y), !e || e == Mz[sC]) return void t.lineTo(n.x, n.y);
            if (e == Mz[Tc]) t.lineTo(i.x, n.y);
            else if (e == Mz.EDGE_TYPE_HORIZONTAL_VERTICAL) t[Nu](n.x, i.y);
            else if (0 == e[zo](Mz[Rc])) {
                var s;
                s = e == Mz[Pc] ? !0 : e == Mz[hC] ? !1 : Math.abs(i.x - n.x) > Math.abs(i.y - n.y);
                var h = (i.x + n.x) / 2,
                    r = (i.y + n.y) / 2;
                s ? (t[Nu](h, i.y), t[Nu](h, n.y)) : (t.lineTo(i.x, r), t[Nu](n.x, r))
            } else if (0 == e.indexOf(Mz[Bc])) {
                var s, a = nU[qY[qI]] || 0;
                s = e == Mz.EDGE_TYPE_ELBOW_HORIZONTAL ? !0 : e == Mz[wc] ? !1 : Math.abs(i.x - n.x) > Math.abs(i.y - n.y),
                s ? (t[Nu](i.x + a, i.y), t[Nu](n.x - a, n.y)) : (t[Nu](i.x, i.y + a), t[Nu](n.x, n.y - a))
            } else if (0 == e[zo](rC)) {
                var a = nU[qY.EDGE_EXTEND] || 0;
                if (e == Mz[Nc]) {
                    var o = Math.min(i.y, n.y) - a;
                    t[Nu](i.x, o),
                    t[Nu](n.x, o)
                } else if (e == Mz.EDGE_TYPE_EXTEND_BOTTOM) {
                    var o = Math.max(i.y, n.y) + a;
                    t[Nu](i.x, o),
                    t[Nu](n.x, o)
                } else if (e == Mz[Oc]) {
                    var f = Math.min(i.x, n.x) - a;
                    t.lineTo(f, i.y),
                    t.lineTo(f, n.y)
                } else if (e == Mz.EDGE_TYPE_EXTEND_RIGHT) {
                    var f = Math.max(i.x, n.x) + a;
                    t[Nu](f, i.y),
                    t.lineTo(f, n.y)
                }
            } else if (e == Mz[KL]) {
                var h = (i.x + n.x) / 2,
                    r = (i.y + n.y) / 2,
                    u = i.x - n.x,
                    c = i.y - n.y,
                    _ = Math[eo](u * u + c * c),
                    d = Math[zr](c, u);
                d += Math.PI / 6,
                _ *= .04,
                _ > 30 && (_ = 30);
                var l = Math.cos(d) * _,
                    v = Math.sin(d) * _;
                t[Nu](h - v, r + l),
                t[Nu](h + v, r - l)
            }
            t[Nu](n.x, n.y)
        },
    Z(vs[er], {
            length: {
                get: function () {
                    return this.path ? this[Ck][Hh] : 0
                }
            }
        }),
    vs.prototype[Pa] = function (t, i, n) {
            var e = pn(this.path, t, i, n);
            if (e && e[Hh] > 2) {
                var s = this[vo],
                    h = e[e[Hh] - 1];
                s[aC] = h[yo] == sH ? e[Xh](1, e[Hh] - 2) : e[Xh](1, e[Hh] - 1)
            }
        },
    bs[er] = {
            _2h: null,
            image: null,
            initialize: function () {
                T(this, bs, pO),
                this[oC](),
                UY.initBindingProperties(this)
            },
            _mua: function () {
                this[vo].image ? this[Gm] && (this.body = this[Gm]) : this[JS] && (this[ZA] = this[JS])
            },
            _mq6: function () {
                this[Gm] = new QY,
                this[LA](this[Gm], 0),
                this._mua()
            },
            doValidate: function () {
                this[ZA] && (this instanceof sh && !this.$data[Zk] && this._5h() ? this[ZA][rf] = !1 : (this.body[rf] = null != this._2h, this[ZA][Lk] = this._2h));
                var t = this.$data[wk],
                    i = 0,
                    n = 0;
                t && (i = t.x, n = t.y);
                var e = this.$x != i || this.$y != n;
                return e && (this[JA] = !0),
                this.$x = i,
                this.$y = n,
                JY[er][wO].call(this) || e
            },
            _$w: function (t, i, n) {
                var e = this[MA][AA](this, t, i, n);
                return e = GY.onBindingPropertyChange(this, t, i, n) || e,
                UY.onBindingPropertyChange(this, t, i, n) || e
            }
        },
    p(bs, JY);
    var nU = {};
    nU[qY[LO]] = _G.SELECTION_COLOR,
    nU[qY.SELECTION_BORDER] = _G[rm],
    nU[qY[am]] = _G[am],
    nU[qY.SELECTION_TYPE] = Mz[om],
    nU[qY[RO]] = 2,
    nU[qY[PO]] = 2,
    nU[qY[Zj]] = _G.LABEL_COLOR,
    nU[qY.LABEL_POSITION] = pG.CENTER_BOTTOM,
    nU[qY[Kj]] = pG[dl],
    nU[qY[cI]] = new xG(0, 2),
    nU[qY[rM]] = 8,
    nU[qY[vI]] = 8,
    nU[qY[dI]] = !0,
    nU[qY.LABEL_BORDER] = 0,
    nU[qY[nI]] = Cm,
    nU[qY[mM]] = !0,
    nU[qY[yM]] = null,
    nU[qY[EI]] = null,
    nU[qY[HM]] = fC,
    nU[qY[GM]] = 1.5,
    nU[qY[YI]] = !0,
    nU[qY.EDGE_TO_AT_EDGE] = !0,
    nU[qY[PI]] = X(3438210798),
    nU[qY[jM]] = 1,
    nU[qY[SM]] = Cm,
    nU[qY[Rk]] = !0,
    nU[qY[yS]] = _G.ARROW_SIZE,
    nU[qY[DS]] = _G.ARROW_SIZE,
    nU[qY[sS]] = 10,
    nU[qY.EDGE_CORNER_RADIUS] = 8,
    nU[qY.EDGE_CORNER] = Mz[Ac],
    nU[qY[oS]] = !0,
    nU[qY[qI]] = 20,
    nU[qY[yc]] = .5,
    nU[qY[mc]] = 20,
    nU[qY[nS]] = 20,
    nU[qY[LM]] = pG[vl],
    nU[qY[mA]] = pG[dl],
    nU[qY[CM]] = uC,
    nU[qY.SHAPE_STROKE] = 1,
    nU[qY.SHAPE_STROKE_STYLE] = cC,
    nU[qY[FO]] = _G[Yo],
    nU[qY[wI]] = 1,
    _G.LOOKING_EDGE_ENDPOINT_TOLERANCE = 2;
    var eU = function (i, n) {
            this._$u = new BG,
            this._$u.on(function (t) {
                qk == t[cd] && this[CT]()
            }, this),
            this._1g = new BG,
            this._1g[_d](function (t) {
                !this.currentSubNetwork || t[cd] != qG[Zl] && t[cd] != qG[rv] || this[$_][i_](this.currentSubNetwork) || (this[qk] = null)
            }, this),
            this._7 = new BG,
            this._10 = new BG,
            this._$o = new BG,
            this._$p = new BG,
            this[$_] = n || new as,
            this._88 = new TY(this, i),
            this._2o = new Dh(this),
            this._18 = new BG,
            this[_C] = KG(t, dC, function () {
                this.updateViewport()
            }, !1, this),
            this._88[nT][lC] = function (t) {
                this.ondrop(t)
            }[lr](this),
            this._88[nT][vC] = function (t) {
                this[vC](t)
            }[lr](this)
        };
    eU[er] = {
            originAtCenter: !0,
            editable: !1,
            ondragover: function (t) {
                jz[bC](t)
            },
            getDropInfo: function (t, i) {
                var n = null;
                if (i) try {
                    n = JSON[Kf](i)
                } catch (e) {}
                return n
            },
            ondrop: function (t) {
                var i = t[gC];
                if (i) {
                    var n = i[ig](yC),
                        e = this[mC](t, n);
                    e || (e = {}, e[Gm] = i[ig](Gm), e[yo] = i[ig](yo), e[JS] = i.getData(JS), e[Zk] = i[ig](Zk));
                    var s = this[EC](t);
                    if (s = this[fT](s.x, s.y), !(this.dropAction instanceof Function && this.dropAction[Yh](this, t, s, e) === !1) && (e[Gm] || e[JS] || e[yo])) {
                            var h = e[Gm],
                                r = e.type,
                                a = e[JS],
                                o = e[Zk];
                            jz.stopEvent(t);
                            var f;
                            if (r && xC != r ? tO == r ? f = this[pC](a, s.x, s.y) : Bk == r ? f = this[wC](a, s.x, s.y) : Jk == r ? (f = this[TC](a, s.x, s.y), o && (o = nh(o), o && (f[Zk] = o))) : (r = J(r), r instanceof Function && r[er] instanceof RY && (f = new r, f.name = a, f[Tk] = new bG(s.x, s.y), this[kC].add(f))) : f = this[OC](a, s.x, s.y), f) {
                                    if (h && (h = nh(h), h && (f.image = h)), t.shiftKey) {
                                        var u = this[jC](t);
                                        u && this[MC](u) && (f[yu] = u)
                                    }
                                    if (e[SC]) for (var c in e.properties) f[c] = e[SC][c];
                                    if (e.clientProperties) for (var c in e[IC]) f.set(c, e[IC][c]);
                                    if (e[AC] && f[rk](e[AC]), this[LC](f, t, e) === !1) return !1;
                                    var _ = new Ch(this, Ch[CC], t, f);
                                    return this.onInteractionEvent(_),
                                    f
                                }
                        }
                }
            },
            _mqw: function (t) {
                return t[r_] || t instanceof BY || t[DC]
            },
            enableDoubleClickToOverview: !0,
            _88: null,
            _$u: null,
            _1g: null,
            _7: null,
            _$p: null,
            _10: null,
            _$o: null,
            _1n: function (t) {
                return this._$u.beforeEvent(t)
            },
            _4a: function (t) {
                this._$u[or](t),
                Lw == t[cd] && this.checkLimitedBounds()
            },
            isVisible: function (t) {
                return this._88._es(t)
            },
            isMovable: function (t) {
                return (t instanceof RY || t instanceof DY && t[Gc]()) && t[RC] !== !1
            },
            isSelectable: function (t) {
                return t[PC] !== !1
            },
            isEditable: function (t) {
                return t[iC] !== !1
            },
            isRotatable: function (t) {
                return t[mI] !== !1
            },
            isResizable: function (t) {
                return t[NC] !== !1
            },
            canLinkFrom: function (t) {
                return t[BC] !== !1 && t[$C] !== !1
            },
            canLinkTo: function (t) {
                return t.linkable !== !1 && t[FC] !== !1
            },
            createNode: function (t, i, n) {
                var e = new RY(t, i, n);
                return this._k7Model.add(e),
                e
            },
            createText: function (t, i, n) {
                var e = new fs(t, i, n);
                return this[kC].add(e),
                e
            },
            createShapeNode: function (t, i, n, e) {
                P(i) && (e = n, n = i, i = null);
                var s = new PY(t, i);
                return s[wk] = new bG(n, e),
                this[kC].add(s),
                s
            },
            createGroup: function (t, i, n) {
                var e = new BY(t, i, n);
                return this[kC].add(e),
                e
            },
            createEdge: function (t, i, n) {
                if (t instanceof RY) {
                    var e = n;
                    n = i,
                    i = t,
                    t = e
                }
                var s = new DY(i, n);
                return t && (s.$name = t),
                this[kC].add(s),
                s
            },
            addElement: function (t, i) {
                this[kC].add(t),
                i && t.hasChildren() && t[Ru](function (t) {
                    this.addElement(t, i)
                }, this)
            },
            removeElement: function (t) {
                this[kC][Jh](t)
            },
            clear: function () {
                this[kC].clear()
            },
            getStyle: function (t, i) {
                var e = t._it[i];
                return e !== n ? e : this.getDefaultStyle(i)
            },
            getDefaultStyle: function (t) {
                if (this._it) {
                    var i = this._it[t];
                    if (i !== n) return i
                }
                return nU[t]
            },
            _32: function (t, i) {
                if (!this.limitedBounds || this.limitedBounds[i_](this[qC])) return i && i(),
                !1;
                t = this._2f(),
                this[GC]();
                var n, e, s, h = this.viewportBounds,
                    r = this[zC],
                    a = h[Ca] / this.limitedBounds.width,
                    o = h[Da] / this.limitedBounds[Da];
                if (1 >= a && 1 >= o) return n = r.left > h[Bo] ? r[Bo] : r[Ur] < h[Ur] ? h[Bo] - (h.right - r[Ur]) : h[Bo],
                e = r.top > h.top ? r.top : r.bottom < h.bottom ? h.top - (h[Yr] - r[Yr]) : h.top,
                void this.translateTo(-n * this.scale, -e * this[nf], this[nf], !1, i);
                var f = a > o;
                s = Math.max(a, o),
                f ? (n = r.x, e = r.y + (h.top - r.top) * (1 - s) / s, e >= r.y ? e = r.y : e < r[Yr] - h[Da] / s && (e = r[Yr] - h.height / s)) : (e = r.y, n = r.x + (h[Bo] - r.left) * (1 - s) / s, n >= r.x ? n = r.x : n < r[Ur] - h[Ca] / s && (n = r.right - h.width / s)),
                s *= this[nf],
                n *= s,
                e *= s,
                this[uT](-n, -e, s, t, i)
            },
            checkLimitedBounds: function (t) {
                return this._muheckingBounds || !this.limitedBounds || this[zC][i_](this.viewportBounds) ? !1 : (this._muheckingBounds = !0, void this[HC](function () {
                    this._32(t, function () {
                        this[YC] = !1
                    }[lr](this))
                }, this))
            },
            zoomByMouseEvent: function (t, i, n, e) {
                var s = this[EC](t);
                return P(i) ? this[UC](Math.pow(this[WC], i), s.x, s.y, n, e) : i ? this.zoomIn(s.x, s.y, n, e) : this.zoomOut(s.x, s.y, n, e)
            },
            resetScale: 1,
            translate: function (t, i, n) {
                return this[uT](this.tx + t, this.ty + i, this[nf], n)
            },
            translateTo: function (t, i, n, e, s) {
                if (n && (n = Math.min(this[qm], Math.max(this[VC], n))), e) {
                    var h = this._5c();
                    return void h._kl(t, i, n, e, s)
                }
                var r = this._88[XC](t, i, n);
                return s && s(),
                r
            },
            centerTo: function (t, i, e, s, h) {
                return (!e || 0 >= e) && (e = this[nf]),
                s === n && (s = this._2f()),
                this[uT](this.width / 2 - t * e, this[Da] / 2 - i * e, e, s, h)
            },
            moveToCenter: function (t, i) {
                if (arguments[2] === !1 || !this._88[KC]()) {
                    var n = this[fo];
                    return void this[ZC](n.cx, n.cy, t, i)
                }
                return this._88[ac] || (i = !1),
                this[HC](this.moveToCenter.bind(this, t, i, !1))
            },
            zoomToOverview: function (t, i) {
                if (arguments[2] === !1 || !this._88[KC]()) {
                    var n = this._88._1l();
                    return void(n && (i && (n.scale = Math.min(n[nf], i)), this.centerTo(n.cx, n.cy, n[nf], t)))
                }
                return this._88[ac] || (t = !1),
                this[HC](this[JC][lr](this, t, i, !1))
            },
            _2f: function () {
                return this._88[ac] ? this[QC] === n || null === this[QC] ? _G.ZOOM_ANIMATE : this[QC] : !1
            },
            zoomAt: function (t, i, e, s, h) {
                s === n && (s = this._2f()),
                i === n && (i = this[Ca] / 2),
                i = i || 0,
                e === n && (e = this[Da] / 2),
                e = e || 0;
                var r = this[nf];
                return t = Math.min(this[qm], Math.max(this[VC], r * t)),
                i = t * (this.tx - i) / r + i,
                e = t * (this.ty - e) / r + e,
                this[uT](i, e, t, s, h)
            },
            zoomOut: function (t, i, n, e) {
                return this[UC](1 / this[WC], t, i, n, e)
            },
            zoomIn: function (t, i, n, e) {
                return this[UC](this.scaleStep, t, i, n, e)
            },
            _5c: function () {
                return this[tD] || (this[tD] = new fU(this)),
                this[tD]
            },
            onAnimationStart: function () {},
            onAnimationEnd: function () {},
            isAnimating: function () {
                return this[tD] && this._panAnimation._dn()
            },
            enableInertia: !0,
            _91: function (t, i) {
                var n = this._5c();
                return n._ge(t || 0, i || 0)
            },
            stopAnimation: function () {
                this[tD] && this._panAnimation._lo()
            },
            getUI: function (t) {
                return Q(t) ? this._88._3k(t) : this._88._l4(t)
            },
            getUIByMouseEvent: function (t) {
                return this._88._3k(t)
            },
            hitTest: function (t) {
                return this._88[s_](t)
            },
            globalToLocal: function (t) {
                return this._88._7s(t)
            },
            toCanvas: function (t, i) {
                return this._88._gd(t, i)
            },
            toLogical: function (t, i) {
                return Q(t) ? this._88._$j(t) : this._88._ep(t, i)
            },
            getElementByMouseEvent: function (t) {
                var i = this._88._3k(t);
                return i ? i.$data : void 0
            },
            getElement: function (t) {
                if (Q(t)) {
                    var i = this._88._3k(t);
                    return i ? i[Af] : null
                }
                return this[kC].getById(t)
            },
            invalidate: function () {
                this._88._muj()
            },
            invalidateUI: function (t) {
                t[Sw](),
                this.invalidate()
            },
            invalidateElement: function (t) {
                this._88._3o(t)
            },
            getUIBounds: function (t) {
                return this._88._30(t)
            },
            forEachVisibleUI: function (t, i) {
                return this._88._41(t, i)
            },
            forEachReverseVisibleUI: function (t, i) {
                return this._88._$x(t, i)
            },
            forEachUI: function (t, i) {
                return this._88._ew(t, i)
            },
            forEachReverseUI: function (t, i) {
                return this._88._44(t, i)
            },
            forEach: function (t, i) {
                return this[kC].forEach(t, i)
            },
            getElementByName: function (t) {
                var i;
                return this[Vf](function (n) {
                    return n[ur] == t ? (i = n, !1) : void 0
                }),
                i
            },
            focus: function (i) {
                if (i) {
                    var n = t[nd] || t[da],
                        e = t[ed] || t[va];
                    return this[ng][iD](),
                    void t[nD](n, e)
                }
                this[ng].focus()
            },
            callLater: function (t, i, n) {
                this._88._el(t, i, n)
            },
            exportImage: function (t, i) {
                return _h(this, t, i)
            },
            setSelection: function (t) {
                return this._k7Model._selectionModel.set(t)
            },
            select: function (t) {
                return this[kC][cv][sd](t)
            },
            unselect: function (t) {
                return this[kC]._selectionModel[eD](t)
            },
            reverseSelect: function (t) {
                return this[kC][cv].reverseSelect(t)
            },
            selectAll: function () {
                ch(this)
            },
            unSelectAll: function () {
                this.selectionModel.clear()
            },
            unselectAll: function () {
                this[sD]()
            },
            isSelected: function (t) {
                return this[kC][cv][i_](t)
            },
            sendToTop: function (t) {
                je(this._k7Model, t)
            },
            sendToBottom: function (t) {
                Me(this[kC], t)
            },
            moveElements: function (t, i, n) {
                var e = [],
                    s = new dG;
                return l(t, function (t) {
                        t instanceof RY ? e[tr](t) : t instanceof DY && s.add(t)
                    }),
                this._de(e, i, n, s)
            },
            _de: function (t, i, n, e) {
                if (0 == i && 0 == n || 0 == t.length && 0 == e[Hh]) return !1;
                if (0 != t[Hh]) {
                    var s = this._3u(t);
                    e = this._3t(s, e),
                    l(s, function (t) {
                        var e = t.$location;
                        e ? t[hD](e.x + i, e.y + n) : t[hD](i, n)
                    })
                }
                return e && e[Hh] && this._df(e, i, n),
                !0
            },
            _df: function (t, i, n) {
                t[Vf](function (t) {
                    t[Ag](i, n)
                })
            },
            _3t: function (t, i) {
                return this.graphModel[Vf](function (n) {
                    n instanceof DY && this[rD](n) && t[i_](n.fromAgent) && t[i_](n.toAgent) && i.add(n)
                }, this),
                i
            },
            _3u: function (t) {
                var i = new dG;
                return l(t, function (t) {
                    !this.isMovable(t),
                    i.add(t),
                    Te(t, i, this[aD])
                }, this),
                i
            },
            reverseExpanded: function (t) {
                if (!t[ZL]()) return !1;
                var i = t[mu](!0);
                return i ? i[oD]() !== !1 ? (this[Sw](), !0) : void 0 : !1
            },
            _2o: null,
            _18: null,
            beforeInteractionEvent: function (t) {
                return this._18[$l](t)
            },
            onInteractionEvent: function (t) {
                this._18.onEvent(t)
            },
            addCustomInteraction: function (t) {
                this._2o[fD](t)
            },
            removeCustomInteraction: function (t) {
                this._2o[uD](t)
            },
            enableWheelZoom: !0,
            enableTooltip: !0,
            getTooltip: function (t) {
                return t.tooltip || t.name
            },
            updateViewport: function () {
                this._88._6n()
            },
            destroy: function () {
                this._4a(new LG(this, dg, !0, this._hged)),
                this[Bx] = !0,
                ZG(t, dC, this[_C]),
                this._2o.destroy(),
                this[$_] = new as;
                var i = this[cD];
                this._88._hg(),
                i && (i[_D] = "")
            },
            onPropertyChange: function (t, i, n) {
                this._$u[_d](function (e) {
                    e[cd] == t && i.call(n, e)
                })
            },
            removeSelection: function () {
                var t = this[B_]._j8;
                return t && 0 != t[Hh] ? (t = t[Vh](), this[kC][Jh](t), t) : !1
            },
            removeSelectionByInteraction: function (t) {
                var i = this.selectionModel[wv];
                return i && 0 != i[Hh] ? void jz[dD](lD + i[Hh], function () {
                    var i = this[vD]();
                    if (i) {
                        var n = new Ch(this, Ch[bD], t, i);
                        this[gD](n)
                    }
                }, this) : !1
            },
            createShapeByInteraction: function (t, i, n, e) {
                var s = new cH(i);
                i[Hh] > 2 && s[sf]();
                var h = this[wC](yD, s, n, e);
                this[LC](h, t);
                var r = new Ch(this, Ch.ELEMENT_CREATED, t, h);
                return this.onInteractionEvent(r),
                h
            },
            createLineByInteraction: function (t, i, n, e) {
                var s = new cH(i),
                    h = this[wC](mD, s, n, e);
                h[bc](jz[ED][ij], null),
                h.setStyle(jz[ED][II], null),
                h[bc](jz[ED].LAYOUT_BY_PATH, !0),
                this.onElementCreated(h, t);
                var r = new Ch(this, Ch.ELEMENT_CREATED, t, h);
                return this[gD](r),
                h
            },
            createEdgeByInteraction: function (t, i, n, e) {
                var s = this.createEdge(xD, t, i);
                if (e) s._9d = e;
                else {
                    var h = this[pD],
                        r = this[Wc];
                    this[wD] && (h = this[wD][qT] || h, r = this.interactionProperties[Wc] || r),
                    h && (s[qT] = h),
                    r && (s[Wc] = r)
                }
                this.onElementCreated(s, n);
                var a = new Ch(this, Ch[CC], n, s);
                return this.onInteractionEvent(a),
                s
            },
            onElementCreated: function (t) {
                !t.parent && this[qk] && (t.parent = this[qk])
            },
            allowEmptyLabel: !1,
            startLabelEdit: function (t, i, n, e) {
                var s = this;
                n.startEdit(e.x, e.y, i[vo], this[gc](t, qY.LABEL_FONT_SIZE), function (n) {
                    return s[TD](t, i, n, i.parent)
                })
            },
            onLabelEdit: function (t, i, n, e) {
                return n || this[kD] ? void(JS == i.name ? t[ur] = n : e._fe(i, n) === !1 && (i.data = n, this[OD](t))) : (jz[sy](jD), !1)
            },
            setInteractionMode: function (t, i) {
                this.interactionMode = t,
                this[wD] = i
            },
            upSubNetwork: function () {
                return this._3m ? this[qk] = eh(this._3m) : !1
            },
            _$s: !1,
            invalidateVisibility: function () {
                this._$s = !0,
                this.invalidate()
            },
            getBundleLabel: function (t) {
                var i = t[mu](!0);
                return i && i.agentEdge == t ? MD + i[SD][Hh] : null
            },
            zoomAnimation: null,
            pauseRendering: function (t, i) {
                (this.delayedRendering || i) && this._88._6k(t)
            },
            _48: n,
            enableRectangleSelectionByRightButton: !0
        },
    Z(eU[er], {
            center: {
                get: function () {
                    return this[fT](this[cD].clientWidth / 2, this[cD].clientHeight / 2)
                }
            },
            visibleFilter: {
                get: function () {
                    return this[OT]
                },
                set: function (t) {
                    this[OT] = t,
                    this[CT]()
                }
            },
            topCanvas: {
                get: function () {
                    return this._88._topCanvas
                }
            },
            propertyChangeDispatcher: {
                get: function () {
                    return this._$u
                }
            },
            listChangeDispatcher: {
                get: function () {
                    return this._1g
                }
            },
            dataPropertyChangeDispatcher: {
                get: function () {
                    return this._7
                }
            },
            selectionChangeDispatcher: {
                get: function () {
                    return this._$p
                }
            },
            parentChangeDispatcher: {
                get: function () {
                    return this._10
                }
            },
            childIndexChangeDispatcher: {
                get: function () {
                    return this._$o
                }
            },
            interactionDispatcher: {
                get: function () {
                    return this._18
                }
            },
            cursor: {
                set: function (t) {
                    this.canvasPanel.style[ID] = t || this._2o[H_]
                },
                get: function () {
                    return this.canvasPanel[na].cursor
                }
            },
            interactionMode: {
                get: function () {
                    return this._2o._muurrentMode
                },
                set: function (t) {
                    var i = this.interactionMode;
                    i != t && (this._2o.currentMode = t, this._4a(new LG(this, AD, t, i)))
                }
            },
            scaleStep: {
                get: function () {
                    return this._88._ed
                },
                set: function (t) {
                    this._88._ed = t
                }
            },
            maxScale: {
                get: function () {
                    return this._88._ft
                },
                set: function (t) {
                    this._88._ft = t
                }
            },
            minScale: {
                get: function () {
                    return this._88._fq
                },
                set: function (t) {
                    this._88._fq = t
                }
            },
            scale: {
                get: function () {
                    return this._88._scale
                },
                set: function (t) {
                    return this._88[db] = t
                }
            },
            tx: {
                get: function () {
                    return this._88._tx
                }
            },
            ty: {
                get: function () {
                    return this._88._ty
                }
            },
            styles: {
                get: function () {
                    return this._it
                },
                set: function (t) {
                    this._it = t
                }
            },
            selectionModel: {
                get: function () {
                    return this[kC]._selectionModel
                }
            },
            graphModel: {
                get: function () {
                    return this[kC]
                },
                set: function (t) {
                    if (this[kC] == t) return !1;
                    var i = this._k7Model,
                        n = new LG(this, $_, i, t);
                    return this._1n(n) === !1 ? !1 : (null != i && (i[LD][zl](this._$u, this), i[fv][zl](this._1g, this), i.dataChangeDispatcher[zl](this._7, this), i[lv][zl](this._10, this), i[vv][zl](this._$o, this), i.selectionChangeDispatcher.removeListener(this._$p, this)), this[kC] = t, this[kC] && (this[kC].propertyChangeDispatcher[_d](this._$u, this), this._k7Model[fv][_d](this._1g, this), this[kC].dataChangeDispatcher[_d](this._7, this), this._k7Model[lv][_d](this._10, this), this[kC][vv][_d](this._$o, this), this[kC][uv][_d](this._$p, this)), this._88 && this._88._l2(), void this._4a(n))
                }
            },
            count: {
                get: function () {
                    return this[kC][Hh]
                }
            },
            width: {
                get: function () {
                    return this[cD][N_]
                }
            },
            height: {
                get: function () {
                    return this[cD][fd]
                }
            },
            viewportBounds: {
                get: function () {
                    return this._88[CD]
                }
            },
            bounds: {
                get: function () {
                    return this._88._46()
                }
            },
            canvasPanel: {
                get: function () {
                    return this._88._mqe
                }
            },
            html: {
                get: function () {
                    return this._88._mqe[pm]
                }
            },
            navigationType: {
                get: function () {
                    return this._88._6h
                },
                set: function (t) {
                    this._88._3l(t)
                }
            },
            _3m: {
                get: function () {
                    return this._k7Model._3m
                }
            },
            currentSubNetwork: {
                get: function () {
                    return this[kC].currentSubNetwork
                },
                set: function (t) {
                    this[kC][qk] = t
                }
            },
            limitedBounds: {
                get: function () {
                    return this[DD]
                },
                set: function (t) {
                    return EG[ql](t, this[DD]) ? !1 : t ? void(this[DD] = new EG(t)) : void(this._limitedBounds = null)
                }
            },
            ratio: {
                get: function () {
                    return this._88.ratio
                }
            },
            delayedRendering: {
                get: function () {
                    return this._48 === n ? _G[RD] : this._48
                },
                set: function (t) {
                    t != this[PD] && (this._48 = t, this.pauseRendering(!1, !0))
                }
            },
            fullRefresh: {
                get: function () {
                    return this._88[Pw]
                },
                set: function (t) {
                    this._88[Pw] = t
                }
            }
        }),
    _G.DELAYED_RENDERING = !0,
    _G.GROUP_MIN_WIDTH = 60,
    _G[ND] = 60,
    sh[er] = {
            initialize: function () {
                T(this, sh, pO),
                this[DI]()
            },
            _mqc: function () {
                this._m3 = new cH,
                this[NI] = new QY(this._m3),
                this[NI].layoutByPath = !1,
                this.addChild(this.shape, 0),
                this.body = this[NI]
            },
            checkBody: function () {
                return this._5h() ? (this._2b = !0, this.shape ? (this[NI].visible = !0, this[ZA] = this[NI]) : (this[BD](), WY[CA](this)), void(this[Gm] && (this[Gm][tT] = !1))) : (this[Gm] ? (this[Gm].visible = !0, this[ZA] = this[Gm]) : this[oC](), void(this.shape && (this[NI].visible = !1)))
            },
            _5h: function () {
                return this.$data._hh() && this[Af].expanded
            },
            _m3: null,
            _2b: !0,
            _5j: function () {
                this._1e = !0,
                this._2b = !0
            },
            doValidate: function () {
                if (this._2b && this._5h()) {
                    if (this._2b = !1, this.shape.invalidateData(), this[Af].groupImage) {
                        this[NI][vo] = this[Af].groupImage;
                        var t = this._29();
                        return this[NI][uO] = t.x + t[Ca] / 2,
                        this[NI][cO] = t.y + t[Da] / 2,
                        this[NI].size = {
                            width: t.width,
                            height: t[Da]
                        },
                        bs[er][wO][Yh](this)
                    }
                    this[NI][uO] = 0,
                    this[NI][cO] = 0;
                    var i = this._8g(this.$data.groupType);
                    this._m3[Ra](),
                    i instanceof EG ? $e(this._m3, i.x, i.y, i.width, i[Da], i.rx, i.ry) : i instanceof Qi ? Fe(this._m3, i) : i instanceof tn && qe(this._m3, i),
                    this._m3._5s = !0,
                    this[NI][iO]()
                }
                return bs[er][wO][Yh](this)
            },
            _7b: function (t, i, n, e, s) {
                switch (Er != typeof e && (e = -i / 2), Er != typeof s && (s = -n / 2), t) {
                case Mz[$D]:
                    var h = Math.max(i, n) / 2;
                    return new Qi(e + i / 2, s + n / 2, h);
                case Mz.GROUP_TYPE_ELLIPSE:
                    return new tn(e + i / 2, s + n / 2, i, n);
                default:
                    return new EG(e, s, i, n)
                }
            },
            _29: function () {
                return this._8g(null)
            },
            _8g: function (t) {
                var i, e, s = this[vo],
                    h = s.padding,
                    r = s.minSize,
                    a = _G.GROUP_MIN_WIDTH,
                    o = _G[ND];
                if (r && (Er == typeof r[Ca] && (a = r[Ca]), Er == typeof r[Da] && (o = r.height), i = r.x, e = r.y), !s[Uh]()) return this._7b(t, a, o, i, e);
                var f, u = this.$data._f5._j8;
                    (t == Mz[$D] || t == Mz[FD]) && (f = []);
                for (var c, _, d, l, v = new EG, b = 0, g = u.length; g > b; b++) {
                        var y = u[b];
                        if (this.graph.isVisible(y)) {
                            var m = this.graph[Uc](y);
                            m && (c = m.$x + m._fa.x, _ = m.$y + m._fa.y, d = m._fa[Ca], l = m._fa[Da], v[tl](c, _, d, l), f && (f[tr]({
                                x: c,
                                y: _
                            }), f.push({
                                x: c + d,
                                y: _
                            }), f[tr]({
                                x: c + d,
                                y: _ + l
                            }), f[tr]({
                                x: c,
                                y: _ + l
                            })))
                        }
                    }
                if (v[Lf]()) return this._7b(t, a, o, i, e);
                var E = this[Af].$location;
                E ? E[Hk] && (E[Hk] = !1, i === n && (E.x = v.cx), e === n && (E.y = v.cy)) : E = this.$data[wk] = {
                        x: v.cx,
                        y: v.cy
                    },
                h && v[vf](h),
                Er == typeof i && i + E.x < v.x && (v.width += v.x - (i + E.x), v.x = i + E.x, f && f[tr]({
                        x: v.x,
                        y: v.cy
                    })),
                Er == typeof e && e + E.y < v.y && (v.height += v.y - (v.y, e + E.y), v.y = e + E.y, f && f.push({
                        x: v.cx,
                        y: v.y
                    }));
                var x, i = E.x,
                    e = E.y;
                if (t == Mz[$D]) {
                        x = nn(f),
                        x.cx -= i,
                        x.cy -= e;
                        var p = Math.max(a, o) / 2;
                        return x.r < p && (x.cx += p - x.r, x.cy += p - x.r, x.r = p),
                        x
                    }
                return t == Mz[FD] ? (x = en(f, v), x.cx -= i, x.cy -= e, x[Ca] < a && (x.cx += (a - x.width) / 2, x[Ca] = a), x[Da] < o && (x.cy += (o - x.height) / 2, x[Da] = o), x) : (x = v, v[Ca] < a && (v[Ca] = a), v[Da] < o && (v[Da] = o), v[dm](-i, -e), x)
            },
            _$w: function (t, i, n) {
                if (!this._5h()) return T(this, sh, qD, arguments);
                var e = this[MA][AA](this, t, i, n);
                return e = GY[AA](this, t, i, n) || e,
                e = UY[AA](this, t, i, n) || e,
                WY[AA](this, t, i, n) || e
            }
        },
    p(sh, bs),
    jz[GD] = sh;
    var sU = {
            draw: function () {}
        };
    _G[k_] = null,
    _G[p_] = null;
    var hU = {
            position: Ew,
            "text-align": Jc
        },
        rU = {
            padding: zD,
            transition: HD
        },
        aU = {
            position: E_,
            display: YD
        };
    gi(UD, "opacity:0.7;vertical-align:middle;"),
    gi(".Q-Graph-Nav img:hover,img.hover", WD),
    uG || (gi(VD, XD + VG(KD) + ZD), gi(JD, QD + VG(KD) + tR)),
    ah[er] = {
            _d2: function (t, i) {
                return t._h9 == i ? !1 : (t._h9 = i, void(t[na].visibility = i ? "visible" : y_))
            },
            _36: function (t, i) {
                var n = i / 2 - this[T_][l_][fd] / 2 + Ba;
                this[T_][l_].style.top = n,
                this[iR]._img[na].top = n,
                this[v_].style[Ca] = t + Ba,
                this[v_][na][Da] = i + Ba
            },
            _9b: function (t, i, n, e) {
                this._d2(this[nR], t),
                this._d2(this[T_], i),
                this._d2(this[eR], n),
                this._d2(this[iR], e)
            },
            _hg: function () {
                var t = this._navPane[pm];
                t && t.removeChild(this._navPane)
            },
            _in: function () {
                var t = this._msaseCanvas._k7;
                if (t) {
                    var i = t.bounds;
                    if (i.isEmpty()) return void this._9b(!1, !1, !1, !1);
                    var n = t.viewportBounds,
                        e = n.y > i.y + 1,
                        s = n.x > i.x + 1,
                        h = n[Yr] < i.bottom - 1,
                        r = n[Ur] < i[Ur] - 1;
                    this._9b(e, s, h, r)
                }
            }
        };
    var oU = 10;
    gi(sR, hR),
    gi(rR, "background-color: #7E7E7E;" + VG(KD) + ": background-color 0.2s linear;"),
    gi(".Q-Graph-ScrollBar--V", "width: 8px;right: 0px;"),
    gi(".Q-Graph-ScrollBar--H", "height: 8px;bottom: 0px;"),
    gi(".Q-Graph-ScrollBar--V.Both", aR),
    gi(".Q-Graph-ScrollBar--H.Both", oR),
    uG || (gi(fR, XD + VG(KD) + uR), gi(".Q-Graph:hover .Q-Graph-ScrollPane", QD + VG(KD) + ":opacity 0.3s linear;")),
    oh[er] = {
            _hg: function () {
                this._verticalDragSupport._hg(),
                this[cR]._hg(),
                delete this[_R],
                delete this._horizontalDragSupport,
                this._lq[pm] && this._lq[pm][Ev](this._lq)
            },
            _lq: null,
            _mqa: null,
            _8e: null,
            init: function (t) {
                var n = i[qa](a_);
                n[gu] = dR,
                li(n, {
                    width: x_,
                    height: x_,
                    position: E_
                });
                var e = i[qa](a_);
                e[gu] = "Q-Graph-ScrollBar Q-Graph-ScrollBar--V";
                var s = i[qa](a_);
                s[gu] = "Q-Graph-ScrollBar Q-Graph-ScrollBar--H",
                n[bu](e),
                n[bu](s),
                t[bu](n),
                this._lq = n,
                this._8e = s,
                this._mqa = e,
                s.isH = !0;
                var h = this,
                    r = {
                        onstart: function (t, i) {
                            i.classList.add(P_)
                        },
                        onrelease: function (t, i) {
                            i[br][Jh](P_)
                        },
                        ondrag: function (t, i) {
                            var n = h._msaseCanvas._k7;
                            if (n) {
                                var e = i.isH,
                                    s = e ? t.dx : t.dy;
                                if (s && i.scale) {
                                        var r = n[nf] / i[nf];
                                        e ? n.translate(-r * s, 0) : n[$o](0, -r * s),
                                        jz[bC](t)
                                    }
                            }
                        },
                        enddrag: function (t, i) {
                            var n = h[du]._k7;
                            if (n && n[lR]) {
                                var e = i.isH,
                                    s = e ? t.vx : t.vy;
                                if (Math.abs(s) > .1) {
                                        var r = n[nf] / i.scale;
                                        s *= r,
                                        e ? n._91(-s, 0) : n._91(0, -s)
                                    }
                            }
                        }
                    };
                this[_R] = new mi(e, r),
                this[cR] = new mi(s, r)
            },
            _36: function () {
                var t = this[du]._k7;
                t && t[HC](this._in[lr](this))
            },
            _in: function () {
                var t = this._msaseCanvas._k7;
                if (t) {
                    var i = t.bounds;
                    if (i[Lf]()) return this._47(!1),
                    void this._43(!1);
                    var n = t[qC],
                        e = t[Ca],
                        s = t[Da],
                        h = t[nf],
                        r = 1 / h,
                        a = n.x > i.x + r || n[Ur] < i[Ur] - r,
                        o = n.y > i.y + r || n[Yr] < i[Yr] - r,
                        f = a && o;
                    f ? (D(this[vR], bR), D(this._8e, bR)) : (R(this._mqa, bR), R(this._8e, bR)),
                    this._47(a, n, i, f ? e - oU : e),
                    this._43(o, n, i, f ? s - oU : s)
                }
            },
            _47: function (t, i, n, e) {
                if (!t) return this._8e[na][gR] = m_,
                void(this._8e[nf] = 0);
                var s = Math.min(i.x, n.x),
                    h = Math.max(i.right, n[Ur]),
                    r = h - s,
                    a = e / r;
                this._8e[nf] = a,
                this._8e[na][Bo] = parseInt((i.x - s) * a) + Ba,
                this._8e[na][Ur] = parseInt((h - i[Ur]) * a) + Ba,
                this._8e[na][gR] = ""
            },
            _43: function (t, i, n, e) {
                if (!t) return this._mqa[na][gR] = m_,
                void(this[vR][nf] = 0);
                var s = Math.min(i.y, n.y),
                    h = Math.max(i.bottom, n[Yr]),
                    r = h - s,
                    a = e / r;
                this[vR][nf] = a,
                this._mqa[na].top = parseInt((i.y - s) * a) + Ba,
                this[vR].style[Yr] = parseInt((h - i[Yr]) * a) + Ba,
                this[vR][na][gR] = ""
            }
        },
    fh[er] = {
            shape: null,
            initialize: function () {
                T(this, fh, pO),
                this[oC](),
                KY[CA](this)
            },
            _mq6: function () {
                this[Gm] = new iU(this[Af][Ck]),
                this[LA](this[Gm], 0),
                this[ZA] = this.image
            },
            invalidateShape: function () {
                this[Gm][iO](),
                this.invalidateRender()
            },
            _$w: function (t, i, n) {
                var e = this[MA][AA](this, t, i, n);
                return e = GY[AA](this, t, i, n) || e,
                KY.onBindingPropertyChange(this, t, i, n) || e
            },
            doValidate: function () {
                this[ZA] && (this.image[vo] = this[vo].path, this[ZA][rf] = null != this._2h, this[ZA][Lk] = this._2h);
                var t = this[Af][wk],
                    i = 0,
                    n = 0;
                t && (i = t.x, n = t.y);
                var e = this.$x != i || this.$y != n;
                return e && (this.$invalidateBounds = !0),
                this.$x = i,
                this.$y = n,
                JY[er][wO].call(this) || e
            }
        },
    p(fh, JY),
    Z(fh[er], {
            path: {
                get: function () {
                    return this[vo][Ck]
                }
            },
            length: {
                get: function () {
                    return this[vo][Hh]
                }
            }
        }),
    fh[er].addPoint = function (t, i, n) {
            var e = this._hb(t, i),
                s = this.data,
                h = pn(s[Ck], e.x, e.y, n);
            h && (s[aC] = h)
        },
    uh[er] = {
            _ly: function () {
                this._ik[na][yR] = tT
            },
            _ja: function () {
                this._ik[na].visibility = y_
            },
            clear: function () {
                this._92[Ra](),
                this[Iw]()
            },
            contains: function (t) {
                return t instanceof Object && t.id && (t = t.id),
                this._92.containsById(t)
            },
            _4x: function (t) {
                AY[bc](this._ik, __, t ? Jw + t.join(Ar) + ")" : "")
            },
            addDrawable: function (t, i) {
                if (i) {
                    var n = {
                        id: ++Xq,
                        drawable: t,
                        scope: i
                    };
                    return this._92.add(n),
                    n
                }
                return t.id || (t.id = ++Xq),
                this._92.add(t),
                t
            },
            removeDrawable: function (t) {
                return t.id ? void this._92.remove(t) : this._92.removeById(t)
            },
            _92: null,
            invalidate: function () {
                this[Iw]()
            },
            _muj: function () {
                this[du]._5s || this._ir()
            },
            _ib: function (t, i) {
                this._ik[Ha](t, i)
            },
            _ir: function () {
                var t = this._msaseCanvas[db],
                    i = this.g;
                i._kh(),
                i[Va](),
                this[du]._8o(i);
                for (var n = this._92._j8, e = 0, s = n[Hh]; s > e; e++) i.save(),
                i.beginPath(),
                this._gy(i, n[e], t),
                i.restore();
                i.restore()
            },
            _gy: function (t, i, n) {
                return i instanceof Function ? void i(t, n) : void(i[mR] instanceof Function && i[Gl] && i[mR].call(i.scope, t, n))
            }
        },
    _G.ZOOM_ANIMATE = !0;
    var fU = function (t) {
            this._k7 = t
        };
    _G[ER] = 600,
    _G[xR] = wz[pR],
    fU[er] = {
            _k7: null,
            _dk: null,
            _ge: function (t, i, n) {
                this._lo();
                var e = Math.abs(t / 2),
                    s = Math.abs(i / 2),
                    h = Math.min(_G[ER], .6 * Math.max(e, s) * 1e3);
                if (10 > h) return !1;
                var r = function (t) {
                        return -(2 * Math.pow(t, 1.5) - 3 * t)
                    },
                    a = t * h / 3,
                    o = i * h / 3;
                this._kl(this._k7.tx + a, this._k7.ty + o, 0, {
                        duration: h,
                        animationType: r
                    }, n)
            },
            _7d: function (t, i, n, e, s) {
                this._dk && this._dk._lo(),
                s && (this[wR] = !0, this._k7[TR](!0)),
                this._4f(),
                this._dk = new kz(t, this, i, n),
                this._dk._6g = this._6g[lr](this),
                this._dk._kj(e)
            },
            _4f: function () {
                this._k7.onAnimationStart()
            },
            _6g: function () {
                this[wR] && (this._k7[TR](!1), delete this[wR]),
                this._k7[kR]()
            },
            _dn: function () {
                return this._dk && this._dk._dn()
            },
            _lo: function () {
                this._dk && this._dk._lo()
            },
            _ia: function (t) {
                var i = this[OR] + (this[jR] - this[OR]) * t,
                    n = this[MR] + (this._toTY - this._fromTY) * t,
                    e = this[SR] + (this[IR] - this[SR]) * t;
                this._k7[uT](i, n, e, this.toInt)
            },
            _kl: function (t, i, n, e, s) {
                this._lo();
                var h = this._k7,
                    r = h.scale;
                if (0 >= n && (n = r), t != h.tx || i != h.ty || n != r) {
                        var a, o, f;
                        e instanceof Object && (a = e[AR], o = e[LR], f = e[CR]);
                        var u = h.tx,
                            c = h.ty;
                        if (!a) if (n != r) {
                                var _ = n > r ? n / r : r / n;
                                _ = Math.log(_) / Math.log(1.3),
                                a = 60 * _
                            } else {
                                var d = gG(t, i, u, c);
                                a = d / 2
                            }
                        o = o || _G[ER],
                        f = f || _G[xR],
                        a = Math.min(o, a),
                        this[OR] = u,
                        this._fromTY = c,
                        this[SR] = r,
                        this._toTX = t,
                        this[DR] = i,
                        this[IR] = n,
                        this._7d(this._ia, a, f, s, n != r)
                    }
            }
        },
    _G.INTERACTION_HANDLER_SIZE_TOUCH = 8,
    _G.INTERACTION_HANDLER_SIZE_DESKTOP = 4,
    _G.INTERACTION_ROTATE_HANDLER_SIZE_TOUCH = 30,
    _G[RR] = 20;
    var uU = Math.PI / 4;
    dh[er] = {
            onElementRemoved: function (t, i) {
                this.element && (t == this.element || $(t) && E(t, this[PR])) && this[dg](i)
            },
            onClear: function (t) {
                this.element && this.destroy(t)
            },
            destroy: function () {
                delete this[PR],
                this.removeDrawable()
            },
            invalidate: function () {
                this[G_][Sw]()
            },
            removeDrawable: function () {
                this._fkId && (this[G_][NR](this[BR]), delete this._fkId, this[Sw]())
            },
            addDrawable: function () {
                this[BR] || (this._fkId = this.topCanvas.addDrawable(this[$R], this).id, this[Sw]())
            },
            doDraw: function () {},
            escapable: !0,
            onkeydown: function (t, i) {
                this[FR] && 27 == t.keyCode && (G(t), this[dg](i))
            }
        },
    jz[qR] = dh,
    lh[er] = {
            defaultCursor: Y_,
            getInteractionInstances: function (t) {
                if (!this[z_]) return null;
                for (var i = [], n = 0, e = this[z_][Hh]; e > n; n++) {
                    var s = this[z_][n];
                    s instanceof Function ? i.push(new s(t)) : s instanceof Object && i.push(s)
                }
                return i
            }
        },
    vh[er] = {
            _dq: null,
            _jm: null,
            destroy: function () {
                T(this, vh, dg, arguments),
                delete this._jm,
                delete this._8q,
                delete this._dq
            },
            doDraw: function (t) {
                var i = this[xa];
                i && (i[Vf](function (i) {
                    this[GR](t, i)
                }, this), this.isClosePath && t[sf](), this.styleDraw(t))
            },
            styleDraw: function (t) {
                var i = bh(this.graph.interactionProperties, this.getDefaultDrawStyles(this[Yc]));
                i[bo] && (t[bo] = i[bo], i[j_] && (t[j_] = i.lineCap), i.lineJoin && (t[M_] = i[M_]), i[Df] && (t[Df] = i.lineDash, t.lineDashOffset = i.lineDashOffset || 0), t[S_] = i[S_], t.stroke()),
                i.fillStyle && (t.fillStyle = i[jm], t[Sm]())
            },
            drawPoint: function (t, i, n) {
                if (n) return void t[Pu](i.x, i.y);
                if (jz[wr](i)) {
                    var e = i[0],
                        s = i[1];
                    t[vm](e.x, e.y, s.x, s.y)
                } else t[Nu](i.x, i.y)
            },
            setCurrentPoint: function (t) {
                this.currentPoint = t
            },
            addPoint: function (t) {
                this._jm || (this._jm = [], this.addDrawable()),
                this._jm[tr](t),
                this[Sw]()
            }
        },
    p(vh, dh),
    Z(vh[er], {
            currentPoint: {
                get: function () {
                    return this._8q
                },
                set: function (t) {
                    this._8q = t,
                    this[Sw]()
                }
            },
            prevPoint: {
                get: function () {
                    return this._jm && this._jm[Hh] ? this._jm[this._jm.length - 1] : null
                }
            },
            points: {
                get: function () {
                    return this._8q && this._jm && this._jm[Hh] ? this._jm[Kh](this._8q) : void 0
                }
            }
        }),
    jz.DrawPathInteraction = vh,
    gh[er] = {
            destroy: function () {
                T(this, gh, dg, arguments),
                delete this[zR],
                this._ks && (clearTimeout(this._ks), delete this._ks)
            },
            doDraw: function (t, i) {
                return this._jm ? this._jm.length <= 1 ? Eh[er][$R][Yh](this, t, i) : void T(this, gh, $R, arguments) : void 0
            },
            ondblclick: function (t, i) {
                this[dg](i)
            },
            finish: function (t, i, n) {
                var e;
                this._jm && this._jm[Hh] >= 2 && (this._jm[Xx](), e = new dG, l(this._jm, function (t) {
                    if (jz.isArray(t)) {
                        var i = t[0],
                            n = t[1];
                        e.add(new fH(Mz[HR], [i.x, i.y, n.x, n.y]))
                    } else e.add(new fH(Mz[rE], [t.x, t.y]))
                }, this)),
                i.createEdgeByInteraction(this[zR], n, t, e),
                this[dg](i)
            },
            _msj: function (t, i) {
                return t instanceof RY && i.canLinkFrom(t)
            },
            _dt: function (t, i) {
                return t instanceof RY && i[FC](t, this[zR])
            },
            _9m: function (t, i) {
                var n = i.getUI(t);
                return n && n[Vc] ? n.bodyBounds[Jc] : t.location
            },
            onstart: function (t, i) {
                if (this[zR]) {
                    var n = t[ig]();
                    return this._dt(n, i) ? void this[YR](t, i, n) : void this[Pa](this.toLogicalPoint(t))
                }
            },
            startdrag: function (t, i) {
                if (!this[zR] && !t[UR]) {
                    var n = t[ig]();
                    n && this._msj(n, i) && (t[UR] = !0, this[zR] = n, this.addPoint(this._9m(n, i)))
                }
            },
            _mu4: function (t) {
                this._ks && (clearTimeout(this._ks), delete this._ks),
                this._ks = setTimeout(function (t) {
                    if (delete this._ks, this[zR] && this[WR]) {
                        var i = t.x - this.currentPoint.x,
                            n = t.y - this[WR].y;
                        Math[eo](i * i + n * n) * this.graph.scale <= 2 && this[Pa](this[WR])
                    }
                }.bind(this, this.toLogicalPoint(t)), _G[Gv])
            },
            ondrag: function (t, i) {
                this.onmousemove(t, i)
            },
            enddrag: function (t, i) {
                if (this.start) {
                    var n = t[ig]();
                    this._dt(n, i) && this.finish(t, i, n)
                }
            },
            onrelease: function (t, i) {
                ez(t) && this.destroy(i)
            },
            onmousemove: function (t, i) {
                this[zR] && (this[WR] = this[VR](t), ez(t) && this[XR](t, i))
            },
            toLogicalPoint: function (t) {
                return this[Yc].toLogical(t)
            },
            getDefaultDrawStyles: function () {
                return {
                    lineWidth: this[Yc][KR](qY[GM]),
                    strokeStyle: this[Yc].getDefaultStyle(qY.EDGE_COLOR),
                    lineDash: this[Yc].getDefaultStyle(qY.EDGE_LINE_DASH),
                    lineDashOffset: this[Yc][KR](qY.EDGE_LINE_DASH_OFFSET),
                    lineCap: this[Yc].getDefaultStyle(qY[xj]),
                    lineJoin: this[Yc][KR](qY[wj])
                }
            }
        },
    p(gh, vh),
    jz[ZR] = gh,
    yh.prototype = {
            getDefaultDrawStyles: function () {
                return {
                    lineWidth: this[Yc][KR](qY[VO]),
                    strokeStyle: this.graph[KR](qY.SHAPE_STROKE_STYLE),
                    fillStyle: this[Yc][KR](qY.SHAPE_FILL_COLOR)
                }
            },
            finish: function (t, i) {
                if (this._jm && this._jm.length) {
                    var n = this._jm,
                        e = 0,
                        s = 0,
                        h = 0;
                    n.forEach(function (t) {
                            return jz[wr](t) ? void t[Vf](function () {
                                e += t.x,
                                s += t.y,
                                h++
                            }) : (e += t.x, s += t.y, void h++)
                        }),
                    e /= h,
                    s /= h;
                    var r = [];
                    n[Vf](function (t, i) {
                            if (0 == i) return void r[tr](new fH(Mz[JR], [t.x - e, t.y - s]));
                            if (jz.isArray(t)) {
                                var n = t[0],
                                    h = t[1];
                                r.push(new fH(Mz[HR], [n.x - e, n.y - s, h.x - e, h.y - s]))
                            } else r[tr](new fH(Mz.SEGMENT_LINE_TO, [t.x - e, t.y - s]))
                        }),
                    this[qa](t, r, e, s),
                    this[dg](i)
                }
            },
            startdrag: function (t) {
                t[UR] = !0
            },
            createElement: function (t, i, n, e) {
                return this.graph.createShapeByInteraction(t, i, n, e)
            },
            onstart: function (t, i) {
                var n = i[fT](t);
                this._dq = n,
                this[Pa](n)
            },
            onmousemove: function (t, i) {
                this._dq && (this[WR] = i[fT](t))
            },
            ondblclick: function (t, i) {
                if (this._dq) {
                    if (this._jm[Hh] < 3) return void this[dg](i);
                    delete this._jm[this._jm[Hh] - 1],
                    this[YR](t, i)
                }
            },
            isClosePath: !0
        },
    p(yh, vh),
    jz[QR] = yh,
    mh.prototype = {
            isClosePath: !1,
            createElement: function (t, i, n, e) {
                return this[Yc].createLineByInteraction(t, i, n, e)
            },
            getDefaultDrawStyles: function () {
                return {
                    lineWidth: nU[qY.SHAPE_STROKE],
                    strokeStyle: nU[qY[KO]],
                    lineDash: this.graph[KR](qY.SHAPE_LINE_DASH),
                    lineDashOffset: this[Yc][KR](qY.SHAPE_LINE_DASH_OFFSET),
                    lineCap: this[Yc].getDefaultStyle(qY[xj]),
                    lineJoin: this.graph[KR](qY[wj])
                }
            }
        },
    p(mh, yh),
    jz[tP] = mh,
    Eh[er] = {
            destroy: function (t) {
                T(this, Eh, dg, arguments),
                t[ID] = "",
                this.start = null
            },
            doDraw: function (t) {
                if (this[zR] && this[WR]) {
                    var i, n;
                    this[Yc].interactionProperties && (i = this.graph[wD][qT], n = this[Yc].interactionProperties.edgeType),
                    i = i || this.graph[pD] || jz[iP],
                    n = n || this.graph[Wc];
                    var e = i[nP] || jz.EdgeUI.drawReferenceLine,
                        s = this[Yc][Uc](this[zR]);
                    s && s.bodyBounds && (s = s.bodyBounds[Jc], e(t, s, this[WR], n), this[eP](t))
                }
            },
            canLinkFrom: function (t, i) {
                return t instanceof RY && i[$C](t)
            },
            canLinkTo: function (t, i) {
                return t instanceof RY && i[FC](t, this[zR])
            },
            startdrag: function (t, i) {
                var n = t[ig]();
                this[$C](n, i) && (t[UR] = !0, this.start = n, i[ID] = Ig, this[sP]())
            },
            ondrag: function (t, i) {
                this[zR] && (jz[bC](t), this[WR] = i[fT](t), this.invalidate())
            },
            enddrag: function (t, i) {
                if (this[zR]) {
                    this.invalidate();
                    var n = t[ig]();
                    this[FC](n, i) && i[hP](this.start, n, t),
                    this[dg](i)
                }
            },
            getDefaultDrawStyles: function () {
                return {
                    lineWidth: this.graph[KR](qY.EDGE_WIDTH),
                    strokeStyle: this[Yc][KR](qY[HM]),
                    lineDash: this[Yc][KR](qY[KM]),
                    lineDashOffset: this.graph[KR](qY.EDGE_LINE_DASH_OFFSET),
                    lineCap: this[Yc][KR](qY[xj]),
                    lineJoin: this[Yc][KR](qY[wj])
                }
            }
        },
    p(Eh, vh),
    jz.CreateSimpleEdgeInteraction = Eh,
    _G.LABEL_EDITOR_SUBMIT_WHEN_LOST_FOCUS = !1,
    Oh.prototype = {
            html: null,
            createHTML: function () {
                var t = i[qa](rP);
                t[gu] = aP,
                t[na][Sf] = Ew,
                t[na][Ka] = Jc,
                t[na][oy] = oP,
                t.style.padding = fP,
                t[na][uP] = "0px 0px 10px rgba(40, 85, 184, 0.75)",
                t[na].display = m_,
                t[na][_w] = y_;
                var n = this;
                return t[cP] = function (t) {
                    n.onValueChange(t)
                },
                t[_P] = function (t) {
                    return 27 == t[Op] ? void n[dP]() : void 0
                },
                t[lP] = function (i) {
                    if (13 == i[Op] || 10 == i.keyCode) {
                        if (i[zv](), i[vP] || i[Ea] || i[kp]) return Th(t, Ja),
                        void n[bP](i);
                        n[gP]()
                    }
                },
                i.body[bu](t),
                t
            },
            setText: function (t, i) {
                this[cD].value = t || "",
                i && (this[cD][na][K_] = i),
                kh(this[cD]),
                this.onSizeChange(this.html)
            },
            onSizeChange: function (t) {
                var i = (t[U_], t[W_], wh(t));
                return t[na][Ca] = i.width + 30 + Ba,
                t[na][Da] = i.height + 10 + Ba,
                i
            },
            onValueChange: function (t) {
                var i = t[R_];
                this.onSizeChange(i),
                i.style.left = i.x - i.offsetWidth / 2 + Ba
            },
            onClickOnWindow: function (t) {
                t.target != this[cD] && (_G[yP] ? this[gP]() : this.cancelEdit())
            },
            startEdit: function (i, n, e, s, h) {
                this[cD] || (this[cD] = this.createHTML()),
                this[mP] || (this[mP] = function (t) {
                    this.onClickOnWindow(t)
                }[lr](this)),
                t[Cv](Tb, this[mP], !0),
                this[_c] = h,
                this[cD].x = i,
                this.html.y = n,
                this[cD][na][gR] = YD,
                ph(this[cD], i, n),
                this[EP](e, s || 10),
                ph(this[cD], i, n)
            },
            isEditing: function () {
                return m_ != this[cD][na][gR]
            },
            cancelEdit: function () {
                this[gP](!0)
            },
            stopEdit: function (i) {
                if (this[xP]()) {
                    t.removeEventListener(Tb, this[mP]);
                    var n = this[cD][cr];
                    if (!i && this[_c] && this[_c](n) === !1) return !1;
                    this.html[na][gR] = m_,
                    this[cD].value = null,
                    this[_c] = null
                }
            },
            destroy: function () {
                this[cD] && i.body.removeChild(this[cD])
            }
        },
    jz[pP] = Oh;
    var cU = function (t) {
            this[Yc] = t
        };
    cU[er] = {
            destroy: function (t) {
                t[wP] && (t.labelEditor[dg](), delete t[wP])
            },
            ondblclick: function (t, i) {
                var n = t[ig]();
                if (n) {
                    if (n[TP] !== !1) {
                        if (i[iC] && i[kP](n)) {
                            var e = i.hitTest(t);
                            if (e instanceof tU && e[iC] !== !1) {
                                var s = i[wP];
                                s || (i[wP] = s = new Oh);
                                var h = e[oo]();
                                return h = i[BT](h.x + h[Ca] / 2, h.y + h[Da] / 2),
                                h = xh(h.x, h.y, i[cD]),
                                void i[OP](n, e, s, h)
                            }
                        }
                        var r = n instanceof BY,
                            a = n instanceof DY && n[jP]();
                        return n._3x && (Ei(t) || !r && !a) ? void(i.currentSubNetwork = n) : r ? (n[GT] = !n[GT], void this[Yc][gD](new Ch(this[Yc], Ch[zk], t, n))) : void(a && this.graph[oD](n) && this.graph.onInteractionEvent(new Ch(this[Yc], Ch[MP], t, n)))
                    }
                } else {
                    if (i[qk]) return void i[SP]();
                    if (i.enableDoubleClickToOverview) {
                        var o = i[IP] || 1;
                        Math.abs(i[nf] - o) < 1e-4 ? i.zoomToOverview() : i[AP](o)
                    }
                }
            }
        };
    var _U = function (t) {
            this[Yc] = t
        };
    _U.prototype = {
            onkeydown: function (t, i) {
                if (i.editable) {
                    var n = t.keyCode;
                    if (8 == n || 46 == n || 127 == n) return i[LP](t),
                    void F(t);
                    if (Ei(t)) {
                        if (67 == n);
                        else if (86 == n);
                        else if (90 == n);
                        else if (89 != n) return;
                        F(t)
                    }
                }
            }
        },
    jz.EditInteraction = _U;
    var dU = function (t) {
            this[Yc] = t
        };
    dU[er] = {
            onkeydown: function (i, n) {
                if (i[ma] && 83 == i[Op]) {
                    var e = n[CP](n[nf], n[qC]),
                        s = t[DP](),
                        h = s[RP];
                    h.title = PP + e[Ca] + NP + e.height;
                    var r = h.createElement(f_);
                    r.src = e[vo],
                    h[ZA][bu](r),
                    F(i)
                }
            }
        };
    var lU = function (t) {
            this[Yc] = t
        };
    lU.prototype = {
            destroy: function () {
                delete this.draggingElements,
                delete this.currentDraggingElement
            },
            _1y: function (t) {
                var i = new dG;
                return t.selectionModel[Vf](function (n) {
                    t[rD](n) && t[BP](n) && i.add(n)
                }, this),
                i
            },
            onstart: function (t, i) {
                this[$P] && this[dg](i)
            },
            startdrag: function (t, i) {
                if (!(t[UR] || t[Fr] && 1 != t.touches[Hh])) {
                    var n = t[ig]();
                    if (!n || !i.isSelected(n) || !i[rD](n)) return void this.destroy(i);
                    t.responded = !0,
                    this.currentDraggingElement = n,
                    this.draggingElements = this._1y(i);
                    var e = new Ch(i, Ch[FP], t, this[$P], this.draggingElements[wv]);
                    return i[qP](e) === !1 ? void this[dg](i) : void i.onInteractionEvent(e)
                }
            },
            ondrag: function (t, i) {
                if (this[$P]) {
                    if (t[Fr] && 1 != t[Fr].length) return void this[tg](t, i);
                    G(t);
                    var n = t.dx,
                        e = t.dy,
                        s = i.scale;
                    n /= s,
                    e /= s;
                    var h = new Ch(i, Ch.ELEMENT_MOVING, t, this.currentDraggingElement, this[GP][wv]);
                    i.moveElements(this[GP][wv], n, e),
                    i.onInteractionEvent(h)
                }
            },
            enddrag: function (t, i) {
                if (this.currentDraggingElement) {
                    if (this.draggingElements && this[GP].length) {
                        if (t.shiftKey) {
                            var n, e = i[fT](t),
                                s = e.x,
                                h = e.y;
                            i.forEachReverseVisibleUI(function (t) {
                                    var i = t[vo];
                                    if (!this[GP].contains(i) && t.uiBounds.intersectsPoint(s - t.x, h - t.y) && t.hitTest(s, h, 1)) {
                                        if (i instanceof jz.Edge) {
                                            if (!i[r_]) return;
                                            for (var e = this.draggingElements.length; e-- > 0;) {
                                                var r = this[GP].get(e);
                                                if (r instanceof jz[xC] && r.linkedWith(i)) return
                                            }
                                            return n = i,
                                            !1
                                        }
                                        return (i[r_] || i._hh() && i.expanded) && (n = i),
                                        !1
                                    }
                                }, this),
                            n && this[GP][Vf](function (t) {
                                    for (var i = t[yu]; i;) {
                                        if (this[GP][i_](i)) return;
                                        i = i[yu]
                                    }
                                    t[yu] = n
                                }, this)
                        }
                        var r = new Ch(i, Ch[zP], t, this[$P], this[GP].datas);
                        i[gD](r)
                    }
                    this.destroy(i)
                }
            },
            onpinch: function (t, i) {
                this.currentDraggingElement && this[tg](t, i)
            },
            step: 1,
            onkeydown: function (t, i) {
                if (Ei(t)) {
                    var n, e;
                    if (37 == t[Op] ? n = -1 : 39 == t.keyCode ? n = 1 : 38 == t.keyCode ? e = -1 : 40 == t[Op] && (e = 1), n || e) {
                        var s = this._1y(i)[wv];
                        if (0 != s.length) {
                            F(t),
                            n = n || 0,
                            e = e || 0;
                            var h = this[HP] / i[nf],
                                r = new Ch(i, Ch.ELEMENT_MOVE_END, t, null, s);
                            i.moveElements(s, n * h, e * h),
                            i[gD](r)
                        }
                    }
                }
            }
        };
    var vU = function (t) {
            this[Yc] = t
        };
    vU[er] = {
            onkeydown: function (t, i) {
                Ei(t) || (37 == t[Op] ? (this._54(i, 1, 0), F(t)) : 39 == t.keyCode ? (this._54(i, -1, 0), F(t)) : 38 == t[Op] ? (this._54(i, 0, 1), F(t)) : 40 == t[Op] && (this._54(i, 0, -1), F(t)))
            },
            _54: function (t, i, n) {
                t._91(i, n)
            },
            onstart: function (t, i) {
                this._kj && this.destroy(i)
            },
            _kj: !1,
            startdrag: function (t, i) {
                if (!t.responded) {
                    t[UR] = !0,
                    this._kj = !0,
                    i.cursor = gz;
                    var n = new Ch(i, Ch[YP], t);
                    i[gD](n)
                }
            },
            ondrag: function (t, i) {
                this._kj && i[$o](t.dx || 0, t.dy || 0)
            },
            enddrag: function (t, i) {
                if (this._kj) {
                    if (i[lR] !== !1) {
                        var n = t.vx,
                            e = t.vy;
                            (Math.abs(n) > .1 || Math.abs(e) > .1) && i._91(n, e)
                    }
                    this.destroy(i);
                    var s = new Ch(i, Ch[UP], t);
                    i[gD](s)
                }
            },
            startpinch: function (t, i) {
                i[TR](!0)
            },
            onpinch: function (t, i) {
                this._kj = !0;
                var n = t[WP];
                if (n) {
                    var e = i[EC](t.center);
                    i[UC](n, e.x, e.y, !1)
                }
            },
            endpinch: function (t, i) {
                i.pauseRendering(!1)
            },
            destroy: function (t) {
                this._kj = !1,
                t[ID] = null
            }
        },
    jh[er] = {
            _1q: function (t) {
                this[PR] && t[Fo] == this[PR] && this.graph[HC](function () {
                    this._in()
                }, this)
            },
            _6: function () {
                this[VP] || (this[VP] = !0, this.graph.dataPropertyChangeDispatcher.addListener(this._1q, this))
            },
            _4: function () {
                this._lrPropertyChangeListing = !1,
                this.graph[XP][zl](this._1q, this)
            },
            onElementRemoved: function (t, i) {
                this[PR] && (t == this[PR] || Array[wr](t) && E(t, this[PR])) && this[dg](i)
            },
            onClear: function (t) {
                this[PR] && this[dg](t)
            },
            destroy: function () {
                this.graph[ID] = null,
                this[PR] && delete this[PR][KP],
                this[ZP] = !1,
                delete this[PR],
                delete this._9d,
                delete this._8q,
                delete this[JP],
                this._6y(),
                this._4()
            },
            _6y: function () {
                this.drawLineId && (this[G_][NR](this.drawLineId), delete this[QP], this[G_][Sw]())
            },
            _msd: function () {
                this[QP] && this.topCanvas.contains(this[QP]) || (this[QP] = this.topCanvas.addDrawable(this[tN], this).id, this.topCanvas.invalidate())
            },
            _9d: null,
            _5g: function (t) {
                this._9d = t,
                this[Sw]()
            },
            _dr: function (t, i, n) {
                t[TE](),
                i[iN] ? t.rect(i.x - this[hd] / n, i.y - this[hd] / n, this.handlerSize / n * 2, this[hd] / n * 2) : t.arc(i.x, i.y, this[hd] / n, 0, 2 * Math.PI, !1),
                t[bo] = 1 / n,
                t[Df] = [],
                t[S_] = L_,
                t.fillStyle = "rgba(255, 255, 0, 0.8)",
                t[lo](),
                t.fill()
            },
            _fk: function (t, i, n, e) {
                e ? t.moveTo(i, n) : t.lineTo(i, n)
            },
            drawLine: function (t, i) {
                if (this._9d && this._9d[Hh]) {
                    t[Va]();
                    var n = this[PR] instanceof PY;
                    n && (t.translate(this.element.x, this[PR].y), this.element[mo] && t[mo](this[PR][mo]));
                    var e, s = [];
                    t[TE](),
                    this._9d[Hh],
                    this._9d[Vf](function (i) {
                        if (i[yo] != Mz[nN]) for (var n = 0, h = i[xa]; n + 1 < h.length;) {
                            var r = h[n],
                                a = h[n + 1],
                                o = {
                                    x: r,
                                    y: a,
                                    isControlPoint: this._74(i, n)
                                };
                            s[tr](o),
                            this._fk(t, o.x, o.y, null == e),
                            e = o,
                            n += 2
                        }
                    }, this),
                    t[bo] = 1 / i,
                    t.lineDash = [2 / i, 3 / i],
                    t[S_] = eN,
                    t[lo](),
                    s[Vf](function (n) {
                        this._dr(t, n, i)
                    }, this),
                    t.restore()
                }
            },
            invalidate: function () {
                this[G_].invalidate()
            },
            _37: function (t) {
                if (this[PR] != t && (this[PR] && this[dg](), t && this.isEditable(t))) {
                    var i = this._5k(t, this[Yc]);
                    i && (this.element = t, t._editting = !0, this[JP] = !0, this._5g(i), this._6(), this[sN]())
                }
            },
            _in: function () {
                if (this.drawLineId && this[PR]) {
                    var t = this._5k(this[PR], this.graph);
                    return t ? void this._5g(t) : void this[dg](this.graph)
                }
            },
            _5k: function (t, i) {
                if (i.isEditable(t)) {
                    var n = t[aC] || [];
                    n instanceof dG && (n = n.toDatas());
                    var e = i[Uc](t);
                    if (e instanceof jz.EdgeUI) {
                        var s = t[pu],
                            h = t[Eu],
                            r = i[Uc](s),
                            a = i.getUI(h),
                            o = r[Vc][Qh](),
                            f = a[Vc][Qh](),
                            u = o.center,
                            c = f[Jc],
                            _ = e[gc](jz[ED][Xc]),
                            d = e[gc](jz[ED][Kc]);
                        _ && (u.x += _.x || 0, u.y += _.y || 0),
                        d && (c.x += d.x || 0, c.y += d.y || 0),
                        n[Xh](0, 0, new jz[oE](Mz[JR], [u.x, u.y])),
                        n.push(new jz[oE](Mz[JR], [c.x, c.y]))
                    }
                    return n
                }
            },
            _hb: function (t, i) {
                t -= this.element.x,
                i -= this[PR].y;
                var n = {
                    x: t,
                    y: i
                };
                return this[PR][mo] && qs(n, -this[PR].rotate),
                n
            },
            onclick: function (t, i) {
                if (i[iC] && t[vP] && this.element) {
                    var n = this._fu(t, i);
                    if (n && n.isControlPoint) return void this.element[hN](n[Yl]);
                    if (this[PR] == t[ig]()) {
                        var e = i[fT](t),
                            s = i[Uc](this[PR]);
                        s[Pa](e.x, e.y, this[hd] || 2)
                    }
                }
            },
            isEditable: function (t) {
                return this[Yc].isEditable(t) && (t instanceof PY || t instanceof DY && (!t[Ou]() || t.hasPathSegments()))
            },
            ondblclick: function (t, i) {
                if (!i[iC]) return void(this.element && this.destroy(i));
                var n = t.getData();
                return !n || n == this[PR] || n._editting ? void this[dg](i) : void this._37(n)
            },
            onstart: function (t, i) {
                if (this._mousePressed = !0, !i[iC]) return void(this.element && this[dg](i));
                if (!t.responded) {
                    if (this[PR] && this._fu(t, i)) return void(t[UR] = !0);
                    var n = t[ig]();
                    return n && i[rN](n) && n instanceof PY ? void(this[PR] && n != this[PR] && this[dg]()) : void this._37(n)
                }
            },
            onrelease: function () {
                this._mousePressed = !1,
                this[PR] && (this._muanEdit = !0)
            },
            _8q: null,
            _fu: function (t, i) {
                var n = i[fT](t);
                this.element instanceof PY && (n = this._hb(n.x, n.y));
                var e, s = i.scale,
                    h = this.handlerSize / s,
                    r = this._9d,
                    a = r[Hh],
                    o = this[PR] instanceof jz[xD];
                return r.forEach(function (t, s) {
                        for (var f = 0, u = t[xa]; f + 1 < u[Hh];) {
                            var c = u[f],
                                _ = u[f + 1],
                                d = gG(n.x, n.y, c, _);
                            if (h > d) {
                                    if (e = {
                                        oldPoints: u[Vh](0),
                                        segment: t,
                                        index: s,
                                        pointIndex: f
                                    }, o && (e[Yl] -= 1), o && !Mh(t) && (0 == s || s == r[Hh] - 1)) {
                                        e[aN] = !0;
                                        var l = 0 == s;
                                        e[oN] = l;
                                        var v = l ? jz.Styles.EDGE_FROM_OFFSET : jz[ED][Kc],
                                            b = i[gc](this[PR], v) || {};
                                        e[fN] = [b.x || 0, b.y || 0]
                                    }
                                    return this._74(t, f) && (e[iN] = !0, s > 0 && (e.prevSegment = r instanceof dG ? r[Nd](s - 1) : r[s - 1]), a > s + 1 && (e[uN] = r instanceof dG ? r.getByIndex(s + 1) : r[s + 1], e[uN][xa] && (e.oldNextPoints = e.nextSegment.points.slice(0)))),
                                    !1
                                }
                            f += 2
                        }
                    }, this),
                e
            },
            _74: function (t, i) {
                return i == t[xa][Hh] - 2
            },
            startdrag: function (t, i) {
                if (this[PR] && this[JP] && (this._8q = this._fu(t, i), this._8q)) {
                    this._6y(),
                    t[UR] = !0;
                    var n = new Ch(i, Ch[cN], t, this[PR]);
                    n[_N] = this._8q,
                    i[gD](n)
                }
            },
            onkeyup: function (t, i) {
                this._mousePressed && 16 != !t.keyCode && this.element && this._8q && this._8q[Lb] && this[dN](this._8q[Lb].x, this._8q.delta.y, i, t, !1)
            },
            onkeydown: function (t, i) {
                this._mousePressed && this[PR] && this._8q && t.shiftKey && this._8q[Lb] && this[dN](this._8q.delta.x, this._8q[Lb].y, i, t, !0)
            },
            _mud: function (t, i, n, e, s) {
                var h = this._8q,
                    r = this[PR],
                    a = h.oldPoints,
                    o = h[lN];
                if (h[aN]) {
                        var f = h[oN] ? jz.Styles.EDGE_FROM_OFFSET : jz[ED][Kc],
                            u = {
                                x: a[0] + t,
                                y: a[1] + i
                            };
                        return void r.setStyle(f, u)
                    }
                if (s && h[iN]) {
                        var c = {
                            x: a[a[Hh] - 2] + t,
                            y: a[a[Hh] - 1] + i
                        },
                            _ = h[vN],
                            d = h.nextSegment,
                            l = 20 / n[nf],
                            v = Number[il],
                            b = v,
                            g = v,
                            y = v;
                        _ && (_ = _.lastPoint, v = Math.abs(c.x - _.x), g = Math.abs(c.y - _.y)),
                        d && (d = d[go], b = Math.abs(c.x - d.x), y = Math.abs(c.y - d.y)),
                        l > v && b > v ? t += _.x - c.x : l > b && v > b && (t += d.x - c.x),
                        l > g && y > g ? i += _.y - c.y : l > y && g > y && (i += d.y - c.y)
                    }
                if (h[iN] && Mh(o)) {
                        for (var m = o.points.length - 4; m < o[xa].length;) {
                            var E = a[m] + t,
                                x = a[m + 1] + i;
                            o.points[m] = E,
                            o.points[m + 1] = x,
                            m += 2
                        }
                        if (h[uN] && Mh(h.nextSegment)) {
                            var p = h.oldNextPoints,
                                E = p[0] + t,
                                x = p[1] + i;
                            h[uN][xa][0] = E,
                            h[uN][xa][1] = x
                        }
                    } else {
                        var m = h[bN],
                            E = a[m] + t,
                            x = a[m + 1] + i;
                        o[xa][m] = E,
                        o.points[m + 1] = x
                    }
                r[lk]();
                var w = new Ch(n, Ch[gN], e, r);
                w[_N] = h,
                n.onInteractionEvent(w)
            },
            ondrag: function (t, i) {
                if (this.element && this._8q) {
                    var n = this._8q,
                        e = this[PR],
                        s = t[Eg],
                        h = t[xg],
                        r = i.scale;
                    if (s /= r, h /= r, e.rotate) {
                            var a = {
                                x: s,
                                y: h
                            };
                            qs(a, -e.rotate),
                            s = a.x,
                            h = a.y
                        }
                    n.delta = {
                            x: s,
                            y: h
                        },
                    this[dN](s, h, i, t, t.shiftKey)
                }
            },
            enddrag: function (t, i) {
                if (this.element && this._8q) {
                    this[sN](),
                    this._in();
                    var n = new Ch(i, Ch[yN], t, this[PR]);
                    n[_N] = this._8q,
                    i[gD](n)
                }
            },
            onmousemove: function (t, i) {
                this[PR] && (i[ID] = t[vP] && (this._fu(t, i) || this.element == t.getData()) ? "crosshair" : null)
            }
        },
    _G[mN] = 1,
    _G[EN] = X(3724541951),
    _G[xN] = X(1430753245);
    var bU = function (t) {
            this[Yc] = t,
            this[G_] = t._88[wT]
        };
    bU[er] = {
            onstart: function (t, i) {
                this._kj && this[dg](i)
            },
            startdrag: function (t, i) {
                t[UR] || (t[UR] = !0, this._kj = i[fT](t), i.cursor = Ig, this[pN] = this.topCanvas[sP](this._12, this).id)
            },
            ondrag: function (t, i) {
                if (this._kj) {
                    G(t),
                    this[wN] = i[fT](t),
                    this[Sw]();
                    var n = new Ch(i, Ch[TN], t, i[B_]);
                    i[gD](n)
                }
            },
            enddrag: function (t, i) {
                if (this._kj) {
                    this[kN] && (clearTimeout(this[kN]), this[kN] = null),
                    this._f6(!0),
                    this[dg](i);
                    var n = new Ch(i, Ch[ON], t, i.selectionModel);
                    i.onInteractionEvent(n)
                }
            },
            onpinch: function (t, i) {
                this._kj && this.enddrag(t, i)
            },
            _12: function (t, i) {
                t[S_] = _G[EN],
                t[jm] = _G.SELECTION_RECTANGLE_FILL_COLOR,
                t.lineWidth = _G.SELECTION_RECTANGLE_STROKE / i;
                var n = this._kj.x,
                    e = this._kj.y;
                t[Ty](n, e, this[wN].x - n, this[wN].y - e),
                t[Sm](),
                t.stroke()
            },
            invalidate: function () {
                return this[Hk] ? void this.topCanvas[Sw]() : (this[Hk] = !0, void(this._f6Timer = setTimeout(this._f6[lr](this), 100)))
            },
            _f6: function (t) {
                if (this[Hk] = !1, this._f6Timer = null, !this._kj || tG && !t) return void this[G_].invalidate();
                var i = Math.min(this._kj.x, this._end.x),
                    n = Math.min(this._kj.y, this._end.y),
                    e = Math.abs(this._kj.x - this._end.x),
                    s = Math.abs(this._kj.y - this[wN].y);
                if (!e || !s) return void this[Yc].selectionModel.clear();
                var h, r = [],
                    a = this[Yc][nf];
                if (this[Yc][jN](function (t) {
                        t._h9 && this[Yc][MN](t[Af]) && (h = t._fa, (ai(i, n, e, s, h.x + t._x, h.y + t._y, h.width, h.height) || An(i, n, e, s, t, a)) && r[tr](t[Af]))
                    }, this), this[Yc][B_].set(r), this[G_][Sw](), !t) {
                        var o = new Ch(this.graph, Ch[SN], null, this[Yc][B_]);
                        this[Yc][gD](o)
                    }
            },
            destroy: function (t) {
                this._kj = null,
                t.cursor = null,
                this[pN] && (this[G_].removeDrawable(this[pN]), delete this[pN], this[G_].invalidate())
            }
        };
    var gU = M({
            "super": bU,
            onstart: null,
            startdrag: null,
            ondrag: null,
            enddrag: null,
            accept: function (t, i, n) {
                return n.enableRectangleSelectionByRightButton !== !1
            },
            oncontextmenu: function (t, i) {
                i[IN] || G(t)
            },
            onstart2: function () {
                bU.prototype[Hb][nr](this, arguments)
            },
            startdrag2: function (t, i) {
                t[UR] || (i[IN] && i[IN][AN] instanceof Function && i.popupmenu[AN](), bU.prototype[Vb][nr](this, arguments))
            },
            ondrag2: function () {
                bU[er][Zb].apply(this, arguments)
            },
            enddrag2: function () {
                bU.prototype[tg][nr](this, arguments)
            }
        }),
        uU = Math.PI / 4;
    Sh.prototype = {
            _dv: !1,
            _dw: !1,
            _1q: function (t) {
                this[PR] && t.source == this.element && this.graph.callLater(function () {
                    this._9u()
                }, this)
            },
            _6: function () {
                this[VP] || (this._lrPropertyChangeListing = !0, this[Yc][XP].addListener(this._1q, this))
            },
            _4: function () {
                this[VP] = !1,
                this[Yc].dataPropertyChangeDispatcher.removeListener(this._1q, this)
            },
            onElementRemoved: function (t, i) {
                this[PR] && (t == this.element || $(t) && E(t, this[PR])) && this[dg](i)
            },
            onClear: function (t) {
                this[PR] && this.destroy(t)
            },
            ondblclick: function (t, i) {
                this.element && this[dg](i)
            },
            destroy: function (t) {
                t.cursor = null,
                delete this[PR],
                delete this._d6,
                delete this._msody,
                delete this._8q,
                delete this._muanEdit,
                delete this._jm,
                delete this[LN],
                delete this._dw,
                delete this._dv,
                delete this._insets,
                this._6y(),
                this._4()
            },
            _6y: function () {
                this[BR] && (this[G_][NR](this[BR]), delete this._fkId, this[G_][Sw]())
            },
            _msd: function () {
                this._fkId && this[G_].contains(this[BR]) || (this._fkId = this[G_][sP](this._fk, this).id, this[G_][Sw]())
            },
            _d6: null,
            _jm: null,
            _7n: function (t) {
                this._d6 = t;
                var i = this._d6.x,
                    n = this._d6.y,
                    e = this._d6[Ca],
                    s = this._d6[Da];
                if (this[PR] instanceof BY && this.element[GT], this._dw) {
                        var h = [];
                        h[tr]({
                            x: i,
                            y: n,
                            p: pG[ul],
                            cursor: CN,
                            rotate: 5 * uU
                        }),
                        h.push({
                            x: i + e / 2,
                            y: n,
                            p: pG.CENTER_TOP,
                            cursor: DN,
                            rotate: 6 * uU
                        }),
                        h.push({
                            x: i + e,
                            y: n,
                            p: pG[gl],
                            cursor: rd,
                            rotate: 7 * uU
                        }),
                        h[tr]({
                            x: i,
                            y: n + s / 2,
                            p: pG.LEFT_MIDDLE,
                            cursor: RN,
                            rotate: 4 * uU
                        }),
                        h[tr]({
                            x: i,
                            y: n + s,
                            p: pG.LEFT_BOTTOM,
                            cursor: rd,
                            rotate: 3 * uU
                        }),
                        h[tr]({
                            x: i + e,
                            y: n + s / 2,
                            p: pG[yl],
                            cursor: RN,
                            rotate: 0
                        }),
                        h[tr]({
                            x: i + e / 2,
                            y: n + s,
                            p: pG[vl],
                            cursor: DN,
                            rotate: 2 * uU
                        }),
                        h[tr]({
                            x: i + e,
                            y: n + s,
                            p: pG[bl],
                            cursor: CN,
                            rotate: uU
                        }),
                        this._jm = h
                    }
                this[LN] = this._dv ? {
                        x: i + e / 2,
                        y: n,
                        cursor: yz
                    } : null,
                this[Iw]()
            },
            _dr: function (t, i, n, e) {
                t[TE]();
                var s = (this.handlerSize - 1) / e;
                t[Ty](i - s, n - s, 2 * s, 2 * s),
                t.lineWidth = 1 / e,
                t[Df] = [],
                t.strokeStyle = L_,
                t[jm] = "rgba(255, 255, 255, 0.8)",
                t[lo](),
                t[Sm]()
            },
            _5p: function (t, i, n, e, s, h) {
                s = s || this.handlerSize,
                h = h || PN,
                t[TE](),
                s /= e,
                t.arc(i, n, s, 0, 2 * Math.PI, !1),
                t[bo] = 1 / e,
                t[Df] = [],
                t.strokeStyle = L_,
                t.fillStyle = h,
                t[lo](),
                t.fill()
            },
            _hb: function (t, i) {
                t -= this[PR].x,
                i -= this[PR].y;
                var n = {
                    x: t,
                    y: i
                };
                return this[PR][mo] && qs(n, -this[PR].rotate),
                n
            },
            _fk: function (t, i) {
                if (this._d6) {
                    if (t[Va](), t[$o](this[PR].x, this[PR].y), this.element.rotate && t.rotate(this[PR][mo]), this[LN]) {
                        this._5p(t, 0, 0, i, 3, NN);
                        var n = this._rotatePoint.x,
                            e = this[LN].y - this[BN] / i;
                        t[TE](),
                        t[Pu](n, this[LN].y),
                        t.lineTo(n, e),
                        t[bo] = 1 / i,
                        t.strokeStyle = eN,
                        t.stroke(),
                        this._5p(t, n, e, i)
                    }
                    if (this._jm) {
                        var s = this._d6.x,
                            h = this._d6.y,
                            r = this._d6[Ca],
                            a = this._d6[Da];
                        t[TE](),
                        t.rect(s, h, r, a),
                        t[bo] = 1 / i,
                        t.lineDash = [2 / i, 3 / i],
                        t[S_] = eN,
                        t[lo](),
                        l(this._jm, function (n) {
                                this._dr(t, n.x, n.y, i)
                            }, this)
                    }
                    t.restore()
                }
            },
            _muj: function () {
                this[G_][Sw]()
            },
            _37: function (t, i, n, e) {
                this.element = t,
                this[sN]();
                var s = i[Uc](t);
                this[zA] = s[ZA],
                this._dw = n,
                this._dv = e,
                this._9u(),
                this._6()
            },
            _9u: function () {
                if (this._fkId) {
                    var t = Ih(this._msody, this[zA]._iv),
                        i = Ih(this[zA], this[zA]._7i);
                    this[$N] = new xG(t.y - i.y, t.x - i.x, i[Yr] - t[Yr], i[Ur] - t[Ur]),
                    this._7n(i)
                }
            },
            _msl: function (t, i) {
                return i.isResizable(t)
            },
            _msn: function (t, i) {
                return (!t._hh() || !t[GT]) && i[FN](t)
            },
            _d0: function (t, i) {
                return t instanceof RY && i[kP](t)
            },
            onstart: function (t, i) {
                if (!i[iC]) return void(this[PR] && this.destroy(i));
                if (!t[UR]) {
                    var n = i.getUI(t),
                        e = t[ig]();
                    if (e != this[PR]) {
                            if (this.element) {
                                if (this._fu(t, i)) return void(t.responded = !0);
                                this[dg](i)
                            }
                            if (e && !e[KP] && this._d0(e, i)) {
                                var s = this[qN](e, i, n),
                                    h = this[GN](e, i, n);
                                    (s || h) && this._37(e, i, s, h)
                            }
                        }
                }
            },
            onrelease: function (t, i) {
                this[PR] && (this[JP] = !0, this._msd(), i.callLater(function () {
                    this._9u()
                }, this))
            },
            _8q: null,
            _fu: function (t, i) {
                var n = i[fT](t);
                n = this._hb(n.x, n.y);
                var e = i.scale,
                    s = this[hd] / e;
                if (this[LN]) {
                        var h = this[LN].x,
                            r = this[LN].y - this[BN] / e;
                        if (gG(n.x, n.y, h, r) < s) return this[LN]
                    }
                if (this._jm && this._jm[Hh]) {
                        var a;
                        return l(this._jm, function (t) {
                            return gG(n.x, n.y, t.x, t.y) < s ? (a = t, !1) : void 0
                        }, this),
                        a
                    }
            },
            onmousemove: function (t, i) {
                if (this[PR]) {
                    var n = this._fu(t, i);
                    if (!n) return void(i[ID] = null);
                    if (n != this[LN] && this[PR].rotate) {
                        var e = n.rotate + this[PR].rotate;
                        return void(i.cursor = Ah(e))
                    }
                    i.cursor = n[ID]
                }
            },
            startdrag: function (t, i) {
                if (this[PR] && (this._6y(), this[JP] && (this._8q = this._fu(t, i), this._8q))) {
                    if (t.responded = !0, this._8q == this._rotatePoint) return this._8q[zR] = i.toLogical(t),
                    void(this._8q.rotate = this[PR].rotate || 0);
                    var n = new Ch(i, Ch[zN], t, this[PR]);
                    n.point = this._8q,
                    i.onInteractionEvent(n)
                }
            },
            _7f: function (t, i, n, e, s, h) {
                var r = this._d6,
                    a = r.x,
                    o = r.y,
                    f = r.width,
                    u = r[Da];
                if (h) {
                        var c = e != f;
                        c ? s = e * u / f : e = s * f / u
                    }
                var _ = t.path._f8,
                    d = e / f,
                    l = s / u,
                    v = -a * d + i,
                    b = -o * l + n;
                _.forEach(function (t) {
                        if (t[yo] != Mz[nN]) {
                            var e = t[xa];
                            if (e && e[Hh]) for (var s = 0, h = e.length; h > s; s += 2) {
                                var r = e[s],
                                    f = e[s + 1];
                                e[s] = (r - a) * d + i - v,
                                e[s + 1] = (f - o) * l + n - b
                            }
                        }
                    }),
                this._d6.set(i - v, n - b, e, s),
                t[hD](t.x + v, t.y + b),
                t.firePathChange()
            },
            _9h: function (t, i, n, e, s) {
                this._d6.set(i, n, e, s),
                t[Xk] = {
                    x: i,
                    y: n,
                    width: e,
                    height: s
                }
            },
            _4n: function (t, i, n, e, s) {
                if (this[PR] instanceof BY) return this._9h(this[PR], t, i, n, e, s);
                if (this[PR] instanceof PY) return this._7f(this.element, t, i, n, e, s);
                var h = this[zA] instanceof tU;
                if (!h && s) {
                    var r = this._d6,
                        a = this[zA][HN],
                        o = n != r[Ca];
                    o ? e = n * a[Da] / a[Ca] : n = e * a.width / a[Da]
                }
                var f = this[PR][Lk],
                    u = new mG(n - this._insets[Bo] - this._insets[Ur], e - this[$N].top - this[$N][Yr]);
                if (u.width < 1 && (n = this[$N][Bo] + this[$N][Ur] + 1, u[Ca] = 1), u[Da] < 1 && (e = this[$N].top + this[$N][Yr] + 1, u[Da] = 1), h ? this.element[bc](qY[_M], u) : this[PR][Aw] = u, f) {
                        var c = fi(f, n, e),
                            _ = c.x + t - (this[zA][uO] || 0),
                            d = c.y + i - (this[zA][cO] || 0);
                        if (this._d6.set(t - _, i - d, n, e), this[PR][mo]) {
                                var c = qs({
                                    x: _,
                                    y: d
                                }, this[PR][mo]);
                                _ = c.x,
                                d = c.y
                            }
                        this.element.x += _,
                        this.element.y += d
                    } else {
                        var _ = this._d6.x * n / this._d6[Ca] - t,
                            d = this._d6.y * e / this._d6[Da] - i;
                        if (this._d6.set(t + _, i + d, n, e), this[PR].rotate) {
                                var c = qs({
                                    x: _,
                                    y: d
                                }, this.element.rotate);
                                _ = c.x,
                                d = c.y
                            }
                        this[PR].x -= _,
                        this.element.y -= d
                    }
            },
            ondrag: function (t, i) {
                if (this.element && this._8q) if (this._8q != this[LN]) {
                    var n = t.dx,
                        e = t.dy,
                        s = i[nf];
                    if (n /= s, e /= s, this[PR][mo]) {
                            var h = {
                                x: n,
                                y: e
                            };
                            qs(h, -this[PR].rotate),
                            n = h.x,
                            e = h.y
                        }
                    var r = this._8q.p,
                        a = this._d6,
                        o = a.x,
                        f = a.y,
                        u = a[Ca],
                        c = a[Da];
                    r[Vr] == wG ? n >= u ? (o += u, u = n - u || 1) : (o += n, u -= n) : r[Vr] == kG && (-n >= u ? (u = -n - u || 1, o -= u) : u += n),
                    r[Xr] == OG ? e >= c ? (f += c, c = e - c || 1) : (f += e, c -= e) : r[Xr] == MG && (-e >= c ? (c = -e - c || 1, f -= c) : c += e),
                    this._4n(o, f, u, c, t[kp]);
                    var _ = new Ch(i, Ch[YN], t, this[PR]);
                    _.point = this._8q,
                    i.onInteractionEvent(_)
                } else {
                    var h = i[fT](t),
                        d = _n(h.x, h.y, this.element.x, this[PR].y, this._8q.start.x, this._8q.start.y, !0);
                    d += this._8q.rotate || 0,
                    t.shiftKey && (d = Math[uo](d / Math.PI * 4) * Math.PI / 4),
                    this.element.rotate = d % (2 * Math.PI);
                    var _ = new Ch(i, Ch.ROTATING, t, this[PR])
                }
            },
            enddrag: function (t, i) {
                if (this[PR] && this._8q && this._8q != this[LN]) {
                    var n = new Ch(i, Ch[UN], t, this[PR]);
                    n[_N] = this._8q,
                    i[gD](n)
                }
            }
        },
    jz[WN] = Sh;
    var yU = function (t) {
            this.graph = t
        };
    yU[er] = {
            onstart2: function (t, i) {
                this[Hb](t, i)
            },
            onstart: function (t, i) {
                if (!t[UR]) {
                    var n = t[ig]();
                    if (n && !i[MN](n) && (n = null), n && Ei(t)) {
                        i.reverseSelect(n);
                        var e = new Ch(i, Ch.SELECT, t, i.selectionModel);
                        return void i[gD](e)
                    }
                    if (!n || !i[B_][VN](n)) {
                        n ? (i[XN](n), i[KN](n)) : i.setSelection(null);
                        var e = new Ch(i, Ch.SELECT, t, i[B_]);
                        i[gD](e)
                    }
                }
            },
            onkeydown: function (t, i) {
                return 27 == t[Op] ? void i[sD]() : void(Ei(t) && 65 == t.keyCode && (i[ZN](), F(t)))
            }
        };
    var mU = 0,
        EU = 15;
    _G.TOOLTIP_DURATION = 3e3,
    _G[JN] = 1e3;
    var xU = QN;
    gi($r + xU, {
            "background-color": tB,
            overflow: y_,
            "box-shadow": "0 5px 10px rgba(136, 136, 136, 0.5)",
            color: Cm,
            "pointer-events": m_,
            border: iB,
            padding: nB,
            display: YD,
            position: Ew
        });
    var pU = function (t) {
            this[Yc] = t,
            this[eB] = {}
        };
    pU[er] = {
            _ms0: null,
            _ms4: null,
            _ms6: function () {
                delete this[sB],
                this._ms0.data && (this[hB] || (this[hB] = i[qa](a_), this[hB].className = xU), this._ms4.parentNode || i[ZA][bu](this[hB]), this[rB](this[Yc], this[eB].data))
            },
            _muw: function (t, i) {
                var n = t[aB](i),
                    e = yC == i[oB];
                n && !e && (n = n.replace(/\n/g, fB)),
                e ? this[hB].textContent = n || "" : this._ms4.innerHTML = n || "";
                var s = this[eB].evt.pageX + mU,
                    h = this[eB].evt.pageY + EU;
                Lh(this._ms4, s, h),
                this[uB] && (clearTimeout(this[uB]), delete this[uB]),
                this[uB] = setTimeout(this._85[lr](this), t[cB] || _G[_B])
            },
            _85: function () {
                delete this[uB],
                this[hB] && this[hB].parentNode && this[hB][pm].removeChild(this[hB]),
                delete this[hB],
                this[eB] = {}
            },
            _dp: function (t, i, n, e) {
                if (!this._ms4) {
                    var s = e.tooltipDelay;
                    return isNaN(s) && (s = _G[JN]),
                    void(this._initTimer = setTimeout(this[dB][lr](this), s))
                }
                this[rB](e, t)
            },
            onstart: function (t, i) {
                this[dg](i)
            },
            onmousemove: function (t, i) {
                if (i[lB]) {
                    var n = t[ig]();
                    if (this[eB].evt = t, this._ms0[vo] != n && (this[eB][vo] = n, this[sB] && (clearTimeout(this[sB]), delete this[sB]), n)) {
                        var e = i[aB](n);
                        e && this._dp(n, e, t, i)
                    }
                }
            },
            destroy: function () {
                this[sB] && (clearTimeout(this[sB]), delete this[sB]),
                this[uB] && (clearTimeout(this[uB]), delete this[uB]),
                this[hB] && this._85(),
                this[eB] = {}
            }
        };
    var wU = function (t) {
            this[Yc] = t
        };
    wU[er] = {
            _f6: function () {
                delete this._ks
            },
            destroy: function (t) {
                this._ks && this._f6(t)
            },
            onmousewheel: function (t, i) {
                if (i.enableWheelZoom !== !1 && t[Lb]) {
                    var n = t.delta > 0,
                        e = i.scale;
                    if (!(n && i[qm] - e < 1e-4 || !n && e - i[VC] < 1e-4)) {
                            G(t);
                            var s = Math[eo](Math.abs(t.delta));
                            n || (s = -s),
                            this._ks && clearTimeout(this._ks),
                            this._ks = setTimeout(this._f6.bind(this, i), 100),
                            i[vB](t, s)
                        }
                }
            }
        };
    var TU = function (t) {
            this[Yc] = t
        };
    TU[er] = {
            onclick: function (t, i) {
                i[vB](t, !Ei(t))
            }
        };
    var kU = function (t) {
            this[Yc] = t
        };
    kU[er] = {
            onclick: function (t, i) {
                i[vB](t, Ei(t))
            }
        },
    p(Ch, AG),
    Ch.ELEMENT_MOVE_START = bB,
    Ch.ELEMENT_MOVING = gB,
    Ch[zP] = yB,
    Ch.ELEMENT_CREATED = mB,
    Ch[bD] = EB,
    Ch[cN] = xB,
    Ch[gN] = pB,
    Ch[yN] = wB,
    Ch.RESIZE_START = TB,
    Ch[YN] = kB,
    Ch[UN] = OB,
    Ch[jB] = MB,
    Ch.ROTATE_END = SB,
    Ch[YP] = IB,
    Ch[UP] = AB,
    Ch[zk] = LB,
    Ch.EDGE_BUNDLE = CB,
    Ch[DB] = sd,
    Ch[TN] = RB,
    Ch.SELECT_BETWEEN = PB,
    Ch.SELECT_END = NB,
    Ch[BB] = $B,
    Dh[er] = {
            _99: function (t) {
                if (this._interactionSupport) switch (t[cd]) {
                case qG[rv]:
                    this[FB][qB](t[vo]);
                    break;
                case qG.KIND_CLEAR:
                    this[FB][GB](t[vo])
                }
            },
            destroy: function () {
                delete this._k7,
                delete this._3r,
                this._interactionSupport && (this[FB]._hg(), delete this._interactionSupport)
            },
            _k7: null,
            _3r: null,
            defaultMode: null,
            _g7: function (t, i, n) {
                this._3r[t] = new lh(i, n),
                t == this.currentMode && this[FB][zB](i)
            },
            addCustomInteraction: function (t) {
                this[FB][HB](t)
            },
            removeCustomInteraction: function (t) {
                this[FB]._jyCustomInteractionListener(t)
            },
            _mf: function (t) {
                var i = this._3r[t];
                return i ? i : OU[t]
            }
        },
    Z(Dh[er], {
            defaultCursor: {
                get: function () {
                    return this[YB] ? this.currentInteractionMode[H_] : void 0
                }
            },
            currentMode: {
                get: function () {
                    return this[UB]
                },
                set: function (t) {
                    this[UB] != t && (this[UB], this[FB] || (this[FB] = new dz(this._k7)), this[UB] = t, this.currentInteractionMode = this._mf(this[UB]), this._k7.cursor = this[H_], this[FB][zB](this[YB] ? this[YB][WB](this._k7) : []))
                }
            }
        });
    var OU = {};
    _G.registerInteractions = function (t, i, n) {
            var e = new lh(i, n);
            OU[t] = e
        },
    Mz.INTERACTION_MODE_VIEW = VB,
    Mz[XB] = Y_,
    Mz[KB] = td,
    Mz[ZB] = JB,
    Mz[QB] = t$,
    Mz[i$] = n$,
    Mz[e$] = s$,
    Mz[h$] = r$,
    Mz[a$] = o$,
    _G[f$](Mz[u$], [yU, vU, wU, dU, cU, pU, gU]),
    _G[f$](Mz[i$], [_U, Eh, yU, vU, wU, dU, pU]),
    _G[f$](Mz.INTERACTION_MODE_CREATE_EDGE, [_U, jh, yU, gh, vU, wU, dU, pU]),
    _G[f$](Mz[h$], [_U, yh, yU, vU, wU, dU, pU]),
    _G[f$](Mz.INTERACTION_MODE_CREATE_LINE, [mh, yU, vU, wU, dU, pU]),
    _G[f$](Mz.INTERACTION_MODE_DEFAULT, [_U, Sh, jh, yU, lU, vU, wU, dU, cU, pU, gU]),
    _G[f$](Mz[KB], [_U, Sh, jh, yU, lU, bU, vU, wU, dU, cU, pU]),
    _G[f$](Mz[ZB], [wU, dU, TU], lz),
    _G[f$](Mz[QB], [wU, dU, kU], vz),
    jz[c$] = vU,
    jz[_$] = yU,
    jz[d$] = lU,
    jz[l$] = wU,
    jz.DoubleClickInteraction = cU,
    jz[v$] = dU,
    jz.TooltipInteraction = pU,
    jz.RectangleSelectionInteraction = bU,
    jz[b$] = gU,
    jz[g$] = jh;
    var jU = function (t) {
            this.graph = t
        };
    jz[y$] = jU,
    jU.prototype = {
            getNodeBounds: function (t) {
                return this[Yc].getUIBounds(t)
            },
            isLayoutable: function (t) {
                return this[Yc][BP](t) && t.layoutable !== !1
            },
            getLayoutResult: function () {},
            updateLocations: function (t, i, n, e, s) {
                if (i === !0) {
                    if (this[m$] || (this.animate = new aW), n && (this.animate[AR] = n), e && (this.animate[CR] = e), this.animate[E$] = t, s) {
                        var h = s,
                            r = this;
                        s = function () {
                                h[Yh](r, t)
                            }
                    }
                    return void this.animate[zR](s)
                }
                for (var a in t) {
                    var o = t[a],
                        f = o.node;
                    f.setLocation(o.x, o.y)
                }
                s && s.call(this, t)
            },
            _fh: function (t) {
                var i, n, e, s = null;
                t && (i = t.byAnimate, s = t[_c], n = t[AR], e = t[CR]);
                var h = this[x$](t);
                return h ? (this.updateLocations(h, i, n, e, s), h) : !1
            },
            doLayout: function (t, i) {
                return this[Yc] && i !== !0 ? void this[Yc][HC](function () {
                    this._fh(t)
                }, this) : this._fh(t)
            }
        };
    var MU = 110,
        SU = 120,
        IU = 130,
        AU = 210,
        LU = 220,
        CU = 230,
        DU = 111,
        RU = 112,
        PU = 121,
        NU = 122,
        BU = 211,
        $U = 212,
        FU = 221,
        qU = 222;
    Mz[p$] = MU,
    Mz.DIRECTION_LEFT = SU,
    Mz[w$] = IU,
    Mz[T$] = AU,
    Mz[k$] = LU,
    Mz.DIRECTION_MIDDLE = CU,
    Mz[O$] = DU,
    Mz[j$] = RU,
    Mz[M$] = PU,
    Mz[S$] = NU,
    Mz[I$] = BU,
    Mz[A$] = $U,
    Mz[L$] = FU,
    Mz.DIRECTION_TOP_RIGHT = qU;
    var GU = C$,
        zU = D$,
        HU = R$,
        YU = P$;
    Mz[N$] = GU,
    Mz[B$] = HU,
    Mz[$$] = YU,
    Mz[F$] = zU,
    jz.isHorizontalDirection = Rh;
    var UU = function (t) {
            this[Yc] = t
        };
    UU[er] = {
            hGap: 50,
            vGap: 50,
            parentChildrenDirection: AU,
            layoutType: GU,
            defaultSize: {
                width: 50,
                height: 60
            },
            getNodeSize: function (t) {
                if (this[Yc]._88._mq0) {
                    var i = this[Yc][Uc](t);
                    if (i) return i._fa
                }
                return t[Gm] && t[Gm].bounds ? {
                    width: t[Gm].bounds.width,
                    height: t.image[fo].height
                } : this[q$]
            },
            _mu6: function (t, i) {
                if (this[ld](t)) {
                    var n, e = this[G$](t);
                    n = e instanceof EG ? [-e.x, -e.y] : [e[Ca] / 2, e.height / 2];
                    var s = t.id,
                        h = (t[z$], i ? this._97[i.id] : this[H$]);
                    this._97[s] = new WU(this.getHGap(t), this[Y$](t), this.getLayoutType(t), t[z$], h, t, e.width, e[Da], n)
                }
            },
            getHGap: function (t) {
                return t && P(t.hGap) ? t[U$] : this[U$]
            },
            getVGap: function (t) {
                return t && P(t[W$]) ? t[W$] : this[W$]
            },
            getLayoutType: function (t) {
                return t && t[V$] ? t[V$] : this.layoutType
            },
            _97: null,
            _msf: null,
            _kh: function () {
                this._97 = null,
                this._msf = null
            },
            getLayoutResult: function (t) {
                var i, n, e, s, h = this.graph;
                t instanceof Object && (i = t.x, n = t.y, h = t.root || this[Yc], e = t[fo], s = t[X$]),
                this._97 = {},
                this._msf = new WU,
                this[H$]._ma(this[U$], this[W$], this[z$], this[V$]);
                var r = {},
                    a = uW(h, this._mu6, this, !1, s);
                return a && (this._msf._fh(i || 0, n || 0, r), e && e.set(this._msf.x, this[H$].y, this[H$].width, this._msf.height)),
                this._kh(),
                r
            },
            doLayout: function (t, i) {
                if (P(t)) {
                    var n = t,
                        e = 0;
                    P(i) && (e = i),
                    t = {
                            x: n,
                            y: e
                        },
                    i = !0
                }
                return T(this, UU, K$, [t, i])
            }
        },
    p(UU, jU);
    var WU = function (t, i, n, e, s, h, r, a, o) {
            this._lu = t || 0,
            this._lw = i || 0,
            this.layoutType = n,
            this[z$] = e,
            this[Z$] = s,
            s && s._fz(this),
            this[J$] = h,
            this._e1 = r,
            this[Q$] = a,
            this[tF] = o
        };
    WU.prototype = {
            _ma: function (t, i, n, e) {
                this._lu = t,
                this._lw = i,
                this.parentChildrenDirection = n,
                this[V$] = e
            },
            _7q: function () {
                this._f5 = []
            },
            _lu: 0,
            _lw: 0,
            _f5: null,
            _e1: 0,
            _muv: 0,
            layoutType: null,
            parentChildrenDirection: null,
            _fz: function (t) {
                this._f5 || (this._f5 = []),
                this._f5.push(t)
            },
            _muo: function (t, i, n, e) {
                var s = new EG;
                return n(this._f5, function (n) {
                    n._3d(t, i),
                    s.add(n),
                    e ? t += n.width + this._lu : i += n[Da] + this._lw
                }, this),
                s
            },
            _7u: function (t, i, n, e, s) {
                var h, r = e ? this._lu : this._lw,
                    a = e ? this._lw : this._lu,
                    o = e ? "width" : Da,
                    f = e ? "height" : Ca,
                    u = e ? "_e1" : Q$,
                    c = e ? "_muv" : iF,
                    _ = e ? "hostDX" : nF,
                    d = e ? "hostDY" : eF,
                    v = new EG,
                    b = 0,
                    g = 0,
                    y = [],
                    m = 0,
                    E = 0;
                n(this._f5, function (n) {
                        var s = E >= g;
                        n._inheritedParentChildrenDirection = s ? e ? SU : LU : e ? MU : AU,
                        n._3d(t, i),
                        s ? (y[tr](n), b = Math.max(b, n[o]), g += n[f] + a) : (h || (h = []), h.push(n), m = Math.max(m, n[o]), E += n[f] + a)
                    }, this),
                g -= a,
                E -= a;
                var x = Math.max(g, E),
                    p = r,
                    w = 0;
                this.node && (s && (p += this[u] + r, x > this[c] ? this[d] = (x - this[c]) / 2 : w = (this[c] - x) / 2), this[_] = b + p / 2 - this[u] / 2);
                var T = 0,
                    k = w;
                return l(y, function (t) {
                        e ? t[dm](b - t[o], k) : t[dm](k, b - t[o]),
                        k += a + t[f],
                        v.add(t)
                    }, this),
                h ? (k = w, T = b + p, l(h, function (t) {
                        e ? t[dm](T, k) : t[dm](k, T),
                        k += a + t[f],
                        v.add(t)
                    }, this), v) : v
            },
            offset: function (t, i) {
                this.x += t,
                this.y += i,
                this[sF] += t,
                this[hF] += i,
                this._72(t, i)
            },
            _msi: function (t, i) {
                return 2 * this.cx - t - i - t
            },
            _msh: function (t, i) {
                return 2 * this.cy - t - i - t
            },
            _lx: function (t) {
                if (this._f5 && 0 != this._f5[Hh]) {
                    if (t) return this[J$] && (this.nodeX += this[rF](this.nodeX, this._e1)),
                    void l(this._f5, function (t) {
                        t.offset(this[rF](t.x, t[Ca]), 0)
                    }, this);
                    this.node && (this[hF] += this[aF](this.nodeY, this[Q$])),
                    l(this._f5, function (t) {
                        t[dm](0, this._msh(t.y, t[Da]))
                    }, this)
                }
            },
            _72: function (t, i) {
                this._f5 && l(this._f5, function (n) {
                    n.offset(t, i)
                }, this)
            },
            _3d: function (t, i) {
                return this.x = t || 0,
                this.y = i || 0,
                this._f5 && 0 != this._f5.length ? void this._1v(this.x, this.y, this[V$]) : void(this[J$] && (this.width = this._e1, this.height = this[Q$], this[sF] = this.x, this.nodeY = this.y))
            },
            _6z: function (t) {
                if (this[J$]) {
                    var i = this._mqnchorLocation,
                        n = i[0],
                        e = i[1];
                    t[this[J$].id] = {
                            node: this.node,
                            x: this.nodeX + n,
                            y: this[hF] + e,
                            left: this[sF],
                            top: this[hF],
                            width: this._e1,
                            height: this[Q$]
                        }
                }
                this._f5 && l(this._f5, function (i) {
                    i._6z(t)
                }, this)
            },
            _fh: function (t, i, n) {
                this._3d(t, i),
                this._6z(n)
            },
            _1v: function (t, i, e) {
                var s, h = t,
                    r = i;
                !this.parentChildrenDirection && this[Z$] && (this[z$] = this._inheritedParentChildrenDirection || this[Z$][z$]);
                var a = this[z$],
                    o = Rh(a);
                if (this[J$]) {
                        s = a == IU || a == CU;
                        var f = Ph(a);
                        s || (o ? t += this._e1 + this._lu : i += this[Q$] + this._lw)
                    }
                var u, c = this.node && this.node[oF] ? b : l;
                if (e == zU) u = this._7u(t, i, c, !o, s);
                else {
                        var _;
                        _ = e == GU ? !o : e == HU,
                        u = this[fF](t, i, c, _, s)
                    }
                var d = 0,
                    v = 0;
                if (u && !u[Lf]() && (d = u[Ca], v = u[Da], this.add(u)), this[J$]) {
                        if (this.nodeX = h, this.nodeY = r, this[eF] !== n || this[nF] !== n) this[sF] += this[eF] || 0,
                        this[hF] += this[nF] || 0;
                        else {
                            var g;
                            g = a == AU || a == LU || a == SU || a == MU ? 1 : a == $U || a == qU || a == NU || a == RU ? 0 : 2,
                            o ? 1 == g ? this[hF] += v / 2 - this[Q$] / 2 : 2 == g && (this[hF] += v - this._muv) : 1 == g ? this[sF] += d / 2 - this._e1 / 2 : 2 == g && (this.nodeX += d - this._e1)
                        }
                        this[tl](this.nodeX, this[hF], this._e1, this[Q$]),
                        f && this._lx(o)
                    }
            },
            node: null,
            uiBounds: null
        },
    p(WU, EG),
    Bh[er] = {
            layoutDatas: null,
            isMovable: function (t) {
                return !this[uF][t.id]
            },
            _64: !1,
            _3c: function () {
                this._64 = !0,
                this.graph._1g[_d](this._9l, this),
                this[Yc]._18[_d](this._23, this)
            },
            _1s: function () {
                this._64 = !1,
                this.graph._1g[zl](this._9l, this),
                this[Yc]._18[zl](this._23, this)
            },
            invalidateFlag: !0,
            invalidateLayoutDatas: function () {
                this[Hk] = !0
            },
            resetLayoutDatas: function () {
                return this.invalidateFlag = !1,
                this[cF] = Nh.call(this)
            },
            _23: function (t) {
                Ch[FP] == t.kind ? (this[uF] = {}, t[wv].forEach(function (t) {
                    this[uF][t.id] = t
                }, this)) : Ch.ELEMENT_MOVE_END == t[cd] && (this[uF] = {})
            },
            _9l: function () {
                this[_F]()
            },
            isRunning: function () {
                return this.timer && this.timer._dn()
            },
            getLayoutResult: function () {
                this[dF](),
                this[lF]();
                for (var t = this[vF](this[cF][bF] || 0, this[cF][gF] || 0), i = 0; t > i && this[HP](!1) !== !1; i++);
                var n = this[cF][yF];
                return this[mF](),
                n
            },
            _m4: function () {
                return !1
            },
            step: function (t) {
                if (t === !1) return this._m4(this[EF]);
                (this[Hk] || !this.layoutDatas) && this[lF]();
                var i = this._m4(t),
                    n = this[cF][yF];
                for (var e in n) {
                        var s = n[e],
                            h = s.node;
                        this[rD](h) ? h.setLocation(s.x, s.y) : (s.x = h.x, s.y = h.y, s.vx = 0, s.vy = 0)
                    }
                return i
            },
            onstop: function () {
                delete this.layoutDatas
            },
            start: function (t) {
                if (this[xF]()) return !1;
                this._64 || this._3c(),
                this[pF] || (this[pF] = function (t) {
                    return this[HP](t)
                }[lr](this)),
                this[_F](),
                this[wF] = new Tz(this[pF]);
                var i = this;
                return this[wF]._kj(function () {
                    i.onstop(),
                    t && t()
                }),
                !0
            },
            stop: function () {
                this[wF] && (this[wF]._lo(), this[mF]())
            },
            getMaxIterations: function (t) {
                return Math.min(1e3, 3 * t + 10)
            },
            minEnergyFunction: function (t, i) {
                return 10 + Math.pow(t + i, 1.4)
            },
            resetGraph: function () {
                this._64 || this._3c(),
                this[lF]()
            },
            destroy: function () {
                this.stop(),
                this._1s()
            }
        },
    p(Bh, jU);
    var VU = function (t, i, n, e) {
            this.graph = t,
            P(i) && (this[ml] = i),
            P(n) && (this.gap = n),
            P(e) && (this.startAngle = e)
        };
    jz.BalloonLayouter = VU;
    var XU = TF,
        KU = kF,
        ZU = OF,
        JU = jF;
    Mz[MF] = XU,
    Mz.ANGLE_SPACING_REGULAR = KU,
    Mz[SF] = ZU,
    Mz[IF] = JU,
    VU[er] = {
            angleSpacing: XU,
            radiusMode: JU,
            gap: 4,
            radius: 50,
            startAngle: 0,
            _97: null,
            _msf: null,
            _kh: function () {
                this._97 = null,
                this[H$] = null
            },
            getLayoutResult: function (t) {
                var i, n = 0,
                    e = 0,
                    s = this[Yc];
                t instanceof Object && (n = t.cx || 0, e = t.cy || 0, s = t[AF] || this[Yc], i = t.bounds),
                this._97 = {},
                this[H$] = new iW(this);
                var h = {},
                    r = cW(s, this[LF], this);
                return r && (this[H$]._f5 && 1 == this._msf._f5.length && (this[H$] = this[H$]._f5[0]), this._msf._ei(!0), this[H$]._5b(n, e, this[CF], h, i)),
                this._kh(),
                h
            },
            _mu6: function (t, i) {
                if (this[ld](t)) {
                    var n = i ? this._97[i.id] : this._msf;
                    this._97[t.id] = new iW(this, t, n)
                }
            },
            defaultSize: 40,
            getRadius: function () {
                return this[ml]
            },
            getNodeSize: function (t) {
                if (this[Yc]._88._mq0) {
                    var i = this[Yc][Uc](t);
                    if (i) return (i._fa[Ca] + i._fa[Da]) / 2
                }
                return this[q$]
            },
            getGap: function () {
                return this.gap
            },
            _2z: function (t, i, n) {
                return this[G$](t, i, n) + this[DF](t, i, n)
            }
        };
    var QU = function (t) {
            var i, n = this._f5.length,
                e = 0,
                s = 0;
            if (l(this._f5, function (t) {
                    var n = t._ei();
                    1 > n && (n = 1),
                    s += n,
                    n > e && (e = n, i = t)
                }, this), n > 1) {
                    var h = 0,
                        r = {},
                        a = s / n / 3;
                    s = 0,
                    l(this._f5, function (t) {
                            var i = t._m1;
                            a > i && (i = a),
                            r[t.id] = i,
                            s += i
                        }, this);
                    var o = jY / s;
                    l(this._f5, function (i, n) {
                            var e = r[i.id],
                                s = e * o;
                            0 === n && (h = t ? -s / 2 : -s),
                            i._kd = h + s / 2,
                            i._kf = s,
                            h += s
                        }, this)
                }
            return [e, i._kf]
        },
        tW = function (t) {
            var i = this._7z,
                n = 2 * Math.PI / i,
                e = 0,
                s = t ? 0 : i > 1 ? -n / 2 : 0;
            return l(this._f5, function (t) {
                    t._kd = s % jY,
                    s += n,
                    t._kf = n;
                    var i = t._ei();
                    i > e && (e = i)
                }, this),
            [e, n]
        },
        iW = function (t, i, n) {
            this[RF] = t,
            i && (this._m2 = i, this.id = i.id),
            n && (n._fz(this), n._lz = !1, this._kc = n._kc + 1)
        },
        jY = 2 * Math.PI;
    iW[er] = {
            _kf: 0,
            _kd: 0,
            _jn: 0,
            _eb: 0,
            _msw: 0,
            _kc: 0,
            _lz: !0,
            _m1: 0,
            _fw: 0,
            _f5: null,
            _m2: null,
            _fz: function (t) {
                this._f5 || (this._f5 = []),
                this._f5[tr](t),
                t[yu] = this
            },
            _g1: function (t) {
                if (this._kd = (this._kd + t) % jY, this._f5) {
                    var i = this._f5[Hh];
                    if (1 == i) return void this._f5[0]._g1(this._kd);
                    t = this._kd + Math.PI,
                    l(this._f5, function (i) {
                        i._g1(t)
                    }, this)
                }
            },
            _7z: 0,
            _6x: function (t) {
                return this._m2 && (this._fw = this[RF]._2z(this._m2, this._kc, this._lz) / 2),
                this._f5 ? (this._fw, this._7z = this._f5[Hh], this._7z <= 2 || this[RF][PF] == KU ? tW[Yh](this, t) : QU.call(this, t)) : null
            },
            _ei: function (t) {
                var i = this._6x(t);
                if (!i) return this._m1 = this._fw;
                var n = i[0],
                    e = i[1],
                    s = this.layouter[NF](this._m2, this._kc);
                if (s < this._fw && (s = this._fw), this._eb = s, this._fw + n > s && (s = this._fw + n), n && this._7z > 1 && e < Math.PI) {
                        var h = n / Math.sin(e / 2);
                        h > s && (s = h)
                    }
                return this._jn = s,
                this._m1 = s + n,
                this._m2 && this._f5 && this.layouter[BF] == JU && l(this._f5, function (t) {
                        var i = t._m1;
                        1 == t._7z && (i /= 2);
                        var n = this._fw + i,
                            e = t._kf;
                        if (e && e < Math.PI) {
                                var s = Math.sin(e / 2),
                                    h = i / s;
                                h > i && (i = h)
                            }
                        n > i && (i = n),
                        t[$F] = i
                    }, this),
                (!this._m2 || t) && this._g1(0),
                this._m1
            },
            _5b: function (t, i, n, e, s) {
                if (this._m2 && (e[this._m2.id] = {
                    x: t,
                    y: i,
                    node: this._m2
                }, s && s[tl](t - this._fw / 2, i - this._fw / 2, this._fw, this._fw)), this._f5) {
                    if (!this._m2 && 1 == this._f5[Hh]) return void this._f5[0]._5b(t, i, n, e, s);
                    n = n || 0;
                    var h = this._jn,
                        r = this._eb;
                    l(this._f5, function (a) {
                            var o = h;
                            a[$F] && (o = Math.max(r, a[$F]));
                            var f = a._kd + n,
                                u = t + o * Math.cos(f),
                                c = i + o * Math.sin(f);
                            a._5b(u, c, n, e, s)
                        }, this)
                }
            }
        },
    p(VU, jU);
    var nW = function () {
            w(this, nW, arguments)
        };
    p(nW, $h);
    var eW = function (t, i) {
            this[FF] = t,
            this[qF] = i,
            t == i ? (this.isLooped = !0, this._ka = t._k8) : this._ka = new dG,
            this._80 = [],
            this._g6 = _G.EDGE_BUNDLE_EXPANDED
        };
    _G[GF] = !0,
    eW[er] = {
            node1: null,
            node2: null,
            _ka: null,
            _g6: _G[GF],
            _80: null,
            _g8: null,
            agentEdge: null,
            _ms2: function (t, i, n) {
                this._ka[Vf](function (e) {
                    return n && e[Du] != n && e.fromAgent != n ? void 0 : t[Yh](i, e)
                })
            },
            _4t: 0,
            _5l: 0,
            _hw: function (t, i) {
                return this._ka.add(t) === !1 ? !1 : (i == this[FF] ? this._4t++ : this._5l++, this[ac] ? void this._13(t) : void(this._mq0 = !0))
            },
            _msv: function (t, i) {
                return this._ka.remove(t) === !1 ? !1 : (i == this[FF] ? this._4t-- : this._5l--, this._13(t), void this._ka[Vf](function (t) {
                    t._edgeBundleInvalidateFlag = !0,
                    t[XT] = !0
                }, this))
            },
            _13: function (t) {
                this[zF] = !0,
                this._5s = !0,
                t[HT] = !0,
                t[XT] = !0
            },
            _muj: function () {
                this._5s || (this._5s = !0, this._ka[Vf](function (t) {
                    t[HT] = !0
                }))
            },
            isEmpty: function () {
                return this._ka.isEmpty()
            },
            isPositiveOrder: function (t) {
                return this.node1 == t[Du] || this.node1 == t[pu]
            },
            canBind: function (t) {
                return t && this._5s && this._f6(t),
                this._ka[Hh] > 1 && this._80[Hh] > 1
            },
            _hj: function (t) {
                return this._80[zo](t)
            },
            getYOffset: function (t) {
                return this._g8[t.id]
            },
            _4l: function (t) {
                if (!this[HF]()) return void(this._g8 = {});
                var i = {},
                    n = this._80.length;
                if (!(2 > n)) {
                        var e = 0,
                            s = this._80[0];
                        i[s.id] = 0;
                        for (var h = 1; n > h; h++) {
                                s = this._80[h];
                                var r = t[gc](s, qY[nS]) || nU[qY.EDGE_BUNDLE_GAP];
                                e += r,
                                i[s.id] = e
                            }
                        if (!this[Ou]) for (var a = e / 2, h = 0; n > h; h++) s = this._80[h],
                        i[s.id] -= a;
                        this._g8 = i
                    }
            },
            _mqt: function (t) {
                return this._g6 == t ? !1 : (this._g6 = t, this._muj(), !0)
            },
            reverseExpanded: function () {
                return this._mqt(!this._g6)
            },
            _1a: function () {
                this._mujBindableFlag = !1,
                this._80[Hh] = 0;
                var t;
                this._ka[Vf](function (i) {
                    if (i.isBundleEnabled()) {
                        if (!this[YF](i)) return t || (t = []),
                        void t[tr](i);
                        this._80[tr](i)
                    }
                }, this),
                t && (this._80 = t.concat(this._80))
            },
            _es: function (t) {
                return t == this[UF] || !this.canBind() || this._g6
            },
            _f6: function (t) {
                this._5s = !1,
                this._ka.forEach(function (t) {
                    t[HT] = !1
                }),
                this[zF] && this._1a();
                var i = this._g6,
                    n = this.canBind(),
                    e = !n || i;
                l(this._80, function (t) {
                        t._$s = !0,
                        t[WF] = e,
                        e && (t[XT] = !0)
                    }, this),
                e ? this._9f(null, t) : (this._9f(this._80[0], t), this[UF]._h9InBundle = !0, this.agentEdge[XT] = !0),
                e && this._4l(t)
            },
            _9f: function (t, i) {
                if (t != this[UF]) {
                    var n = this.agentEdge;
                    return this.agentEdge = t,
                    i && i._4a(new LG(this, UF, t, n)),
                    !0
                }
            }
        },
    Z(eW[er], {
            bindableEdges: {
                get: function () {
                    return this._80
                }
            },
            edges: {
                get: function () {
                    return this._ka._j8
                }
            },
            length: {
                get: function () {
                    return this._ka ? this._ka[Hh] : 1
                }
            },
            expanded: {
                get: function () {
                    return this._g6
                },
                set: function (t) {
                    return this._g6 == t ? !1 : (this._g6 = t, void this[Iw]())
                }
            }
        });
    var sW = function () {
            function t(t, i) {
                this[J$] = t,
                this[ZA] = i
            }
            function i() {
                this[VF] = [],
                this[XF] = 0
            }
            var n = -1e6,
                e = .8;
            i[er] = {
                    isEmpty: function () {
                        return 0 === this[XF]
                    },
                    push: function (i, n) {
                        var e = this.stack[this[XF]];
                        e ? (e[J$] = i, e[ZA] = n) : this.stack[this[XF]] = new t(i, n),
                        ++this.popIdx
                    },
                    pop: function () {
                        return this[XF] > 0 ? this[VF][--this[XF]] : void 0
                    },
                    reset: function () {
                        this.popIdx = 0
                    }
                };
            var s = [],
                h = new i,
                r = function () {
                    this[ZA] = null,
                    this[KF] = [],
                    this[ZF] = 0,
                    this.massX = 0,
                    this.massY = 0,
                    this.left = 0,
                    this.top = 0,
                    this[Yr] = 0,
                    this.right = 0,
                    this.isInternal = !1
                },
                a = [],
                o = 0,
                f = function () {
                    var t;
                    return a[o] ? (t = a[o], t[KF][0] = null, t.quads[1] = null, t.quads[2] = null, t[KF][3] = null, t[ZA] = null, t.mass = t[JF] = t.massY = 0, t.left = t[Ur] = t.top = t.bottom = 0, t[QF] = !1) : (t = new r, a[o] = t),
                    ++o,
                    t
                },
                u = f(),
                c = function (t, i) {
                    var n = Math.abs(t.x - i.x),
                        e = Math.abs(t.y - i.y);
                    return 1e-8 > n && 1e-8 > e
                },
                _ = function (t) {
                    for (h[tq](), h.push(u, t); !h[Lf]();) {
                        var i = h.pop(),
                            n = i[J$],
                            e = i[ZA];
                        if (n[QF]) {
                                var s = e.x,
                                    r = e.y;
                                n[ZF] = n[ZF] + e[ZF],
                                n.massX = n[JF] + e.mass * s,
                                n[iq] = n.massY + e.mass * r;
                                var a = 0,
                                    o = n[Bo],
                                    _ = (n.right + o) / 2,
                                    d = n.top,
                                    l = (n[Yr] + d) / 2;
                                if (s > _) {
                                        a += 1;
                                        var v = o;
                                        o = _,
                                        _ += _ - v
                                    }
                                if (r > l) {
                                        a += 2;
                                        var b = d;
                                        d = l,
                                        l += l - b
                                    }
                                var g = n[KF][a];
                                g || (g = f(), g[Bo] = o, g.top = d, g[Ur] = _, g[Yr] = l, n[KF][a] = g),
                                h[tr](g, e)
                            } else if (n.body) {
                                var y = n[ZA];
                                if (n[ZA] = null, n[QF] = !0, c(y, e)) {
                                    if (n.right - n.left < 1e-8) return;
                                    do {
                                        var m = Math[Sr](),
                                            E = (n[Ur] - n.left) * m,
                                            x = (n[Yr] - n.top) * m;
                                        y.x = n[Bo] + E, y.y = n.top + x
                                    } while (c(y, e))
                                }
                                h.push(n, y),
                                h[tr](n, e)
                            } else n[ZA] = e
                    }
                },
                d = function (t) {
                    var i, h, r, a, o = s,
                        f = 1,
                        c = 0,
                        _ = 1;
                    for (o[0] = u; f;) {
                            var d = o[c],
                                l = d[ZA];
                            f -= 1,
                            c += 1,
                            l && l !== t ? (h = l.x - t.x, r = l.y - t.y, a = Math.sqrt(h * h + r * r), 0 === a && (h = (Math[Sr]() - .5) / 50, r = (Math.random() - .5) / 50, a = Math.sqrt(h * h + r * r)), i = n * l[ZF] * t[ZF] / (a * a), -1e3 > i && (i = -1e3), i /= a, t.fx = t.fx + i * h, t.fy = t.fy + i * r) : (h = d[JF] / d[ZF] - t.x, r = d[iq] / d[ZF] - t.y, a = Math.sqrt(h * h + r * r), 0 === a && (h = (Math.random() - .5) / 50, r = (Math[Sr]() - .5) / 50, a = Math.sqrt(h * h + r * r)), (d[Ur] - d[Bo]) / a < e ? (i = n * d.mass * t[ZF] / (a * a), -1e3 > i && (i = -1e3), i /= a, t.fx = t.fx + i * h, t.fy = t.fy + i * r) : (d[KF][0] && (o[_] = d.quads[0], f += 1, _ += 1), d[KF][1] && (o[_] = d[KF][1], f += 1, _ += 1), d[KF][2] && (o[_] = d.quads[2], f += 1, _ += 1), d.quads[3] && (o[_] = d[KF][3], f += 1, _ += 1)))
                        }
                },
                l = function (t, i) {
                    n = i;
                    var e, s = Number[il],
                        h = Number[il],
                        r = Number[nq],
                        a = Number[nq],
                        c = t,
                        d = c.length;
                    for (e = d; e--;) {
                            var l = c[e].x,
                                v = c[e].y;
                            s > l && (s = l),
                            l > r && (r = l),
                            h > v && (h = v),
                            v > a && (a = v)
                        }
                    var b = r - s,
                        g = a - h;
                    for (b > g ? a = h + b : r = s + g, o = 0, u = f(), u[Bo] = s, u.right = r, u.top = h, u[Yr] = a, e = d; e--;) _(c[e], u)
                };
            return {
                    init: l,
                    update: d
                }
        },
        hW = function (t) {
            t.fx -= t.x * this[eq],
            t.fy -= t.y * this.attractive
        },
        rW = function (t) {
            if (0 != t.k) {
                var i = this._d4,
                    n = t[ku],
                    e = t.to,
                    s = e.x - n.x,
                    h = e.y - n.y,
                    r = s * s + h * h,
                    a = Math.sqrt(r) || .1,
                    o = (a - i) * t.k * this[sq];
                o /= a;
                var f = o * s,
                    u = o * h;
                e.fx -= f,
                e.fy -= u,
                n.fx += f,
                n.fy += u
            }
        };
    $h[er] = {
            appendNodeInfo: function (t, i) {
                i.mass = t[hq] || 1,
                i.fx = 0,
                i.fy = 0,
                i.vx = 0,
                i.vy = 0
            },
            appendEdgeInfo: function (t, i) {
                i.k = t.layoutElasticity || 1
            },
            setMass: function (t, i) {
                t[hq] = i,
                this.layoutDatas && this.layoutDatas[yF] && (t = this[cF][yF][t.id], t && (t[ZF] = i))
            },
            setElasticity: function (t, i) {
                t[rq] = i,
                this[cF] && this[cF][ck] && (t = this[cF].edges[t.id], t && (t.k = i))
            },
            _d4: 50,
            _i1: .5,
            timeStep: .15,
            repulsion: 50,
            attractive: .1,
            elastic: 3,
            _m6: 1e3,
            _ju: function (t) {
                return this._m6 + .3 * (t - this._m6)
            },
            _m4: function (t, i) {
                var n = (Date.now(), this.layoutDatas[yF]);
                for (var e in n) {
                    var s = n[e];
                    i && (s.x += Math[Sr]() - .5, s.y += Math[Sr]() - .5),
                    hW[Yh](this, s)
                }
                var h = this[cF][aq];
                if (h) for (var e in h) {
                    var r = h[e],
                        a = r.children,
                        o = 0,
                        f = 0;
                    a[Vf](function (t) {
                            o += t.x,
                            f += t.y
                        }),
                    o /= a.length,
                    f /= a[Hh];
                    var u = 10 * this[eq];
                    a.forEach(function (t) {
                            t.fx -= (t.x - o) * u,
                            t.fy -= (t.y - f) * u
                        })
                }
                var c = this._nbodyForce;
                c || (c = this[oq] = sW()),
                c[fq](this[cF].nodesArray, -this.repulsion * this.repulsion * this[uq]);
                for (var e in n) c[cq](n[e]);
                if (this[sq]) {
                    var _ = this[cF][ck];
                    for (var e in _) rW[Yh](this, _[e])
                }
                return this._m7(t)
            },
            _m7: function (t) {
                var i = this.layoutDatas.minEnergy,
                    n = (this[cF].currentEnergy, this.layoutDatas[yF]),
                    t = this[EF],
                    e = 0,
                    s = this._i1;
                for (var h in n) {
                        var r = n[h],
                            a = r.fx / r.mass,
                            o = r.fy / r[ZF],
                            f = r.vx += a * t,
                            u = r.vy += o * t;
                        r.x += f * t,
                        r.y += u * t,
                        i > e && (e += 2 * (f * f + u * u)),
                        r.fx = 0,
                        r.fy = 0,
                        r.vx *= s,
                        r.vy *= s
                    }
                return this[cF][_q] = e,
                e >= i
            }
        },
    p($h, Bh),
    jz.SpringLayouter = $h;
    var aW = function (t) {
            this.locations = t
        };
    aW.prototype = {
            oldLocations: null,
            _eu: null,
            duration: 700,
            animationType: wz[dq],
            _62: function (t) {
                if (this._eu = t, this.oldLocations = {}, t) for (var i in t) {
                    var n = t[i],
                        e = n[J$];
                    this[lq][i] = {
                            x: e.x,
                            y: e.y
                        }
                }
            },
            setLocation: function (t, i, n) {
                t[hD](i, n)
            },
            forEach: function (t, i) {
                for (var n in this[E$]) {
                    var e = this[lq][n],
                        s = this[E$][n];
                    t.call(i, e, s)
                }
            },
            _jq: function (t) {
                this[Vf](function (i, n) {
                    var e = n[J$],
                        s = i.x + (n.x - i.x) * t,
                        h = i.y + (n.y - i.y) * t;
                    this[hD](e, s, h)
                }, this)
            },
            stop: function () {
                this._mqnimate && this._mqnimate._lo()
            },
            start: function (t) {
                this[vq] ? (this[vq]._lo(), this[vq]._i5 = this.duration, this._mqnimate[bq] = this[CR], this._mqnimate[gq] = this._onfinish) : this._mqnimate = new kz(this._jq, this, this.duration, this[CR]),
                this[vq]._kj(t)
            }
        },
    Z(aW.prototype, {
            locations: {
                get: function () {
                    return this._eu
                },
                set: function (t) {
                    this._eu != t && this._62(t)
                }
            }
        });
    var oW = function (t) {
            var i, n = new dG;
            return t.forEach(function (t) {
                t instanceof RY && (t[yq]() ? !i && t.hasOutEdge() && (i = t) : n.add(t))
            }),
            n[Lf]() && i && n.add(i),
            n
        },
        fW = function (t, i, n, e, s, h) {
            if (i instanceof GG) return t(i, n, e, s, h),
            i;
            if (i instanceof eU) {
                var r = new dG;
                i[kC][Vf](function (t) {
                    return i[BP](t) ? t._hh() && t._g6 && t[Uh]() ? void(t[wk] && (t[wk][Hk] = !1)) : void r.add(t) : void 0
                }),
                i = r
            }
            var i = oW(i, e);
            return l(i, function (i) {
                t(i, n, e, s, h)
            }),
            i
        },
        uW = function (t, i, n, e, s) {
            return fW(_W, t, i, n, e, s)
        },
        cW = function (t, i, n, e, s) {
            return fW(dW, t, i, n, e, s)
        };
    as.prototype.forEachByTopoDepthFirstSearch = function (t, i, n, e) {
            uW(this, t, i, n, e)
        },
    as[er].forEachByTopoBreadthFirstSearch = function (t, i, n, e) {
            t instanceof Object && 1 == arguments[Hh] && (i = t.scope),
            cW(this, t, i, n, e)
        },
    jz.forEachByTopoDepthFirstSearch = uW,
    jz[mq] = cW;
    var _W = function (t, i, n, e, s) {
            function h(t, i, n, e, s, r, a, o) {
                t[md] = r,
                e || i[Yh](n, t, o, a),
                Fh(t, function (o) {
                    h(o, i, n, e, s, r, a + 1, t)
                }, o, s, r, n),
                e && i[Yh](n, t, o, a)
            }
            h(t, i, n, e, s, {}, 0)
        },
        dW = function (t, i, n, e, s) {
            function h(t, i, n, e, s, r, a) {
                var o, f = t[Hh];
                t.forEach(function (t, h) {
                    var u = t.v;
                    u[md] = r,
                    e || i[Yh](n, u, t._from, a, h, f),
                    Fh(u, function (t) {
                        o || (o = []),
                        t._marker = r,
                        o[tr]({
                            v: t,
                            _from: u
                        })
                    }, u, s, r, n)
                }),
                o && h(o, i, n, e, s, r, a + 1),
                e && t[Vf](function (t, e) {
                    i[Yh](n, t.v, t[Eq], a, e, f)
                })
            }
            h([{
                v: t
            }], i, n, e, s, {}, 0)
        };
    jz[xq] = X,
    jz.log = ti,
    jz[Gr] = ni,
    jz[pq] = ii,
    jz[wq] = Jq,
    jz.isOpera = Zq,
    jz[Tq] = iG,
    jz[kq] = nG,
    jz[Oq] = eG,
    jz[jq] = hG,
    jz[Mq] = sG,
    jz.isMac = rG,
    jz[Sq] = nU,
    jz[Iq] = _G,
    jz[ED] = qY,
    jz.Consts = Mz,
    jz[Aq] = lH,
    jz.Graph = eU,
    jz[Lq] = $Y,
    jz[Cq] = JY,
    jz[Dq] = bs,
    jz[iP] = vs,
    jz[Rq] = tU,
    jz.ImageUI = QY,
    jz.Shapes = NY,
    jz[Pq] = cH,
    jz[SL] = Vz,
    jz[Nq] = Ch,
    jz.Element = CY,
    jz[xC] = RY,
    jz.Edge = DY,
    jz[Bq] = as,
    jz[$q] = eW,
    jz[Fq] = UU,
    jz[ur] = qq;
    var lW = Gq;
    return jz.version = zq,
    jz[Ip] = Hq,
    jz[Yq] = "Copyright © 2017 Qingpu.com",
    jz.css = li,
    jz[Uq] = sU,
    ti = function () {},
    jz[Wq] = Vq,
    jz
}(window, document);