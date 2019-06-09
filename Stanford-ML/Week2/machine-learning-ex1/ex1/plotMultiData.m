data = load('ex1data2.txt');
X =  data(:,1:2);
y = data(:,3);

figure;

plot(X, y, 'rx', 'MarkerSize', 10);
ylabel('Profit in $10,000s');
xlabel('Population of City in 10,000s');

m = length(X);
X = [ones(m, 1), data(:,1:2)];
alpha = 0.1;
theta = zeros(3, 1);
iterations = 500;

X_norm = featureNormalize(X)
theta = normalEqn(X, y);

hold on; % keep previous plot visible
fplot(poly2sym(theta));
legend('Training data', 'Linear regression', 'Location','southeast')
hold off % don't overlay any more plots on this figure

