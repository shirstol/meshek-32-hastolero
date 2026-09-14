import { useEffect, useState, type FormEvent } from 'react'
import { AdminDashboard } from './components/AdminDashboard'
import { Cart } from './components/Cart'
import { CheckoutForm, type CheckoutValues } from './components/CheckoutForm'
import { ProductCard } from './components/ProductCard'
import { distributionPoints as examplePoints } from './data/distributionPoints'
import { products as exampleProducts } from './data/products'
import { api } from './services/api'
import type { Order, StoreCatalog } from './types/api'
import type { CartItem } from './types/cart'
import type { DistributionPoint } from './types/distributionPoint'
import type { Product } from './types/product'
import './App.css'

type Screen = 'welcome' | 'guest' | 'catalog' | 'checkout' | 'confirmation' | 'login' | 'adminLogin' | 'admin'
const emptyCheckout: CheckoutValues = { fullName: '', phone: '', distributionPointId: '', paymentMethod: 'NONE' }
const storeSlug = window.location.pathname.split('/')[2] || 'zippori-store'
const isAdminRoute = window.location.pathname.startsWith('/admin')

function App() {
  const [screen, setScreen] = useState<Screen>(isAdminRoute ? 'adminLogin' : 'welcome')
  const [selectedCategory, setSelectedCategory] = useState<string | null>(null)
  const [cartItems, setCartItems] = useState<CartItem[]>([])
  const [checkout, setCheckout] = useState<CheckoutValues>(emptyCheckout)
  const [products, setProducts] = useState<Product[]>(exampleProducts)
  const [points, setPoints] = useState<DistributionPoint[]>(examplePoints)
  const [store, setStore] = useState<StoreCatalog['store'] | null>(null)
  const [createdOrder, setCreatedOrder] = useState<Order | null>(null)
  const [error, setError] = useState('')
  const [submitting, setSubmitting] = useState(false)
  const [adminCredentials, setAdminCredentials] = useState('')
  const total = cartItems.reduce((sum, item) => sum + item.product.price * item.quantity, 0)
  const totalQuantity = cartItems.reduce((sum, item) => sum + item.quantity, 0)

  useEffect(() => { void loadCatalog() }, [])
  async function loadCatalog() { try { const catalog = await api.store(storeSlug); setProducts(catalog.products); setStore(catalog.store); setPoints([{ id: catalog.store.id, slug: catalog.store.slug, locality: catalog.store.locality, name: catalog.store.name, details: catalog.store.details, aboutText: catalog.store.aboutText }]) } catch { setError('השרת לא זמין כרגע. מוצגים נתוני הדוגמה המקומיים.') } }
  function addToCart(product: Product) { setCartItems((items) => { const existing = items.find((item) => item.product.id === product.id); if (!existing) return [...items, { product, quantity: 1 }]; if (product.maxQuantity !== null && existing.quantity >= product.maxQuantity) return items; return items.map((item) => item.product.id === product.id ? { ...item, quantity: item.quantity + 1 } : item) }) }
  function decreaseQuantity(productId: string) { setCartItems((items) => items.map((item) => item.product.id === productId ? { ...item, quantity: item.quantity - 1 } : item).filter((item) => item.quantity > 0)) }
  function increaseQuantity(productId: string) { const item = cartItems.find((currentItem) => currentItem.product.id === productId); if (item) addToCart(item.product) }
  async function submitGuest(event: FormEvent<HTMLFormElement>) { event.preventDefault(); setSubmitting(true); setError(''); try { await api.identifyCustomer(checkout.fullName, checkout.phone); setSelectedCategory(null); setScreen('catalog') } catch { setError('לא ניתן לשמור את פרטי הלקוח. ודאי שהשרת פעיל ונסי שוב.') } finally { setSubmitting(false) } }
  async function confirmOrder() { setSubmitting(true); setError(''); try { const order = await api.createOrder({ fullName: checkout.fullName, phone: checkout.phone, distributionPointId: checkout.distributionPointId, items: cartItems.map((item) => ({ productId: item.product.id, quantity: item.quantity })), paymentMethod: checkout.paymentMethod }); setCreatedOrder(order); setScreen('confirmation') } catch { setError('ההזמנה לא נשמרה. בדקי שהמוצרים והפרטים תקינים ונסי שוב.') } finally { setSubmitting(false) } }
  function startAgain() { setCartItems([]); setCheckout(emptyCheckout); setCreatedOrder(null); setScreen('welcome') }

  if (screen === 'welcome') return <main className="welcome"><div className="welcome__leaf" aria-hidden="true">🥬</div><p className="eyebrow">משק משפחתי · חסטולרו</p><h1>{store?.name ?? 'איסוף מהחנות שלנו'}<br />במושב ציפורי.</h1><p className="welcome__description">חסות ועלים ירוקים שנקטפים אצלנו בחממה ומגיעים אליכם טריים.</p><div className="welcome__actions"><button className="button button--primary" onClick={() => setScreen('guest')}>להזמנה כאורחת</button><button className="button button--secondary" onClick={() => setScreen('login')}>כניסה לחשבון</button></div>{store?.aboutText && <section className="about-us"><img src="/images/about-us.png" alt="החממה ההידרופונית של משק חסטולרו" /><div><h2>קצת עלינו</h2>{store.aboutText.split('\n').filter(Boolean).map((paragraph) => <p key={paragraph}>{paragraph}</p>)}</div></section>}<a className="manager-link" href="/admin">כניסת מנהל</a></main>

  if (screen === 'guest') return <main className="centered-page"><section className="entry-card"><button className="back-link" onClick={() => setScreen('welcome')}>← חזרה</button><p className="eyebrow">שלב 1 מתוך 3</p><h1>נעים להכיר</h1><p>השם והטלפון נשמרים במאגר הלקוחות כדי לשייך את ההזמנה שלך.</p><form className="entry-card__form" onSubmit={submitGuest}><label>שם מלא<input required autoComplete="name" value={checkout.fullName} onChange={(event) => setCheckout({ ...checkout, fullName: event.target.value })} /></label><label>מספר טלפון<input required type="tel" inputMode="tel" autoComplete="tel" value={checkout.phone} onChange={(event) => setCheckout({ ...checkout, phone: event.target.value })} /></label>{error && <p className="form-error">{error}</p>}<button className="button button--primary button--wide" type="submit" disabled={submitting}>{submitting ? 'שומר פרטים…' : 'המשך לקטלוג'}</button></form></section></main>

  if (screen === 'login') return <main className="centered-page"><section className="entry-card"><button className="back-link" onClick={() => setScreen('welcome')}>← חזרה</button><p className="eyebrow">חשבון לקוח</p><h1>התחברות עם קוד SMS — בקרוב</h1><p>שם וטלפון לבד אינם אימות מאובטח. נוסיף קוד חד־פעמי ב־SMS כשהמערכת תעלה לאוויר. בינתיים אפשר לבצע הזמנה כאורחת.</p><button className="button button--primary button--wide" onClick={() => setScreen('guest')}>להזמנה כאורחת</button></section></main>

  if (screen === 'adminLogin') return <AdminLogin onBack={() => { window.location.href = `/store/${storeSlug}` }} onLogin={(credentials) => { setAdminCredentials(credentials); setScreen('admin') }} />
  if (screen === 'admin') return <AdminDashboard credentials={adminCredentials} points={points} products={products} storeSlug={storeSlug} onProductAdded={() => void loadCatalog()} onLogout={() => { setAdminCredentials(''); setScreen('adminLogin') }} />

  if (screen === 'confirmation' && createdOrder) return <main className="centered-page"><section className="confirmation-card"><div className="confirmation-card__mark">✓</div><p className="eyebrow">ההזמנה נשמרה</p><h1>תודה, {createdOrder.customer.fullName}!</h1><p>מספר ההזמנה שלך הוא <strong>{createdOrder.orderNumber}</strong></p><div className="confirmation-card__details"><span>איסוף</span><strong>{createdOrder.distributionPoint.name}</strong><span>{createdOrder.distributionPoint.details}</span><span>סכום: ₪{createdOrder.total}</span></div><p className="confirmation-card__notice">ההזמנה נשמרה במערכת. אישור תשלום ב־Bit או PayBox יתווסף רק לאחר חיבור לספק תשלום רשמי.</p><button className="button button--primary" onClick={startAgain}>חזרה לדף הבית</button></section></main>

  if (screen === 'checkout') return <main><AppHeader name={checkout.fullName} quantity={totalQuantity} onHome={() => setScreen('catalog')} /><div className="checkout-layout"><CheckoutForm values={checkout} distributionPoints={points} total={total} submitting={submitting} error={error} onChange={setCheckout} onBack={() => { setError(''); setScreen('catalog') }} onSubmit={() => void confirmOrder()} /><Cart items={cartItems} total={total} onIncrease={increaseQuantity} onDecrease={decreaseQuantity} onContinue={() => undefined} /></div></main>

  const categories = [...new Set(products.map((product) => product.category || 'מוצרים נוספים'))]
  const categoryImage = (category: string) => category === 'חסות ועלים ירוקים' ? '/images/salnova.png' : '/images/lemon.png'

  if (selectedCategory === null) return <main><AppHeader name={checkout.fullName} quantity={totalQuantity} onHome={() => setScreen('welcome')} /><section className="category-picker"><p className="eyebrow">שלב 2 מתוך 3 · {store?.locality ?? 'מושב ציפורי'}</p><h1>מה תרצי להזמין?</h1><p>בחרי קטגוריה כדי לראות את המוצרים הזמינים בחנות הזו.</p><div className="category-picker__buttons">{categories.map((category) => <button key={category} className="category-picker__button" onClick={() => setSelectedCategory(category)}><img src={categoryImage(category)} alt="" /><span>{category}</span><small>לצפייה במוצרים ←</small></button>)}</div></section></main>

  return <main><AppHeader name={checkout.fullName} quantity={totalQuantity} onHome={() => setScreen('welcome')} /><section className="catalog-page"><button className="back-link catalog-back" onClick={() => setSelectedCategory(null)}>→ חזרה לבחירת קטגוריה</button><div className="catalog-intro"><p className="eyebrow">שלב 2 מתוך 3 · {store?.locality ?? 'מושב ציפורי'}</p><h1>{selectedCategory}</h1><p>{store?.details ?? 'איסוף מהחנות שלנו במושב ציפורי'} · המחירים והזמינות שייכים לחנות הזאת בלבד.</p></div>{error && <p className="form-error">{error}</p>}<div className="catalog-layout"><div className="product-grid">{products.filter((product) => (product.category || 'מוצרים נוספים') === selectedCategory).map((product) => <ProductCard key={product.id} product={product} quantityInCart={cartItems.find((item) => item.product.id === product.id)?.quantity ?? 0} onAddToCart={addToCart} />)}</div><Cart items={cartItems} total={total} onIncrease={increaseQuantity} onDecrease={decreaseQuantity} onContinue={() => { setError(''); setScreen('checkout') }} /></div></section></main>
}

function AdminLogin({ onBack, onLogin }: { onBack: () => void; onLogin: (credentials: string) => void }) { const [username, setUsername] = useState(''); const [password, setPassword] = useState(''); function submit(event: FormEvent<HTMLFormElement>) { event.preventDefault(); onLogin(btoa(`${username}:${password}`)) } return <main className="centered-page"><section className="entry-card"><button className="back-link" onClick={onBack}>← חזרה</button><p className="eyebrow">גישה מאובטחת</p><h1>כניסת מנהל</h1><p>הכניסה מאומתת מול שרת המערכת. אין לשמור את הסיסמה בדפדפן ציבורי.</p><form className="entry-card__form" onSubmit={submit}><label>שם משתמש<input required value={username} onChange={(e) => setUsername(e.target.value)} autoComplete="username" /></label><label>סיסמה<input required type="password" value={password} onChange={(e) => setPassword(e.target.value)} autoComplete="current-password" /></label><button className="button button--primary button--wide" type="submit">כניסה לניהול</button></form></section></main> }
function AppHeader({ name, quantity, onHome }: { name: string; quantity: number; onHome: () => void }) { return <header className="app-header"><button className="brand" onClick={onHome}>משק 32 <span>חסטולרו</span></button><div><span>שלום, {name}</span><span className="app-header__cart">סל: {quantity}</span></div></header> }
export default App
