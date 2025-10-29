import React from 'react';
import clsx from 'clsx';

const Select = ({ label, id, name, options, className, error, ...props }) => {
  const baseStyles = "block w-full px-3 py-2 border rounded-md shadow-sm focus:outline-none sm:text-sm";
  const errorStyles = "border-red-500 focus:ring-red-500 focus:border-red-500";
  const defaultStyles = "border-gray-300 focus:ring-green-500 focus:border-green-500";

  return (
    <div>
      {label && (
        <label htmlFor={id} className="block text-sm font-medium text-gray-700">
          {label}
        </label>
      )}
      <select
        id={id}
        name={name}
        className={clsx(baseStyles, error ? errorStyles : defaultStyles, className)}
        {...props}
      >
        {options.map((option) => (
          <option key={option.value} value={option.value}>
            {option.label}
          </option>
        ))}
      </select>
      {error && <p className="mt-1 text-sm text-red-600">{error}</p>}
    </div>
  );
};

export default Select;
