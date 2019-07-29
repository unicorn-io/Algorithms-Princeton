clc; clear;

fprintf("Loading Manipal recommender set....\n\n");

places = ["End Point", "Manipal Lake", "Inox Cinemas", "kapu beach", "agumbe", "chill space", "malpe", "Manipal Museum"];

[Y, TXT, RAW] = xlsread("Sample Data Collection Form (Responses).xlsx");
Y = Y';
R = Y > 0;

imagesc(Y);
ylabel("Places");
xlabel("Users");

fprintf('\nProgram paused. Press enter to continue.\n');
pause;

%===================================================================================%

% Useful Values
[num_places, num_users] = size(Y);
num_features = 5;

load("X_vector");
load("Theta_vector");

initial_parameters = [X(:); Theta(:)];

% options for fmincg
options = optimset('GradObj', 'on', 'MaxIter', 100);

% Set Regularization
lambda = 10;
theta = fmincg (@(t)(cofiCostFunc(t, Y, R, num_users, num_places, ...
                                num_features, lambda)), ...
                initial_parameters, options);
% Unfold the returned theta back into U and W
X = reshape(theta(1:num_places*num_features), num_places, num_features);
Theta = reshape(theta(num_places*num_features+1:end), ...
                num_users, num_features);

fprintf('Recommender system learning completed.\n');

fprintf('\nProgram paused. Press enter to continue.\n');
pause;

p = X * Theta';
my_predictions = p(:,1) + Y;

movieList = loadMovieList();

[r, ix] = sort(my_predictions, 'descend');
fprintf('\nTop recommendations for you:\n');
for i=1:8
    j = ix(i);
    fprintf('Predicting rating %.1f for place %s\n', my_predictions(j), ...
            places(j));
end

fprintf('\n\nOriginal ratings provided:\n');
for i = 1:length(my_ratings)
    if my_ratings(i) > 0 
        fprintf('Rated %d for %s\n', my_ratings(i), ...
                 places(i));
    end
end



