import type { FormEvent } from 'react'
import type { PaymentMethod } from '../types/api'
import type { DistributionPoint } from '../types/distributionPoint'

export type CheckoutValues = { fullName: string; phone: string; distributionPointId: string; paymentMethod: PaymentMethod }
type CheckoutFormProps = { values: CheckoutValues; distributionPoints: DistributionPoint[]; total: number; submitting: boolean; error: string; onChange: (values: CheckoutValues) => void; onSubmit: () => void; onBack: () => void }

export function CheckoutForm({ values, distributionPoints, total, submitting, error, onChange, onSubmit, onBack }: CheckoutFormProps) {
  function submit(event: FormEvent<HTMLFormElement>) { event.preventDefault(); onSubmit() }
  return <form className="checkout-form" onSubmit={submit}>
    <div className="checkout-form__intro"><p className="eyebrow">שלב 3 מתוך 3</p><h2>פרטי איסוף ותשלום</h2><p>ההזמנה נבדקת ונשמרת בשרת לפני שיוצג אישור.</p></div>
    <div className="form-grid"><label>שם מלא<input required autoComplete="name" value={values.fullName} onChange={(event) => onChange({ ...values, fullName: event.target.value })} /></label><label>מספר טלפון<input required type="tel" inputMode="tel" autoComplete="tel" value={values.phone} onChange={(event) => onChange({ ...values, phone: event.target.value })} /></label><label className="form-grid__full">נקודת חלוקה<select required value={values.distributionPointId} onChange={(event) => onChange({ ...values, distributionPointId: event.target.value })}><option value="">בחירת נקודת חלוקה</option>{distributionPoints.map((point) => <option value={point.id} key={point.id}>{point.locality} — {point.name} · {point.details}</option>)}</select></label></div>
    <fieldset className="payment-choice"><legend>אמצעי תשלום</legend><p>קישורי תשלום טרם הוגדרו. הבחירה נשמרת בהזמנה בלבד.</p><label><input type="radio" name="payment" checked={values.paymentMethod === 'NONE'} onChange={() => onChange({ ...values, paymentMethod: 'NONE' })} /> תיאום תשלום לאחר ההזמנה</label><label><input type="radio" name="payment" checked={values.paymentMethod === 'BIT'} onChange={() => onChange({ ...values, paymentMethod: 'BIT' })} /> Bit <span className="payment-choice__placeholder">קישור יתווסף בקרוב</span></label><label><input type="radio" name="payment" checked={values.paymentMethod === 'PAYBOX'} onChange={() => onChange({ ...values, paymentMethod: 'PAYBOX' })} /> PayBox <span className="payment-choice__placeholder">קישור יתווסף בקרוב</span></label></fieldset>
    {error && <p className="form-error" role="alert">{error}</p>}<div className="checkout-form__actions"><button className="button button--secondary" type="button" onClick={onBack} disabled={submitting}>חזרה לסל</button><button className="button button--primary" type="submit" disabled={submitting}>{submitting ? 'שומר הזמנה…' : `אישור הזמנה · ₪${total}`}</button></div>
  </form>
}
