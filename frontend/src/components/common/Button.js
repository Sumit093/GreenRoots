import React from 'react';
import clsx from 'clsx';

const Button = ({ children, className, variant = 'primary', ...props }) => {
  const baseStyles = "px-4 py-2 rounded-md font-medium transition duration-300";
  
  const variants = {
    primary: "bg-green-600 text-white hover:bg-green-700",
    secondary: "bg-gray-200 text-gray-800 hover:bg-gray-300",
    outline: "border border-green-600 text-green-600 hover:bg-green-50",
    danger: "bg-red-600 text-white hover:bg-red-700",
  };

  return (
    <button className={clsx(baseStyles, variants[variant], className)} {...props}>
      {children}
    </button>
  );
};

export default Button;
