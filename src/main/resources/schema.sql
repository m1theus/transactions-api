CREATE TABLE accounts (
      account_id BIGSERIAL PRIMARY KEY,
      document_number VARCHAR(11) NOT NULL
);

CREATE TABLE transactions (
      transaction_id BIGSERIAL PRIMARY KEY,
      account_id BIGINT NOT NULL,
      operation_type INT NOT NULL,
      amount DECIMAL(15, 2) NOT NULL,
      balance DECIMAL(15, 2) NOT NULL,
      event_date TIMESTAMP NOT NULL DEFAULT NOW(),

      CONSTRAINT fk_transaction_account FOREIGN KEY (account_id)
          REFERENCES accounts(account_id)
);
