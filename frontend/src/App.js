import React, { useState, useEffect } from 'react';
import './App.css';

function App() {
  const [principal, setPrincipal] = useState('');
  const [interestRate, setInterestRate] = useState('');
  const [tenure, setTenure] = useState('');
  const [emi, setEmi] = useState(null);
  const [history, setHistory] = useState([]);
  const [amortizationSchedule, setAmortizationSchedule] = useState([]);

  const calculateEmi = async () => {
    const response = await fetch('/api/loan/calculate', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({ principal, interestRate, tenure }),
    });
    const data = await response.json();
    setEmi(data.emi);
    fetchHistory();
    fetchAmortizationSchedule(principal, interestRate, tenure);
  };

  const fetchHistory = async () => {
    const response = await fetch('/api/loan/history');
    const data = await response.json();
    setHistory(data);
  };

  const fetchAmortizationSchedule = async (principal, interestRate, tenure) => {
    const response = await fetch(`/api/loan/amortization?principal=${principal}&interestRate=${interestRate}&tenure=${tenure}`);
    const data = await response.json();
    setAmortizationSchedule(data);
  };

  const handleDownloadPdf = async () => {
    const response = await fetch(`/api/loan/amortization/pdf?principal=${principal}&interestRate=${interestRate}&tenure=${tenure}`);
    const blob = await response.blob();
    const url = window.URL.createObjectURL(new Blob([blob]));
    const link = document.createElement('a');
    link.href = url;
    link.setAttribute('download', 'amortization_schedule.pdf');
    document.body.appendChild(link);
    link.click();
    link.parentNode.removeChild(link);
  };

  useEffect(() => {
    fetchHistory();
  }, []);

  return (
    <div className="App">
      <header className="App-header">
        <h1>Loan EMI Calculator</h1>
        <div className="form">
          <input type="number" placeholder="Principal Amount" value={principal} onChange={(e) => setPrincipal(e.target.value)} />
          <input type="number" placeholder="Interest Rate (%)" value={interestRate} onChange={(e) => setInterestRate(e.target.value)} />
          <input type="number" placeholder="Tenure (Months)" value={tenure} onChange={(e) => setTenure(e.target.value)} />
          <button onClick={calculateEmi}>Calculate EMI</button>
        </div>
        {emi && <h2>EMI: {emi.toFixed(2)}</h2>}

        {amortizationSchedule.length > 0 && (
          <div className="amortization-schedule">
            <h2>Amortization Schedule</h2>
            <button onClick={handleDownloadPdf}>Download PDF</button>
            <table>
              <thead>
                <tr>
                  <th>Month</th>
                  <th>Starting Balance</th>
                  <th>Monthly Payment</th>
                  <th>Interest Paid</th>
                  <th>Principal Paid</th>
                  <th>Ending Balance</th>
                </tr>
              </thead>
              <tbody>
                {amortizationSchedule.map((entry) => (
                  <tr key={entry.monthNumber}>
                    <td>{entry.monthNumber}</td>
                    <td>{entry.startingBalance.toFixed(2)}</td>
                    <td>{entry.monthlyPayment.toFixed(2)}</td>
                    <td>{entry.interestPaid.toFixed(2)}</td>
                    <td>{entry.principalPaid.toFixed(2)}</td>
                    <td>{entry.endingBalance.toFixed(2)}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        )}

        <div className="history">
          <h2>Calculation History</h2>
          <table>
            <thead>
              <tr>
                <th>Principal</th>
                <th>Interest Rate</th>
                <th>Tenure</th>
                <th>EMI</th>
              </tr>
            </thead>
            <tbody>
              {history.map((item) => (
                <tr key={item.id}>
                  <td>{item.principal}</td>
                  <td>{item.interestRate}</td>
                  <td>{item.tenure}</td>
                  <td>{item.emi.toFixed(2)}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </header>
    </div>
  );
}

export default App;
